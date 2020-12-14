/*
 * File name:  GameController.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.util.Duration;
import main.org.botka.pong.backnd.Ball;
import main.org.botka.pong.backnd.KeyControl;
import main.org.botka.pong.backnd.Player;

/**
 * Controller class that communicates between the back end model and the front end framework.
 *
 * @author Jake Botka
 *
 */
public final class GameController extends AppDriver {
	private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
	public static double FRAMES_PER_SECONDS = 120;
	public static double FRAME_PER_SECOND = 1 / FRAMES_PER_SECONDS;
	public static volatile int FpsDisplay = 0;

	public static LocalTime currentTime = LocalTime.now();

	private boolean player1Up = false;
	private boolean player1Down = false;
	private boolean player2Up = false;
	private boolean player2Down = false;
	private Pane gamePane;

	private Player player1;
	private Player player2;
	private Ball gameBall;

	private Rectangle player1Paddle;
	private Rectangle player2Paddle;

	private ImageView ballSprite;
	private ImageView PaddleSprite;

	public static Line player1GoalLine;
	public static Line player2GoalLine;
	public static Line screenLine1;
	public static Line screenLine2;

	public static Label score1 = new Label("0");
	public static Label score2 = new Label("0");

	private GraphicsContext gc;
	private Canvas canvas;

	public GameController() {
		File file = new File("/Pong/src/edu/ilstu/ballSprite.png");
		String url = file.getPath();
		System.out.println(url);
		initialize();

		ballSprite = new ImageView("file:///Pong/src/edu/ilstu/ballSprite.png");

		ballSprite.setLayoutX(100);
		ballSprite.setLayoutY(100);

		// gamePane.getChildren().add(ballSprite);
		ballSprite.toFront();

//		ballSprite.layoutXProperty().bindBidirectional(gameBall.layoutXProperty());
//		ballSprite.layoutYProperty().bindBidirectional(gameBall.layoutYProperty());
		updaterInit();
	}

