package com.arcadeengine.gui;

import com.arcadeengine.*;

import java.awt.*;
import java.awt.image.*;

/**
 * Created by David on 3/6/2015.
 */
public class GuiImgButton extends GuiButton {

	private BufferedImage img, hover, disabled;

	public GuiImgButton(AnimPanel panel, BufferedImage img, int x, int y, String l) {

		super(panel, x, y, img.getWidth(), img.getHeight(), l);

		this.img = img;

		float hScale = 1.3f;
		RescaleOp hOp = new RescaleOp(new float[] { hScale, hScale, hScale, 1f }, new float[4], null);
		hover = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		hover.getGraphics().drawImage(hOp.filter(img, null), 0, 0, null);

		float dScale = 0.8f;
		RescaleOp dOp = new RescaleOp(new float[] { dScale, dScale, dScale, 1f }, new float[4], null);
		disabled = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		disabled.getGraphics().drawImage(dOp.filter(img, null), 0, 0, null);
	}

	public GuiImgButton(AnimPanel panel, BufferedImage img, String l) {

		super(panel, img.getWidth(), img.getHeight(), l);

		this.img = img;

		float hScale = 1.3f;
		RescaleOp hOp = new RescaleOp(new float[] { hScale, hScale, hScale, 1f }, new float[4], null);
		hover = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		hover.getGraphics().drawImage(hOp.filter(img, null), 0, 0, null);

		float dScale = 0.8f;
		RescaleOp dOp = new RescaleOp(new float[] { dScale, dScale, dScale, 1f }, new float[4], null);
		disabled = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		disabled.getGraphics().drawImage(dOp.filter(img, null), 0, 0, null);
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

		if(hovered) {
			if(isEnabled())
				page.drawImage(hover, x, y, null);
		}
		else {
			if(isEnabled())
				page.drawImage(img, x, y, null);
			else
				page.drawImage(disabled, x, y, null);
		}
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

		this.x = x;
		this.y = y;

		this.draw(g);
	}
}
