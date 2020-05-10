package com.arcadeengine;

import java.awt.event.*;
import java.util.ArrayList;

public class KeyBindingHandler {


	private ArrayList<KeyEvent> keysPressed = new ArrayList<>();
	private ArrayList<KeyBinding> customBindings = new ArrayList<>();

	private ArrayList<KeyBinding> toAdd = new ArrayList<>();
	private ArrayList<KeyBinding> toRemove = new ArrayList<>();

	public boolean isKeyHeld(String key) {

		boolean isHeld = false;
		for (KeyEvent keyEvent : keysPressed) {
			if(KeyEvent.getKeyText(keyEvent.getKeyCode()).equals(key))
				isHeld = true;
		}
		return isHeld;
	}

	private boolean containsEvent(KeyEvent event) {

		boolean contains = false;

		for (KeyEvent keyEvent : keysPressed) {

			if(keyEvent.getKeyCode() == event.getKeyCode())
				contains = true;
		}
		return contains;
	}

	private void removeEvent(KeyEvent event) {

		for (int i = 0; i < keysPressed.size(); i++) {
			KeyEvent e = keysPressed.get(i);
			if(e.getKeyCode() == event.getKeyCode()) {
				keysPressed.remove(i);
				break;
			}
		}
	}

	public void addBindings(KeyBinding binding) {

		for (int i = 0; i < customBindings.size(); i++) {
			KeyBinding kb = customBindings.get(i);
			if (kb.getName().equals(binding.getName())) {
				toRemove.add(kb);
			}
		}

		toAdd.add(binding);
	}

	public void onPress(KeyEvent e) {
		String key = KeyEvent.getKeyText(e.getKeyCode());

		if(!this.containsEvent(e)) {
			keysPressed.add(e);
			for(KeyBinding b : customBindings) {
				if (b.canListen())
					b.onPress(e, key);
			}
		}
	}
	
	public void runBindings() {

		if(toRemove.size() != 0) {
			customBindings.removeAll(toRemove);
			toRemove.clear();
		}

		if(toAdd.size() != 0) {
			customBindings.addAll(toAdd);
			toAdd.clear();
		}

		for (KeyEvent e : keysPressed) {
			String key = KeyEvent.getKeyText(e.getKeyCode());

			for(KeyBinding b : customBindings) {
				if (b.canListen())
					b.whilePressed(e, key);
			}
		}
	}
	
	public void removeKey(KeyEvent e) {

		String key = KeyEvent.getKeyText(e.getKeyCode());

		for(KeyBinding b : customBindings) {
			if (b.canListen())
				b.onRelease(e, key);
		}
		
		removeEvent(e);
	}
	
}
