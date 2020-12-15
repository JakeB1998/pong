/**
 * File Name: KeyControl.java
 * Programmer: Jake Botka
 * Date Created: Dec 14, 2020
 *
 */
package main.org.botka.pong.backnd.controls;

/**
 * @author Jake Botka
 * @param <T> Type variable for class represented as the 'control'.
 *
 */
public class KeyControl<T> extends Control<T> {

	private final ControlListenerHook<T> CONTROL_LISTENER_RELAY_HOOK;
	
	
	private boolean mActive, mDisabled;
	private String mControlDescription;
	
	
	/**
	 * @param control
	 */
	public KeyControl(T control) {
		super(control);
		CONTROL_LISTENER_RELAY_HOOK = new ControlListenerHook<>(getSuperActivatedListener(), getSuperDeactivatedListener());
		this.mControlDescription = null;
		
	}
	
     /**
	 * 
	 */
	public KeyControl(T control, String controlDescription) {
		this(control);
		this.mControlDescription = controlDescription;
	}

	public void registerControlListeners(ControlActivatedListener<T> activatedListener, ControlDeactivatedListener<T> deactivatedListener) {
		CONTROL_LISTENER_RELAY_HOOK.registerSubRelays(activatedListener, deactivatedListener);
	}
	/**
	 * @return
	 * 
	 */
	@Override
	public String controlDescription() {
		return this.mControlDescription;
	}

	/**
	 * @return
	 * 
	 */
	@Override
	public boolean isActive() {
		return this.mActive;
	}

	/**
	 * @return
	 * 
	 */
	@Override
	public boolean isDisabled() {
		return this.mDisabled;
	}
	
	/**
	 * Sends control activated event to delegate.
	 * @param control
	 * @param timestamp
	 */
	public void sendControlActivatedEvent(T control, long timestamp) {
		CONTROL_LISTENER_RELAY_HOOK.relayControlActivatedEvent(control, timestamp);
	}
	
	/**
	 * Send control deactivated event to delegate.
	 * @param control
	 * @param timestamp
	 */
	public void sendControlDeactivatedEvent(T control, long timestamp) {
		CONTROL_LISTENER_RELAY_HOOK.relayControlDeactivatedEvent(control, timestamp);
	}
	
	/**
	 * @return Description of the control.
	 */
	public String getControlDescription() {
		return this.mControlDescription;
	}
	
	/**
	 * @return
	 * 
	 */
	@Override
	public String controlBindingText() {
		return null;
	}
	
	/**
	 * Hook that sends events to the system before sending the event to the inplemented code.
	 * @author Jake Botka
	 *
	 */
	private class ControlListenerHook<T> {
		private final String TAG = getClass().getSimpleName();
		private ControlActivatedListener<T> mControlActivatedListenerSuper, mControlActivatedListenerSub;
		private ControlDeactivatedListener<T> mControlDeactivatedListenerSuper, mControlDeactivatedListenerSub;
		
		public ControlListenerHook() {
			this.mControlActivatedListenerSuper = null;
			this.mControlDeactivatedListenerSuper = null;
			this.mControlActivatedListenerSub = null;
			this.mControlDeactivatedListenerSub = null;
		}
		
		public ControlListenerHook(ControlActivatedListener<T> activatedListener, ControlDeactivatedListener<T> deactivatedListener) {
			this();
			this.mControlActivatedListenerSuper = activatedListener;
			this.mControlDeactivatedListenerSuper = deactivatedListener;
		}
		
		public void registerSubRelayActivatedListener(ControlActivatedListener<T> activatedListener) {
			this.mControlActivatedListenerSub = activatedListener;
		}
		
		public void registerSubRelayDeactivatedListener(ControlDeactivatedListener<T> deactivatedListener) {
			this.mControlDeactivatedListenerSub = deactivatedListener;
		}
		
		public void registerSubRelays(ControlActivatedListener<T> activatedListener, ControlDeactivatedListener<T> deactivatedListener) {
			this.registerSubRelayActivatedListener(activatedListener);
			this.registerSubRelayDeactivatedListener(deactivatedListener);
		}
		
		public void relayEvent(T control, long timestamp) {
			
		}
		
		public void relayControlActivatedEvent(T control, long timestamp) {
			if (this.mControlActivatedListenerSuper != null) {
				this.mControlActivatedListenerSuper.onControlActivated(control, timestamp);
			} else {
				System.err.println("[" + TAG + "] control listener in super is null");
			}
			
			if (this.mControlActivatedListenerSub != null) {
				this.mControlActivatedListenerSub.onControlActivated(control, timestamp);
			}
		}
		
		public void relayControlDeactivatedEvent(T control, long timestamp) {
			if (this.mControlDeactivatedListenerSuper != null) {
				this.mControlDeactivatedListenerSuper.onControlDeactivated(control, timestamp);
			} else {
				System.err.println("[" + TAG + "] control listener in super is null");
			}
			
			if (this.mControlDeactivatedListenerSub != null) {
				this.mControlDeactivatedListenerSub.onControlDeactivated(control, timestamp);
			} 
		}
	}

	

}
