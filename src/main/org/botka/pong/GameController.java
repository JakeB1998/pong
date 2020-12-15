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
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import main.org.botka.pong.backnd.Ball;
import main.org.botka.pong.backnd.Paddle;
import main.org.botka.pong.backnd.Player;
import main.org.botka.pong.backnd.Player.Side;
import main.org.botka.pong.backnd.controls.KeyControl;

/**
 * Controller class that communicates between the back end model and the front end framework.
 *
 * @author Jake Botka
 *
 */
public final class GameController  {
	private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
	public static double FRAMES_PER_SECONDS = 120;
	public static double FRAME_PER_SECOND = 1 / FRAMES_PER_SECONDS;
	public static volatile int FpsDisplay = 0;

	public static LocalTime currentTime = LocalTime.now();

	private Stage mStage;
	private Scene mScene;
	private boolean mPlayerOneUp = false;
	private boolean mPlayerOneDown = false;
	private boolean mPlayerTwoUp = false;
	private boolean mPlayerTwoDown = false;
	private Pane mGamePane;

	private Player mPlayerOne;
	private Player mPlayerTwo;
	private Ball mGameBall;

	private Rectangle mPlayerOnePaddle;
	private Rectangle mPlayerTwoPaddle;

	private ImageView mBallSprite;
	

	private Line mPlayerOneGoalLine;
	private Line mPlayerTwoGoalLine;
	private Line mScreenLineOne;
	private Line mScreenLineTwo;
	
	private Bounds mPlayerOneGoalLineBounds;
	public Bounds mPlayerTwoGoalLineBounds;
	public Bounds mScreenLineOneBounds;
	public Bounds mScreenLineTwoBounds;

	public Label mScoreLabelOne = new Label("0");
	public Label mScoreLabelTwo = new Label("0");

	
	private GraphicsContext mGC;
	private Canvas mCanvas;

	/**
	 * Constructor.
	 * @param stage Stage object.
	 */
	public GameController(Stage stage) {
		this.mStage = stage;
		File file = new File("/Pong/src/edu/ilstu/ballSprite.png");
		String url = file.getPath();
		System.out.println(url);
		initialize();
		mBallSprite = new ImageView("file:///Pong/src/edu/ilstu/ballSprite.png");
		mBallSprite.setLayoutX(100);
		mBallSprite.setLayoutY(100);
		mBallSprite.toFront();

//		ballSprite.layoutXProperty().bindBidirectional(gameBall.layoutXProperty());
//		ballSprite.layoutYProperty().bindBidirectional(gameBall.layoutYProperty());
		updaterInit();
	}

