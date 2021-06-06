package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.util.Locale;


public class SolarSystem extends Application {

    private double x = 0;
    private double y = 0;
    private double X = 0;
    private double Y = 0;
    private static final double SIZE = 300;
    private Content contentForNewScene;
    private Content content;

    private static final class Content {

        private final Group firstGroupForPlanets = new Group();
        private final Group groupForMain = new Group();
        private final Group groupNewScene = new Group();
        private final Group groupInNewScene = new Group();
        private final Group groupMini = new Group();
        private final Group groupForEarth = new Group();
        private final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        private final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        private final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        private final Shape circleForMercury;
        private final Sphere sphereForMercury;
        private final Shape circleForVenus;
        private final Sphere sphereForVenus;
        private final Shape circleForEarth;
        private final Sphere sphereForEarth;
        private final Shape circleEarthMoon;
        private final Sphere sphereEarthMoon;
        private final Shape circleForMars;
        private final Sphere sphereForMars;
        private final Shape circleForJupiter;
        private final Sphere sphereForJupiter;
        private final Shape circleForSaturn;
        private final Sphere sphereForSaturn;
        private final Shape circleForUranus;
        private final Sphere sphereForUranus;
        private final Shape circleForNeptune;
        private final Sphere sphereForNeptune;
        private final Sphere sun;
        private final Shape shadow;
        private final Button button;
        private final Animation animation;
        private final Animation animationForEarth;

        private Content(double size) {
            circleForMercury = createCircle(size * 0.8);
            sphereForMercury = createShape(5, "file:mercury.jpg", "МЕРКУРИЙ");
            circleForVenus = createCircle(size * 1.05);
            sphereForVenus = createShape(7, "file:venus.jpg", "ВЕНЕРА");
            circleForEarth = createCircle(size * 1.35);
            sphereForEarth = createShape(8, "file:earth.jpg", "ЗЕМЛЯ");
            circleEarthMoon = createEllipse(size * 0.19);
            sphereEarthMoon = createShape(4, "file:moon.jpg", "ЛУНА");
            circleForMars = createCircle(size * 1.75);
            sphereForMars = createShape(7, "file:mars.jpg", "МАРС");
            circleForJupiter = createCircle(size * 2.05);
            sphereForJupiter = createShape(18, "file:jupiter.jpg", "ЮПИТЕР");
            circleForSaturn = createCircle(size * 2.45);
            sphereForSaturn = createShape(16, "file:saturn.jpg", "САТУРН");
            circleForUranus = createCircle(size * 2.9);
            sphereForUranus = createShape(12, "file:uranus.jpg", "УРАН");
            circleForNeptune = createCircle(size * 3.15);
            sphereForNeptune = createShape(9, "file:neptune.jpg", "НЕПТУН");
            sun = createMainShape();
            shadow = createShadowCircle(size / 2);
            button = new Button("СТАРТ");
            button.setLayoutX(170);
            button.setLayoutY(190);
            Rotate rxGroup = new Rotate(0, Rotate.X_AXIS);
            rxGroup.setAngle(30);
            groupMini.getChildren().addAll(sphereForSaturn, createSaturnEllipse(14.3), createSaturnEllipse(13.5), createSaturnEllipse(12));
            groupMini.getTransforms().addAll(rxGroup);

            groupForEarth.getChildren().addAll(sphereForEarth, circleEarthMoon, sphereEarthMoon);
            groupForEarth.getTransforms().addAll(rxGroup);

            animation = new ParallelTransition(
                    createTransition(circleForMercury, sphereForMercury, Duration.seconds(5)),
                    createTransition(circleForVenus, sphereForVenus, Duration.seconds(7)),
                    createTransitionGroup(circleForEarth, groupForEarth, Duration.seconds(17)),
                    createTransition(circleForMars, sphereForMars, Duration.seconds(25)),
                    createTransition(circleForJupiter, sphereForJupiter, Duration.seconds(30)),
                    createTransitionGroup(circleForSaturn, groupMini, Duration.seconds(50)),
                    createTransition(circleForUranus, sphereForUranus, Duration.seconds(65)),
                    createTransition(circleForNeptune, sphereForNeptune, Duration.seconds(75))
            );
            animationForEarth = new ParallelTransition(
                    createTransition(circleEarthMoon, sphereEarthMoon, Duration.seconds(5))
            );
        }

        public void addMouseScrolling(Node node) {
            node.setOnScroll((ScrollEvent event) -> {
                double zoomFactor = 1.05;
                double deltaY = event.getDeltaY();
                if (deltaY < 0) {
                    zoomFactor = 2.0 - zoomFactor;
                }
                node.setScaleX(node.getScaleX() * zoomFactor);
                node.setScaleY(node.getScaleX() * zoomFactor);
                event.consume();
            });
        }

        private Circle createCircle(double size) {
            Circle c = new Circle(size/4);
            c.setFill(Color.TRANSPARENT);
            c.setStroke(Color.GRAY);
            return c;
        }

        private Ellipse createSaturnEllipse(double size) {
            Ellipse c = new Ellipse(1.7 * size, 1.3 * size);
            c.setFill(Color.TRANSPARENT);
            c.setRotate(30);
            //c.setStroke(Color.rgb(208, 246, 14, 0.31));
            c.setStroke(Color.LIGHTYELLOW);
            c.setStrokeWidth(1.75);
            return c;
        }

        private Ellipse createEllipse(double size) {
            Ellipse c = new Ellipse();
            c.setRadiusX(size / 4);
            c.setRadiusY(size / 4.5);
            c.setFill(Color.TRANSPARENT);
            c.setStroke(Color.GRAY);
            return c;
        }

        private Circle createShadowCircle(double size) {
            Circle circle = new Circle(size / 6);
            Shadow shadow = new Shadow(80, Color.YELLOW);
            circle.setEffect(shadow);
            return circle;
        }

        private Sphere createShape(int radius, String imageUrl, String planet) {
            Sphere s = new Sphere(radius);
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseMap(new Image(imageUrl));
            s.setMaterial(material);
            s.setEffect(new Bloom());
            s.setOnMouseEntered((final MouseEvent e) -> {
                Tooltip tooltip = new Tooltip(planet);
                tooltip.setShowDelay(Duration.millis(10));
                Tooltip.install(s, tooltip);
            });
            return s;
        }

