/*
 * File name:  Paddle.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class Paddle {
	private double speed = 10;
	private Rectangle paddle;
	private Point2D cord;

	private boolean moveUp = true;
	private boolean moveDown = true;

	private double xCord;
	private double yCord;

	public Paddle() {
		paddle = new Rectangle(25, 100);

	}

	public void moveUp() {

		if ((!(GameController.screenLine2.getBoundsInParent().intersects(paddle.getBoundsInParent())))) {
			moveDown = true;
			moveUp = true;
			for (int i = 0; i < speed; i++) {
				Thread.currentThread();
				// Thread.sleep((long) GameController.FRAME_PER_SECOND);
				paddle.setLayoutY(yCord - 1);
				yCord = paddle.getLayoutY();

			}
		} else {
			moveUp = false;
			System.out.println("Reached border");

		}

	}

	public void moveDown() {
		if (!(GameController.screenLine1.getBoundsInParent().intersects(paddle.getBoundsInParent()))) {
			moveUp = true;
			moveDown = true;
			for (int i = 0; i < speed; i++) {

				// Thread.sleep((long) GameController.FRAME_PER_SECOND);
				paddle.setLayoutY(yCord + 1);

				yCord = paddle.getLayoutY();

			}
		} else {
			moveDown = false;
			System.out.println("Reached border");
		}

	}

	public boolean canMoveUp() {
		return moveUp;

	}

	public boolean canMoveDown() {
		return moveDown;

	}

	/**
	 * @return the paddle
	 */
	public Rectangle getPaddle() {
		return paddle;
	}

	/**
	 * @param paddle the paddle to set
	 */
	public void setPaddle(Rectangle paddle) {
		this.paddle = paddle;
	}

	/**
	 * @return the cord
	 */
	public Point2D getCord() {
		cord = new Point2D(xCord, yCord);
		return cord;
	}

	/**
	 * @param cord the cord to set
	 */
	public void setCord(Point2D cord) {
		this.cord = cord;
		xCord = cord.getX();
		yCord = cord.getY();
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * @return the xCord
	 */
	public double getXCord() {
		return xCord;
	}

	/**
	 * @param xCord the xCord to set
	 */
	public void setXCord(double xCord) {
		this.xCord = xCord;
	}

	/**
	 * @return the yCord
	 */
	public double getYCord() {
		return yCord;
	}

	/**
	 * @param yCord the yCord to set
	 */
	public void setYCord(double yCord) {
		this.yCord = yCord;
	}

}
