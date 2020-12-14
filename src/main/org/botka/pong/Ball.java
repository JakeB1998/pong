/*
 * File name:  Ball.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong;

import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class Ball extends Circle {
	private double ballSpeedMod;

	int bounceCount = 0;
	double xVec = 0.5;
	double yVec = 0;
	double velocity = 5;
	double offset = 10;

	double xLocation = 0;
	double yLocation = 0;

	boolean canMove;
	boolean canBounce = true;

	private Point2D cord;
	private Point2D DEF_DIR_VEC = new Point2D(1, 0);

	public Ball(Pane gamePane) {
		canMove = true;

		this.setRadius(12);
		this.setCenterX(gamePane.getLayoutBounds().getMaxX() / 2);
		this.setCenterY(gamePane.getLayoutBounds().getMaxY() / 2);
		cord = new Point2D(this.getCenterX(), this.getCenterY());

	}

	public void constantMove(double centerX, double centerY, GraphicsContext gc) {
		if (canMove = true) {
			xLocation = centerX + (xVec * velocity);
			yLocation = centerY + (yVec * velocity);

			if (((GameController.screenLine2.getBoundsInParent().intersects(this.getBoundsInParent())))) {

				bounce();
				// yLocation += offset;

			}
			if ((GameController.screenLine1.getBoundsInParent().intersects(this.getBoundsInParent()))) {
				bounce();
				// yLocation -= offset;

			}

			if (yVec > 0.60) {
				yVec = yVec / 2;
				xVec = xVec * 2;

				System.out.println("yVec altered");

			}

		}

	}

	public void hitPaddle(double x, double y) {
		MediaSystem.playPongBlip();
		yVec = y;

		if (xVec > 0) {
			xVec = x;
			xLocation -= 10;
		} else if (xVec < 0) {
			xVec = x;
			xLocation += offset;

		}

		if (bounceCount >= 2) {
			increaseSpeed();
			bounceCount = 0;
		} else {
			bounceCount++;
		}

		System.out.println(xVec + " " + yVec);
	}

	public void increaseSpeed() {
		velocity = velocity * 1.25;
		offset += 10;

	}

	public void bounce() {
		MediaSystem.playPongBlip();
		bounceCount++;
		if (canBounce == true) {
			yVec = yVec * -1;
			canBounce = false;
		}
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					Thread.sleep(100);
					canBounce = true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public void changeBallVector(double xVec, double yVec) {
		this.xVec = xVec;
		this.yVec = yVec;
	}

//	public void StartConstantMove()
//	
//	{
//		canMove = true;
//		
//		
//	}
	public void stopConstantMove() {
		canMove = false;

	}

	public void reset() {
		// xLocation = gamePane.getLayoutBounds().getMaxX() / 2;
		// yLocation = gamePane.getLayoutBounds().getMaxY() / 2;

		xLocation = this.getParent().getLayoutBounds().getMaxX() / 2;
		yLocation = this.getParent().getLayoutBounds().getMaxY() / 2;
		velocity = 5;
		offset = 10;
		bounceCount = 0;

		AnimationSystem.countdownnAimatioon(3, (Pane) this.getParent());
		lockBall(3);

	}

	public void lockBall(int seconds) {
		canMove = false;
		System.out.println("Gay");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					Thread.sleep(seconds * 1000);
					System.out.println("Gay");
					canMove = true;
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	/**
	 * @return the this
	 */
	public Circle getBallGui() {
		return this;
	}

	/**
	 * @param this the this to set
	 */

	/**
	 * @return the cord
	 */
	public Point2D getCord() {
		return cord;
	}

	/**
	 * @param cord the cord to set
	 */
	public void setCord(Point2D cord) {
		this.cord = cord;
	}

	/**
	 * @return the ballSpeedMod
	 */
	public double getBallSpeedMod() {
		return ballSpeedMod;
	}

	/**
	 * @param ballSpeedMod the ballSpeedMod to set
	 */
	public void setBallSpeedMod(double ballSpeedMod) {
		this.ballSpeedMod = ballSpeedMod;
	}

	/**
	 * @return the xVec
	 */
	public double getxVec() {
		return xVec;
	}

	/**
	 * @param xVec the xVec to set
	 */
	public void setXVec(double xVec) {
		this.xVec = xVec;
	}

	/**
	 * @return the yVec
	 */
	public double getYVec() {
		return yVec;
	}

	/**
	 * @param yVec the yVec to set
	 */
	public void setYVec(double yVec) {
		this.yVec = yVec;
	}

	/**
	 * @return the velocity
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the dEF_DIR_VEC
	 */
	public Point2D getDEF_DIR_VEC() {
		return DEF_DIR_VEC;
	}

	/**
	 * @param dEF_DIR_VEC the dEF_DIR_VEC to set
	 */
	public void setDEF_DIR_VEC(Point2D dEF_DIR_VEC) {
		DEF_DIR_VEC = dEF_DIR_VEC;
	}

	/**
	 * @return the xLocation
	 */
	public double getXLocation() {
		return xLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setXLocation(double xLocation) {
		this.xLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public double getYLocation() {
		return yLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setYLocation(double yLocation) {
		this.yLocation = yLocation;
	}

	/**
	 * @return the yVec
	 */
	public double getyVec() {
		return yVec;
	}

	/**
	 * @param yVec the yVec to set
	 */
	public void setyVec(double yVec) {
		this.yVec = yVec;
	}

	/**
	 * @return the xLocation
	 */
	public double getxLocation() {
		return xLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setxLocation(double xLocation) {
		this.xLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public double getyLocation() {
		return yLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setyLocation(double yLocation) {
		this.yLocation = yLocation;
	}

	/**
	 * @return the canMove
	 */
	public boolean isCanMove() {
		return canMove;
	}

	/**
	 * @param canMove the canMove to set
	 */
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	/**
	 * @param xVec the xVec to set
	 */
	public void setxVec(double xVec) {
		this.xVec = xVec;
	}

	/**
	 * @return the bounceCount
	 */
	public int getBounceCount() {
		return bounceCount;
	}

	/**
	 * @param bounceCount the bounceCount to set
	 */
	public void setBounceCount(int bounceCount) {
		this.bounceCount = bounceCount;
	}

	/**
	 * @return the offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	private void update() {
		Timeline update = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@SuppressWarnings("deprecation")
			@Override
			public void handle(ActionEvent event) {

				reset();

			}
		}));
		update.setCycleCount(Animation.INDEFINITE);
		update.play();

	}

}
