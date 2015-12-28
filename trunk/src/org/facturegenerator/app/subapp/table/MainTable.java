package org.facturegenerator.app.subapp.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MainTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] columnNames; 
	private List<Object[]> data;
	
	public MainTable(String[] columnNames) {
		this.columnNames = columnNames;
		data = new ArrayList<Object[]>();
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	public Object[] getRow(int row) {
		return data.get(row);
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void addRow(Object[] add) {
		Iterator<Object[]> it = data.iterator();
		while(it.hasNext()) {
			Object[] old = it.next();
			if(old[1].equals(add[1])) {
				add[0] = old[0];
				Integer i = (Integer) old[0];
				data.set(i.intValue()-1, add);
				return;
			}
		}
		add[0] = getRowCount()+1;
		data.add(add);
	}
	
	public boolean delRow(Object[] del) {
		Iterator<Object[]> it = data.iterator();
		while(it.hasNext()) {
			Object[] old = it.next();
			if(old[1].equals(del[1])) {
				Integer i = (Integer) old[0];
				data.remove(i.intValue()-1);
				return true;
			}
		}
		return false;
	}
	
	public void clear() {
		data.clear();
	}
}
