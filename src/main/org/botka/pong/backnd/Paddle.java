/*
 * File name:  Paddle.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong.backnd;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.org.botka.pong.GameController;
import main.org.botka.pong.backnd.controls.KeyControl;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class Paddle {
	private final double DEFAULT_MOVEMENT_SPEED = 10.0;
	private Rectangle mPaddle;
	private Point2D mCord;
	private KeyControl<?> mUpControl;
	private KeyControl<?> mDownControl;

	private boolean mMoveUp;
	private boolean mMoveDown;

	private double mSpeed;
	private double mXCord;
	private double mYCord;

	public Paddle() {
		mPaddle = new Rectangle(25, 100);
		this.mMoveUp = true;
		this.mMoveDown = true;
		this.mSpeed = DEFAULT_MOVEMENT_SPEED;
	}

	public void moveUp() {
		for (int i = 0; i < mSpeed; i++) {
			mYCord -= 1;
			mPaddle.setLayoutY(mYCord);
		}
	}

	public void moveDown() {
		for (int i = 0; i < mSpeed; i++) {
			mYCord += 1;
			mPaddle.setLayoutY(mYCord);
		}
	}

	public boolean canMoveUp() {
		return mMoveUp;
	}

	public boolean canMoveDown() {
		return mMoveDown;

	}

	public void registerControls(KeyControl<?> upControl, KeyControl<?> downControl) {
		this.mUpControl = upControl;
		this.mDownControl = downControl;
	}
	
	
	/**
	 * @return the paddle
	 */
	public Rectangle getPaddle() {
		return mPaddle;
	}

	/**
	 * @param paddle the paddle to set
	 */
	public void setPaddle(Rectangle paddle) {
		this.mPaddle = paddle;
	}

	/**
	 * @return the cord
	 */
	public Point2D getCord() {
		mCord = new Point2D(mXCord, mYCord);
		return mCord;
	}

	/**
	 * @param cord the cord to set
	 */
	public void setCord(Point2D cord) {
		this.mCord = cord;
		mXCord = cord.getX();
		mYCord = cord.getY();
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return mSpeed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.mSpeed = speed;
	}

	/**
	 * @return the xCord
	 */
	public double getXCord() {
		return mXCord;
	}

	/**
	 * @param xCord the xCord to set
	 */
	public void setXCord(double xCord) {
		this.mXCord = xCord;
	}

	/**
	 * @return the yCord
	 */
	public double getYCord() {
		return mYCord;
	}

	/**
	 * @param yCord the yCord to set
	 */
	public void setYCord(double yCord) {
		this.mYCord = yCord;
	}
	
	public KeyControl<?> getUpControl(){
		return this.mUpControl;
	}
	
	public void setUpControl(KeyControl<?> control) {
		this.mUpControl = control;
	}
	
	public KeyControl<?> getDownControl(){
		return this.mDownControl;
	}
	
	public void setDownControl(KeyControl<?> control) {
		this.mDownControl = control;
	}
	
	public void setCanMoveUp(boolean value) {
		this.mMoveUp = value;
	}
	
	public void setCanMoveDown(boolean value) {
		this.mMoveDown = value;
	}

}
