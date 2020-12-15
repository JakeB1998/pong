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
import main.org.botka.pong.backnd.controls.KeyControl;

/**
 * Model class for Players.
 *
 * @author Jake Botka
 *
 */
public class Player {
	public static enum Side {
		Unnassigned, Left,Right
	}
	
	private KeyControl<KeyCode> DEFAULT_PAGE_UP_CONTROL = new KeyControl<KeyCode>(KeyCode.W);
	private KeyControl<KeyCode> DEFAULT_PAGE_DOWN_CONTROL = new KeyControl<KeyCode>(KeyCode.S);
	private Paddle mPlayerPaddle;
	private Side mPlayerSide;

	private short mScore;
	public Player() {
		mPlayerPaddle = new Paddle();
		this.mPlayerSide = Side.Unnassigned;
		this.mScore = 0;
		mPlayerPaddle.registerControls(DEFAULT_PAGE_UP_CONTROL, DEFAULT_PAGE_DOWN_CONTROL);
	}
	
	public Player(KeyCode upControlBinding, KeyCode downControlBinding) {
		this();
		mPlayerPaddle.registerControls(new KeyControl<KeyCode>(upControlBinding, upControlBinding.getName()), 
				new KeyControl<KeyCode>(downControlBinding, downControlBinding.getName()));
	}
	
	public Player(KeyCode upControlBinding, KeyCode downControlBinding, Side side) {
		this(upControlBinding,downControlBinding);
		this.mPlayerSide = side;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player player = (Player) obj;
			return player != null ? player.getPlayerSide().equals(this.getPlayerSide()) : false;
		}
		
		return false;
	}

	/**
	 * 
	 */
	public void playerScoredGoal() {
		this.mScore++;
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
	 * @return
	 */
	public Side getPlayerSide() {
		return this.mPlayerSide;
	}
	
	/**
	 * 
	 * @param side
	 */
	public void setPlayerSide(Side side) {
		this.mPlayerSide = side;
	}
	
	public short getPlayerScore() {
		return this.mScore;
	}
	
	public void setPLayerScore(short score) {
		this.mScore = score;
	}
	

}
