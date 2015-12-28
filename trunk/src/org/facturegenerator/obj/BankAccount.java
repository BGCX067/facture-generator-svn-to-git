package org.facturegenerator.obj;

public class BankAccount extends FGObject {
	
	private String bankName;
	private String accountNumber;
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public Object[] getObject() {
		return new Object[] {0, code, bankName, accountNumber};
	}
	
//	public static void main(String[] args) {
//		MessageList ml = MessageList.getInstance();
//		BankAccount ba = new BankAccount();
//		
//		ba.setBankName("ING Bank Slaski");
//		ba.setAccountNumber("00 0000 0000 0000 0000 0000 0900");
//		ba.validate(ml);
//		
//	}
}
