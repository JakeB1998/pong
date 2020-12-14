/*
 * File name:  Player.java
 *
 * Programmer : Jake Botka
 *
 * Date: Sep 9, 2019
 */

package main.org.botka.pong;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class Player {
	private Paddle playerPaddle;

	public Player() {
		playerPaddle = new Paddle();
	}

	public void playerScoredGoal() {
		MediaSystem.playSound();
	}

	/**
	 * @return the playerPaddle
	 */
	public Paddle getPlayerPaddle() {
		return playerPaddle;
	}

	/**
	 * @param playerPaddle the playerPaddle to set
	 */
	public void setPlayerPaddle(Paddle playerPaddle) {
		this.playerPaddle = playerPaddle;
	}

}
