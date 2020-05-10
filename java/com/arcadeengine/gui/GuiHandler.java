package com.arcadeengine.gui;

import com.arcadeengine.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class GuiHandler {

	private AnimPanel panel;

	private ArrayList<DebugLine> debugLines;

	private Gui currentGui = null;

	private Color bg = new Color(68, 68, 68, 160);
	private boolean debugstate = false, debugenabled = false;

	/**
	 * Constructor
	 *
	 * @param startGui
	 *            The gui to be displayed when first started.
	 */
	public GuiHandler(AnimPanel panel, Gui startGui) {

		this.panel = panel;
		this.currentGui = startGui;

		debugLines = new ArrayList<>();
	}

	/**
	 * The current Gui.
	 */
	public Gui getGui() {

		try {
			return currentGui;
		}
		catch(NullPointerException e) {
			return null;
		}
	}

	public void addDebugLine(DebugLine dl) {

		DebugLine temp = null;

		for (DebugLine debugLine : debugLines) {
			if(debugLine.getLabel().equals(dl.getLabel()))
				temp = debugLine;
		}

		if(temp != null)
			debugLines.remove(temp);

		debugLines.add(dl);
		this.debugenabled = true;
	}

	/**
	 * The current state of the Debug screen.
	 */
	public boolean getDebugState() {

		return debugstate;
	}

	/**
	 * Set the state of the debug screen.
	 */
	public void setDebugState(boolean state) {

		debugstate = state;
	}

	/**
	 * Inverts the current state of the debug screen. (ON/OFF)
	 */
	public void invertDebugState() {

		debugstate = !debugstate;
	}

	public Color getBGColor() {

		try {
			return bg;
		}
		catch(NullPointerException e) {
			return null;
		}
	}

	/**
	 * The current GUI being displayed.
	 */
	public Gui getCurrentGui() {

		return this.currentGui;
	}

	/**
	 * Updates everything about the GUI.
	 *
	 * @param g
	 *            The graphics object.
	 */
	public void drawGui(Graphics g) {

		g.setColor(bg);
		g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

		this.currentGui.drawGui(g);

		if(this.debugstate && debugenabled) {

			Graphics2D page = (Graphics2D) g;

			int height = -4;
			int spacing = -2;

			final Font font = new Font("Consolas", Font.BOLD, 13);

			for (DebugLine dl : debugLines) {
				for (String line : dl.getLines()) {

					g.setFont(font);
					page.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

					Rectangle2D rect = g.getFontMetrics().getStringBounds(line, g);

					if (font.getSize() <= 20)
						height += rect.getHeight() + 1;
					else
						height += rect.getHeight() + 2;

					g.setColor(new Color(0,0,0, 40));
					g.fillRect(this.panel.getWidth() - (int) rect.getWidth() - 3, height - (int) rect.getHeight() + 2, (int) rect.getWidth(), (int) rect.getHeight());

					currentGui.drawString(line, font, Color.WHITE, this.panel.getWidth() - (int) rect.getWidth() - 3, height, g);

					height += spacing;
				}
			}
		}
	}

	public void updateGui() {

		updateBG();
		this.currentGui.updateGui();
	}

	/**
	 * Updates the color of the GUI to match the currently stored color.
	 */
	public void updateBG() {

		int r = bg.getRed(), g = bg.getGreen(), b = bg.getBlue(), a = bg.getAlpha();

		for(int loop = 0; loop < 5; loop++) {
			if(r < this.currentGui.getBGColor().getRed())
				r++;
			else if(r > this.currentGui.getBGColor().getRed())
				r--;

			if(b < this.currentGui.getBGColor().getBlue())
				b++;
			else if(b > this.currentGui.getBGColor().getBlue())
				b--;

			if(g < this.currentGui.getBGColor().getGreen())
				g++;
			else if(g > this.currentGui.getBGColor().getGreen())
				g--;

			if(a < this.currentGui.getBGColor().getAlpha())
				a++;
			else if(a > this.currentGui.getBGColor().getAlpha())
				a--;
		}

		bg = new Color(r, g, b, a);
	}

	// ---------- Changing Guis ----------

	/**
	 * ONLY FOR GUITRANSITION!
	 */
	public void setGui(Gui next) {

		currentGui = next;
	}

	/**
	 * Switches smoothly the Gui to another while preserving the last to be used
	 * for later .
	 *
	 * @param next
	 *            The Gui you want to switch too. Put "new Gui___()" here.
	 * @return The next Gui with the given parent for use with previousGui()"
	 */
	public void switchGui(Gui next) {

		if(this.debugstate)
			System.out.println("*** Switching Guis From " + currentGui.getClass().getSimpleName() + " To " + next.getClass().getSimpleName() + " ***");

		next.setParent(currentGui);

		this.currentGui = new GuiTransition(panel, TransitionType.slideLeft, currentGui, next);
	}
	
	public void switchGui(Gui next, TransitionType type) {

		if(this.debugstate)
			System.out.println("*** Switching Guis From " + currentGui.getClass().getSimpleName() + " To " + next.getClass().getSimpleName() + " ***");

		next.setParent(currentGui);

		this.currentGui = new GuiTransition(panel, type, currentGui, next);
	}

	/**
	 * Returns to the last Gui that was visited.
	 *
	 * @return The last Gui that was visited.
	 */
	public void previousGui() {

		this.previousGui(TransitionType.slideRight);
	}

	public void previousGui(TransitionType type) {

		if(this.debugstate)
			System.out.println("*** Returning to Gui: " + currentGui.getParent().getClass().getSimpleName() + " ***");

		this.currentGui = new GuiTransition(panel, type, currentGui, currentGui.getParent());
	}
}
