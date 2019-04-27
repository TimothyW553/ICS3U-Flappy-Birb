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
    public static ArrayList<Rectangle> rectangles;
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
        root         = new StackPane();
        game         = new StackPane();
        instructions = new StackPane();

        titleScene       = new Scene(root, 750, 450);
        gameScene        = new Scene(game, 750, 450);
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

        MusicPlayer("/Sounds/In the Hall of the Mountain King.mp3");

        window.setTitle("Flappy Pappy");
        window.setScene(titleScene);
        window.show();

    }

    public static void setSize(int idx, int counter) {
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

    public static void runningGame() {

    }

    public static void MusicPlayer(String musicPath) {
        musicPath = Main.class.getResource(musicPath).toString();
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
