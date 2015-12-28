package org.facturegenerator.msg;

public class FGMessage {
	
	private String text;
	
	public FGMessage(String msg) {
		this.text = msg;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
}
