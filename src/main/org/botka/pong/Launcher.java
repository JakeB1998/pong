/**
 * File Name: Launcher.java
 * Programmer: Jake Botka
 * Date Created: Dec 16, 2020
 *
 */
package main.org.botka.pong;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Jake Botka
 *
 */
public class Launcher extends Application {


	/**
	 * Called by launch().
	 * @param arg0 Stage object.
	 * @throws Exception
	 *
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		GameController game = new GameController(arg0);
//		appScene.getStylesheets().add
//		 (Login.class.getResource("Login.css").toExternalForm());

	}
}
