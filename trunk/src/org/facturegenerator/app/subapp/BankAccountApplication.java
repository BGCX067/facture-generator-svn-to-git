package org.facturegenerator.app.subapp;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.facturegenerator.app.FGComponent;
import org.facturegenerator.app.FGTextField;
import org.facturegenerator.app.subapp.table.MainTable;
import org.facturegenerator.msg.FGError;
import org.facturegenerator.msg.MessageList;
import org.facturegenerator.obj.BankAccount;
import org.facturegenerator.obj.logic.BankAccountLogic;
import org.facturegenerator.obj.validation.BankAccountValidation;

public class BankAccountApplication extends TableApplication<BankAccount> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "Konta bankowe";
	
	private JLabel lBankName, lAccountNumber;
	private FGTextField tfBankName, tfAccountNumber;
	
	private BankAccountLogic baLogic;
	private BankAccountValidation baValidation;
	
	public BankAccountApplication() {
		super(APP_NAME);
		
		current = new BankAccount();
		baLogic = BankAccountLogic.getInstance();
		baValidation = BankAccountValidation.getInstance();
		
		init();
	}

	@Override
	public void initCurrentPane() {
		//current pane
		lCode = new JLabel("Kod:");
		lBankName = new JLabel("Nazwa banku:");
		lAccountNumber = new JLabel("Numer konta:");
		
		tfCode = new FGTextField();
		tfBankName = new FGTextField();
		tfAccountNumber = new FGTextField();
		
		currentPane = new JPanel(new GridLayout(3, 4, 10, 10));
		currentPane.add(lCode);
		currentPane.add(tfCode);
		currentPane.add(new JLabel());
		currentPane.add(new JLabel());
		currentPane.add(lBankName);
		currentPane.add(tfBankName);
		currentPane.add(new JLabel());
		currentPane.add(new JLabel());
		currentPane.add(lAccountNumber);
		currentPane.add(tfAccountNumber);
		currentPane.add(new JLabel());
		currentPane.add(new JLabel());
	}

	@Override
	public void initTable() {
		table = new MainTable(new String[] {"Lp.", "Kod", "Nazwa banku", "Numer konta"});
	}

	@Override
	public void setColumnSizes() {
		super.setColumnSizes();
		jTable.getColumnModel().getColumn(2).setMinWidth(300);
		jTable.getColumnModel().getColumn(3).setMinWidth(300);
		jTable.setPreferredScrollableViewportSize(new Dimension(1000, 300));
	}
	
	@Override
	public void setCurrent() {
		current.setCode(tfCode.getText());
		current.setBankName(tfBankName.getText());
		current.setAccountNumber(tfAccountNumber.getText());
	}
	
	@Override
	public void getFromTable() {
		int i = jTable.getSelectedRow();
		tfCode.setText((String) table.getValueAt(i, 1));
		tfBankName.setText((String) table.getValueAt(i, 2));
		tfAccountNumber.setText((String) table.getValueAt(i, 3));
	}
	
	@Override
	public void clearFields() {
		tfCode.setText("");
		tfBankName.setText("");
		tfAccountNumber.setText("");
		super.clearFields();
	}
	
	@Override
	public List<BankAccount> loadList() {
		return baLogic.loadAll();
	}

	@Override
	public void saveList(List<BankAccount> list) {
		baLogic.saveAll(list);
	}

	@Override
	public void updateList(List<BankAccount> list) {
		baLogic.updateAll(list);
	}

	@Override
	public void removeList(List<BankAccount> list) {
		baLogic.removeAll(list);
	}

	@Override
	public void validateAdd(BankAccount obj, MainTable table, MessageList msg) {
		baValidation.validateSave(obj, msg);
		for(int i = 0; i < table.getRowCount(); i++) {
			BankAccount ba = new BankAccount();
			ba.setAccountNumber((String)table.getValueAt(i, 3));
			baValidation.validateCompare(obj, ba, msg);
		}
		
	}

	@Override
	public void validateRemove(BankAccount obj, MainTable table, MessageList msg) {
		baValidation.validateRemove(obj, msg);
		if(msg.haveErrors()) return;
		for(int i = 0; i < table.getRowCount(); i++) {
			if(obj.getCode().equals(table.getValueAt(i, 1))) {
				return;
			}
		}
		msg.add(NO_ENTRY);
	}

	@Override
	public void markError(FGError m) {
		super.markError(m);
		if(m.equals(BankAccountValidation.NULL_BANK_NAME)) {
			tfBankName.setMode(FGComponent.FG_ERROR);
			return;
		}
		if(m.equals(BankAccountValidation.NULL_ACC_NUM) ||
				m.equals(BankAccountValidation.INV_ACC_NUM) ||
				m.equals(BankAccountValidation.DB_ACC_NUM)) {
			tfAccountNumber.setMode(FGComponent.FG_ERROR);
			return;
		}
	}
	
	@Override
	public void unmarkMessages() {
		super.unmarkMessages();
		tfBankName.setMode(FGComponent.FG_NORMAL);
		tfAccountNumber.setMode(FGComponent.FG_NORMAL);
	}

}