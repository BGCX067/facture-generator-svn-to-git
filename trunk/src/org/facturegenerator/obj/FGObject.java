package org.facturegenerator.obj;

public abstract class FGObject {
	
	protected String code;
	
	public abstract Object[] getObject();
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
