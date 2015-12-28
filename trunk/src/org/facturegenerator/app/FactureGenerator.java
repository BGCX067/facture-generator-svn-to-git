package org.facturegenerator.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.facturegenerator.app.subapp.BankAccountApplication;
import org.facturegenerator.app.subapp.StartApplication;

public class FactureGenerator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    //private static final int WIDTH = 800;
    //private static final int HEIGHT = 600;

    //Applications
    StartApplication startApp;
    BankAccountApplication bankApp;
    
    //GUI
	private JPanel mainPane, subAppPane;
	private TitlePanel labelPane;
	private JLabel lSubAppTitle;
	
	//menu
	private JMenuBar menu;
	private JMenu applications;
	private JMenuItem start, bankAccounts;
	
	private ChangeApplicationListener changeAppListener;
	
	public FactureGenerator() {
		super("Generator Faktur");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    init();
	    
	    //setSize(WIDTH, HEIGHT);
	    setLocation(100, 30);
	    pack();
	    setVisible(true);
	}
	
	private void init() {
	
		initMenuBar();
		setJMenuBar(menu);
		
		initLabelPane();
		initSubAppPane();
		
		mainPane = new JPanel(new BorderLayout());
		mainPane.add(labelPane, BorderLayout.NORTH);
		mainPane.add(subAppPane, BorderLayout.CENTER);
		add(mainPane);
		
		switchCards(StartApplication.APP_NAME);
	}
	
	private void initMenuBar() {
		
		changeAppListener = new ChangeApplicationListener();

		start = new JMenuItem(StartApplication.APP_NAME);
		start.addActionListener(changeAppListener);
		
		bankAccounts = new JMenuItem(BankAccountApplication.APP_NAME);
		bankAccounts.addActionListener(changeAppListener);
		
		applications = new JMenu("Aplikacje");
		applications.add(start);
		applications.add(bankAccounts);
		
		menu = new JMenuBar();
		menu.add(applications);
	}
	
	private void initLabelPane() {
		labelPane = new TitlePanel();
		lSubAppTitle = new JLabel("Witaj!");
		lSubAppTitle.setHorizontalTextPosition(JLabel.LEFT);
		labelPane.add(lSubAppTitle);
	}
	
	private void initSubAppPane() {
		
		startApp = new StartApplication();
		bankApp = new BankAccountApplication();
		
		subAppPane = new JPanel(new CardLayout());
		subAppPane.add(startApp, StartApplication.APP_NAME);
		subAppPane.add(bankApp, BankAccountApplication.APP_NAME);
	}
	
	private void switchCards(String app) {
		CardLayout cl = (CardLayout)(subAppPane.getLayout());
	    cl.show(subAppPane, app);
	    lSubAppTitle.setText(app);
	    
	    if(app.equals(BankAccountApplication.APP_NAME)) {
	    	bankApp.reload();
	    	return;
	    }
	    if(app.equals(StartApplication.APP_NAME)) {
	    	startApp.reload();
	    	return;
	    }
	    mainPane.revalidate();
	    mainPane.repaint();
	}
	
	public static void main(String[] args) {
		new FactureGenerator();
	}
	
	class ChangeApplicationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switchCards(e.getActionCommand());
		}
		
	}
}
