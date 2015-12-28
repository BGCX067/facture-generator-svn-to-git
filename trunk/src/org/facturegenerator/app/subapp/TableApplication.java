package org.facturegenerator.app.subapp;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.facturegenerator.app.FGComponent;
import org.facturegenerator.app.FGTextField;
import org.facturegenerator.app.subapp.table.MainTable;
import org.facturegenerator.msg.FGError;
import org.facturegenerator.msg.FGWarning;
import org.facturegenerator.msg.MessageList;
import org.facturegenerator.msg.MessageWindows;
import org.facturegenerator.msg.Messages;
import org.facturegenerator.obj.FGObject;
import org.facturegenerator.obj.validation.FGObjectValidation;

public abstract class TableApplication<T extends FGObject> extends AbstractApplication 
			implements Messages {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final MessageList msg = MessageList.getInstance();
	
	protected static final FGError NO_ENTRY = new FGError("Objekt o podanym kodzie nie istnieje.");
	
	protected String subAppName;
	protected List<T> loadList, addList, removeList;
	protected T current;
	
	protected JPanel mainPane, currentPane, buttonPane;
	protected JScrollPane tablePane;
	
	protected JLabel lCode;
	protected FGTextField tfCode;
	
	protected JButton bSave, bLoad, bAdd, bRemove, bClear;
	
	protected MainTable table;
	protected JTable jTable;
	
	public TableApplication(String subAppName) {
		this.subAppName = subAppName;
		addList = new ArrayList<T>();
		removeList = new ArrayList<T>();
		
		loadList = new ArrayList<T>();
		
		current = null;
	}
	
	/**
	 * Initializes GUI panel.
	 */
	public void init() {
		
		initCurrentPane();
		initButtonPane();
		initTablePane();
		
		mainPane = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		mainPane.add(currentPane, c);
		c.gridx = 0;
		c.gridy = 1;
		mainPane.add(buttonPane, c);
		c.gridx = 0;
		c.gridy = 2;
		mainPane.add(tablePane, c);
		add(mainPane);
		mainPane.revalidate();
		mainPane.repaint();
	}
	
	public void initCurrentPane() {
		currentPane = new JPanel();
	}
	
	public void initButtonPane() {
		
		bSave = new JButton("Zapisz");
		bLoad = new JButton("Wczytaj");
		bAdd = new JButton("Dodaj");
		bRemove = new JButton("Usuñ");
		bClear = new JButton("Nowy");

		bSave.addActionListener(new SaveAction());
		bLoad.addActionListener(new LoadAction());
		bAdd.addActionListener(new AddAction());
		bRemove.addActionListener(new RemoveAction());
		bClear.addActionListener(new ClearAction());
		
		buttonPane = new JPanel(new FlowLayout());
		buttonPane.add(bSave);
		buttonPane.add(bLoad);
		buttonPane.add(bAdd);
		buttonPane.add(bRemove);
		buttonPane.add(bClear);
	}

	public void initTablePane() {
	    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        initTable();
	    jTable = new JTable(table);
	    
	    for(int i = 0; i < jTable.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
		
        jTable.setFillsViewportHeight(true);
        jTable.getSelectionModel().addListSelectionListener(new TableListener());
        jTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setColumnSizes();
        
	    tablePane = new JScrollPane(jTable);
	    //tablePane.set
	    tablePane.revalidate();
	    tablePane.repaint();
	}
	
	public void setColumnSizes() {
		jTable.getColumnModel().getColumn(0).setWidth(30);
		jTable.getColumnModel().getColumn(1).setWidth(100);
	}
	
	public abstract void initTable();
	
	public abstract void getFromTable();
	
	public abstract void setCurrent();
	
	public abstract List<T> loadList();
	
	public abstract void saveList(List<T> list);
	
	public abstract void updateList(List<T> list);
	
	public abstract void removeList(List<T> list);
	
	public abstract void validateAdd(T obj, MainTable table, MessageList msg);
	
	public abstract void validateRemove(T obj, MainTable table, MessageList msg);
	
	public void markMessages() {
		for(Iterator<FGError> it = msg.getErrors().iterator(); it.hasNext();) {
			markError(it.next());
		}
		for(Iterator<FGWarning> it = msg.getWarnings().iterator(); it.hasNext();) {
			markWarning(it.next());
		}
	}
	
	public void markError(FGError m) {
		
		if(m.equals(FGObjectValidation.NULL_CODE) ||
				m.equals(FGObjectValidation.INV_CODE) ||
				m.equals(NO_ENTRY)) {
			tfCode.setMode(FGComponent.FG_ERROR);
		}
	}
	
	public void markWarning(FGWarning w) {
		
	}
	
	public void unmarkMessages() {
		tfCode.setMode(FGComponent.FG_NORMAL);
	}

	@Override
	public void reload() {
		loadList = loadList();
		addList.clear();
		removeList.clear();
		
		unmarkMessages();
		clearFields();
		jTable.clearSelection();
		refresh();
	}
	
	private boolean isLoadListOk() {
		List<T> baseList = loadList();
		if(baseList.size() != loadList.size()) return false;
		Iterator<T> it = loadList.iterator();
		while(it.hasNext()) {
			//TODO
			it.next();
		}
		return true;
	}
	
	private void refreshTable() {
		table.clear();
		for(Iterator<T> it = loadList.iterator(); it.hasNext();) {
			table.addRow(it.next().getObject());
		}
		for(Iterator<T> it = removeList.iterator(); it.hasNext();) {
			table.delRow(it.next().getObject());
		}
		for(Iterator<T> it = addList.iterator(); it.hasNext();) {
			table.addRow(it.next().getObject());
		}
	}
	
	protected void saveAction() {
		if(!isLoadListOk()) {
			msg.add(new FGError(RELOAD));
			MessageWindows.showErrorWindow(mainPane, msg);
			//reloadList();
			return;
		}
		
		if(!removeList.isEmpty()) removeList(removeList);
		removeList.clear();
		
		List<T> editList = new ArrayList<T>();
		List<T> insertList = new ArrayList<T>();
		for(Iterator<T> it = addList.iterator(); it.hasNext();) {
			T temp = it.next();
			Iterator<T> itList = loadList.iterator();
			boolean edit = false;
			while(itList.hasNext()) {
				if(itList.next().getCode().equals(temp.getCode())) {
					editList.add(temp);
					edit = true;
					break;
				}
			}
			if(!edit) {
				insertList.add(temp);
			}
		}
		if(!insertList.isEmpty()) saveList(insertList);
		if(!editList.isEmpty()) updateList(editList);
		addList.clear();
		
		loadList = loadList();
		addList.clear();
		removeList.clear();
	}
	
	protected void loadAction() {
		if(!addList.isEmpty() || !removeList.isEmpty()) {
			msg.add(new FGWarning(CONFRIM));
			int option = MessageWindows.showWarningWindow(mainPane, msg);
			if(option == JOptionPane.NO_OPTION) return;
		}
		
		loadList = loadList();
		addList.clear();
		removeList.clear();
	}
	
	protected void addAction() {
		setCurrent();
		validateAdd(current, table, msg);
		if(msg.haveErrors()) {
			MessageWindows.showErrorWindow(mainPane, msg);
			return;
		}
		Object[] row = current.getObject();
		table.addRow(row);
		addList.add(current);
	}
	
	protected void removeAction() {
		setCurrent();
		validateRemove(current, table, msg);
		if(msg.haveErrors()) {
			MessageWindows.showErrorWindow(mainPane, msg);
			return;
		}
		Object[] row = current.getObject();
		table.delRow(row);
		removeList.add(current);
		Iterator<T> it = addList.iterator();
		while(it.hasNext()) {
			T temp = it.next();
			if(temp.getCode().equals(current.getCode())) {
				addList.remove(temp);
				break;
			}
		}
	}
	
	protected void clearAction() {
		clearFields();
	}
	
	protected void clearMessageList() {
		unmarkMessages();
		msg.reset();
	}
	
	public void clearFields() {
		tfCode.setText("");
	}
	
	@Override
	public void refresh() {
		refreshTable();
		mainPane.revalidate();
		mainPane.repaint();
	}
	
	class SaveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			clearMessageList();
			saveAction();
			refresh();
		}
		
	}
	
	class LoadAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			clearMessageList();
			loadAction();
			refresh();
		}
		
	}
	
	class AddAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			clearMessageList();
			addAction();
			markMessages();
			refresh();
		}
		
	}
	
	class RemoveAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			clearMessageList();
			removeAction();
			markMessages();
			refresh();
		}
		
	}
	
	class ClearAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			clearMessageList();
			clearAction();
			refresh();
		}
	}
	
	class TableListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(jTable.getSelectedRow() > -1) {
				getFromTable();
				setCurrent();
			}
		}

	}
}
