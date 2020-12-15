/**
 * File Name: Controls.java
 * Programmer: Jake Botka
 * Date Created: Dec 14, 2020
 *
 */
package main.org.botka.pong.backnd.controls;

import javafx.scene.input.KeyCode;

/**
 * @author Jake Botka
 *
 */
public abstract class Control<T> {

	private final String TAG = getClass().getName();
	private final ControlActivatedListener<T> ACTIVATED_LISTENER = new ControlActivatedListener<T>() {

		@Override
		public void onControlActivated(T control, long timestamp) {
			logControl(control, timestamp);
		}
	};
	private final ControlDeactivatedListener<T> DEACTIVATED_LISTENER = new ControlDeactivatedListener<T>() {
		@Override
		public void onControlDeactivated(T control, long timestamp) {
			logControl(control, timestamp);
		}
	};
	
	
	private T mControl;
	private boolean mLogging;
	
	private Control() {
		this.mControl = null;
	}
	
	public Control(T control) {
		this();
		this.mControl = control;
	}
	
	private final void logControl(T control, long timeStamp) {
		if (this.isLogging()) {
			if (control != null) {
				System.out.println("[" + TAG + "]" + timeStamp + ": " + control.toString());
			} else {
				System.err.println("Control wass null at time: " + timeStamp);
			}	
		}
	}
	
	public abstract String controlDescription();
	public abstract String controlBindingText();
	public abstract boolean isActive();
	public abstract boolean isDisabled();
	
	public boolean isLogging() {
		return this.getLogging();
	}
	
	public ControlActivatedListener<T> getSuperActivatedListener(){
		return ACTIVATED_LISTENER;
	}
	
	public ControlDeactivatedListener<T> getSuperDeactivatedListener(){
		return DEACTIVATED_LISTENER;
	}
	public T getControl() {
		return this.mControl;
	}
	
	public void setControl(T control) {
		this.mControl = control;
	}
	
	public boolean getLogging() {
		return this.mLogging;
	}
	
	public void setLogging(boolean value) {
		this.mLogging = value;
	}
	
}
