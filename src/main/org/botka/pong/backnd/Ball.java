/*
 * File name:  Ball.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong.backnd;

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
import main.org.botka.pong.AnimationSystem;
import main.org.botka.pong.GameController;
import main.org.botka.pong.MediaSystem;

/**
 * Model class that represents the ball.
 *
 * @author Jake Botka
 *
 */
public class Ball extends Circle {
	
	private static final double DEFAULT_VELOCITY = 5;
	private static final double DEFAULT_OFFSET = 10;
	
	private final Point2D DEF_DIR_VEC = new Point2D(1, 0);
	
	private int mBounceCount = 0;
	private double mBallSpeedMod, mXVec, mYVec, mVelocity, mOffset, mXLocation, mYLocation;
	private boolean mCanMove, mCanBounce;

	private Point2D cord;
	private String mLastCollisionID;
	
	
	private Ball() {
		this.mXVec = 0.5;
		this.mYVec = 0;
		this.mVelocity = DEFAULT_VELOCITY;
		this.mOffset = DEFAULT_OFFSET;
		this.mCanMove = true;
		this.mCanBounce = true;
		this.cord = null;
		this.mLastCollisionID = null;
	
	}

	public Ball(Pane gamePane) {
		this();
		this.setRadius(12);
		this.setCenterX(gamePane.getLayoutBounds().getMaxX() / 2);
		this.setCenterY(gamePane.getLayoutBounds().getMaxY() / 2);
		cord = new Point2D(this.getCenterX(), this.getCenterY());

	}
	
	public void bounce(Object collidingObject) {
		//MediaSystem.playPongBlip();
		mBounceCount++;
		if (mCanBounce == true && String.valueOf(collidingObject.hashCode()).equals(this.mLastCollisionID) == false ) {
			mYVec = mYVec * -1;
		}
		
	}

	public void changeBallVector(double xVec, double yVec) {
		this.mXVec = xVec;
		this.mYVec = yVec;
	}

	public void constantMove(double centerX, double centerY, GraphicsContext gc) {
		if (mCanMove) {
			mXLocation = centerX + (mXVec * mVelocity);
			mYLocation = centerY + (mYVec * mVelocity);
			if (mYVec > 0.60) {
				mYVec = mYVec / 2;
				mXVec = mXVec * 2;
				//System.out.println("yVec altered");
			}
		}
	}

	public void hitPaddle(double x, double y, Paddle paddle) {
		//MediaSystem.playPongBlip();
		this.bounce(paddle);
		mYVec = y;
		if (mXVec > 0) {
			mXVec = x;
			mXLocation -= 10;
		} else if (mXVec < 0) {
			mXVec = x;
			mXLocation += mOffset;
		}
		if (mBounceCount >= 2) {
			increaseSpeed();
			mBounceCount = 0;
		} else {
			mBounceCount++;
		}
		//System.out.println(mXVec + " " + mYVec);
	}

	public void increaseSpeed() {
		mVelocity = mVelocity * 1.25;
		mOffset += 10;
	}


	public void stopConstantMove() {
		mCanMove = false;
	}
	
	public void registerCollision(Object collisionSource) {
		this.mLastCollisionID = String.valueOf(collisionSource.hashCode());
	}

	public void reset() {
		mXLocation = this.getParent().getLayoutBounds().getMaxX() / 2;
		mYLocation = this.getParent().getLayoutBounds().getMaxY() / 2;
		mVelocity = 5;
		mOffset = 10;
		mBounceCount = 0;
		AnimationSystem.countdownnAimatioon(3, (Pane) this.getParent());
		lockBall(3);

	}

	public void lockBall(int seconds) {
		mCanMove = false;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(seconds * 1000);
					mCanMove = true;
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
		return mBallSpeedMod;
	}

	/**
	 * @param ballSpeedMod the ballSpeedMod to set
	 */
	public void setBallSpeedMod(double ballSpeedMod) {
		this.mBallSpeedMod = ballSpeedMod;
	}

	/**
	 * @return the xVec
	 */
	public double getxVec() {
		return mXVec;
	}

	/**
	 * @param xVec the xVec to set
	 */
	public void setXVec(double xVec) {
		this.mXVec = xVec;
	}

	/**
	 * @return the yVec
	 */
	public double getYVec() {
		return mYVec;
	}

	/**
	 * @param yVec the yVec to set
	 */
	public void setYVec(double yVec) {
		this.mYVec = yVec;
	}

	/**
	 * @return the velocity
	 */
	public double getVelocity() {
		return mVelocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(double velocity) {
		this.mVelocity = velocity;
	}

	/**
	 * @return the dEF_DIR_VEC
	 */
	public Point2D getDEF_DIR_VEC() {
		return DEF_DIR_VEC;
	}

	

	/**
	 * @return the xLocation
	 */
	public double getXLocation() {
		return mXLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setXLocation(double xLocation) {
		this.mXLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public double getYLocation() {
		return mYLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setYLocation(double yLocation) {
		this.mYLocation = yLocation;
	}

	/**
	 * @return the yVec
	 */
	public double getyVec() {
		return mYVec;
	}

	/**
	 * @param yVec the yVec to set
	 */
	public void setyVec(double yVec) {
		this.mYVec = yVec;
	}

	/**
	 * @return the xLocation
	 */
	public double getxLocation() {
		return mXLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setxLocation(double xLocation) {
		this.mXLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public double getyLocation() {
		return mYLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setyLocation(double yLocation) {
		this.mYLocation = yLocation;
	}

	/**
	 * @return the canMove
	 */
	public boolean isCanMove() {
		return mCanMove;
	}

	/**
	 * @param canMove the canMove to set
	 */
	public void setCanMove(boolean canMove) {
		this.mCanMove = canMove;
	}

	/**
	 * @param xVec the xVec to set
	 */
	public void setxVec(double xVec) {
		this.mXVec = xVec;
	}

	/**
	 * @return the bounceCount
	 */
	public int getBounceCount() {
		return mBounceCount;
	}

	/**
	 * @param bounceCount the bounceCount to set
	 */
	public void setBounceCount(int bounceCount) {
		this.mBounceCount = bounceCount;
	}

	/**
	 * @return the offset
	 */
	public double getOffset() {
		return mOffset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(double offset) {
		this.mOffset = offset;
	}
	
	public String getLastCollisionID() {
		return this.mLastCollisionID;
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
