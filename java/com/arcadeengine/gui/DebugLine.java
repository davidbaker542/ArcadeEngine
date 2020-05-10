package com.arcadeengine.gui;

/**
 * Created by David on 3/3/2015.
 */
public class DebugLine {

	private String label;

	public DebugLine(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String[] getLines() {

		return new String[]{
				"Default Line.",
		};
	}

	protected String addBreak() {

		return "----------------------";
	}

}