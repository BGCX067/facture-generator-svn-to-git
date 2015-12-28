package org.facturegenerator.app;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextField;

public class FGTextField extends JTextField implements FGComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color BLACK = new Color(0, 0, 0);
	private static final Color RED = new Color(200, 0, 0);
	private static final Color YELLOW = new Color(170, 170, 0);
	
	private int mode;
	
	public FGTextField() {
		mode = FG_NORMAL;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		switch(mode) {
		case FG_NORMAL:
			g.setColor(BLACK);
			break;
		case FG_WARNING:
			g.setColor(YELLOW);
			break;
		case FG_ERROR:
			g.setColor(RED);
			break;
		}
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}
	
	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	
}
