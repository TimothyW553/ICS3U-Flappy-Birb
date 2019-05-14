/* Group Members: Timothy Wang and Alireza Azimi Tabrizi 
 * Game Name: Flappy Pappy
 * Work Cited Links:
 *
 */
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.text.Highlighter;
import java.io.*;
import java.util.*;

public class Main extends Application {

    public static Stage window;
    public static Stage instructionsMenu;
    public static StackPane root;
    public static StackPane game;
    public static StackPane instructions;
    public static Scene titleScene;
    public static Scene gameScene;
    public static Scene instructionsScene;
    public static ImageView birdView;
    public static Button playButton;
    public static Button restart;
    public static Button test;
    public static Button instructionsButton;
    public boolean above = false;
    public boolean move = true;
    //Makes a boolean to check the score
    public boolean[] scoreCheck;
    //int that the value of the score is added to
    public static int score = 0;
    public static int pipeCount = 999;
    public static int[] position;
    //int for the value of High Score
    public static int highScore = 0;
    public static double gravity = 0;
    public ArrayList<Rectangle> rectangleArrayList;
    public ArrayList<Circle> coinsArrayList;
    //Text for Current Score
    public static Text currentScore;
    //Text for High Score
    public static Text maxScore;
    public static Text text;
    public static Text name;
    public static Rectangle[] rectangleTop = new Rectangle[pipeCount];
    public static Rectangle[] rectangleBot = new Rectangle[pipeCount];
    public static Circle[] coins = new Circle[pipeCount];
    public static AnimationTimer animation;
    public static MediaPlayer player;
    public static MediaPlayer point;
    public static MediaPlayer hit;
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

        //Makes the text for the Current Score
        currentScore = new Text("Current score " + score);
        //Sets the Y value of the current score
        currentScore.setTranslateY(185);
        //Sets the X value of the current score
        currentScore.setTranslateX(-185);
        //Sets the font and the size
        currentScore.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        //sets the colour of the text to white
        currentScore.setFill(Color.GHOSTWHITE);

        //Makes the text for High Score
        maxScore = new Text("High Score" + score);
        //Sets the Y value of High score
        maxScore.setTranslateY(185);
        //Sets the X value of the high score
        maxScore.setTranslateX(70);
        //Sets the font and the size
        maxScore.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        //sets the colour of the text to white
        maxScore.setFill(Color.GHOSTWHITE);

        TextArea instructionsText = new TextArea();
        instructionsText.setEditable(false);
        //The code below is the text for the instruction page
        instructionsText.setText("Use the W button to" + "\nget started. Fly the bird" + "\nas far as you can" + "\nwithout hitting a pipe.");
        //The number of lines that the instruction page will need
        instructionsText.setPrefRowCount(4);

        //The font and size of the instruction page
        instructionsText.setFont(Font.font(java.awt.Font.SANS_SERIF, 17));
        //Shows the Instruction page text.
        instructions.getChildren().add(instructionsText);
        //The line bellow inserts the play button image for the start menu
        Image play = new Image("file:playbutton.png", 200, 200, true, true);
        ImageView playView = new ImageView(play);
        //adds a button that over the playbutton image
        playButton = new Button("", playView);
        //Sets the background of the button to transparent so you can see the image
        playButton.setBackground(Background.EMPTY);
        //Sets the location of the button
        playButton.setTranslateY(70);
        playButton.setOnAction(e -> {
            window.setScene(gameScene);
            //If the button is clicked, the runningGame method will run.
            runningGame();
        });
        //Displays the playButton
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
        instructionsButton.setTextFill(Color.GREEN);

        //Makes the text for the names
        name = new Text("By:Timothy and Alireza");
        //Sets the Y value of High score
        name.setTranslateY(185);
        //Sets the X value of the high score
        name.setTranslateX(-200);
        //Sets the font and the size
        name.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        //sets the colour of the text to Green
        name.setFill(Color.GREEN);
        root.getChildren().add(name);
        
        instructionsButton.setOnAction(e ->
                instructionsMenu.show()
        );
        root.getChildren().add(instructionsButton);


        BackgroundMusic();

