package org.facturegenerator.app;

public interface FGComponent {
	
	int FG_NORMAL =  10;
	int FG_WARNING = 11;
	int FG_ERROR =   12;
	
	int getMode();
	
	void setMode(int mode);
}
