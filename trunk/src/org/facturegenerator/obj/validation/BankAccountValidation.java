package org.facturegenerator.obj.validation;

import org.facturegenerator.msg.FGError;
import org.facturegenerator.msg.MessageList;
import org.facturegenerator.obj.BankAccount;

public class BankAccountValidation extends FGObjectValidation<BankAccount> {

	public static final FGError NULL_NUMBER = new FGError("kod konta jest pusty");
	public static final FGError NULL_BANK_NAME = new FGError("nazwa banku jest pusta");
	public static final FGError NULL_ACC_NUM = new FGError("numer konta jest pusty");
	public static final FGError INV_ACC_NUM = new FGError("numer konta jest nieprawid³owy");
	public static final FGError DB_ACC_NUM = new FGError("numer konta jest ju¿ zajêty");
	
	private static final BankAccountValidation baValidation = new BankAccountValidation();
	
	private BankAccountValidation() {
		
	}
	
	public static BankAccountValidation getInstance() {
		return baValidation;
	}
	
	@Override
	public void validateSave(BankAccount obj, MessageList msg) {
		validateCode(obj, msg);
		if(msg.haveErrors()) return;
		
		if(obj.getBankName() == null || obj.getBankName().length() == 0) {
			msg.add(NULL_BANK_NAME);
		}
		
		if(obj.getAccountNumber() == null || obj.getAccountNumber().isEmpty()) {
			msg.add(NULL_ACC_NUM);
		} else if(!TextFormatValidation.isBankAccountNumber(obj.getAccountNumber())) {
			msg.add(INV_ACC_NUM);
		}
	}

	@Override
	public void validateRemove(BankAccount obj, MessageList msg) {
		validateCode(obj, msg);
	}

	@Override
	public void validateCompare(BankAccount obj1, BankAccount obj2, MessageList msg) {
		if(obj1.getAccountNumber().equals(obj2.getAccountNumber())) {
			msg.add(DB_ACC_NUM);
		}
	}

}
