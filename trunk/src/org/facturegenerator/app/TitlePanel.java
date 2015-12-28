package org.facturegenerator.app;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TitlePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setOpaque(true);
		g.setColor(new Color(10, 100, 190));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
