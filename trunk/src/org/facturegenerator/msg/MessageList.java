package org.facturegenerator.msg;

import java.util.ArrayList;
import java.util.List;

public class MessageList {

	private static MessageList messageList = null;
	private List<FGError> errors;
	private List<FGWarning> warnings;
	private List<FGInfo> info;
	
	public static MessageList getInstance() {
		if(messageList == null) {
			messageList = new MessageList();
		}
		return messageList;
	}
	
	private MessageList() {
		errors = new ArrayList<FGError>();
		warnings = new ArrayList<FGWarning>();
		info = new ArrayList<FGInfo>();
	}
	
	public void reset() {
		errors.clear();
		warnings.clear();
		info.clear();
	}
	
	public boolean haveErrors() {
		return !errors.isEmpty();
	}
	
	public boolean haveWarnings() {
		return !warnings.isEmpty();
	}
	
	public boolean haveInfo() {
		return !info.isEmpty();
	}
	
	public void add(FGMessage msg) {
		if(msg instanceof FGError) {
			errors.add((FGError) msg);
			return;
		}
		if(msg instanceof FGWarning) {
			warnings.add((FGWarning) msg);
			return;
		}
		if(msg instanceof FGInfo) {
			info.add((FGInfo)msg);
			return;
		}
		throw new RuntimeException("FG: Invalid message type.");
	}
	
	public List<FGError> getErrors() {
		return errors;
	}
	
	public List<FGWarning> getWarnings() {
		return warnings;
	}
	
	public List<FGInfo> getInfo() {
		return info;
	}
}