        private Sphere createMainShape() {
            PhongMaterial whiteMaterial = new PhongMaterial();
            whiteMaterial.setDiffuseColor(Color.rgb(252, 204, 80));
            //whiteMaterial.setDiffuseMap(new Image("file:sun.jpg"));
            Sphere s = new Sphere(20);
            s.setMaterial(whiteMaterial);
            s.setEffect(new Bloom());
            s.setOnMouseEntered((final MouseEvent e) -> {
                Tooltip tooltip = new Tooltip("СОЛНЦЕ");
                tooltip.setShowDelay(Duration.millis(10));
                Tooltip.install(s, tooltip);
            });
            return s;
        }

        private Transition createTransition(Shape path, Sphere node, Duration duration) {
            PathTransition t = new PathTransition(duration, path, node);
            t.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t.setCycleCount(Timeline.INDEFINITE);
            t.setInterpolator(Interpolator.LINEAR);
            return t;
        }

        private Transition createTransitionGroup(Shape path, Group node, Duration duration) {
            PathTransition t = new PathTransition(duration, path, node);
            t.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            t.setCycleCount(Timeline.INDEFINITE);
            t.setInterpolator(Interpolator.LINEAR);
            return t;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        startTheGame(primaryStage);
    }

    void startTheGame(Stage primaryStage) {
        //Locale.setDefault(new Locale("ru"));
        //Content content = Content.create(SIZE);
        content = new Content(SIZE);
        content.firstGroupForPlanets.getChildren().addAll(content.sphereForNeptune, content.circleForNeptune, content.sphereForUranus, content.circleForUranus, content.groupMini,
                content.circleForSaturn, content.sphereForJupiter, content.circleForJupiter, content.sphereForMars, content.circleForMars, content.groupForEarth, content.circleForEarth,
                content.sphereForVenus, content.circleForVenus, content.sphereForMercury, content.circleForMercury, content.sun, content.shadow);
        content.firstGroupForPlanets.getTransforms().addAll(content.rz, content.ry, content.rx);
        //c.addMouseScrolling(c.group);
        content.groupForMain.getChildren().addAll(content.firstGroupForPlanets, content.button);
        content.rx.setAngle(-1);
        content.ry.setAngle(1);


        contentForNewScene = new Content(SIZE);
        primaryStage.setTitle("Solar System");
        primaryStage.getIcons().add(new Image(
                "https://www.pngix.com/pngfile/middle/60-608017_planet-earth-free-png-image-earth-png-transparent.png"
        ));


        content.addMouseScrolling(content.firstGroupForPlanets);
        Scene scene = new Scene(content.groupForMain, 600, 600, true);
        primaryStage.setScene(scene);
        scene.setFill(Color.rgb(3, 10, 26));

        //rotateNode(scene, content.rx, content.ry);
        rotateNode(content.firstGroupForPlanets, content.rx, content.ry);
        content.button.setOnAction(rotation -> {
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UNDECORATED);

            contentForNewScene.addMouseScrolling(contentForNewScene.firstGroupForPlanets);

            contentForNewScene.groupNewScene.getChildren().addAll(contentForNewScene.sphereForNeptune, contentForNewScene.circleForNeptune,
                    contentForNewScene.sphereForUranus, contentForNewScene.circleForUranus,
                    contentForNewScene.groupMini, contentForNewScene.circleForSaturn, contentForNewScene.sphereForJupiter,
                    contentForNewScene.circleForJupiter, contentForNewScene.sphereForMars,
                    contentForNewScene.circleForMars, contentForNewScene.groupForEarth, contentForNewScene.circleForEarth, contentForNewScene.sphereForVenus,
                    contentForNewScene.circleForVenus, contentForNewScene.sphereForMercury, contentForNewScene.circleForMercury, contentForNewScene.sun, contentForNewScene.shadow);
            contentForNewScene.groupNewScene.getTransforms().addAll(contentForNewScene.rz, contentForNewScene.ry, contentForNewScene.rx);
            Button backButton = new Button("НАЗАД");
            backButton.setStyle("-fx-text-base-color: white;");
            backButton.setStyle("-fx-background-color: powderblue;");
            backButton.setLayoutX(230);
            backButton.setLayoutY(250);
            backButton.setOnAction((final ActionEvent event) -> {
               /* @Override
                public void handle(ActionEvent event) {*/
                Stage stageForBack = new Stage();
                startTheGame(stageForBack);
                newStage.close();
                primaryStage.close();
                //}
            });

            Label labelInfo = new Label("Нажмите на планету, чтобы изучить");
            labelInfo.setLayoutX(-295);
            labelInfo.setLayoutY(-280);
            labelInfo.setTextFill(Color.LIGHTGRAY);
            labelInfo.setFont(Font.font("Arial"));

            Button animaButton = new Button("ВОСПРОИЗВЕСТИ");
            animaButton.setStyle("-fx-text-base-color: white;");
            animaButton.setStyle("-fx-background-color: powderblue;");
            animaButton.setLayoutX(100);
            animaButton.setLayoutY(250);
            animaButton.setOnAction((final ActionEvent event) -> {
                /*@Override
                public void handle(ActionEvent event) {*/
                contentForNewScene.animation.play();
                contentForNewScene.animationForEarth.play();
                //}
            });
            Button animaStopButton = new Button("ПАУЗА");
            animaStopButton.setStyle("-fx-text-base-color: white;");
            animaStopButton.setStyle("-fx-background-color: powderblue;");
            animaStopButton.setLayoutX(33);
            animaStopButton.setLayoutY(250);
            animaStopButton.setOnAction((final ActionEvent event) -> {
               /* @Override
                public void handle(ActionEvent event) {*/
                contentForNewScene.animation.pause();
                contentForNewScene.animationForEarth.pause();
                //}
            });

            contentForNewScene.groupInNewScene.getChildren().addAll(contentForNewScene.groupNewScene, backButton, animaButton, animaStopButton, labelInfo);
            contentForNewScene.groupInNewScene.setTranslateX(300);
            contentForNewScene.groupInNewScene.setTranslateY(300);
            contentForNewScene.groupInNewScene.setTranslateZ(0);
            Scene scene2 = new Scene(contentForNewScene.groupInNewScene, 600, 598, true);

            contentForNewScene.sun.setOnMouseClicked((final MouseEvent event) -> {
                String information = "СОЛНЦЕ";
                String planets = "желтый карлик";

                String txt = "\n" + "Солнце - это звезда в центре Солнечной системы. Масса Солнца составляет 99,86% общей массы Солнечной системы. " +
                        "\n\nСОСТАВ\nПриблизительно три четверти массы Солнца составляет водород, остальное, в основном, " +
                        "приходится на гелий. Только 1.69% массы Солнца (что равняется 5628 масс Земли) составляют более тяжелые элементы, " +
                        "такие как углерод, железо, неон, кислород." +
                        "\n\nРАССТОЯНИЕ\nСреднее расстояние от Солнца до Земли примерно составляет 149.6 млн км (= 1 а.е.). " +
                        "В среднем, чтобы свету достичь Земли, требуется 8 минут и 19 секунд." +
                        "\n\nГАЛАКТИКА\nРаньше считалось, что Солнце - незначительная звезда, однако сейчас полагают, что она ярче 85% остальных звезд" +
                        " Млечного пути. Солнце располагается на расстоянии 2/3 от центра Млечного пути." +
                        "\n\nСТРОЕНИЕ\n\nФотосфера:\nФотосфера - это тонкий внешний слой Солнца.  Под фотосферой Солнце становится непрозрачным.  " +
                        "Однако сама фотосфера лишь немного менее непрозрачна, чем воздух на Земле.  " +
                        "\n\nКонвективная зона:\nТермики переносят горячий материал на поверхность Солнца.  " +
                        "Как только материал остынет, он снова погружается в зону горячего излучения." +
                        "\n\nЗона лучистого переноса:\nЗона лучистого переноса переносит интенсивное тепло ядра наружу в виде теплового излучения, " +
                        "при этом материал быстро остывает, а плотность солнечного материала уменьшается в сотню раз от верха ядра к верху излучающей зоны.  " +
                        "\n\nЯдро:\n99% всей энергии, производимой Солнцем, исходит из 24% солнечного ядра.\n\nСОСТАВ ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("1.99 * 10^30 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("1.39 млн км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до центра галактики");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("26 038 св. лет");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("25 дней");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг центра галактики");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("225 млн лет");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения на экваторе");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("274 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("5 778 К");
                sixth2.setFont(new Font("Arial", 14));

                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);

                GridPane core = new GridPane();
                Label coreFirst1 = new Label("Фотосфера");
                Label coreFirst2 = new Label("100-400 км");
                Label coreSecond1 = new Label("Конвективная зона");
                Label coreSecond2 = new Label("200 000 км");
                Label coreThird1 = new Label("Зона лучистого переноса");
                Label coreThird2 = new Label("345—973 тыс. км");
                Label coreFourth1 = new Label("Ядро");
                Label coreFourth2 = new Label("150—175 тыс. км");
                core.add(coreFirst1, 0, 0);
                core.add(new Label("    "), 1, 0);
                core.add(coreFirst2, 2, 0);
                core.add(coreSecond1, 0, 1);
                core.add(new Label("    "), 1, 1);
                core.add(coreSecond2, 2, 1);
                core.add(coreThird1, 0, 2);
                core.add(new Label("    "), 1, 2);
                core.add(coreThird2, 2, 2);
                core.add(coreFourth1, 0, 3);
                core.add(new Label("    "), 1, 3);
                core.add(coreFourth2, 2, 3);
                core.add(new Label("  "), 0, 4);

                showPlanet(primaryStage, scene2, txt, "file:8k_sun.jpg", "file:mercurystructure.jpg", information, planets, info, core);

            });

            contentForNewScene.sphereForMercury.setOnMouseClicked((final MouseEvent event) -> {
                String information = "МЕРКУРИЙ";
                String planets = "планета";

                String txt = "\n" +
                        "Меркурий - самая маленькая планета Солнечной системы. " +
                        "Поскольку у него нет атмосферы, чтобы сохранять тепло, eго поверхность испытывает самый большой перепад " +
                        "температур по сравнению с другими планетами.\n\nНАБЛЮДЕНИЕ\n" +
                        "На небосклоне Земли Меркурий виден как утренняя или вечерняя звезда, " +
                        "но из-за его близости к Солнцу его очень тяжело увидеть. " +
                        "И, тем не менее, его можно наблюдать 2 раза в год весной на закате и осенью перед рассветом.\n\nСТРОЕНИЕ\n" +
                        "Кора по внешнему виду похожа на лунную, с равнинами и тяжелыми кратерами.  " +
                        "Отличительной особенностью поверхностной коры является наличие множества узких гребней, " +
                        "которые могли образоваться, когда ядро и мантия Меркурия охлаждались и сжимались после того, как кора затвердела.\n\n" +
                        "Чрезвычайно высокая плотность Меркурия предполагает, что у планеты есть большое, богатое железом ядро " +
                        "с более высоким содержанием железа, чем у любой другой крупной планеты Солнечной системы." + "\n\nСОСТАВ ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("3.3 * 10^23 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("4 829 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("58 млн км");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("58 дней");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("88 дней");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("3.7 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("167 °C");
                sixth2.setFont(new Font("Arial", 14));
                Label eighth1 = new Label("Температура - день");
                sixth1.setFont(new Font("Arial", 14));
                Label eighth2 = new Label("473 °C");
                sixth2.setFont(new Font("Arial", 14));
                Label ninth1 = new Label("Температура - ночь");
                sixth1.setFont(new Font("Arial", 14));
                Label ninth2 = new Label("-183 °C");
                sixth2.setFont(new Font("Arial", 14));

                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);
                info.add(eighth1, 0, 7);
                info.add(new Label("    "), 1, 7);
                info.add(eighth2, 2, 7);
                info.add(ninth1, 0, 8);
                info.add(new Label("    "), 1, 8);
                info.add(ninth2, 2, 8);

                GridPane core = new GridPane();
                Label coreFirst1 = new Label("Кора");
                Label coreFirst2 = new Label("100-200 км");
                Label coreSecond1 = new Label("Мантия");
                Label coreSecond2 = new Label("600 км");
                Label coreThird1 = new Label("Ядро");
                Label coreThird2 = new Label("1800 км");
                core.add(coreFirst1, 0, 0);
                core.add(new Label("    "), 1, 0);
                core.add(coreFirst2, 2, 0);
                core.add(coreSecond1, 0, 1);
                core.add(new Label("    "), 1, 1);
                core.add(coreSecond2, 2, 1);
                core.add(coreThird1, 0, 2);
                core.add(new Label("    "), 1, 2);
                core.add(coreThird2, 2, 2);
                core.add(new Label("  "), 0, 3);

                showPlanet(primaryStage, scene2, txt, "file:mercury.jpg", "file:mercurystructure.jpg", information, planets, info, core);
            });

            contentForNewScene.sphereForVenus.setOnMouseClicked((final MouseEvent event) -> {
                String information = "ВЕНЕРА";
                String planets = "планета";

                String txt = "\n" +
                        "Хотя Венера подобна по размеру и внутренней структуре Земле, eе вулканическая поверхность, " +
                        "жаркая и плотная атмосфера, делают ее одним из самых негостеприимных мест Солнечной системы. " +
                        "\n\nAТМОСФЕРА\n Плотные облака серной кислоты и лыли отражают большую часть солнечного света в космос, " +
                        "в товремя как углекислый газ удерживает солнечное тепло внутри атмосферы, вызывая парниковый эффект. " +
                        "Атмосферное давление на поверхности планеты в 92 раза больше Земного.\n\nНАБЛЮДЕНИЕ\nВенера одно из самых ярких объектов ночного неба, " +
                        "по яркости уступает только Луне. Она появляется либо как утренняя либо как вечерняя звезда.\n\nСТРУКТУРА\n" +
                        "В настоящее время у нас мало прямой информации о внутреннем устройстве Венеры.  " +
                        "Однако его размер и плотность, близкие к Земле, позволяют предположить, что они имеют общую внутреннюю структуру.  " +
                        "Принципиальное различие между двумя планетами заключается в отсутствии тектоники плит на Венере, вероятно, " +
                        "из-за сухой поверхности и мантии.  Это приводит к уменьшению потерь тепла от планеты, предотвращая ее охлаждение и о" +
                        "беспечивая вероятное объяснение отсутствия у нее внутреннего магнитного поля.  " +
                        "Вместо этого Венера может терять свое внутреннее тепло в периодических крупных событиях обновления поверхности." +
                        " ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("4.9 * 10^24 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("12 104 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("108 млн км");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("243 дня");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("225 дней");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("8.9 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("462 °C");
                sixth2.setFont(new Font("Arial", 14));

                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);

                GridPane core = new GridPane();

                showPlanet(primaryStage, scene2, txt, "file:venus.jpg", "file:venusstructure.jpg", information, planets, info, core);

            });

            contentForNewScene.sphereForEarth.setOnMouseClicked((final MouseEvent event) -> {
                String information = "ЗЕМЛЯ";
                String planets = "планета";

                String txt = "\n" +
                        "Наша планета самая плотная из 8 планет Солнечной системы. " +
                        "Она таюже самая большая из четырех планет Земной группы. " +
                        "\n\nПОВЕРХНОСТЬ\n 71% Земной поверхности покрыт океанами, оставшиеся 30% заняты семью континентами. " +
                        "Наружная поверхность Земли делится на несколько тектонических плит, " +
                        "которые медленно передвигаются по поверхности в течении миллионов лет. \n\nНАКЛОН ОСИ\n" +
                        "Cегодня ось Земли наклонена на 23.4 градуса, что создает сезонные изменения в климате и погоде " +
                        "на всей поверхности Земли в течении года.\n\n" +
                        "МАГНИТНОЕ ПОЛЕ\nMагнитное поле Земли, генерируется простирается во все стороны, образуя магнитосферу - " +
                        "барьер окружающий Землю, захватывающий частицы солнечного ветра и защищающий Землю от солнечной радиации\n\n" +
                        "СТРУКТУРА\nЗемная кора вместе с самыми верхними частями мантии образует литосферу.  " +
                        "Литосфера разбита на серию тектонических плит, которые «плавают» на более упругой части верхней мантии. " +
                        "Движение этих плит вызывает землетрясения и вулканизм.\n\n" +
                        "МАНТИЯ\nМантия Земли представляет собой скалистую оболочку, на которую приходится 84% объема планеты.  " +
                        "Самые верхние области мантии самые твердые и относительно жесткие.\n\n" +
                        "ВНЕШНЕЕ ЯДРО\nРазница температур во внешнем ядре вызывает конвекционные токи, " +
                        "когда горячие жидкие металлы поднимаются, охлаждают, а затем опускаются обратно к горячему внутреннему ядру.  " +
                        "Эти конвекционные потоки приводят в действие магнитное поле Земли.\n\n" +
                        "ВНУТРЕННЕЕ ЯДРО\nХотя считается, что температура во внутреннем ядре примерно такая же, как и на поверхности Солнца, " +
                        "экстремальное давление не позволяет ему стать жидкостью." +
                        "\n\nСОСТАВ ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("6 * 10^24 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("12 754 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("150 млн км");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("23 часа 56 минут");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("1 год");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("9.8 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("15 °C");


                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);

                GridPane core = new GridPane();
                Label coreFirst1 = new Label("Земная кора");
                Label coreFirst2 = new Label("4-75 км");
                Label coreSecond1 = new Label("Мантия");
                Label coreSecond2 = new Label("3 000 км");
                Label coreThird1 = new Label("Внешнее ядро");
                Label coreThird2 = new Label("2 200 км");
                Label coreFourth1 = new Label("Внутреннее ядро");
                Label coreFourth2 = new Label("1 250 км");
                core.add(coreFirst1, 0, 0);
                core.add(new Label("    "), 1, 0);
                core.add(coreFirst2, 2, 0);
                core.add(coreSecond1, 0, 1);
                core.add(new Label("    "), 1, 1);
                core.add(coreSecond2, 2, 1);
                core.add(coreThird1, 0, 2);
                core.add(new Label("    "), 1, 2);
                core.add(coreThird2, 2, 2);
                core.add(coreFourth1, 0, 3);
                core.add(new Label("    "), 1, 3);
                core.add(coreFourth2, 2, 3);
                core.add(new Label("  "), 0, 4);

                showPlanet(primaryStage, scene2, txt, "file:earth.jpg", "file:earthstructure.jpg", information, planets, info, core);
            });

            contentForNewScene.sphereForMars.setOnMouseClicked((final MouseEvent event) -> {
                String information = "МАРС";
                String planets = "планета";

                String txt = "\n" +
                        "Марс это четвертая планета от Солнца и вторая самая маленькая планета Солнечной системы. " +
                        "Красноватая окраска поверхности Марса обусловлена присутствием оксида железа (ржавчины).\n\nЗЕМЛЕПОДОБНЫЙ\n" +
                        "И хотя Марс составляет лишь половину размера Земли,  между ним и Землей много сходства. " +
                        "На Марсе каменная поверхность и ледяные шапки на полюсах. Дни на Марсе всего лишь на 40 минут длиннее Земных," +
                        " а наклон оси способствует смене сезонов, как и на Земле. Хотя каждый сезон длится в 2 раза дольше.\n\n" +
                        "ПОВЕРХНОСТЬ\nМарс имеет нeобычный пейзаж: конусные вулканы и огромные системы каньонов. " +
                        "Из всех планет Солнечной системы, на Марсе самая высокая гора - Олимп и самый большой каньон - Долина Маринера.\n\n" +
                        "ВОДА\nСуществуют убедительные доказательства того, что атмосфера Марса когда-то была плотнее, чем сейчас, " +
                        "и что вода могла свободно существовать на его поверхности. Существуют также свидетельства того, " +
                        "что большая часть этой воды остается запертой под землей.\n\n" +
                        "ЛУНЫ\nМарс имеет два маленьких естественных спутника Фобос и Деймос, которые вращаются очень близко к планете. " +
                        "Эти луны могли быть захваченными астероидами. Полагают, что через 50 млн. лет Фобос столкнется с поверхностью Марса, " +
                        "вызвав образование кольцевых структур вокруг планеты.\n\n" +
                        "МИССИЯ\nМарс сейчас в фокусе исследований, два работающих марсохода (марсоход Оррortunlry и " +
                        "научная лаборатория Curlosity на поверхности планеты и три интернациональные миссии, работающие на oрбите Марса: " +
                        "аппараты НАСА Mars Odyssey и Mars Reconnaissance Orbiter, и зонд Мars Express eвропейского космического агентства.\n\n" +
                        "НАБЛЮДЕНИЕ\nМарс легко можно увидеть невооруженным глазом с Земли, благодаря красноватой окраске. " +
                        "Eго видимая звездная величина уступает только Солнцу, Луне, Венере и Юпитеру," +
                        " но иногда она может быть сопоставима с яркостью Юпитера, в моменты противостояний планеты.\n\nСТРУКТУРА\n" +
                        "Внешняя кора богата кремнием, кислородом, железом, магнием, алюминием, кальцием и калием - " +
                        "все из которых могут быть использованы для развития будущего на Марсе.\n\n" +
                        "Силикатная мантия когда-то была очень активной, давая начало многим  отличительным особенностям поверхности планеты, " +
                        "но которая теперь кажется бездействующей.\n\n" +
                        "Как и Земля, Марс претерпел дифференциацию, в результате чего образовалось плотное ядро, в основном состоящее из железа и никеля, " +
                        "но с содержанием серы около 17%, что делает ядро частично жидким." +
                        "\n\nСОСТАВ ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("6.4 * 10^23 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("6 792 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("229 млн км");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("1.03 дня");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("1.88 год");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("3.7 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("-63 °C");
                Label eighth1 = new Label("Температура - лето");
                sixth1.setFont(new Font("Arial", 14));
                Label eighth2 = new Label("17 °C");
                sixth2.setFont(new Font("Arial", 14));
                Label ninth1 = new Label("Температура - ночь");
                sixth1.setFont(new Font("Arial", 14));
                Label ninth2 = new Label("-140 °C");
                sixth2.setFont(new Font("Arial", 14));


                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);
                info.add(eighth1, 0, 7);
                info.add(new Label("    "), 1, 7);
                info.add(eighth2, 2, 7);
                info.add(ninth1, 0, 8);
                info.add(new Label("    "), 1, 8);
                info.add(ninth2, 2, 8);

                GridPane core = new GridPane();
                Label coreFirst1 = new Label("Кора");
                Label coreFirst2 = new Label("50 км");
                Label coreSecond1 = new Label("Мантия");
                Label coreSecond2 = new Label("700—800 км");
                Label coreThird1 = new Label("Ядро");
                Label coreThird2 = new Label("1 860 км");
                core.add(coreFirst1, 0, 0);
                core.add(new Label("    "), 1, 0);
                core.add(coreFirst2, 2, 0);
                core.add(coreSecond1, 0, 1);
                core.add(new Label("    "), 1, 1);
                core.add(coreSecond2, 2, 1);
                core.add(coreThird1, 0, 2);
                core.add(new Label("    "), 1, 2);
                core.add(coreThird2, 2, 2);
                core.add(new Label("  "), 0, 3);

                showPlanet(primaryStage, scene2, txt, "file:mars.jpg", "file:marsStructure.jpg", information, planets, info, core);
            });

            contentForNewScene.sphereForJupiter.setOnMouseClicked((final MouseEvent event) -> {
                String information = "ЮПИТЕР";
                String planets = "планета";

                String txt = "\n" +
                        "Юпитер - самая большая планета Солнечной системы, с массой в 2, 5раза большей, чем у всех остальных" +
                        "вместе взятых планет, и составляющая всего 0,001% массы Солнца.\n\n" +
                        "ЗВЕЗДОПОДОБНЫЙ\nЮпитер, с точки зрения состава, наиболее похож на Солнце. " +
                        "Однако Юпитеру нужно быть в 75 раз более массивным для того, " +
                        "чтобы запустить реакцию термоядерного синтеза и стать звездой. " +
                        "При этом ему нужно быть в 13 раз более массивным, чтобы сжигать Дейтерий и стать коричневым карликом.\n\n" +
                        "АТМОСФЕРА\nЮпитер вращается вокруг свoей оси гораздо быстрее, чем любая другая планета." +
                        " Из-за подобного вращения, атмосфера Юпитера подвержена сильным ветрам, которые вызывают в атмосфере " +
                        "образование характерных цветовых полос, а также различных вихрей и гигантских антициклонических штормов.\n\n" +
                        "ЛУНЫ\n\nЮпитер управляет самым большим количеством лун, на сегодняшний день у него обнаружено 79 спутников. " +
                        "Четыре самых больших это галилеевские луны: Ио, Европа, Ганимед и Каллисто. " +
                        "Ганимед- самая большая луна в Солнечной системе, его диаметр больше чем у Меркурия.\n\n" +
                        "НАБЛЮДЕНИЕ\nЮпитер - четвертый самый яркий объект в нашем небе, после Солнца, Луны и Венеры. " +
                        "Поскольку орбита Юпитера находится снаружи Земной орбиты, планета видна как очень яркий объект, " +
                        "если наблюдать ее в телескоп.\n\nСТРУКТУРА ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("1.9 * 10^27 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("142 984 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("779 млн км");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("9 часов 55 минут");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("11.9 лет");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("25 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("-120 °C");

                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);

                GridPane core = new GridPane();
                Label coreFirst1 = new Label("Атмосфера");
                Label coreFirst2 = new Label("21 тыс. км");
                Label coreSecond1 = new Label("Металлического водород");
                Label coreSecond2 = new Label("30—50 тыс. км");
                Label coreThird1 = new Label("Каменное ядро");
                Label coreThird2 = new Label("20 тыс. км");
                core.add(coreFirst1, 0, 0);
                core.add(new Label("    "), 1, 0);
                core.add(coreFirst2, 2, 0);
                core.add(coreSecond1, 0, 1);
                core.add(new Label("    "), 1, 1);
                core.add(coreSecond2, 2, 1);
                core.add(coreThird1, 0, 2);
                core.add(new Label("    "), 1, 2);
                core.add(coreThird2, 2, 2);
                core.add(new Label("  "), 0, 3);

                showPlanet(primaryStage, scene2, txt, "file:jupiter.jpg", "file:jupiterStructure.jpg", information, planets, info, core);
            });

            contentForNewScene.sphereForSaturn.setOnMouseClicked((final MouseEvent event) -> {
                String information = "САТУРН";
                String planets = "планета";

                String txt = "\n" +
                        "Сатурн это шестая планета от Солнца и вторая самая большая планета Солнечной системы. " +
                        "Вплоть до изобретения современного телескопа, Сатурн считали самой отдаленной из планет.\n\n" +
                        "МАССА\nХотя он и является второй планетой по размеру, тем не менее, из всех планет Солнечной системы у него самая маленькая" +
                        " плотность, составляющая 1/8 от плотности Земли. И несмотря на это он в девять раз, по диаметру, больше чем Земля. " +
                        "Это единственная планета, которая чуть менее плотная чем вода.\n\n" +
                        "КОЛЬЦА\nНесмотря на то, что у других газовых гигантов есть кольцевые системы, кольца Сатурна самые большие и заметнее, " +
                        "чем у других планет. Кольца состоят из ледяных кристаллов воды и незначительного количества скальных пород, " +
                        "по размерам они варьируют от пылинок до частиц размером с гору. " +
                        "\n\nСПУТНИКИ\nУ Сатурна как минимум 150 спутников. Точная цифра до сих пор неизвестна, потому что очень тяжело четко " +
                        "отличить большую кольцевую частицу от луны. Некоторые луны Сатурна выступают в роли 'пастухов'," +
                        " предотвращая расползание частиц по орбите.\n\nНАБЛЮДЕНИЕ\nСатурн на ночном небе выглядит как яркая" +
                        "желтоватая точка света.\n\nСТРУКТУРА\n" +
                        "Внутренняя часть Сатурна похожа на внутреннюю часть Юпитера.  " +
                        "В отличие от Юпитера, плотность Сатурна ниже, что делает его наименее плотной планетой в Солнечной системе.  " +
                        "В сочетании с быстрым вращением Сатурн становится самой сжатой планетой в нашей Солнечной системе: его экваториальный и " +
                        "полярный диаметры различаются почти на 10 процентов.\n\n" +
                        "АТМОСФЕРА\nИз-за более низкой гравитации атмосфера Сатурна более мутная, чем у Юпитера, с размытыми деталями и " +
                        "приглушенными цветами до общего желтоватого оттенка.  Ветры на Сатурне в пять раз быстрее ветров на Юпитере." +
                        "\n\nСОСТАВ ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("5.7 * 10^26 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("120 536 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("9.57 а.е.");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("10 часов 39 минут");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("29 лет");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("10.4 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("-125 °C");

                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);

                GridPane core = new GridPane();
                Label coreFirst1 = new Label("Молекулярный водород");
                Label coreFirst2 = new Label("14.5 тыс. км");
                Label coreSecond1 = new Label("Металлического водород");
                Label coreSecond2 = new Label("18.5 тыс. км");
                Label coreThird1 = new Label("Ядро");
                Label coreThird2 = new Label("25 тыс. км");
                core.add(coreFirst1, 0, 0);
                core.add(new Label("    "), 1, 0);
                core.add(coreFirst2, 2, 0);
                core.add(coreSecond1, 0, 1);
                core.add(new Label("    "), 1, 1);
                core.add(coreSecond2, 2, 1);
                core.add(coreThird1, 0, 2);
                core.add(new Label("    "), 1, 2);
                core.add(coreThird2, 2, 2);
                core.add(new Label("  "), 0, 3);

                showPlanet(primaryStage, scene2, txt, "file:saturn.jpg", "file:saturnStructure.jpg", information, planets, info, core);
            });

            contentForNewScene.sphereForUranus.setOnMouseClicked((final MouseEvent event) -> {
                String information = "УРАН";
                String planets = "планета";

                String txt = "\n" +
                        "Уран это седьмая планета от Солнца и третий самый большой газовый гигант Солнечной системы. Он один из самых холодных планет " +
                        "Солнечной системы.\n\n" +
                        "ОТКРЫТИЕ\nУран был первой планетой, которую открыли с помощью телескопа. Это открытие сделал Уильям Гершель 1781 году.\n\n" +
                        "НАКЛОН ОСИ\nУран имеет наклон оси 97.77 градусов, и он оращается на боку, по сравнемию со всеми остальными планетами Солнечной системы." +
                        "\n\nНАБЛЮДЕНИЕ\nКогда он находится в оппозиции (от Солнца относительно Земли) Уран выглядит как бледная звезда на темном небе." +
                        "\n\nСТРУКТУРА\nГолубой цвет Урана обусловлен поглощением красного цвета атмосферным метаном.\n\n" +
                        "АТМОСФЕРА\nГазовая атмосфера постепенно переходит во внутренние жидкие слои.\n\n" +
                        "МАНТИЯ\nЛедяная мантия на самом деле состоит не из льда в обычном понимании, а из горячего и плотного флюида, " +
                        "также называемого водно-аммиачным океаном. Относительно большое магнитное поле создается конвекционными токами на небольших глубинах планеты." +
                        "\n\nСОСТАВ ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("8.7 * 10^25 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("51 118 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("19.2 а.е.");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("17 часов 14 минут");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("84 года");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("8.7 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("-210 °C");

                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);

                GridPane core = new GridPane();
                Label coreFirst1 = new Label("Атмосфера");
                Label coreFirst2 = new Label("5 тыс. км");
                Label coreSecond1 = new Label("Ледяная мантия");
                Label coreSecond2 = new Label("15 тыс. км");
                Label coreThird1 = new Label("Каменное ядро");
                Label coreThird2 = new Label("5 тыс. км");
                core.add(coreFirst1, 0, 0);
                core.add(new Label("    "), 1, 0);
                core.add(coreFirst2, 2, 0);
                core.add(coreSecond1, 0, 1);
                core.add(new Label("    "), 1, 1);
                core.add(coreSecond2, 2, 1);
                core.add(coreThird1, 0, 2);
                core.add(new Label("    "), 1, 2);
                core.add(coreThird2, 2, 2);
                core.add(new Label("  "), 0, 3);

                showPlanet(primaryStage, scene2, txt, "file:uranus.jpg", "file:saturnStructure.jpg", information, planets, info, core);
            });

            contentForNewScene.sphereForNeptune.setOnMouseClicked((final MouseEvent event) -> {
                String information = "НЕПТУН";
                String planets = "планета";

                String txt = "\n" +
                        "Нептун - восьмая и официально самая дальняя планета от Солнца. Эта самая маленькая, но самая плотная планета," +
                        "газовый гигант. Сила гравитации на поверхности Нeптуна уступает только Юпитеру. " +
                        "\n\nОТКРЫТИЕ\nНептун - это первая планета, которая была открыта с помощью математических расчетов, " +
                        "нежели путем прямого наблюдения. Со времени открытия Нептуна, он успел совершить только один оборот вокруг Солнца." +
                        "\n\nСТРУКТУРА\nСиний цвет Нептуна намного ярче, чем у Урана, который имеет такое же количество метана," +
                        " предполагается, что неизвестный компонент вызывает интенсивный цвет Нептуна.\n\nАТМОСФЕРА\n" +
                        "Нептун вырабатывает значительное количество внутреннего тепла за счет процессов, которые до конца не изучены.  " +
                        "Это вызывает самые быстрые ветры в Солнечной системе и порождает характерные высотные полосы облаков, " +
                        "а также антициклонические штормы.\n\nМАНТИЯ\n" +
                        "Мантия обладает высокой электропроводностью и порождает магнитное поле планеты." +
                        "\n ";

                GridPane info = new GridPane();
                Label first1 = new Label("Масса");
                first1.setFont(new Font("Arial", 14));
                Label first2 = new Label("1.02 * 10^26 кг");
                first2.setFont(new Font("Arial", 14));
                Label second1 = new Label("Экваториальный диаметр");
                second1.setFont(new Font("Arial", 14));
                Label second2 = new Label("49 528 км");
                second2.setFont(new Font("Arial", 14));
                Label third1 = new Label("Расстояние до Солнца");
                third1.setFont(new Font("Arial", 14));
                Label third2 = new Label("30.1 а.е.");
                third2.setFont(new Font("Arial", 14));
                Label fourth1 = new Label("Период вращения");
                fourth1.setFont(new Font("Arial", 14));
                Label fourth2 = new Label("16 часов 6 минут");
                fourth2.setFont(new Font("Arial", 14));
                Label fifth1 = new Label("Период вращения вокруг Солнца");
                fifth1.setFont(new Font("Arial", 14));
                Label fifth2 = new Label("165 лет");
                fifth2.setFont(new Font("Arial", 14));
                Label sixth1 = new Label("Ускорение свободного падения");
                sixth1.setFont(new Font("Arial", 14));
                Label sixth2 = new Label("11.2 м/c^2");
                sixth2.setFont(new Font("Arial", 14));
                Label seventh1 = new Label("Температура поверхности");
                sixth1.setFont(new Font("Arial", 14));
                Label seventh2 = new Label("-200 °C");

                info.add(first1, 0, 0);
                info.add(new Label("    "), 1, 0);
                info.add(first2, 2, 0);
                info.add(second1, 0, 1);
                info.add(new Label("    "), 1, 1);
                info.add(second2, 2, 1);
                info.add(third1, 0, 2);
                info.add(new Label("    "), 1, 2);
                info.add(third2, 2, 2);
                info.add(fourth1, 0, 3);
                info.add(new Label("    "), 1, 3);
                info.add(fourth2, 2, 3);
                info.add(fifth1, 0, 4);
                info.add(new Label("    "), 1, 4);
                info.add(fifth2, 2, 4);
                info.add(sixth1, 0, 5);
                info.add(new Label("    "), 1, 5);
                info.add(sixth2, 2, 5);
                info.add(seventh1, 0, 6);
                info.add(new Label("    "), 1, 6);
                info.add(seventh2, 2, 6);

                GridPane core = new GridPane();

                showPlanet(primaryStage, scene2, txt, "file:neptune.jpg", "file:saturnStructure.jpg", information, planets, info, core);
            });


            primaryStage.setScene(scene2);
            scene2.setFill(Color.rgb(3, 10, 26));

            contentForNewScene.animation.pause();
            contentForNewScene.animationForEarth.pause();
            contentForNewScene.addMouseScrolling(contentForNewScene.groupNewScene);
            scene2.setOnMousePressed((final MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
                X = contentForNewScene.rx.getAngle();
                Y = contentForNewScene.ry.getAngle();
            });
            scene2.setOnMouseDragged((final MouseEvent e) -> {
                contentForNewScene.rx.setAngle(X - (y - e.getSceneY()));
                contentForNewScene.ry.setAngle(Y + x - e.getSceneX());
            });
            primaryStage.show();
        });

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(SIZE * 6);
        camera.setTranslateZ(-3 * SIZE);
        scene.setCamera(camera);
        primaryStage.show();
        content.animation.play();
        content.animationForEarth.play();
        contentForNewScene.animation.play();
        contentForNewScene.animationForEarth.play();
    }

    public void showPlanet(Stage primaryStage, Scene scene2, String txt, String imageUrl, String imageCore,
                           String information, String planets, GridPane info, GridPane core) {
        Button exploreButton = new Button("ИССЛЕДОВАТЬ");
        exploreButton.setStyle("-fx-text-color: white;");
        exploreButton.setStyle("-fx-background-color: powderblue;");
        exploreButton.setLayoutX(-80);
        exploreButton.setLayoutY(250);
        exploreButton.setOnAction((final ActionEvent event) -> {
/*            @Override
            public void handle(ActionEvent event) {*/
            Group groupPlanet = new Group();
            Button backToPlanet = new Button("НАЗАД");

            Sphere planet = (new Content(SIZE)).sphereForMercury;
            Rotate rx = new Rotate(0, Rotate.X_AXIS);
            Rotate ry = new Rotate(0, Rotate.Y_AXIS);
            Rotate rz = new Rotate(0, Rotate.Z_AXIS);
            PhongMaterial whiteMaterial = new PhongMaterial();

            whiteMaterial.setDiffuseMap(new Image(imageUrl));
            planet.setMaterial(whiteMaterial);
            planet.setRadius(150);
            planet.setOnMouseEntered((final MouseEvent e) -> {
                Tooltip tooltip = new Tooltip(information);
                tooltip.setShowDelay(Duration.millis(10));
                Tooltip.install(planet, tooltip);
            });

            Button backFromPlanet = new Button("Назад");
            backFromPlanet.setLayoutX(130);
            backFromPlanet.setLayoutY(200);
            backFromPlanet.setStyle("-fx-text-base-color: white;");
            backFromPlanet.setStyle("-fx-background-color: powderblue;");
            backFromPlanet.setOnAction((final ActionEvent event3) -> {
                    /*@Override
                    public void handle(ActionEvent event) {*/
                primaryStage.setScene(scene2);
                //}
            });

            Button aboutPlanet = new Button("Энциклопедия");
            aboutPlanet.setLayoutX(10);
            aboutPlanet.setLayoutY(200);
            aboutPlanet.setStyle("-fx-text-base-color: white;");
            aboutPlanet.setStyle("-fx-background-color: powderblue;");
            aboutPlanet.setOnAction((final ActionEvent event2) -> {
                    /*@Override
                    public void handle(ActionEvent event) {*/
                Label start = new Label("ЭНЦИКЛОПЕДИЯ");
                start.setFont(new Font("Arial", 14));
                Label text = new Label(txt);
                text.setMaxWidth(580);
                text.setWrapText(true);
                text.setTextFill(Color.WHITE);
                text.setFont(Font.font("Arial"));
/*
                ImageView imageView = new ImageView();
                Image image = new Image(imageCore);
                imageView.setImage(image);*/

                VBox vBox = new VBox();
                vBox.getChildren().addAll(start, info, text, core, backToPlanet);
                ScrollPane root = new ScrollPane(vBox);
                root.setPrefViewportHeight(585);
                root.setPrefViewportWidth(586);
                root.setStyle("-fx-background: #030a1a;");
                backToPlanet.setStyle("-fx-text-base-color: white;");
                backToPlanet.setStyle("-fx-background-color: powderblue;");
                FlowPane flowPane = new FlowPane(Orientation.VERTICAL, 10, 10, root);
                flowPane.setStyle("-fx-background: #030a1a;");
                Scene scene4 = new Scene(flowPane, 600, 600, true);
                primaryStage.setScene(scene4);
                scene4.setFill(Color.rgb(3, 10, 26));
                //}
            });
            Group planetInGroup = new Group();
            planetInGroup.getChildren().addAll(planet);
/*                if (!imageUrl.equals("file:saturn.jpg")) {
                    planetInGroup.getChildren().addAll(planet);
                } else {
                    c.sphere6.setMaterial(whiteMaterial);
                    Group saturnGroup = c.groupMini;
                    planetInGroup.getChildren().addAll(saturnGroup);
                    rotateNode(saturnGroup, rx, ry);
                }*/
            planetInGroup.getTransforms().addAll(rz, ry, rx);
            Label INFO = new Label(information + "\n" + planets);
            INFO.setTextFill(Color.WHITE);
            INFO.setFont(new Font("Arial", 15));
            INFO.setLayoutX(-290);
            INFO.setLayoutY(-220);
            groupPlanet.getChildren().addAll(INFO, planetInGroup, backFromPlanet, aboutPlanet);
            contentForNewScene.addMouseScrolling(planetInGroup);
            groupPlanet.setTranslateX(50);
            groupPlanet.setTranslateY(0);
            groupPlanet.setTranslateZ(0);
            Scene scene3 = new Scene(groupPlanet, 600, 600, true);
            scene3.setFill(Color.rgb(3, 10, 26));

            rotateNode(planet, rx, ry);

            PerspectiveCamera camera = new PerspectiveCamera(true);
            camera.setFarClip(SIZE * 6);
            camera.setTranslateZ(-3 * SIZE);
            scene3.setCamera(camera);
            //planetStage.setScene(scene3);
            primaryStage.setScene(scene3);
            backToPlanet.setOnAction((final ActionEvent event3) -> {
                    /*@Override
                    public void handle(ActionEvent event) {*/
                primaryStage.setScene(scene3);
                //planetStage.setScene(scene3);
                //}
            });
            //primaryStage.setScene(scene3);
            contentForNewScene.groupInNewScene.getChildren().remove(exploreButton);
            //planetStage.show();
            //}
        });
        contentForNewScene.groupInNewScene.getChildren().addAll(exploreButton);
    }

    public void rotateNode(Node node, Rotate rx, Rotate ry) {
        node.setOnMousePressed((final MouseEvent e) -> {
            x = e.getSceneX();
            y = e.getSceneY();
            X = rx.getAngle();
            Y = ry.getAngle();
        });
        node.setOnMouseDragged((final MouseEvent e) -> {
            rx.setAngle(X - (y - e.getSceneY()));
            ry.setAngle(Y + x - e.getSceneX());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}