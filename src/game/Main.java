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

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

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
    public static int pipeCount = 999;
    public static int[] position;
    public static int highScore = 0;//int for High Score
    public static double gravity = 0;
    public ArrayList<Rectangle> rectangleArrayList;
    public static Text currentScore; //Text for Current Score
    public static Text maxScore; //Text for High Score
    public static Text text;
    public static Rectangle[] rectangle1 = new Rectangle[pipeCount];
    public static Rectangle[] rectangle2 = new Rectangle[pipeCount];
    public static AnimationTimer animation;
    public static MediaPlayer player;
    public static ImageView gameOver;
    public static Ellipse hitbox;


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setResizable(false);
        root = new StackPane();
        game = new StackPane();
        instructions = new StackPane();

        titleScene = new Scene(root, 750, 450);
        gameScene = new Scene(game, 750, 450);
        instructionsScene = new Scene(instructions, 300, 100);

        instructionsMenu = new Stage();
        instructionsMenu.setX(1100);
        instructionsMenu.setScene(instructionsScene);

        currentScore = new Text("Current score " + score);
        currentScore.setTranslateY(185);
        currentScore.setTranslateX(-185);
        currentScore.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        currentScore.setFill(Color.GHOSTWHITE);

        maxScore = new Text("High Score" + score);
        maxScore.setTranslateY(-200);
        maxScore.setTranslateX(0);
        maxScore.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        maxScore.setFill(Color.GHOSTWHITE);

        TextArea instructionsText = new TextArea();
        instructionsText.setEditable(false);
        //The code below is the text for the instruction page
        instructionsText.setText("Use the W button to" + "\nget started. Fly the bird" + "\nas far as you can" + "\nwithout hitting a pipe.");
        instructionsText.setPrefRowCount(4);

        instructionsText.setFont(Font.font(java.awt.Font.SANS_SERIF, 17));
        instructions.getChildren().add(instructionsText);
        //The line bellow inserts the play button image for the start menu
        Image play = new Image("file:playbutton.png", 200, 200, true, true);
        ImageView playView = new ImageView(play);
        playButton = new Button("", playView);
        playButton.setBackground(Background.EMPTY);
        playButton.setTranslateY(70);
        playButton.setOnAction(e -> {
            window.setScene(gameScene);
            runningGame();
        });
        root.getChildren().add(playButton);
        //The line bellow inserts the game title file for the intro menu
        Image title = new Image("file:flappytitle.png", 1050, 375, true, true);
        ImageView titleView = new ImageView();
        titleView.setImage(title);
        titleView.setTranslateY(-50);
        root.getChildren().add(titleView);
        //The line bellow inserts the game over logo.
        Image over = new Image("file:gameover.png");
        gameOver = new ImageView(over);
        gameOver.setFitHeight(400);
        gameOver.setFitWidth(400);
        gameOver.setPreserveRatio(true);
        //The line bellow inserts the image that will be the background of the game.
        Image background = new Image("file:flappybackground.jpg");
        BackgroundImage imageOfBackground = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(imageOfBackground));
        game.setBackground(new Background(imageOfBackground));

        restart = new Button("");
        restart.setTranslateX(290);
        restart.setTranslateY(185);
        restart.setBackground(Background.EMPTY);
        restart.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        restart.setTextFill(Color.GHOSTWHITE);

        instructionsButton = new Button("HOW TO PLAY");
        instructionsButton.setTranslateX(250);
        instructionsButton.setTranslateY(185);
        instructionsButton.setBackground(Background.EMPTY);
        instructionsButton.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        instructionsButton.setTextFill(Color.GHOSTWHITE);

        instructionsButton.setOnAction(e ->
                instructionsMenu.show()
        );
        root.getChildren().add(instructionsButton);


        Music();

        window.setTitle("Flappy Pappy");
        window.setScene(titleScene);
        window.show();


    }

    public void setSize(int i, int counter) {
        int rand = new Random().nextInt(3);

        if (rand == 0) {
            rectangle1[i].setHeight(200);
            rectangle1[i].setTranslateY(36);

            rectangle2[i].setHeight(100);
            rectangle2[i].setTranslateY(-222);

        } else if (rand == 1) {
            rectangle1[i].setHeight(100);
            rectangle1[i].setTranslateY(85);

            rectangle2[i].setHeight(200);
            rectangle2[i].setTranslateY(-178);

        } else {
            rectangle1[i].setHeight(150);
            rectangle1[i].setTranslateY(61);

            rectangle2[i].setTranslateY(-192);
            rectangle2[i].setHeight(150);

        }
        rectangleArrayList.add(rectangle1[i]);
        rectangleArrayList.add(rectangle2[i]);

    }

    public void runningGame() {
        score = 0;
        above = false;

        currentScore.setText("Current score: " + score);
        maxScore.setText("High Score: " + highScore);
        restart.setText("");
        game.getChildren().add(restart);

        restart.setOnMouseClicked(e -> {
            game.getChildren().clear();
            move = true;
            runningGame();

        });

        game.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                if (move) {
                    jump();
                } else {
                    game.getChildren().clear();
                    move = true;
                    runningGame();
                }
            }
        });
        //The line bellow iserts the image of the bird.
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

        rectangleArrayList = new ArrayList<Rectangle>();
        position = new int[pipeCount];
        scoreCheck = new boolean[pipeCount];

        text = new Text("Press W to start");
        text.setTranslateY(-50);
        text.setFont(Font.font(java.awt.Font.SANS_SERIF, 50));
        text.setFill(Color.GHOSTWHITE);

        game.getChildren().add(text);
        game.getChildren().add(birdView);
        game.getChildren().add(hitbox);
        game.getChildren().add(currentScore);
        game.getChildren().add(maxScore);

        int counter = 0;
        for (int i = 0; i < pipeCount; i++) {
            rectangle1[i] = new Rectangle();
            rectangle2[i] = new Rectangle();
            rectangle1[i].setFill(Color.rgb(53, 200, 0));
            rectangle2[i].setFill(Color.rgb(53, 200, 0));
            rectangle1[i].setWidth(75);
            rectangle2[i].setWidth(75);
            rectangle1[i].setTranslateX(600 + counter);
            rectangle2[i].setTranslateX(600 + counter);

            position[i] = 600 + counter;
            counter += 250;
            setSize(i, counter);
        }
        for (int i = 0; i < pipeCount; i++) {
            game.getChildren().add(rectangle1[i]);
            game.getChildren().add(rectangle2[i]);
        }
        runGame();
    }

    public void jump() {
        animation.start();
        gravity = -5.1;
        birdView.setManaged(false);
        hitbox.setManaged(false);
        for (int i = 0; i < pipeCount; i++) {
            rectangle1[i].setManaged(false);
            rectangle2[i].setManaged(false);
        }
        text.setText("");
    }
    //The code for the start of the game goes here
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
                if (gravity >= 12) {
                    gravity = 12;
                } else {
                    gravity += 0.26;
                }
                update();
                if (gravity >= 0 && gravity < 2) {
                    birdView.setRotate(0);
                    hitbox.setRotate(0);
                } else if (gravity > -6 && gravity < -4) {
                    birdView.setRotate(-30);
                    hitbox.setRotate(-30);
                } else if (gravity > -4 && gravity < -2) {
                    birdView.setRotate(-20);
                    hitbox.setRotate(-20);
                } else if (gravity > -2 && gravity < 0) {
                    birdView.setRotate(-10);
                    hitbox.setRotate(-10);
                } else if (gravity >= 2 && gravity < 2.5) {
                    birdView.setRotate(10);
                    hitbox.setRotate(10);
                } else if (gravity >= 2.5 && gravity < 3) {
                    birdView.setRotate(20);
                    hitbox.setRotate(20);
                } else if (gravity >= 3 && gravity < 3.5) {
                    birdView.setRotate(30);
                    hitbox.setRotate(30);
                } else if (gravity >= 3.5 && gravity < 4) {
                    birdView.setRotate(40);
                    hitbox.setRotate(40);
                } else if (gravity >= 4.5 && gravity < 5) {
                    birdView.setRotate(50);
                    hitbox.setRotate(50);
                } else if (gravity >= 5 && gravity < 5.5) {
                    birdView.setRotate(60);
                    hitbox.setRotate(60);
                } else if (gravity >= 5.5 && gravity < 6) {
                    birdView.setRotate(70);
                    hitbox.setRotate(70);
                } else if (gravity >= 6 && gravity < 6.5) {
                    birdView.setRotate(80);
                    hitbox.setRotate(80);
                } else if (gravity >= 6.5 && gravity < 7) {
                    birdView.setRotate(90);
                    hitbox.setRotate(90);
                }
            }


        };

    }
    public void update() {
        birdView.setY(birdView.getY() + gravity);
        hitbox.setCenterY(hitbox.getCenterY() + gravity);
    }

    public void pipes() {
        if (move) {
            for (int i = 0; i < pipeCount; i++) {
                rectangle1[i].setX(rectangle1[i].getX() - 2.2);
                rectangle2[i].setX(rectangle2[i].getX() - 2.2);
                if (position[i] + rectangle1[i].getX() > -50 &&
                        position[i] + rectangle1[i].getX() < 50 && !scoreCheck[i]) {
                    if (birdView.getY() < -200) {
                        above = true;
                    }
                    else {
                        score++;
                        currentScore.setText("Current score: " + score);
                        //The line bellow compares if the current High Score is lower than the current score so it can update it
                        if (highScore<score) {
                            highScore = score;
                            System.out.println(highScore);
                            PrintWriter out = null;
                            try {
                                out = new PrintWriter(new FileWriter("HighScore.txt"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out.println(highScore);
                            out.close();
                        }
                        maxScore.setText("High Score: " + highScore);
                        scoreCheck[i] = true;
                    }
                }
            }
        }


    }


    public void Music() {
        String filePath = Main.class.getResource("/Sounds/In the Hall of the Mountain King.mp3").toString();
        Media song = new Media(filePath);
        player = new MediaPlayer(song);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
        player.setVolume(0.2);
    }


    public boolean check() {
        boolean flag = true;
        if (hitbox.getCenterY() >= 110) {
            flag = false;
        }
        if (checkBounds(hitbox)) {
            flag = false;
        }
        return flag;
    }


    public boolean checkBounds(Ellipse hitbox) {
        boolean collision = false;
        for (Rectangle rect : rectangleArrayList) {
            if (hitbox.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                collision = true;
            }
        }
        return collision;
    }

    public static void main(String args[]) {
        launch(args);
    }
}
