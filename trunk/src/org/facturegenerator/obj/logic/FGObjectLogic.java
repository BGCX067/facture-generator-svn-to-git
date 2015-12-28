package org.facturegenerator.obj.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.facturegenerator.obj.FGObject;

public abstract class FGObjectLogic<T extends FGObject> {

	private static final String DB = "jdbc:mysql://localhost/facture?user=facture&password=gen02!";
	
	protected static final String CODE = "Code";
	
	public List<T> loadAll() {
		List<T> list = new ArrayList<T>();
		
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch(ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
			
			connect = DriverManager.getConnection(DB);
										
			preparedStatement = connect.prepareStatement(
				createLoadQuery());
			
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				list.add(init(rs));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				connect.close();
				preparedStatement.close();
				if(rs != null) rs.close();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
		
		return list;
	}
	
	public void saveAll(List<T> list) {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch(ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
			
			connect = DriverManager.getConnection(DB);
							
			Iterator<T> it = list.iterator();
			
			while(it.hasNext()) {
				preparedStatement = connect.prepareStatement(
					createInsertQuery());
				
				setInsertValues(preparedStatement, it.next());
				
				preparedStatement.execute();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				connect.close();
				preparedStatement.close();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
	public void updateAll(List<T> list) {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch(ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
			
			connect = DriverManager.getConnection(DB);
							
			Iterator<T> it = list.iterator();
			
			while(it.hasNext()) {
				preparedStatement = connect.prepareStatement(
					createUpdateQuery());
				
				setUpdateValues(preparedStatement, it.next());
				
				preparedStatement.execute();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				connect.close();
				preparedStatement.close();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
	public void removeAll(List<T> list) {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch(ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
			
			connect = DriverManager.getConnection(DB);
			
			Iterator<T> it = list.iterator();
			
			while(it.hasNext()) {
				preparedStatement = connect.prepareStatement(
						createRemoveQuery());
				
				setRemoveValues(preparedStatement, it.next());
				preparedStatement.execute();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				connect.close();
				preparedStatement.close();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
	private String createLoadQuery() {
		String query = "SELECT * FROM Facture." + getTableName();
		return query;
	}
	
	private String createInsertQuery() {
		String query = "INSERT INTO Facture."+ getTableName();
		
		String cols = "(" + CODE;
		String vals = "(?";
		for(Iterator<String> it = getColumnNames().iterator(); it.hasNext();) {
			cols += ", " + it.next();
			vals += ", ?";
		}
		cols += ")";
		vals += ")";
		query += cols + " VALUES " + vals;
		return query;
	}
	
	private String createUpdateQuery() {
		String query = "UPDATE Facture." + getTableName() + " SET ";
		
		Iterator<String> it = getColumnNames().iterator();
		query += it.next() + " = ?";
		while(it.hasNext()) {
			query += ", " + it.next() + " = ?";
		}
		query += " WHERE " + CODE + " = ?";
		
		return query;
	}
	
	private String createRemoveQuery() {
		String query = "DELETE FROM Facture." + getTableName() + " WHERE code = ?";
		return query;
	}
	
	private void setInsertValues(PreparedStatement st, T obj) throws SQLException {
		st.setString(1, obj.getCode());
		List<String> colNames = getColumnNames();
		int i = 2;
		for(Iterator<String> it = colNames.iterator(); it.hasNext();) {
			st.setObject(i, getValue(it.next(), obj));
			i++;
		}
	}
	
	private void setUpdateValues(PreparedStatement st, T obj) throws SQLException {
		List<String> colNames = getColumnNames();
		int i = 1;
		for(Iterator<String> it = colNames.iterator(); it.hasNext();) {
			st.setObject(i, getValue(it.next(), obj));
			i++;
		}
		st.setString(i, obj.getCode());
	}
	
	private void setRemoveValues(PreparedStatement st, T obj) throws SQLException{
		st.setString(1, obj.getCode());
	}
	
	protected abstract String getTableName();

	protected abstract List<String> getColumnNames();
	
	protected abstract Object getValue(String colName, T obj);
	
	protected abstract T init(ResultSet rs) throws SQLException;
}
