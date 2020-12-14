/*
 * File name:  AnimationSystem.java
 *
 * Programmer : Jake Botka
 *
 * Date: Oct 23, 2019
 */
package main.org.botka.pong;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class AnimationSystem {
	private static int seconds;

	public static void countdownnAimatioon(int second, Pane gamePane) {
		seconds = 0;
		seconds = second;
		Timeline oneSec = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			boolean x = false;
			Label L = null;
			int cycle = 0;
			int cycleCount = second;

			@SuppressWarnings("deprecation")
			@Override
			public void handle(ActionEvent event) {

				if (x == false) {
					L = new Label("");
					L.setText(Integer.toString(seconds));
					L.setLayoutX(gamePane.getBoundsInParent().getMaxX() / 2);
					L.setLayoutY(gamePane.getBoundsInParent().getMaxY() / 2 - 100);
					gamePane.getChildren().add(L);
					x = true;

				} else {
					L.setText(Integer.toString(seconds));

				}
				minus(1);
				cycle += 1;
				if (cycle == cycleCount) {
					gamePane.getChildren().remove(gamePane.getChildren().lastIndexOf(L));
				}

			}
		}));
		oneSec.setCycleCount(second);
		oneSec.play();

	}

	public static void minus(int num) {
		seconds -= num;
		;

	}

}
