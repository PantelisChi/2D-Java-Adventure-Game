package thegoldenapples;

import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.Group;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Pantelis Chintiadis
 */
public class TheGoldenApples extends Application {
    static final double WIDTH = 1280, HEIGHT = 720;
    int gameScore = 0;
    Text scoreText, scoreLabel;
    private Font scoreFont;
    private AudioClip aSound0, aSound1, aSound2, aSound3, aSound4;
    private URL aAudioFile0, aAudioFile1, aAudioFile2, aAudioFile3, aAudioFile4;
    private boolean up, down, left, right, wKey, aKey, sKey, dKey;
    Group root;
    private HBox buttonContainer;
    Archelaos oArchelaos;
    Ladon ladonas;
    Projectile electricBall,goldenApple;
    Prop oPR0, oPR1;
    PropH oPH0;
    PropV oPV0;
    PropB oPB0;
    Treasure aTR0, aTR0_1, aTR0_2, aTR0_3, aTR1, aTR1_1, aTR1_2, aTR1_3;
    private Scene scene;
    private Image splashScreen, instructionLayer, storyLayer, scoresLayer, backgroundSky;
    private Image oA0, oA1, oA2, oA3, oA4, oA5, oA6, oA7, oA8, oP0, oP1, aT0, aT1, aE0, iC0, iC1;
    private ImageView splashScreenBackplate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, storyButton;
    private Insets buttonContainerPadding;
    private GamePlayLoop gamePlayLoop;
    CastingDirector castDirector;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Golden Apples");
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
        sceneEventHandling();
        loadAudioAssets();
        loadImageAssets();
        createGameActors();
        addGameActorNodes();
        createCastingDirection();
        createSplashScreenNodes();
        addNodesToStackPane();
        createStartGameLoop();
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void sceneEventHandling() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP: up = true; break;
                case DOWN: down = true; break;
                case LEFT: left = true; break;
                case RIGHT: right = true; break;
                case W: wKey = true; break;
                case S: sKey = true; break;
                case A: aKey = true; break;
                case D: dKey = true; break;
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP: up = false; break;
                case DOWN: down = false; break;
                case LEFT: left = false; break;
                case RIGHT: right = false; break;
                case W: wKey = false; break;
                case S: sKey = false; break;
                case A: aKey = false; break;
                case D: dKey = false; break;
            }
        });
    }
    private void loadAudioAssets() {
        aAudioFile0 = getClass().getResource("/shock.wav");
        aSound0 = new AudioClip(aAudioFile0.toString());
        aAudioFile1 = getClass().getResource("/collect.wav");
        aSound1 = new AudioClip(aAudioFile1.toString());
        aAudioFile2 = getClass().getResource("/rock.wav");
        aSound2 = new AudioClip(aAudioFile2.toString());
        aAudioFile3 = getClass().getResource("/growl.wav");
        aSound3 = new AudioClip(aAudioFile3.toString());
        aAudioFile4 = getClass().getResource("/ost.wav");
        aSound4 = new AudioClip(aAudioFile4.toString());
    }
    private void loadImageAssets() {
        splashScreen = new Image("/TheGoldenApples.png", 1280, 720, true, false, true);
        instructionLayer = new Image("/instructions.png", 1280, 720, true, false, true);
        storyLayer = new Image("/story.png", 1280, 720, true, false, true);
        scoresLayer = new Image("/score.png", 1280, 720, true, false, true);
        backgroundSky = new Image("/sky.png", 1280, 720, true, false, true);
        oA0 = new Image("/sprite0.png", 98, 131, true, false, true);
        oA1 = new Image("/sprite1.png", 98, 131, true, false, true);
        oA2 = new Image("/sprite2.png", 98, 131, true, false, true);
        oA3 = new Image("/sprite3.png", 98, 131, true, false, true);
        oA4 = new Image("/sprite4.png", 98, 131, true, false, true);
        oA5 = new Image("/sprite5.png", 98, 131, true, false, true);
        oA6 = new Image("/sprite6.png", 98, 131, true, false, true);
        oA7 = new Image("/sprite7.png", 98, 131, true, false, true);
        oA8 = new Image("/sprite8.png", 98, 131, true, false, true);
        oP0 = new Image("/prop0.png", 158, 51, true, false, true);
        aT0 = new Image("/treasure1.png", 64, 64, true, false, true);
        aT1 = new Image("/treasure2.png", 54, 54, true, false, true);
        aE0 = new Image("/ladon.png", 200, 280, true, false, true);
        iC0 = new Image("/electricball2.png", 128, 128, true, false, true);
        iC1 = new Image("/treasure1.png", 64, 64, true, false, true);
    }
    private void createGameActors() {
        oArchelaos = new Archelaos(this, "M 64.18,-0.36 L 64.18,-0.36 56.73,13.27 56.73,13.27 56.73,13.27 56.55,25.09 56.36,25.09 56.18,25.09 48.55,26.55 48.55,26.55 48.55,26.55 37.45,36.18 37.45,36.18 37.45,36.18 39.64,53.45 39.64,53.45 39.64,53.45 24.18,75.09 24.18,75.09 24.18,75.09 13.64,58.36 13.64,58.36 13.64,58.36 2.00,62.18 2.00,62.18 2.00,62.18 2.18,64.91 2.36,64.91 2.55,64.91 11.09,65.45 11.09,65.45 11.09,65.45 17.64,83.82 17.45,83.82 17.27,83.82 3.45,95.64 3.09,95.82 2.73,96.00 2.91,102.73 3.09,103.09 3.27,103.45 12.36,108.36 12.36,108.18 12.36,108.00 40.55,84.18 40.55,84.18 40.55,84.18 43.82,129.09 43.82,129.09 43.82,129.09 54.73,129.45 54.73,129.45 54.73,129.45 50.18,122.55 50.18,122.55 50.18,122.55 59.82,59.82 59.82,59.82 59.82,59.82 66.55,47.09 66.55,47.09 66.55,47.09 74.73,50.91 74.73,50.91 74.73,50.91 82.18,50.55 82.18,50.55 82.18,50.55 94.00,28.36 94.00,28.36 94.00,28.36 91.45,23.09 91.45,23.09 91.45,23.09 86.91,25.09 86.73,25.27 86.55,25.45 76.73,41.45 76.73,41.45 76.73,41.45 68.36,33.82 68.36,33.82 68.36,33.82 73.09,28.73 73.09,28.73 73.09,28.73 74.73,1.27 74.73,1.27 Z",
                WIDTH/2, HEIGHT/2, oA0, oA1, oA2, oA3, oA4, oA5, oA6, oA7, oA8);
        ladonas = new Ladon(this, "M0 6 L0 52 70 52 70 70 70 93 115 45 115 0 84 0 68 16 Z", -520, 0, aE0);
        electricBall = new Projectile("M0 0 L0 128 128 128 128 0 Z", -120, 0, iC0);
        goldenApple = new Projectile("M0 0 L0 64 64 64 64 0 Z", -80, 0, iC1);
        oPR0 = new Prop("M0 0 L0 51 158 51 158 0 Z", 250, 276, oP0);
        oPR1 = new Prop("M0 0 L0 51 158 51 158 0 Z", 480, 516, oP0);
        oPH0 = new PropH("M0 0 L0 51 158 51 158 0 Z", 44, 135, oP0);
        oPV0 = new PropV("M0 0 L0 51 158 51 158 0 Z", 1045, 572, oP0);
        oPB0 = new PropB("M0 0 L0 51 158 51 158 0 Z", 744, 152, oP0);
        aTR0 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 70, 65, aT0);
        aTR0_1 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 140, 620, aT0);
        aTR0_2 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 1170, 15, aT0);
        aTR0_3 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 970, 600, aT0);
        aTR1 = new Treasure("M0 0 L0 54 54 54 54 0 Z", 1133, 476, aT1);
        aTR1_1 = new Treasure("M0 0 L0 54 54 54 54 0 Z", 265, 225, aT1);
        aTR1_2 = new Treasure("M0 0 L0 54 54 54 54 0 Z", 800, 100, aT1);
        aTR1_3 = new Treasure("M0 0 L0 54 54 54 54 0 Z", 400, 540, aT1); 
    }
    private void addGameActorNodes() {
        root.getChildren().add(oPR0.spriteFrame);
        root.getChildren().add(oPR1.spriteFrame);
        root.getChildren().add(oPH0.spriteFrame);
        root.getChildren().add(oPV0.spriteFrame);
        root.getChildren().add(oPB0.spriteFrame);
        root.getChildren().add(aTR0.spriteFrame);
        root.getChildren().add(aTR0_1.spriteFrame);
        root.getChildren().add(aTR0_2.spriteFrame);
        root.getChildren().add(aTR0_3.spriteFrame);
        root.getChildren().add(aTR1.spriteFrame);
        root.getChildren().add(aTR1_1.spriteFrame);
        root.getChildren().add(aTR1_2.spriteFrame);
        root.getChildren().add(aTR1_3.spriteFrame);
        root.getChildren().add(goldenApple.spriteFrame);
        root.getChildren().add(electricBall.spriteFrame);
        root.getChildren().add(ladonas.spriteFrame);
        root.getChildren().add(oArchelaos.spriteFrame);
    }
    private void createCastingDirection() {
        castDirector = new CastingDirector();
        castDirector.addCurrentCast(oPR0, oPR1, oPH0, oPV0, oPB0, aTR0, aTR1, aTR0_1, aTR0_2, aTR0_3, aTR1_1, aTR1_2, aTR1_3, ladonas, electricBall, goldenApple);
}
    private void createSplashScreenNodes() {
        scoreText = new Text();
        scoreText.setText(String.valueOf(gameScore));
        scoreText.setLayoutY(705);
        scoreText.setLayoutX(430);
        scoreFont = new Font("Verdana", 25);
        scoreText.setFont(scoreFont);
        scoreText.setFill(Color.BLUE);
        scoreLabel = new Text();
        scoreLabel.setText("ΣΚΟΡ:");
        scoreLabel.setLayoutY(705);
        scoreLabel.setLayoutX(340);
        scoreLabel.setFont(scoreFont);
        scoreLabel.setFill(Color.BLACK);        
        buttonContainer = new HBox(12);
        buttonContainer.setLayoutY(685);
        buttonContainerPadding = new Insets(0, 0, 10, 16);
        buttonContainer.setPadding(buttonContainerPadding);
        gameButton = new Button();
        gameButton.setText("ΠΑΙΧΝΙΔΙ");
        //gameButton.setLayoutX(0);
        gameButton.setOnAction((ActionEvent) -> { //lamdba εκφραση (αναγκαζει τον compiler να δημιουργισει ο ιδιος ενα ActionEvent object)
            splashScreenBackplate.setImage(backgroundSky);
            splashScreenBackplate.setVisible(true);
            splashScreenBackplate.toBack();
            splashScreenTextArea.setVisible(false);            
        });
        helpButton = new Button();
        helpButton.setText("ΟΔΗΓΙΕΣ");
        helpButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(instructionLayer);
            splashScreenTextArea.toFront();
            buttonContainer.toFront();
        });
        scoreButton = new Button();
        scoreButton.setText("ΒΑΘΜΟΛΟΓΙΑ");
        scoreButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(scoresLayer);
            splashScreenTextArea.toFront();
            buttonContainer.toFront();
        });
        storyButton = new Button();
        storyButton.setText("ΙΣΤΟΡΙΑ");
        storyButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(storyLayer);
            splashScreenTextArea.toFront();
            buttonContainer.toFront();
        });
        buttonContainer.getChildren().addAll(gameButton, helpButton, scoreButton, storyButton);
        splashScreenBackplate = new ImageView();
        splashScreenBackplate.setImage(splashScreen);
        splashScreenTextArea = new ImageView();
        splashScreenTextArea.setImage(instructionLayer);
    }
    private void addNodesToStackPane() {
        root.getChildren().add(splashScreenBackplate);
        root.getChildren().add(splashScreenTextArea);
        root.getChildren().add(buttonContainer);
        root.getChildren().add(scoreText);
        root.getChildren().add(scoreLabel);
    }
    private void createStartGameLoop() {
        gamePlayLoop = new GamePlayLoop(this);
        gamePlayLoop.start();
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean iswKey() {
        return wKey;
    }

    public void setwKey(boolean wKey) {
        this.wKey = wKey;
    }

    public boolean isaKey() {
        return aKey;
    }

    public void setaKey(boolean aKey) {
        this.aKey = aKey;
    }

    public boolean issKey() {
        return sKey;
    }

    public void setsKey(boolean sKey) {
        this.sKey = sKey;
    }

    public boolean isdKey() {
        return dKey;
    }

    public void setdKey(boolean dKey) {
        this.dKey = dKey;
    }

    public void playaSound0() {
        this.aSound0.play();
    }

    public void playaSound1() {
        this.aSound1.play();
    }

    public void playaSound2() {
        this.aSound2.play();
    }

    public void playaSound3() {
        this.aSound3.play();
    }

    public void playaSound4() {
        this.aSound4.play();
    }
    
}
