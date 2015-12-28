package org.facturegenerator.obj.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.facturegenerator.obj.BankAccount;

public class BankAccountLogic extends FGObjectLogic<BankAccount> {

	private static final String TABLE_NAME = "BankAccount";
	
	//column names
	private static final String BANK_NAME = "BankName";
	private static final String ACCOUNT_NUMBER = "AccountNumber";
	
	private static final BankAccountLogic baLogic = new BankAccountLogic();
	
	private BankAccountLogic() {
		
	}
	
	public static BankAccountLogic getInstance() {
		return baLogic;
	}
	
	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected BankAccount init(ResultSet rs) throws SQLException {
		BankAccount ba = new BankAccount();
		ba.setCode(rs.getString(CODE));
		ba.setBankName(rs.getString(BANK_NAME));
		ba.setAccountNumber(rs.getString(ACCOUNT_NUMBER));
		return ba;
	}

	@Override
	protected List<String> getColumnNames() {
		List<String> colNames = new ArrayList<String>();
		colNames.add(BANK_NAME);
		colNames.add(ACCOUNT_NUMBER);
		return colNames;
	}

	@Override
	protected Object getValue(String colName, BankAccount obj) {
		if(BANK_NAME.equals(colName)) return obj.getBankName();
		if(ACCOUNT_NUMBER.equals(colName)) return obj.getAccountNumber();
		return null;
	}

}
