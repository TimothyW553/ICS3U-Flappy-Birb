/* Group Members: Timothy Wang and Alireza Azimi Tabrizi
 * Game Name: Flappy Pappy
 * Work Cited Links:
 *  > Music:
 *    > Sound Effects: https://www.sounds-resource.com/mobile/flappybird/sound/5309/
 *    > Background Music: https://www.youtube.com/watch?v=cD8EPdn5Ctg
 *  > Game Images:
 *    > Play button: data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAQlBMVEX////w8PAAAAAAwmGlpaX29vacnJz7+/v78/kAvlH28vShoaEAwFmc2rKl4rvV1dWU2KwAvEmd37WUlJRlzo3/9v/joPAjAAACNklEQVR4nO3c7W6CQBRFUa3yLbXa9v1ftQp1EjsWAxycE93rvwObIldjM6sVAAAAAAAAAAAAAAAAAAAAAAAA8KL2zdZJs5cXNm9eGnnhNnXSH1sKKbzI5SuKhCueqxayLZx7Yi9UqH86z9MoCvNlJ+w8V59Apr4XHW/OWHeXTXwthR4oHEKhBwqHOA76WDOt8DJP7QZ9bOKpTrwwyYy/3ebc3CmMf2RQ6IbCGIVuKIxJC4/vvaNqwVjiwsOud1AtGEtcWG16lWrBGIUxCke5FG6qqjos87hxKTzZvatWvUJhjMJRrgq/Fhn9RoWbZUa/U+EyY4PCGIWjRIX60W9XKB8bFMYoHOV2oXT0OxZqR79lYfdMVR2CwtiLFBZriXKgsNQcoqCQwnn+KeymxTMX7r7atv2oNYfwLGzLuhYFuhaq8tYUpig8fwNWvQfP7Aqr77IshYGGhaIhEVD40ELloA+cCqWDPrAqVA76wKtQnrem8IGF6kEfuBTKB31gU6geEgGFSxeG//p61sL6o+0t8pDpJC5c1xei9WKpC5dHIYX+KKTQH4UU+qPwhm5bmlVRFFnqk78rO51l9zv+hM0xzi/7dE/MPrs/xjTdhbEvzCmkkMLUKKTwNEaLnltp9nteqzmFZ2E3M7PR3w/68R/WBgrNbtdMtt3h8xeGre3yInNShMK5ewSFre2a3Esj3wzwafcvpTAZfWF+/6APNXfrWQAAAAAAAAAAAAAAAAAAAAAAAAAA8K8f7o5QPp/pb7cAAAAASUVORK5CYII=
 *    > Font: https://www.dafont.com/flappybirdy.font
 *    > Background Image: https://archive-media-0.nyafuu.org/wg/image/1513/48/1513484769283.png
 *    > Bird Image: https://i-cdn.phonearena.com/images/article/52185-image/Catch-the-pigeon-Here-are-the-best-and-worst-Flappy-Bird-clones.jpg
 *  > Code Samples:
 *    > Collision Detection: https://stackoverflow.com/questions/15013913/checking-collision-of-shapes-with-javafx
 *    > EventHandler (lambda): https://stackoverflow.com/questions/45306039/how-to-write-lambda-expression-with-eventhandler-javafx
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
    public static int PIPE_COUNT = 999;
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
    public static Rectangle[] rectangleTop = new Rectangle[PIPE_COUNT];
    public static Rectangle[] rectangleBot = new Rectangle[PIPE_COUNT];
    public static Circle[] coins = new Circle[PIPE_COUNT];
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
        instructionsScene = new Scene(instructions, 700, 400);

        instructionsMenu = new Stage();
        instructionsMenu.setScene(instructionsScene);

        //Makes the text for the Current Score
        currentScore = new Text("Current score " + score);
        //Sets the Y value of the current score
        currentScore.setTranslateY(185);
        //Sets the X value of the current score
        currentScore.setTranslateX(-185);
        //Sets the font and the size
        currentScore.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        //sets the colour of the text to Green
        currentScore.setFill(Color.GREEN);

        //Makes the text for High Score
        maxScore = new Text("High Score" + score);
        //Sets the Y value of High score
        maxScore.setTranslateY(185);
        //Sets the X value of the high score
        maxScore.setTranslateX(70);
        //Sets the font and the size
        maxScore.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        //sets the colour of the text to Green
        maxScore.setFill(Color.GREEN);

        TextArea instructionsText = new TextArea();
        Image instructionText = new Image("file:instructions.png", 750, 450, true, true);
        BackgroundImage instructionBackground = new BackgroundImage(instructionText,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        //The font and size of the instruction page
        //Shows the Instruction page text.
        instructions.setBackground(new Background(instructionBackground));
        //The line bellow inserts the play button image for the start menu
        //http://animalia-life.club/other/flappy-bird-play-button.html
        Image play = new Image("file:playbutton.png", 200, 200, true, true);
        ImageView playView = new ImageView(play);
        //adds a button that over the playbutton image
        playButton = new Button("", playView);
        //Sets the background of the button to transparent so you can see the image
        playButton.setBackground(Background.EMPTY);
        //Sets the location of the button
        playButton.setTranslateY(70);
        //If the button is clicked, the runningGame method will run.
        playButton.setOnAction(e -> {
            window.setScene(gameScene);
            runningGame();
        });
        //Displays the playButton
        root.getChildren().add(playButton);
        //The line bellow inserts the game title file for the intro menu
        Image title = new Image("file:flappy_title.png", 1050, 375, true, true);
        ImageView titleView = new ImageView();
        titleView.setImage(title);
        titleView.setTranslateY(-50);
        root.getChildren().add(titleView);
        //The line bellow inserts the game over logo.
        Image over = new Image("file:game_over.png");
        gameOver = new ImageView(over);
        gameOver.setFitHeight(400);
        gameOver.setFitWidth(400);
        gameOver.setPreserveRatio(true);
        //The line bellow inserts the image that will be the background of the game.
        //https://ar.pngtree.com/freebackground/blue-cartoon-pictures_375809.html
        Image background = new Image("file:flappy_background.jpg");
        BackgroundImage imageOfBackground = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(imageOfBackground));
        game.setBackground(new Background(imageOfBackground));
        //A button is made named restart
        restart = new Button("");
        restart.setTranslateX(290);
        restart.setTranslateY(185);
        restart.setBackground(Background.EMPTY);
        restart.setFont(Font.font(java.awt.Font.SANS_SERIF, 33));
        restart.setTextFill(Color.GREEN);

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
        //If the button is pressed it will open the instruction menu
        instructionsButton.setOnAction(e ->
                instructionsMenu.show()
        );
        root.getChildren().add(instructionsButton);

        // plays background music at @ "/Sounds/In the Hall of the Mountain King.mp3"
        //https://www.youtube.com/watch?v=cD8EPdn5Ctg
        BackgroundMusic();

        // sets application title to "Flappy Pappy"
        window.setTitle("Flappy Pappy");
        window.setScene(titleScene);
        window.show();


    }
    //Sets the size of 4 diffrent cases of pipes
    public void setSize(int i, int counter) {
        int rand = new Random().nextInt(3);
        /*
         * Checks what the value of the random int is
         * Whatever the value of the int is, there will be a specific pipe generated
         * There are three differnet cases
         * First if statement checks the case that int rand is 0
         */
        if (rand == 0) {
            rectangleTop[i].setHeight(200);
            rectangleTop[i].setTranslateY(36);

            rectangleBot[i].setHeight(100);
            rectangleBot[i].setTranslateY(-222);

            coins[i].setTranslateY(70);
            //this else if statement checks the case that int rand is 1
        } else if (rand == 1) {
            rectangleTop[i].setHeight(100);
            rectangleTop[i].setTranslateY(85);

            rectangleBot[i].setHeight(250);
            rectangleBot[i].setTranslateY(-178);

            coins[i].setTranslateY(00);
            //this else statement checks the case that int rand is 2
        } else if (rand == 2) {
            rectangleTop[i].setHeight(150);
            rectangleTop[i].setTranslateY(61);

            rectangleBot[i].setTranslateY(-192);
            rectangleBot[i].setHeight(150);

            coins[i].setTranslateY(-150);
        } else {
            rectangleTop[i].setHeight(120);
            rectangleTop[i].setTranslateY(50);

            rectangleBot[i].setHeight(120);
            rectangleBot[i].setTranslateY(200);

            coins[i].setCenterX(-100);
        }

        rectangleArrayList.add(rectangleTop[i]);
        rectangleArrayList.add(rectangleBot[i]);
        coinsArrayList.add(coins[i]);

    }
    /* The Method Bellow
     * The method will run if the play button is clicked
     * The code is the code for the play status of the game
     */
    public void runningGame() {
        score = 0;
        above = false;
        //High score value has to be get from the text and the value of High score has to be updated from 0 to the value in the text file
        try (BufferedReader br = new BufferedReader(new FileReader("HighScore.txt"))) {
            //A string is made named sCurrentLine
            String sCurrentLine;
            //The value of sCurrentLine changes to the value of the text file
            //while it is not empty, the value of high score is updared to the number in the text file
            while ((sCurrentLine = br.readLine()) != null) {
                //Setes the value of integer into the highscore
                highScore = Integer.parseInt(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentScore.setText("Current score: " + score);
        maxScore.setText("High Score: " + highScore);
        restart.setText("");
        game.getChildren().add(restart);
        //Checks if the reset Button is clicked
        //if it is clicked, runningGame method is run
        restart.setOnMouseClicked(e -> {
            game.getChildren().clear();
            move = true;
            runningGame();
        });
        //Checks if W is pressed
        //If W is pressed the jump method runs
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
        //https://i-cdn.phonearena.com/images/article/52185-image/Catch-the-pigeon-Here-are-the-best-and-worst-Flappy-Bird-clones.jpg
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
        position = new int[PIPE_COUNT];
        scoreCheck = new boolean[PIPE_COUNT];
        //Sets the text for the start instructions
        text = new Text("Press W to start");
        text.setTranslateY(-50);
        text.setFont(Font.font(java.awt.Font.SANS_SERIF, 50));
        text.setFill(Color.BLUE);

        game.getChildren().add(text);
        game.getChildren().add(birdView);
        game.getChildren().add(hitbox);
        game.getChildren().add(currentScore);
        game.getChildren().add(maxScore);

        int counter = 0;
        //Gets a rextangle for the pipes
        for (int i = 0; i < PIPE_COUNT; i++) {
            rectangleTop[i] = new Rectangle();
            rectangleBot[i] = new Rectangle();
            coins[i] = new Circle();
            rectangleTop[i].setFill(Color.rgb(53, 200, 0));
            rectangleBot[i].setFill(Color.rgb(53, 200, 0));
            coins[i].setFill(Color.YELLOW);
            coins[i].setRadius(12);
            rectangleTop[i].setWidth(75);
            rectangleBot[i].setWidth(75);
            rectangleTop[i].setTranslateX(650 + counter);
            rectangleBot[i].setTranslateX(650 + counter);
            coins[i].setTranslateX(650 + counter);

            position[i] = 650 + counter;
            counter += 250; // units between pipes (250 pixels)
            setSize(i, counter);
        }
        //Gets the pipes and coines
        for (int i = 0; i < PIPE_COUNT; i++) {
            game.getChildren().add(rectangleTop[i]);
            game.getChildren().add(rectangleBot[i]);
            game.getChildren().add(coins[i]);
        }
        runGame();
    }
    //The method is for the Jump function
    public void jump() {
        animation.start();
        gravity = -5.1;
        birdView.setManaged(false);
        hitbox.setManaged(false);
        for (int i = 0; i < PIPE_COUNT; i++) {
            rectangleTop[i].setManaged(false);
            rectangleBot[i].setManaged(false);
            coins[i].setManaged(false);
        }
        text.setText("");
    }

    //The code for the start of the game goes here
    public void runGame() {
        //The bloc below is the animation timer
        animation = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!check() || above) {
                    game.getChildren().add(gameOver);
                    //stops animation if bird hits pipe
                    animation.stop();
                    restart.setText("RESTART");
                    move = false;
                    //https://www.sounds-resource.com/mobile/flappybird/sound/5309/
                    HitSound();
                    player.stop();
                }
                // pipes move to the left
                pipes();
                // cap for gravity
                if (gravity >= 12) {
                    gravity = 12;
                } else {
                    // gravity acceleration of 0.26 per frame
                    gravity += 0.26;
                }
                // calls on update method and updates the position of the bird
                update();

                /*
                 * As the gravity increases, the rotation changes
                 * gravity = 1, 10 degree rotation
                 * gravity = 2, 20 degree rotation
                 * gravity = n, n*10 degree rotation
                 * etc...
                 */
                birdView.setRotate(gravity*10);
                hitbox.setRotate(gravity*10);
            }


        };

    }

    //Updates the location of the bird
    public void update() {
        birdView.setY(birdView.getY() + gravity);
        hitbox.setCenterY(hitbox.getCenterY() + gravity);
    }
    /*
     * Collision detection for hitbox (of bird) and pipes
     * This method goes through all the pipes and checks if the current pipe
     * is intersecting with the bird
     */
    public boolean birdCollidesPipe(Ellipse hitbox) {
        boolean collision = false;
        for (Rectangle rect : rectangleArrayList) {
            if (hitbox.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                collision = true;
            }
        }
        return collision;
    }

    /*
     * Checks for the intersection of coins with the hitbox (of bird)
     * If the bird DOES intersect with the coin, it sets collected to true
     * and sets the position of the coin to x = 100000. We did this so that it
     * no longer intersects with the bird.
     */
    public boolean coinCollected(Ellipse hitbox) {
        boolean collected = false;
        for (Circle coins : coinsArrayList) {
            if (hitbox.getBoundsInParent().intersects(coins.getBoundsInParent())) {
                collected = true;
                coins.setCenterX(100000);
            }
        }
        return collected;
    }
    /*
     * Checkes if the coin is collected
     * Checks if bird has gone the the pipes
     */
    public void pipes() {
        //checks if the bird has collected the coin, if so a point is added
        if (coinCollected(hitbox)) {
            score++;
            currentScore.setText("Current score: " + score);
            if (highScore<score){
                highScore = score;
                maxScore.setText("High Score: " + highScore);
            }
            //The point sound is added
            PointSound();
        }

        if (move) { // if game is still running, continue
            for (int i = 0; i < PIPE_COUNT; i++) {
                rectangleTop[i].setX(rectangleTop[i].getX() - 2.2); // moves top pipe by 2.2 units to the left every iteration
                rectangleBot[i].setX(rectangleBot[i].getX() - 2.2); // moves bottom pipe by 2.2 units to the left every iteration
                coins[i].setCenterX(coins[i].getCenterX() - 3.5);
                if (position[i] + rectangleTop[i].getX() > -25 &&
                        position[i] + rectangleTop[i].getX() < 25 && !scoreCheck[i]) {
                    /*
                     * Checks if bird above the pipes (screen)
                     * and sets above to true
                     */
                    if (birdView.getY() < -200) {
                        above = true;
                    }
                    /*
                     * If hitbox collides with coin, adds one(1) to
                     * score and runs the point added point.
                     */
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
                            //Outputs the value of Highscore into a text file
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
    //The method bellow gets the soundfx for the bird dying
    public void HitSound() {
        String filePath = Main.class.getResource("/Sounds/sfx_hit.wav").toString();
        Media song = new Media(filePath);
        hit = new MediaPlayer(song);
        hit.setCycleCount(1);
        hit.play();
        hit.setVolume(0.2);
    }
    //The method bellow gets the soundfx for the collection of a point
    public void PointSound() {
        String filePath = Main.class.getResource("/Sounds/sfx_point.wav").toString();
        Media song = new Media(filePath);
        point = new MediaPlayer(song);
        point.setCycleCount(1);
        point.play();
        point.setVolume(0.2);
    }

    /*
     * Checks if the bird is above y = 110 (floor)
     * and checks if it hitbox is intersecting with pipes or not
     */
    public boolean check() {
        boolean flag = true;
        if (hitbox.getCenterY() >= 110) {
            flag = false;
        }
        if (birdCollidesPipe(hitbox)) {
            flag = false;
        }
        return flag;
    }

    public static void main(String args[]) {
        launch(args);
    }
}
