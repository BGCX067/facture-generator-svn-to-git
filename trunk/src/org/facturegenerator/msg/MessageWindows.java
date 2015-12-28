package org.facturegenerator.msg;

import java.awt.Component;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class MessageWindows {
	
	public static void showErrorWindow(Component cmp, MessageList msg) {
		String text = "<html>Operacja nie powiod³a siê " +
			"z powodu b³êdów:<br>";
		Iterator<FGError> it = msg.getErrors().iterator();
		while(it.hasNext()) {
			text += "<li>" + it.next().getText();
		}
		text += "</html>";
		
		JOptionPane.showMessageDialog(cmp, text, "B³¹d", JOptionPane.OK_OPTION);
	}
	
	public static int showWarningWindow(Component cmp, MessageList msg) {
		String text = "<html>Pojawi³y siê nastêpuj¹ce ostrze¿enia:<br>";
		Iterator<FGWarning> it = msg.getWarnings().iterator();
		while(it.hasNext()) {
			text += "<li>" + it.next().getText();
		}
		text += "Czy mimo to chcesz kontynuowaæ?</html>";
		
		return JOptionPane.showConfirmDialog(cmp, text, "Ostrze¿enie", JOptionPane.YES_NO_OPTION);
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
