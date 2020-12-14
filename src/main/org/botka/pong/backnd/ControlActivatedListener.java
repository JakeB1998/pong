/**
 * File Name: ControlPressedListener.java
 * Programmer: Jake Botka
 * Date Created: Dec 14, 2020
 *
 */
package main.org.botka.pong.backnd;

/**
 * Interface listener for control activated callbacks.
 * @author Jake Botka
 *
 */
public interface ControlActivatedListener<T> {

	public void onControlActivated(T control, long timeStamp);
}
