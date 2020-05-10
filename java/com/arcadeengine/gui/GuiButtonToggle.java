package com.arcadeengine.gui;

import com.arcadeengine.*;

import java.awt.*;
import java.awt.geom.*;

public class GuiButtonToggle extends GuiButton {
	/** The state of the button. */
	private boolean state = false;

	public String titleOn, titleOff;

	/**
	 * Creates a togglable Button.
	 * 
	 * @param x
	 *            The X Coordinate of the Button
	 * @param y
	 *            The Y Coordinate of the Button
	 * @param w
	 *            The Width of the Button
	 * @param h
	 *            The Height of the Button
	 * @param off
	 *            The string to be displayed if button is off.
	 * @param on
	 *            The string to be displayed if button is on.
	 * @param state
	 *            The starting state of the button.
	 */
	public GuiButtonToggle(AnimPanel panel, int x, int y, int w, int h, String off, String on, boolean state) {
		super(panel, x, y, w, h, "Loading...");

		if (state)
			this.label = on;
		else
			this.label = off;

		this.titleOff = off;
		this.titleOn = on;

		this.state = state;
	}

	/**
	 * Creates a togglable Button.
	 * 
	 * @param w
	 *            The Width of the Button
	 * @param h
	 *            The Height of the Button
	 * @param off
	 *            The string to be displayed if button is off.
	 * @param on
	 *            The string to be displayed if button is on.
	 * @param state
	 *            The starting state of the button.
	 */
	public GuiButtonToggle(AnimPanel panel, int w, int h, String off, String on, boolean state) {
		super(panel, w, h, "Loading...");

		if (state)
			this.label = on;
		else
			this.label = off;

		this.titleOff = off;
		this.titleOn = on;

		this.state = state;
	}

	/**
	 * Draws the button on to the screen.
	 * 
	 * @param g
	 *            The graphics object
	 */
	@Override
	public void draw(Graphics g) {
		// Draws the Button to be clicked upon.
		Graphics2D page = (Graphics2D) g;

		page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// The Background color of the button.
		page.setColor(secColor);
		page.fill(this.buttonShadow);

		// Highlight the button if hovered.
		if(hovered) {
			if(isEnabled())
				page.setColor(primColor.darker());
		}
		else {
			if(isEnabled())
				page.setColor(primColor);
		}
		if(!isEnabled())
			page.setColor(disabledColor);

		page.fill(this.button);

		Font font = new Font("Verdana", Font.BOLD, 13);
		Font old = g.getFont();

		g.setFont(font);

		page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Rectangle2D rect = page.getFontMetrics().getStringBounds(label, page);

		int drawX = (this.button.width / 2 + this.button.x) - (int) rect.getWidth() / 2;
		int drawY = (this.button.height / 2 + this.button.y) - (int) (rect.getHeight() / 2 + rect.getY());

		panel.getGuiHandler().getCurrentGui().drawString(label, font, textColor, drawX, drawY, page);

		g.setFont(old);
	}

	public String getTitleOn() {
		return titleOn;
	}

	public String getTitleOff() {
		return titleOff;
	}

	/**
	 * Draws the button on to the screen.
	 * 
	 * @param g
	 *            The graphics object
	 * @param x
	 *            The current x coord in the for loop for auto placement of
	 *            buttons.
	 * @param y
	 *            The current y coord in the for loop for auto placement of
	 *            buttons.
	 */
	@Override
	public void draw(int x, int y, Graphics g) {
		this.button.setLocation(x + 2, y + 2);
		this.buttonShadow.setLocation(x, y);

		this.draw(g);
	}

	public void invertState() {
		this.state = !this.state;

		if(state)
			this.label = titleOn;
		else
			this.label = titleOff;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean getState() {
		return this.state;
	}

}
