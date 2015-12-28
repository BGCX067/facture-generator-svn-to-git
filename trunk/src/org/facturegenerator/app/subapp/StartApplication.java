package org.facturegenerator.app.subapp;

public class StartApplication extends AbstractApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "Witaj!";

	@Override
	public void reload() {
		revalidate();
		repaint();
	}

	@Override
	public void refresh() {
		revalidate();
		repaint();
	}
}
