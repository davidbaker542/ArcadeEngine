package com.arcadeengine;

import java.awt.event.*;

public class KeyBinding {


	private final String name;

	public KeyBinding(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	public boolean canListen() {

		return true;
	}

	/**
	 * Key binding to be fired upon key being pressed
	 */
	@Deprecated
	public void onPress(String key) { }
	
	/**
	 * Key binding to be fired upon key being released
	 */
	@Deprecated
	public void onRelease(String key) { }

	/**
	 * Key binding to be fired while key is pressed.
	 */
	@Deprecated
	public void whilePressed(String key) { }

	/**
	 * Key binding to be fired upon key being pressed
	 */
	public void onPress(KeyEvent e, String key) {
		this.onPress(key);
	}

	/**
	 * Key binding to be fired upon key being released
	 */
	public void onRelease(KeyEvent e,String key) {
		this.onRelease(key);
	}

	/**
	 * Key binding to be fired while key is pressed.
	 */
	public void whilePressed(KeyEvent e, String key) {
		this.whilePressed(key);
	}
}
