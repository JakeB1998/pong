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
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


/**
 * Class that handles animations for the application.
 *
 * @author Jake Botka
 *
 */
public class AnimationSystem {
	

	/**
	 * Initalizes the animation for the countdown sequence.
	 * @param second Length of animation in seconds.
	 * @param context Graphics context for canvas rendering.
	 */
	public  void countdownAnimation(int second, final GraphicsContext context) {
		final int cycleCount = 3;
		final int x = 500;
		final int y = 500;
		final int w = 100;
		final int h = 100;
		context.setFont(new Font("Courier New", w));
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		Timeline oneSec = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			
			int cycle = 0;
			@SuppressWarnings("deprecation")
			@Override
			public void handle(ActionEvent event) {
				context.clearRect(x, y, w, h);
				context.strokeText(String.valueOf(cycleCount - cycle),x + (w / 2),y + (h / 2));
				cycle += 1;
			}
		}));
		
		oneSec.setCycleCount(cycleCount);
		oneSec.play();
		oneSec.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Animation finished");
			}
		});

	}

	

}
