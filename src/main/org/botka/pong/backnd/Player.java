/*
 * File name:  Player.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong.backnd;

import javafx.scene.input.KeyCode;
import main.org.botka.pong.MediaSystem;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class Player {
	private KeyControl<KeyCode> DEFAULT_PAGE_UP_CONTROL = new KeyControl<KeyCode>(KeyCode.W);
	private KeyControl<KeyCode> DEFAULT_PAGE_DOWN_CONTROL = new KeyControl<KeyCode>(KeyCode.S);
	private Paddle mPlayerPaddle;

	public Player() {
		mPlayerPaddle = new Paddle();
		mPlayerPaddle.registerControls(DEFAULT_PAGE_UP_CONTROL, DEFAULT_PAGE_DOWN_CONTROL);
	}
	
	public Player(KeyCode upControlBinding, KeyCode downControlBinding) {
		this();
		mPlayerPaddle.registerControls(new KeyControl<KeyCode>(upControlBinding, upControlBinding.getName()), 
				new KeyControl<KeyCode>(downControlBinding, downControlBinding.getName()));
	}

	public void playerScoredGoal() {
		MediaSystem.playSound();
	}

	/**
	 * @return the playerPaddle
	 */
	public Paddle getPlayerPaddle() {
		return mPlayerPaddle;
	}

	/**
	 * @param playerPaddle the playerPaddle to set
	 */
	public void setPlayerPaddle(Paddle playerPaddle) {
		this.mPlayerPaddle = playerPaddle;
	}
	
	/**
	 * 
	 * @return Paddle up control.
	 */
	public KeyControl<?> getPaddleUpControl(){
		return mPlayerPaddle.getUpControl();
	}
	
	/**
	 * 
	 * @return Paddle down control
	 */
	public KeyControl<?> getPaddleDownControl(){
		return mPlayerPaddle.getDownControl();
	}

}