	@FXML
	public void initialize() {

		gamePane = new Pane();
		gamePane.setMinSize(1500, 1000);
		canvas = new Canvas(1500, 1000);
		gc = canvas.getGraphicsContext2D();

		gamePane.getChildren().add(canvas);
		appScene = new Scene(gamePane);
		primaryStage.setScene(appScene);
		primaryStage.show();

		player1 = new Player(KeyCode.W, KeyCode.S);
		player2 = new Player(KeyCode.P, KeyCode.L);
		gameBall = new Ball(gamePane);

		gameBall.setVisible(false);
		gameBall.setDisable(true);

		gameBall.applyCss();

		player1Paddle = player1.getPlayerPaddle().getPaddle();
		player2Paddle = player2.getPlayerPaddle().getPaddle();

		gamePane.getChildren().add(player1.getPlayerPaddle().getPaddle());
		gamePane.getChildren().add(player2.getPlayerPaddle().getPaddle());
		gamePane.getChildren().add(gameBall);

		
		canvas.toFront();
		

		player1Paddle.setLayoutX((gamePane.getLayoutBounds().getMinX() + 100));
		player1Paddle.setLayoutY(gamePane.getLayoutBounds().getMaxY() / 2 - 50);
		player2Paddle.setLayoutX(gamePane.getLayoutBounds().getMaxX() - 100);
		player2Paddle.setLayoutY(gamePane.getLayoutBounds().getMaxY() / 2 - 50);
		player1.getPlayerPaddle().setCord(new Point2D(player1Paddle.getLayoutX(), player1Paddle.getLayoutY()));
		player2.getPlayerPaddle().setCord(new Point2D(player2Paddle.getLayoutX(), player2Paddle.getLayoutY()));

		screenLine1 = new Line();
		screenLine1.setStartX(0);
		screenLine1.setEndX(gamePane.getLayoutBounds().getMaxX());
		screenLine1.setLayoutX(0);
		screenLine1.setLayoutY(gamePane.getLayoutBounds().getMaxY());
		screenLine2 = new Line();
		screenLine2.setStartX(0);
		screenLine2.setEndX(gamePane.getLayoutBounds().getMaxX());
		screenLine2.setLayoutX(0);
		screenLine2.setLayoutY(gamePane.getLayoutBounds().getMinY());
		gamePane.getChildren().add(screenLine2);
		player1GoalLine = new Line();
		player1GoalLine.setLayoutX(0);
		player1GoalLine.setLayoutY(0);
		player1GoalLine.setStartY(gamePane.getLayoutBounds().getMinY());
		player1GoalLine.setEndY(gamePane.getLayoutBounds().getMaxY());
		player2GoalLine = new Line();
		player2GoalLine.setLayoutX(gamePane.getLayoutBounds().getMaxX());
		player2GoalLine.setLayoutY(0);
		player2GoalLine.setStartY(gamePane.getLayoutBounds().getMinY());
		player2GoalLine.setEndY(gamePane.getLayoutBounds().getMaxY());

		score2.relocate(gamePane.getLayoutBounds().getMaxX() / 2 - 50, gamePane.getLayoutBounds().getMaxY() - 100);
		score2.setFont(Font.font(50));

		score1.relocate(gamePane.getLayoutBounds().getMaxX() / 2 + 50, gamePane.getLayoutBounds().getMaxY() - 100);
		score1.setFont(Font.font(50));

		gamePane.getChildren().add(player1GoalLine);
		gamePane.getChildren().add(player2GoalLine);
		gamePane.getChildren().add(score1);
		gamePane.getChildren().add(score2);

		;

		appScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				KeyControl<KeyCode> playerOnePaddleUpControl = (KeyControl<KeyCode>)player1.getPaddleUpControl();
				KeyControl<KeyCode> playerOnePaddleDownControl = (KeyControl<KeyCode>)player1.getPaddleDownControl();
				KeyControl<KeyCode> playerTwoPaddleUpControl = (KeyControl<KeyCode>)player2.getPaddleUpControl();
				KeyControl<KeyCode> playerTwoPaddleDownControl = (KeyControl<KeyCode>)player2.getPaddleDownControl();
				System.out.println(event.getCharacter());
				if (event.getCode().equals(playerOnePaddleUpControl.getControl())) {
					if (player1Up != true) {
						playerOnePaddleUpControl.sendControlActivatedEvent(playerOnePaddleUpControl.getControl(), System.currentTimeMillis());
						player1Up = true;
					}

				} else if (event.getCode().equals(playerOnePaddleDownControl.getControl())) {
					if (player1Down != true) {
						playerOnePaddleDownControl.sendControlActivatedEvent(playerOnePaddleDownControl.getControl(), System.currentTimeMillis());
						player1Down = true;
					}

				}
				if (event.getCode().equals(playerTwoPaddleUpControl.getControl())) {
					if (player2Up != true) {
						playerTwoPaddleUpControl.sendControlActivatedEvent(playerTwoPaddleUpControl.getControl(), System.currentTimeMillis());
						player2Up = true;
					}

				} else if (event.getCode().equals(playerTwoPaddleDownControl.getControl())) {
					if (player2Down != true) {
						playerTwoPaddleDownControl.sendControlActivatedEvent(playerTwoPaddleDownControl.getControl(), System.currentTimeMillis());
						player2Down = true;
					}

				}

			}

		});
		appScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.W) {
					player1Up = false;

				} else if (event.getCode() == KeyCode.S) {
					player1Down = false;

				}
				if (event.getCode() == KeyCode.P) {

					player2Up = false;

				} else if (event.getCode() == KeyCode.L) {
					player2Down = false;
				}

			}

		});

	}

	/**
	 * Initializes the updater.
	 */
	public void updaterInit() {
		final Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(FRAME_PER_SECOND), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {

						if (gameBall.isCanMove() == true) {
							
							gameBall.constantMove(gameBall.getCenterX(), gameBall.getCenterY(), gc);
							gc.clearRect(0, 0, 2000, 2000);

							gameBall.setCenterX(gameBall.getXLocation());
							gameBall.setCenterY(gameBall.getYLocation());

							gc.fillOval(gameBall.getCenterX(), gameBall.getCenterY(), gameBall.getRadius() * 2,
									gameBall.getRadius() * 2);
						}

						checkCollisions();

						if (gameBall.getCenterX() < gamePane.getLayoutBounds().getMinX()
								|| gameBall.getCenterX() > gamePane.getLayoutBounds().getMinX()) {
							// gc.clearRect(0, 0, 2000, 2000);
							// gameBall.reset(gamePane);

						} else if (gameBall.getCenterY() < gamePane.getLayoutBounds().getMinY()
								|| gameBall.getCenterY() > gamePane.getLayoutBounds().getMinY()) {
							// gc.clearRect(0, 0, 2000, 2000);
							// gameBall.reset(gamePane);
						}

						if (player1Up == true && player1.getPlayerPaddle().canMoveUp() == true) {
							player1.getPlayerPaddle().moveUp();
						} else {
							player1Up = false;

						}
						if (player1Down == true && player1.getPlayerPaddle().canMoveDown() == true) {
							player1.getPlayerPaddle().moveDown();
						} else {
							player1Down = false;

						}
						if (player2Up == true && player2.getPlayerPaddle().canMoveUp() == true) {
							player2.getPlayerPaddle().moveUp();
						} else {
							player2Up = false;

						}
						if (player2Down == true && player2.getPlayerPaddle().canMoveDown() == true) {
							player2.getPlayerPaddle().moveDown();
						} else {
							player2Down = false;
						}

						player1Paddle.setLayoutX(player1.getPlayerPaddle().getXCord());
						player1Paddle.setLayoutY(player1.getPlayerPaddle().getYCord());
						player2Paddle.setLayoutX(player2.getPlayerPaddle().getXCord());
						player2Paddle.setLayoutY(player2.getPlayerPaddle().getYCord());

						FpsDisplay += 1;
//			           gay = (double)LocalTime.now().get(ChronoField.MILLI_OF_SECOND) / 999 - gay;
//			           System.out.println(gay);
					}
				}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		this.initFPSDisplay();
	}
	
	public void initFPSDisplay() {
		Timeline oneSec = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@SuppressWarnings("deprecation")
			@Override
			public void handle(ActionEvent event) {
				//System.out.println(FpsDisplay);
				//score1.setText(Integer.toString(FpsDisplay));
				FpsDisplay = 0;

			}
		}));
		oneSec.setCycleCount(Animation.INDEFINITE);
		oneSec.play();
	}
	
	public final void checkCollisions() {
		double a1 = 0.0;
		double a2 = 0.0;
		double a3 = 0.0;
		double y = 0.0;
		double z = 0.0;
		if (player1Paddle.getBoundsInParent().intersects(gameBall.getBoundsInParent())) {
			a1 = (player1Paddle.getHeight() * 1 / 3);
			a2 = (player1Paddle.getHeight() * 2 / 3);
			a3 = (player1Paddle.getHeight() * 3 / 3);
			y = player1Paddle.getLayoutY();
			z = gameBall.getCenterY();
			a1 = y + a1;
			a2 = y + a2;
			a3 = y + a3;
			if (z >= y && z <= a1 && z <= a3) {
				gameBall.hitPaddle(0.5, -0.5);
				//System.out.println("HUp");
			} else if (z > a1 && z <= a2 && z <= a3) {
				gameBall.hitPaddle(1.0, 0.1);
				//System.out.println("HCenter");
			} else if (z > a2 && z <= a3) {
				gameBall.hitPaddle(0.5, 0.5);
				//System.out.println("Hdown");

			} else {
				System.out.println("did not hit front paddle");

			}

			//System.out.println(z + " " + y + "  " + a1 + "  " + a2 + "  " + a3);
			gameBall.setCenterX(gameBall.getXLocation());
			gameBall.setCenterY(gameBall.getYLocation());
		} else if (player2Paddle.getBoundsInParent().intersects(gameBall.getBoundsInParent())) {
			a1 = (player2Paddle.getHeight() * 1 / 3);
			a2 = (player2Paddle.getHeight() * 2 / 3);
			a3 = (player2Paddle.getHeight() * 3 / 3);
			y = player2Paddle.getLayoutY();
			z = gameBall.getCenterY();
			a1 = y + a1;
			a2 = y + a2;
			a3 = y + a3;

			if (z >= y && z <= a1 && z <= a3) {
				gameBall.hitPaddle(-0.5, -0.5);
				System.out.println("HUp");
			} else if (z > a1 && z <= a2 && z <= a3) {
				gameBall.hitPaddle(-0.5, 0.1);
				System.out.println("HCenter");
			} else if (z > a2 && z <= a3) {
				gameBall.hitPaddle(-0.5, 0.5);
				System.out.println("Hdown");

			} else {
				System.out.println("did not hit front paddle");

			}
			//System.out.println("Paddle 2" + z + " " + y + "  " + a1 + "  " + a2 + "  " + a3);
			gameBall.setCenterX(gameBall.getXLocation());
			gameBall.setCenterY(gameBall.getYLocation());
		}
		if (player1GoalLine.getBoundsInParent().intersects(gameBall.getBoundsInParent())) {
			player1.playerScoredGoal();
			gameBall.reset();
			gameBall.setCenterX(gameBall.getXLocation());
			gameBall.setCenterY(gameBall.getYLocation());
			System.out.println("Scored");
			gc.clearRect(0, 0, 2000, 2000);
			gc.fillOval(gameBall.getCenterX(), gameBall.getCenterY(), gameBall.getRadius() * 2,
					gameBall.getRadius() * 2);
			int score = Integer.parseInt(score1.getText());
			score += 1;
			score1.setText(Integer.toString(score));
		} else if (gameBall.getBoundsInParent().intersects(player2GoalLine.getBoundsInParent())) {
			player2.playerScoredGoal();
			gameBall.reset();
			gameBall.setCenterX(gameBall.getXLocation());
			gameBall.setCenterY(gameBall.getYLocation());
			gc.clearRect(0, 0, 2000, 2000);
			gc.fillOval(gameBall.getCenterX(), gameBall.getCenterY(), gameBall.getRadius() * 2,
					gameBall.getRadius() * 2);

			System.out.println("Scored");
			int score = Integer.parseInt(score2.getText());
			score += 1;
			score2.setText(Integer.toString(score));
		}
	}

}
