package org.facturegenerator.msg;

import java.awt.Component;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class MessageWindows {
	
	public static void showErrorWindow(Component cmp, MessageList msg) {
		String text = "<html>Operacja nie powiod�a si� " +
			"z powodu b��d�w:<br>";
		Iterator<FGError> it = msg.getErrors().iterator();
		while(it.hasNext()) {
			text += "<li>" + it.next().getText();
		}
		text += "</html>";
		
		JOptionPane.showMessageDialog(cmp, text, "B��d", JOptionPane.OK_OPTION);
	}
	
	public static int showWarningWindow(Component cmp, MessageList msg) {
		String text = "<html>Pojawi�y si� nast�puj�ce ostrze�enia:<br>";
		Iterator<FGWarning> it = msg.getWarnings().iterator();
		while(it.hasNext()) {
			text += "<li>" + it.next().getText();
		}
		text += "Czy mimo to chcesz kontynuowa�?</html>";
		
		return JOptionPane.showConfirmDialog(cmp, text, "Ostrze�enie", JOptionPane.YES_NO_OPTION);
	}
	
	public static void showInfoWindow(Component cmp, MessageList msg) {
		String text = "<html>Informacje:<br>";
		Iterator<FGInfo> it = msg.getInfo().iterator();
		while(it.hasNext()) {
			text += "<li>" + it.next().getText();
		}
		text += "</html>";
		
		JOptionPane.showMessageDialog(cmp, text, "Informacja", JOptionPane.OK_OPTION);
	}
}
