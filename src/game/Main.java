package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;

public class Main extends Application {

    public static Stage window;
    public static Stage instructionsMenu;
    public static StackPane root;
    public static StackPane game;
    public static StackPane instructions;
    public static Scene titleScene;
    public static Scene gameScene;
    public static Scene leaderboardScene;
    public static Scene instructionsScene;
    public static ImageView birdView;
    public static Button playButton;
    public static Button restart;
    public static Button test;
    public static Button instructionsButton;
    public boolean above = false;
    public boolean move = true;
    public boolean[] scoreCheck;
    public static int score = 0;
    public static int pipeCount = 0x3f3f3f;
    public static int[] position;
    public static double gravity = 0;
    public ArrayList<Rectangle> rectangles;
    public static Text currentScore;
    public static Text text;
    public static Rectangle[] topRectangle = new Rectangle[pipeCount];
    public static Rectangle[] botRectangle = new Rectangle[pipeCount];
    public static AnimationTimer animation;
    public static MediaPlayer musicPlayer;
    public static ImageView gameOver;
    public static Ellipse hitbox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;
        window.setResizable(false);
        root = new StackPane();
        game = new StackPane();
        instructions = new StackPane();

        titleScene = new Scene(root, 750, 450);
        gameScene = new Scene(game, 750, 450);
        Scene instructionScene = new Scene(instructions, 750, 450);

        Stage instructionMenu = new Stage();
        instructionMenu.setX(1100);
        instructionMenu.setScene(instructionScene);

        Text currScore = new Text("Current highscore: " + score);
        currScore.setTranslateX(-185);
        currScore.setTranslateY(185);
        currScore.setFont(Font.font(java.awt.Font.MONOSPACED, 33));
        currScore.setFill(Color.FLORALWHITE);

        TextArea instructionsText = new TextArea();
        instructionsText.setEditable(false);
        instructionsText.setText("instructions "); // TODO: FILL IN LATER
        instructionsText.setPrefRowCount(4);
        instructionsText.setFont(Font.font(java.awt.Font.MONOSPACED, 17));
        instructions.getChildren().add(instructionsText);

        Image play = new Image("file:playbutton.png", 200, 200, true, true);
        ImageView playView = new ImageView(play);

        Button playButton = new Button("", playView);
        playButton.setBackground(Background.EMPTY);
        playButton.setTranslateY(70);
        playButton.setOnAction(e -> {
            window.setScene(gameScene);
            runningGame();
        });

        root.getChildren().add(playButton);

        Image title = new Image("file:flappytitle.png", 1050, 375, true, true);
        ImageView titleView = new ImageView();
        titleView.setImage(title);
        titleView.setTranslateY(-50);
        root.getChildren().add(titleView);

        Image over = new Image("file:gameover.png");
        gameOver = new ImageView(over);
        gameOver.setFitHeight(400);
        gameOver.setFitWidth(400);
        gameOver.setPreserveRatio(true);

