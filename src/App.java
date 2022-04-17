import java.io.File;
import java.util.Random;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
    Scene scene;
    GridPane gp;
    StackPane sp;
    HBox controlsHb;
    Timeline tline;
    Text t;
    Image splashImage;
    AudioClip click, error;
    Random random = new Random();
    boolean shiftPressed = true;
    boolean includeCapitalLetters = false;
    boolean includeNumbers = false;
    boolean includeSpecialCharacters = false;
    int specialCharacterTypeCounter = 1;
    int yPos = 0;
    int score = 0;
    int wrongAttempts = 0;
    double speed = 100.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        click = new AudioClip(new File("src/sounds/click.mp3").toURI().toString());
        error = new AudioClip(new File("src/sounds/error.wav").toURI().toString());

        Canvas canvas = new Canvas(700, 600);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        tline = new Timeline(new KeyFrame(Duration.millis(speed), e -> run(ctx)));
        tline.setCycleCount(Timeline.INDEFINITE);

        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setVgap(15);
        gp.setHgap(15);

        Text title = new Text("Typing Game");
        title.setFont(Font.font("ubuntu", FontWeight.BOLD, 25));
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setMargin(title, new Insets(0, 0, 20, 0));
        gp.add(title, 0, 0, 2, 1);

        HBox speedHb = new HBox();
        Label speedLabel = new Label("Speed ");
        Label speedMeasureLabel = new Label("<in ms>");
        speedLabel.setFont(Font.font("ubuntu", FontWeight.BOLD, 13));
        speedMeasureLabel.setFont(Font.font("ubuntu", FontWeight.NORMAL, 12));
        speedHb.getChildren().addAll(speedLabel, speedMeasureLabel);
        gp.add(speedHb, 0, 1);

        Slider slider = new Slider();
        slider.setMin(50);
        slider.setMax(150);
        slider.setValue(100);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(50);
        slider.setBlockIncrement(50);
        GridPane.setMargin(slider, new Insets(0, 0, 10, 30));
        gp.add(slider, 0, 2);

        Label charTypeLabel = new Label("Type of Characters to Include");
        charTypeLabel.setFont(Font.font("ubuntu", FontWeight.BOLD, 13));
        gp.add(charTypeLabel, 0, 3);

        JFXCheckBox capLettersCB = new JFXCheckBox("Include Capital Letters");
        capLettersCB.setFont(Font.font("ubuntu", FontWeight.NORMAL, 13));
        GridPane.setMargin(capLettersCB, new Insets(0, 0, 10, 30));
        gp.add(capLettersCB, 0, 4);

        JFXCheckBox numbersCB = new JFXCheckBox("Include Numbers");
        numbersCB.setFont(Font.font("ubuntu", FontWeight.NORMAL, 13));
        GridPane.setMargin(numbersCB, new Insets(0, 0, 10, 30));
        gp.add(numbersCB, 0, 5);

        JFXCheckBox specialCharsCB = new JFXCheckBox("Include Special Characters");
        specialCharsCB.setFont(Font.font("ubuntu", FontWeight.NORMAL, 13));
        GridPane.setMargin(specialCharsCB, new Insets(0, 0, 10, 30));
        gp.add(specialCharsCB, 0, 6);

        JFXButton startButton = new JFXButton("Start");
        startButton.setStyle(
                "-fx-padding: 0.4em 0.3em; -fx-font-size: 13px; -jfx-button-type: RAISED; -fx-background-color: rgb(77,102,204); -fx-pref-width: 200; -fx-text-fill: WHITE; ");
        startButton.setFont(Font.font("ubuntu"));
        startButton.setPrefWidth(200);
        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setMargin(startButton, new Insets(10, 0, 20, 0));
        gp.add(startButton, 0, 7, 2, 1);

        t = new Text();
        generateNextCharacter();

        sp = new StackPane(canvas);

        controlsHb = new HBox();
        controlsHb.setPadding(new Insets(10, 10, 10, 10));
        controlsHb.setSpacing(10);

        JFXButton homeButton = new JFXButton();
        Image homeImage = new Image(getClass().getResourceAsStream("images/house.png"), 70, 70, true, true);
        homeButton.setGraphic(new ImageView(homeImage));

        JFXButton reloadButton = new JFXButton();
        Image reloadImage = new Image(getClass().getResourceAsStream("images/refresh.png"), 70, 70, true, true);
        reloadButton.setGraphic(new ImageView(reloadImage));

        controlsHb.getChildren().addAll(homeButton, reloadButton);
        controlsHb.setAlignment(Pos.CENTER);

        splashImage = new Image(getClass().getResourceAsStream("images/splash.png"), 100, 100, true, true);

        scene = new Scene(gp, 700, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Typing Game");
        primaryStage.setResizable(false);

        slider.valueProperty().addListener((ov, old_value, new_value) -> {
            speed = (Double) new_value;
        });

        capLettersCB.selectedProperty().addListener((ov, old_value, new_value) -> {
            includeCapitalLetters = new_value;
        });

        numbersCB.selectedProperty().addListener((ov, old_value, new_value) -> {
            includeNumbers = new_value;
        });

        specialCharsCB.selectedProperty().addListener((ov, old_value, new_value) -> {
            includeSpecialCharacters = new_value;
        });

        startButton.setOnAction(e -> {
            sp.getChildren().remove(controlsHb);
            scene.setRoot(sp);
            generateNextCharacter();
            yPos = -10;
            score = 0;
            wrongAttempts = 0;
            tline = new Timeline(new KeyFrame(Duration.millis(speed), te -> run(ctx)));
            tline.setCycleCount(Timeline.INDEFINITE);
            tline.play();
        });

        homeButton.setOnAction(e -> {
            scene.setRoot(gp);
        });

        reloadButton.setOnAction(e -> {
            sp.getChildren().remove(controlsHb);
            score = 0;
            wrongAttempts = 0;
            generateNextCharacter();
            tline.play();
        });

        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                tline.stop();
                scene.setRoot(gp);
            } else if (!e.isShiftDown() && e.getText().equals(t.getText())) {
                success();
            } else if (e.isShiftDown() && KeyCodeMacher.match(e.getCode(), t)) {
                success();
            } else {
                if (!e.getCode().isModifierKey())
                    wrongAttempts += 1;
                else
                    e.consume();
            }
        });
    }

    private void generateNextCharacter() {
        int ascii, characterType;
        characterType = random.nextInt(0, 4);

        if (characterType == 1 && includeCapitalLetters)
            ascii = random.nextInt(65, 91);
        else if (characterType == 2 && includeNumbers)
            ascii = random.nextInt(48, 58);
        else if (characterType == 3 && includeSpecialCharacters) {
            switch (specialCharacterTypeCounter) {
                case 1:
                    ascii = random.nextInt(33, 48);
                    break;
                case 2:
                    ascii = random.nextInt(58, 65);
                    break;
                case 3:
                    ascii = random.nextInt(91, 97);
                    break;
                case 4:
                    ascii = random.nextInt(123, 127);
                    break;
                default:
                    ascii = random.nextInt(33, 48);
            }
            ++specialCharacterTypeCounter;
        } else
            ascii = random.nextInt(97, 123);

        char charValue = (char) ascii;
        t.setText(String.valueOf(charValue));
    }

    private void success() {
        generateNextCharacter();
        yPos = -10;
        score += 1;
        click.play();
    }

    private void run(GraphicsContext ctx) {
        yPos += 10;
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, 700, 600);

        ctx.setStroke(Color.WHITE);
        ctx.setFont(Font.font(30));
        ctx.setFill(Color.WHITE);
        ctx.fillText(t.getText(), 350 - 15, yPos);

        ctx.setFont(Font.font(12));
        ctx.setFill(Color.YELLOW);
        ctx.fillText("Speed: <" + (int) speed + " ms>", 500, 50);
        ctx.fillText("Score: " + score, 500, 80);
        ctx.fillText("Wrong Attempts: " + wrongAttempts, 500, 110);

        if (yPos == 600) {
            ctx.drawImage(splashImage, 300, 530, 100, 100);
            error.play();
            yPos = -10;
            sp.getChildren().add(controlsHb);
            tline.stop();
        }
    }
}