	/**
	 * Initializes FXMl loading.
	 */
	@FXML
	public void initialize() {
		mGamePane = new Pane();
		mGamePane.setMinSize(1500, 1000);
		mCanvas = new Canvas(1500, 1000);
		mGC = mCanvas.getGraphicsContext2D();

		mGamePane.getChildren().add(mCanvas);
		mScene = new Scene(mGamePane);
		mStage.setScene(mScene);
		mStage.show();

		mPlayerOne = new Player(KeyCode.W, KeyCode.S, Side.Left);
		mPlayerTwo = new Player(KeyCode.P, KeyCode.L, Side.Right);
		
		mGameBall = new Ball(mGamePane);
		mGameBall.setVisible(false);
		mGameBall.setDisable(true);
		mGameBall.applyCss();

		mPlayerOnePaddle = mPlayerOne.getPlayerPaddle().getPaddle();
		mPlayerTwoPaddle = mPlayerTwo.getPlayerPaddle().getPaddle();

		mGamePane.getChildren().add(mPlayerOne.getPlayerPaddle().getPaddle());
		mGamePane.getChildren().add(mPlayerTwo.getPlayerPaddle().getPaddle());
		mGamePane.getChildren().add(mGameBall);
		//Ensures that canvas is visible
		mCanvas.toFront();

		mPlayerOnePaddle.setLayoutX((mGamePane.getLayoutBounds().getMinX() + 100));
		mPlayerOnePaddle.setLayoutY(mGamePane.getLayoutBounds().getMaxY() / 2 - 50);
		mPlayerTwoPaddle.setLayoutX(mGamePane.getLayoutBounds().getMaxX() - 100);
		mPlayerTwoPaddle.setLayoutY(mGamePane.getLayoutBounds().getMaxY() / 2 - 50);
		mPlayerOne.getPlayerPaddle().setCord(new Point2D(mPlayerOnePaddle.getLayoutX(), mPlayerOnePaddle.getLayoutY()));
		mPlayerTwo.getPlayerPaddle().setCord(new Point2D(mPlayerTwoPaddle.getLayoutX(), mPlayerTwoPaddle.getLayoutY()));
		//Adds boundry lines for the screen edges to check for collisions.
		mScreenLineOne = new Line();
		mScreenLineOne.setStartX(0);
		mScreenLineOne.setEndX(mGamePane.getLayoutBounds().getMaxX());
		mScreenLineOne.setLayoutX(0);
		mScreenLineOne.setLayoutY(mGamePane.getLayoutBounds().getMaxY());
		mScreenLineTwo = new Line();
		mScreenLineTwo.setStartX(0);
		mScreenLineTwo.setEndX(mGamePane.getLayoutBounds().getMaxX());
		mScreenLineTwo.setLayoutX(0);
		mScreenLineTwo.setLayoutY(mGamePane.getLayoutBounds().getMinY());
		
		mGamePane.getChildren().add(mScreenLineTwo);
		
		mPlayerOneGoalLine = new Line();
		mPlayerOneGoalLine.setLayoutX(0);
		mPlayerOneGoalLine.setLayoutY(0);
		mPlayerOneGoalLine.setStartY(mGamePane.getLayoutBounds().getMinY());
		mPlayerOneGoalLine.setEndY(mGamePane.getLayoutBounds().getMaxY());
		mPlayerTwoGoalLine = new Line();
		mPlayerTwoGoalLine.setLayoutX(mGamePane.getLayoutBounds().getMaxX());
		mPlayerTwoGoalLine.setLayoutY(0);
		mPlayerTwoGoalLine.setStartY(mGamePane.getLayoutBounds().getMinY());
		mPlayerTwoGoalLine.setEndY(mGamePane.getLayoutBounds().getMaxY());
		
		mScreenLineOneBounds = mScreenLineOne.getBoundsInParent();
		mScreenLineTwoBounds = mScreenLineTwo.getBoundsInParent();
		mPlayerOneGoalLineBounds = mPlayerOneGoalLine.getBoundsInParent();
		mPlayerTwoGoalLineBounds = mPlayerTwoGoalLine.getBoundsInParent();

		mScoreLabelTwo.relocate(mGamePane.getLayoutBounds().getMaxX() / 2 - 50, mGamePane.getLayoutBounds().getMaxY() - 100);
		mScoreLabelTwo.setFont(Font.font(50));

		mScoreLabelOne.relocate(mGamePane.getLayoutBounds().getMaxX() / 2 + 50, mGamePane.getLayoutBounds().getMaxY() - 100);
		mScoreLabelOne.setFont(Font.font(50));

		mGamePane.getChildren().add(mPlayerOneGoalLine);
		mGamePane.getChildren().add(mPlayerTwoGoalLine);
		mGamePane.getChildren().add(mScoreLabelOne);
		mGamePane.getChildren().add(mScoreLabelTwo);
		KeyControl<KeyCode> playerOnePaddleUpControl = (KeyControl<KeyCode>)mPlayerOne.getPaddleUpControl();
		KeyControl<KeyCode> playerOnePaddleDownControl = (KeyControl<KeyCode>)mPlayerOne.getPaddleDownControl();
		KeyControl<KeyCode> playerTwoPaddleUpControl = (KeyControl<KeyCode>)mPlayerTwo.getPaddleUpControl();
		KeyControl<KeyCode> playerTwoPaddleDownControl = (KeyControl<KeyCode>)mPlayerTwo.getPaddleDownControl();
		mScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				System.out.println(event.getCharacter());
				if (event.getCode().equals(playerOnePaddleUpControl.getControl())) {
					if (mPlayerOneUp != true) {
						playerOnePaddleUpControl.sendControlActivatedEvent(playerOnePaddleUpControl.getControl(), System.currentTimeMillis());
						mPlayerOneUp = true;
					}

				} else if (event.getCode().equals(playerOnePaddleDownControl.getControl())) {
					if (mPlayerOneDown != true) {
						playerOnePaddleDownControl.sendControlActivatedEvent(playerOnePaddleDownControl.getControl(), System.currentTimeMillis());
						mPlayerOneDown = true;
					}

				}
				if (event.getCode().equals(playerTwoPaddleUpControl.getControl())) {
					if (mPlayerTwoUp != true) {
						playerTwoPaddleUpControl.sendControlActivatedEvent(playerTwoPaddleUpControl.getControl(), System.currentTimeMillis());
						mPlayerTwoUp = true;
					}

				} else if (event.getCode().equals(playerTwoPaddleDownControl.getControl())) {
					if (mPlayerTwoDown != true) {
						playerTwoPaddleDownControl.sendControlActivatedEvent(playerTwoPaddleDownControl.getControl(), System.currentTimeMillis());
						mPlayerTwoDown = true;
					}
				}
			}
		});
		
		mScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (event.getCode().equals(playerOnePaddleUpControl.getControl())) {
					mPlayerOneUp = false;
				} else if (event.getCode().equals(playerOnePaddleDownControl.getControl())) {
					mPlayerOneDown = false;
				}
				if (event.getCode().equals(playerTwoPaddleUpControl.getControl())) {
					mPlayerTwoUp = false;
				} else if (event.getCode().equals(playerTwoPaddleDownControl.getControl())) {
					mPlayerTwoDown = false;
				}
			}
		});
	}

	/**
	 * Initializes the updater.
	 */
	public void updaterInit() {
		this.resetGameBall(null);
		final Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(FRAME_PER_SECOND), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						render();
						if (mGameBall.isCanMove() == true) {
							mGameBall.constantMove(mGameBall.getCenterX(), mGameBall.getCenterY(), mGC);
							mGC.clearRect(0, 0, 2000, 2000);
							mGameBall.setCenterX(mGameBall.getXLocation());
							mGameBall.setCenterY(mGameBall.getYLocation());
							mGC.fillOval(mGameBall.getCenterX(), mGameBall.getCenterY(), mGameBall.getRadius() * 2,
									mGameBall.getRadius() * 2);
						}
						checkCollisions();
						if (mGameBall.getCenterX() < mGamePane.getLayoutBounds().getMinX()
								|| mGameBall.getCenterX() > mGamePane.getLayoutBounds().getMinX()) {
							// gc.clearRect(0, 0, 2000, 2000);
							// gameBall.reset(gamePane);

						} else if (mGameBall.getCenterY() < mGamePane.getLayoutBounds().getMinY()
								|| mGameBall.getCenterY() > mGamePane.getLayoutBounds().getMinY()) {
							// gc.clearRect(0, 0, 2000, 2000);
							// gameBall.reset(gamePane);
						}

						if (mPlayerOneUp && mPlayerOne.getPlayerPaddle().canMoveUp()) {
							mPlayerOne.getPlayerPaddle().moveUp();
						} else {
							mPlayerOneUp = false;
						}
						if (mPlayerOneDown  && mPlayerOne.getPlayerPaddle().canMoveDown()) {
							mPlayerOne.getPlayerPaddle().moveDown();
						} else {
							mPlayerOneDown = false;
						}
						if (mPlayerTwoUp && mPlayerTwo.getPlayerPaddle().canMoveUp()) {
							mPlayerTwo.getPlayerPaddle().moveUp();
						} else {
							mPlayerTwoUp = false;

						}
						if (mPlayerTwoDown && mPlayerTwo.getPlayerPaddle().canMoveDown()) {
							mPlayerTwo.getPlayerPaddle().moveDown();
						} else {
							mPlayerTwoDown = false;
						}

						
						FpsDisplay += 1;
					}
				}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		this.initFPSDisplay();
	}
	
	/**
	 * Initializes the FPS display.
	 */
	public void initFPSDisplay() {
		Timeline oneSec = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@SuppressWarnings("deprecation")
			@Override
			public void handle(ActionEvent event) {
				//System.out.println(FpsDisplay);
				//mScoreLabelOne.setText(Integer.toString(FpsDisplay));
				FpsDisplay = 0;

			}
		}));
		oneSec.setCycleCount(Animation.INDEFINITE);
		oneSec.play();
	}
	
	/**
	 * Checks for collisions.
	 */
	public final void checkCollisions() {
		this.checkPlayerCollisions(mPlayerOne, mGameBall);
		this.checkPlayerCollisions(mPlayerTwo, mGameBall);
		this.checkWallBallCollisions(mGameBall);
	}
	
	public void checkPlayerCollisions(Player player,  Ball gameBall) {
		double a1 = 0.0;
		double a2 = 0.0;
		double a3 = 0.0;
		double y = 0.0;
		double z = 0.0;
		Paddle paddle = player.getPlayerPaddle();
		Rectangle paddleRec = paddle.getPaddle();
		Bounds paddleRecBounds = paddleRec.getBoundsInParent();
		if (paddleRecBounds.intersects(mScreenLineOneBounds)) {
			paddle.setCanMoveUp(true);
			paddle.setCanMoveDown(false);
		} else if (paddleRecBounds.intersects(mScreenLineTwoBounds)) {
			paddle.setCanMoveUp(false);
			paddle.setCanMoveDown(true);
		} else {
			paddle.setCanMoveUp(true);
			paddle.setCanMoveDown(true);
		}
		
		if (paddleRecBounds.intersects(gameBall.getBoundsInParent())) {
			System.out.println(player.getPlayerSide().toString() + " player's paddle hit the ball");
			a1 = (paddleRec.getHeight() * 1 / 3);
			a2 = (paddleRec.getHeight() * 2 / 3);
			a3 = (paddleRec.getHeight() * 3 / 3);
			y = paddleRec.getLayoutY();
			z = gameBall.getCenterY();
			a1 = y + a1;
			a2 = y + a2;
			a3 = y + a3;
			double invertedXVec = gameBall.getxVec() * -1;
		
			if (z >= y && z <= a1 && z <= a3) {
				gameBall.hitPaddle(invertedXVec, -0.5, paddle);
				gameBall.registerCollision(paddle);
				//System.out.println("HUp");
			} else if (z > a1 && z <= a2 && z <= a3) {
				gameBall.hitPaddle(invertedXVec, 0.1, paddle);
				gameBall.registerCollision(paddle);
				//System.out.println("HCenter");
			} else if (z > a2 && z <= a3) {
				gameBall.hitPaddle(invertedXVec, 0.5, paddle);
				gameBall.registerCollision(paddle);
				//System.out.println("Hdown");
				
			} else {
				System.out.println("did not hit front paddle");
			}
		} 
	}
	
	/**
	 * Checks for wall collissions.
	 * @param gameBall
	 */
	public void checkWallBallCollisions(Ball gameBall) {
		if (gameBall != null) {
			Bounds gameBallBounds = gameBall.getBoundsInParent();
			if (mPlayerOneGoalLineBounds.intersects(gameBallBounds)) {
				mPlayerOne.playerScoredGoal();
				this.clearScreen();
				this.resetGameBall(mPlayerOne);
				int score = Integer.parseInt(mScoreLabelOne.getText());
				score += 1;
				mScoreLabelOne.setText(Integer.toString(score));
			} else if (mPlayerTwoGoalLineBounds.intersects(gameBallBounds)) {
				mPlayerTwo.playerScoredGoal();
				this.clearScreen();
				this.resetGameBall(mPlayerTwo);
				System.out.println("Scored");
				int score = Integer.parseInt(mScoreLabelTwo.getText());
				score += 1;
				mScoreLabelTwo.setText(Integer.toString(score));
			} else if (mScreenLineOneBounds.intersects(gameBallBounds)) {
				gameBall.bounce(mScreenLineOneBounds);
				gameBall.registerCollision(mScreenLineOneBounds);
			}  else if (mScreenLineTwoBounds.intersects(gameBallBounds)) {
				gameBall.bounce(mScreenLineOneBounds);
				gameBall.registerCollision(mScreenLineTwoBounds);
			} else {
				
			}
		} else {
			System.err.println("Game ball is null");
		}
	}
	
	/**
	 * Writes a blank screen.
	 */
	public void clearScreen() {
		mGC.clearRect(0, 0, 2000, 2000);
	}
	
	public void render() {
		mPlayerOnePaddle.setLayoutX(mPlayerOne.getPlayerPaddle().getXCord());
		mPlayerOnePaddle.setLayoutY(mPlayerOne.getPlayerPaddle().getYCord());
		mPlayerTwoPaddle.setLayoutX(mPlayerTwo.getPlayerPaddle().getXCord());
		mPlayerTwoPaddle.setLayoutY(mPlayerTwo.getPlayerPaddle().getYCord());
		mGameBall.setCenterX(mGameBall.getXLocation());
		mGameBall.setCenterY(mGameBall.getYLocation());
	}
	
	/**
	 * Resets the game ball to the middle of the screen.
	 */
	public void resetGameBall(Player scoringPlayer) {
		mGameBall.reset();
		mGameBall.setCenterX(mGameBall.getXLocation());
		mGameBall.setCenterY(mGameBall.getYLocation());
		if (scoringPlayer != null) {
			switch (scoringPlayer.getPlayerSide()) {
			case Left:
				mGameBall.changeBallVector(1, 0);
				break;
			case Right:
				mGameBall.changeBallVector(-1, 0);
				break;
			case Unnassigned:
				mGameBall.changeBallVector(0, 0);
				break;
				default:
					break;
			}
		} else {
			//called when a player hasnt score.
			double xVec = 0;
			int pic = new Random().nextInt(2);
			if (pic == 1) {
				xVec = -1;
			} else {
				xVec = 1;
			}
			mGameBall.changeBallVector(xVec, 0);
		}
		
		mGC.fillOval(mGameBall.getCenterX(), mGameBall.getCenterY(), mGameBall.getRadius() * 2,
				mGameBall.getRadius() * 2);
	}
	
	

}
