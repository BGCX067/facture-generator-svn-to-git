package org.facturegenerator.app.subapp;

import javax.swing.JPanel;

public abstract class AbstractApplication extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Used only on reload sub application.
	 */
	public abstract void reload();
	
	public abstract void refresh();
}
