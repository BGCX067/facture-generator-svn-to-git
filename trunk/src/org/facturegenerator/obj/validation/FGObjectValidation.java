package org.facturegenerator.obj.validation;

import org.facturegenerator.msg.FGError;
import org.facturegenerator.msg.MessageList;
import org.facturegenerator.obj.FGObject;

public abstract class FGObjectValidation<T extends FGObject> {
	
	public static final FGError NULL_CODE = new FGError("Kod jest pusty.");
	public static final FGError INV_CODE = new FGError("Kod jest za d³ugi (max. 10 znaków).");
	
	public void validateCode(FGObject obj, MessageList msg) {
		if(obj.getCode() == null || obj.getCode().length() == 0) {
			msg.add(NULL_CODE);
		} else if(obj.getCode().length() > 10) {
			msg.add(INV_CODE);
		}
	}
	
	public abstract void validateSave(T obj, MessageList msg);
	
	public abstract void validateRemove(T obj, MessageList msg);
	
	public abstract void validateCompare(T obj1, T obj2, MessageList msg);
}
