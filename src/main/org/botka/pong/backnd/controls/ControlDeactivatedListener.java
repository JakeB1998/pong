/**
 * File Name: ControlDeactivatedListener.java
 * Programmer: Jake Botka
 * Date Created: Dec 14, 2020
 *
 */
package main.org.botka.pong.backnd.controls;

/**
 * Interface listener for control deactivated callbacks.
 * @author Jake Botka
 *
 */
public interface ControlDeactivatedListener<T> {
	public void onControlDeactivated(T control, long timestamp);
}