        Image background = new Image("file: background.png");
        BackgroundImage backgroundImg = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImg));
        game.setBackground(new Background(backgroundImg));

        restart = new Button("");
        restart.setTranslateX(290);
        restart.setTranslateY(185);
        restart.setBackground(Background.EMPTY);
        restart.setFont(Font.font(java.awt.Font.MONOSPACED, 33));
        restart.setTextFill(Color.FLORALWHITE);

        instructionsButton = new Button("How to Play");
        instructionsButton.setTranslateX(290);
        instructionsButton.setTranslateY(185);
        instructionsButton.setBackground(Background.EMPTY);
        instructionsButton.setFont(Font.font(java.awt.Font.MONOSPACED, 33));
        instructionsButton.setTextFill(Color.FLORALWHITE);
        instructionsButton.setOnAction(e -> {
            instructionMenu.show();
        });
        root.getChildren().add(instructionsButton);

        MusicPlayer();

        window.setTitle("Flappy Pappy");
        window.setScene(titleScene);
        window.show();

    }

    public void runningGame() {
        score = 0;
        above = false;
        currentScore.setText("Current score: "+score);
        restart.setText("");
        game.getChildren().add(restart);

        restart.setOnMouseClicked(e-> {
            if (!move) {
                game.getChildren().clear();
                move = true;
                runningGame();
            }
        });

        game.setOnKeyPressed(e->{
            if (e.getCode() == KeyCode.UP) {
                if (move) {
                    Jump();
                } else {
                    game.getChildren().clear();
                    move = true;
                    runningGame();
                }
            }
        });

        Image bird = new Image("file:bird.png");
        birdView = new ImageView();
        birdView.setImage(bird);
        birdView.setFitHeight(45);
        birdView.setFitWidth(45);
        birdView.setPreserveRatio(true);

        hitbox = new Ellipse();
        hitbox.setRadiusX(21);
        hitbox.setRadiusY(14);
        hitbox.setFill(Color.TRANSPARENT);

        rectangles = new ArrayList<Rectangle>();
        position = new int[pipeCount];
        scoreCheck = new boolean[pipeCount];
        text = new Text("Press UP to start");

        text.setTranslateY(-50);
        text.setFont(Font.font(java.awt.Font.MONOSPACED, 50));
        text.setFill(Color.FLORALWHITE);

        game.getChildren().add(text);
        game.getChildren().add(birdView);
        game.getChildren().add(hitbox);
        game.getChildren().add(currentScore);
        int counter = 0;
        for (int i=0; i<pipeCount; i++) {
            topRectangle[i] = new Rectangle();
            botRectangle[i] = new Rectangle();

            topRectangle[i].setFill(Color.rgb(66, 244, 113));
            botRectangle[i].setFill(Color.rgb(66, 244, 113));

            topRectangle[i].setWidth(75);
            botRectangle[i].setWidth(75);
            topRectangle[i].setTranslateX(600+counter);

            botRectangle[i].setTranslateX(600+counter);
            position[i] = 600 + counter;
            counter+=250;
            pipeSize(i, counter);
        }

        for (int i=0; i<pipeCount; i++) {
            game.getChildren().add(topRectangle[i]);
            game.getChildren().add(botRectangle[i]);

        }

        runGame();
    }

    public void Jump() {
        animation.start();
        gravity = -5.1;

        birdView.setManaged(false);
        hitbox.setManaged(false);

        for(int i = 0; i < pipeCount; i++) {
            topRectangle[i].setManaged(false);
            botRectangle[i].setManaged(false);
        }
        text.setText("");
    }


    public void pipeSize(int idx, int counter) {
        int pipeGen = new Random().nextInt(4);

        if(pipeGen == 0) {
            topRectangle[idx].setHeight(100);
            topRectangle[idx].setTranslateY(36);

            botRectangle[idx].setHeight(100);
            botRectangle[idx].setTranslateY(-222);
        }
        else if(pipeGen == 1) {
            topRectangle[idx].setHeight(100);
            topRectangle[idx].setTranslateY(85);

            botRectangle[idx].setHeight(100);
            botRectangle[idx].setTranslateY(-178);
        }
        else if(pipeGen == 2) {
            topRectangle[idx].setHeight(150);
            topRectangle[idx].setTranslateY(61);

            botRectangle[idx].setHeight(50);
            botRectangle[idx].setTranslateY(-194);
        }
        else {
            topRectangle[idx].setHeight(50);
            topRectangle[idx].setTranslateY(194);

            botRectangle[idx].setHeight(150);
            botRectangle[idx].setTranslateY(-61);
        }
        rectangles.add(topRectangle[idx]);
        rectangles.add(botRectangle[idx]);
    }

    public void runGame() {
        animation = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!check() || above) {
                    game.getChildren().add(gameOver);
                    animation.stop();
                    restart.setText("RESTART");
                    move = false;
                }
                pipes();
                if(gravity >= 12) {
                    gravity = 12;
                } else {
                    gravity += 0.26;
                }
                update();
                if (gravity>=0 && gravity<2) {
                    birdView.setRotate(0);
                    hitbox.setRotate(0);
                } else if (gravity>-6 && gravity<-4) {
                    birdView.setRotate(-30);
                    hitbox.setRotate(-30);
                } else if (gravity>-4 && gravity<-2) {
                    birdView.setRotate(-20);
                    hitbox.setRotate(-20);
                } else if (gravity>-2 && gravity<0) {
                    birdView.setRotate(-10);
                    hitbox.setRotate(-10);
                } else if (gravity>=2 && gravity<2.5) {
                    birdView.setRotate(10);
                    hitbox.setRotate(10);
                } else if (gravity>=2.5 && gravity<3) {
                    birdView.setRotate(20);
                    hitbox.setRotate(20);
                } else if (gravity>=3 && gravity<3.5) {
                    birdView.setRotate(30);
                    hitbox.setRotate(30);
                } else if (gravity>=3.5 && gravity<4) {
                    birdView.setRotate(40);
                    hitbox.setRotate(40);
                } else if (gravity>=4.5 && gravity<5) {
                    birdView.setRotate(50);
                    hitbox.setRotate(50);
                } else if (gravity>=5 && gravity<5.5) {
                    birdView.setRotate(60);
                    hitbox.setRotate(60);
                } else if (gravity>=5.5 && gravity<6) {
                    birdView.setRotate(70);
                    hitbox.setRotate(70);
                } else if (gravity>=6 && gravity<6.5) {
                    birdView.setRotate(80);
                    hitbox.setRotate(80);
                } else if (gravity>=6.5 && gravity<7) {
                    birdView.setRotate(90);
                    hitbox.setRotate(90);
                }
            }
        };
    }

    public void pipes() {
        if(move == true) {
            for(int i = 0; i < pipeCount; i++) {
                topRectangle[i].setX(topRectangle[i].getX() - 2.2);
                botRectangle[i].setX(botRectangle[i].getX() - 2.2);
                if(position[i] + topRectangle[i].getX() > -50 && position[i] + topRectangle[i].getX() < 50 && !scoreCheck[i]) {
                    if(birdView.getY() < -200) {
                        above = true;
                    } else {
                        score++;
                        currentScore.setText("Score: " + score);
                        scoreCheck[i] = true;
                    }
                }
            }
        }
    }

    public void update() {
        birdView.setY(birdView.getY() - gravity);
        hitbox.setCenterY(hitbox.getCenterY()+gravity);
    }

    public boolean checkModel(Ellipse hitbox) {
        boolean hitOrMiss = false;
        for(Rectangle rectangle : rectangles) {
            if (hitbox.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
                hitOrMiss = true;
            }
        }
        return hitOrMiss;
    }

    public boolean check() {
        boolean hitOrMiss = true;
        if(hitbox.getCenterY() >= 110) {
            hitOrMiss = false;
        }
        if(checkModel(hitbox)) {
            hitOrMiss = true;
        }
        return hitOrMiss;
    }

    public void MusicPlayer() {
        String musicPath = Main.class.getResource("/Sounds/In the Hall of the Mountain King.mp3").toString();
        Media backgroundMusic = new Media(musicPath);
        musicPlayer = new MediaPlayer(backgroundMusic);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
        musicPlayer.setVolume(0.5);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