        window.setTitle("Flappy Pappy");
        window.setScene(titleScene);
        window.show();


    }

    public void setSize(int i, int counter) {
        int rand = new Random().nextInt(3);

        if (rand == 0) {
            rectangleTop[i].setHeight(200);
            rectangleTop[i].setTranslateY(36);

            rectangleBot[i].setHeight(100);
            rectangleBot[i].setTranslateY(-222);

            coins[i].setTranslateY(70);
        } else if (rand == 1) {
            rectangleTop[i].setHeight(100);
            rectangleTop[i].setTranslateY(85);

            rectangleBot[i].setHeight(250);
            rectangleBot[i].setTranslateY(-178);

            coins[i].setTranslateY(00);
        } else {
            rectangleTop[i].setHeight(150);
            rectangleTop[i].setTranslateY(61);

            rectangleBot[i].setTranslateY(-192);
            rectangleBot[i].setHeight(150);

            coins[i].setTranslateY(-150);
        }

        rectangleArrayList.add(rectangleTop[i]);
        rectangleArrayList.add(rectangleBot[i]);
        coinsArrayList.add(coins[i]);

    }

    public void runningGame() {
        score = 0;
        above = false;
        //High score value has to be get from the text and the value of High score has to be updated from 0 to the value in the text file
        try (BufferedReader br = new BufferedReader(new FileReader("HighScore.txt"))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                highScore = Integer.parseInt(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
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
        coinsArrayList = new ArrayList<>();
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
            rectangleTop[i] = new Rectangle();
            rectangleBot[i] = new Rectangle();
            coins[i] = new Circle();
            rectangleTop[i].setFill(Color.rgb(53, 200, 0));
            rectangleBot[i].setFill(Color.rgb(53, 200, 0));
            coins[i].setFill(Color.YELLOW);
            coins[i].setRadius(12);
            rectangleTop[i].setWidth(75);
            rectangleBot[i].setWidth(75);
            rectangleTop[i].setTranslateX(600 + counter);
            rectangleBot[i].setTranslateX(600 + counter);
            coins[i].setTranslateX(600 + counter);

            position[i] = 600 + counter;
            counter += 250;
            setSize(i, counter);
        }
        for (int i = 0; i < pipeCount; i++) {
            game.getChildren().add(rectangleTop[i]);
            game.getChildren().add(rectangleBot[i]);
            game.getChildren().add(coins[i]);
        }
        runGame();
    }

    public void jump() {
        animation.start();
        gravity = -5.1;
        birdView.setManaged(false);
        hitbox.setManaged(false);
        for (int i = 0; i < pipeCount; i++) {
            rectangleTop[i].setManaged(false);
            rectangleBot[i].setManaged(false);
            coins[i].setManaged(false);
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
                    HitSound();
                    player.stop();
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
        if (coinCollected(hitbox)) {
            System.out.println("Coin Collected!");
            PointSound();
        }
        if (move) {
            for (int i = 0; i < pipeCount; i++) {
                rectangleTop[i].setX(rectangleTop[i].getX() - 2.2);
                rectangleBot[i].setX(rectangleBot[i].getX() - 2.2);
                coins[i].setCenterX(coins[i].getCenterX() - 3.5);
                if (position[i] + rectangleTop[i].getX() > -25 &&
                        position[i] + rectangleTop[i].getX() < 25 && !scoreCheck[i]) {
                    if (birdView.getY() < -200) {
                        above = true;
                    }
                    if (coinCollected(hitbox)) {
                        score++;
                        PointSound();

                    } else {
                        score++;
                        PointSound();
                        currentScore.setText("Current score: " + score);
                        //The line bellow compares if the current High Score is lower than the current score so it can update the high score
                        if (highScore < score) {
                            highScore = score;
                            PrintWriter out = null;
                            //The code here will add the new Value of the high score to the text file


                            try {
                                out = new PrintWriter(new FileWriter("HighScore.txt"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out.println(highScore);
                            out.close();
                        }
                    }

                    maxScore.setText("High Score: " + highScore);
                    scoreCheck[i] = true;
                }
            }
        }
    }

    public void BackgroundMusic() {
        String filePath = Main.class.getResource("/Sounds/In the Hall of the Mountain King.mp3").toString();
        Media song = new Media(filePath);
        player = new MediaPlayer(song);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
        player.setVolume(0.2);
    }

    public void HitSound() {
        String filePath = Main.class.getResource("/Sounds/sfx_hit.wav").toString();
        Media song = new Media(filePath);
        hit = new MediaPlayer(song);
        hit.setCycleCount(1);
        hit.play();
        hit.setVolume(0.2);
    }

    public void PointSound() {
        String filePath = Main.class.getResource("/Sounds/sfx_point.wav").toString();
        Media song = new Media(filePath);
        point = new MediaPlayer(song);
        point.setCycleCount(1);
        point.play();
        point.setVolume(0.2);
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

    public boolean coinCollected(Ellipse hitbox) {
        boolean collected = false;
        for (Circle coins : coinsArrayList) {
//            System.out.println(hitbox.getBoundsInParent());
            if (hitbox.getBoundsInParent().intersects(coins.getBoundsInParent())) {
                collected = true;
                coins.setCenterX(100000);
            }
        }
        return collected;
    }

    public static void main(String args[]) {
        launch(args);
    }
}
