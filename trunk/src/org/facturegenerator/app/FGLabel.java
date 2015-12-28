package org.facturegenerator.app;

import java.awt.Graphics;

import javax.swing.JLabel;

public class FGLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int NORMAL = 10;
	public static final int BOLD =   11;
	public static final int ITALIC = 12;
	
	private int mode;
	
	public FGLabel() {
		mode = NORMAL;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
}
