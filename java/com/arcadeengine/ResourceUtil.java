package com.arcadeengine;

import javax.imageio.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

/**
 * This class provides utility methods for loading res
 *
 * @author D.Baker
 * @author Byron Zaharako
 */
public abstract class ResourceUtil {

	/**
	 * Get a BufferedImage from an image within the classpath
	 *
	 * @param fileName
	 * @return
	 */
	public static BufferedImage loadInternalImage(String fileName) {
		try {
			URL path = ClassLoader.getSystemClassLoader().getResource(fileName);
			return ImageIO.read(path);
		}
		catch (IllegalArgumentException e) {
			//e.printStackTrace();
		}
		catch (IOException e) {
		//	e.printStackTrace();
		}


		System.out.println("Image File: '" + fileName + "' was not found in package '" + fileName + "'");

		return ResourceUtil.getNullImage();
	}

	/**
	 * Get the null image to show something went wrong when loading
	 */
	private static BufferedImage getNullImage() {

		BufferedImage n = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		Graphics g = n.getGraphics();
		g.setColor(Color.MAGENTA);
		g.fillRect(1, 1, 14, 14);
		g.setColor(Color.BLACK);
		g.drawOval(1, 1, 14, 14);

		return n;
	}

	/**
	 * Gets an Image from outside the classpath
	 */
	public static BufferedImage loadExternalImage(String folder, String fileName) {
		return loadExternalImage(folder + "/" + fileName);
	}

	public static BufferedImage loadExternalImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return getNullImage();
		}
	}

	public static BufferedImage rotateImage(BufferedImage img, double radian) {

		double sin = Math.abs(Math.sin(radian));
		double cos = Math.abs(Math.cos(radian));

		int w = img.getWidth(null), h = img.getHeight(null);

		int newW = (int) Math.floor(w * cos + h * sin);
		int newH = (int) Math.floor(h * cos + w * sin);

		BufferedImage newImg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImg.createGraphics();

		g.translate((newW - w) / 2, (newH - h) / 2);
		g.rotate(radian, w / 2, h / 2);
		g.drawRenderedImage(img, null);
		g.dispose();

		return newImg;
	}

	public static BufferedImage flipImage(BufferedImage img, boolean horizontal, boolean vertical) {

		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int x = 0;
		if(horizontal) {
			x = width;
			width *= -1;
		}

		int y = 0;
		if(vertical) {
			y = height;
			height *= -1;
		}

		Graphics2D g2 = (Graphics2D) newImg.getGraphics();

		g2.drawImage(img, x, y, width, height, null);

		return newImg;
	}

	/**
	 * Safely attempts to get the requested sound file.
	 *
	 * @param packageName Name of the file.
	 * @param fileName    Class Being Used in, Example : Player.class
	 * @return The desired sound file.
	 */
	public static AudioInputStream loadClip(String packageName, String fileName) {
		try {
			String pathName = packageName.replaceAll("\\.", "/") + "/" + fileName;
			URL path = ClassLoader.getSystemClassLoader().getResource(fileName);
			return AudioSystem.getAudioInputStream(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Font getTrueTypeFont(String packageName, String fileName) {

		try {
			String pathName = packageName.replaceAll("\\.", "/") + "/" + fileName;
			URL path = ClassLoader.getSystemClassLoader().getResource(pathName);

			Font f = Font.createFont(Font.TRUETYPE_FONT, path.openStream());

			return f;
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

		return Font.getFont("Arial");
	}
}
