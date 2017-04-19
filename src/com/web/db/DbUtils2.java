package com.web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.web.util.Putil;

public class DbUtils2 {

	public static DataSource getDataSource() {
		try {
			InitialContext ctx = new InitialContext();
			Context context = (Context) ctx.lookup("java:comp/env");
			DataSource dataSource = (DataSource)context.lookup("insure");
			//javax.naming.Context ctx = new javax.naming.InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("emt");
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection() {
		DataSource dataSource = getDataSource();
		try {
			if (dataSource != null)
				return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection(String jndiName) {
		try {
			Context ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			DataSource dataSource = (DataSource) env.lookup(jndiName);
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static QueryRunner getQueryRunner() {
		DataSource dataSource = getDataSource();
		if (dataSource != null)
			return new QueryRunner(dataSource);
		return null;
	}

	public static List<Map<String, Object>> query(String sql) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		
		Connection connection = DbUtils2.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			Map<String, Object> map = null;
			ResultSetMetaData metaData = resultSet.getMetaData();
			
			while (resultSet.next()) {
				map = new HashMap<String, Object>();
				int col = metaData.getColumnCount();
				String columnName, columnType;
				for (int i = 1; i <= col; i++) {
					columnName = metaData.getColumnLabel(i);
					columnType = metaData.getColumnTypeName(i);
					if (columnType.indexOf("INT")>=0) {
						map.put(columnName.toLowerCase(), resultSet.getInt(columnName));
					} else if (columnType.equals("DATETIME")) {
						if(resultSet.getTimestamp(columnName)!=null) {
							map.put(columnName.toLowerCase(), Putil.format2(resultSet.getTimestamp(columnName).getTime()));
							map.put(columnName.toLowerCase()+"_", Putil.format4(resultSet.getTimestamp(columnName).getTime()));
							map.put(columnName.toLowerCase()+"__", Putil.format5(resultSet.getTimestamp(columnName).getTime()));
						} else {
							map.put(columnName.toLowerCase(), "");
							map.put(columnName.toLowerCase()+"_", "");
							map.put(columnName.toLowerCase()+"__", "");
						}
					} else {
						map.put(columnName.toLowerCase(), resultSet.getString(columnName));
					}

				}
				items.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils2.close(resultSet, statement, connection);
		}

		return items;
	}

	public static Map<String, Object> queryOne(String sql) {
		Map<String, Object> map = null;
		
		Connection connection = DbUtils2.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			ResultSetMetaData metaData = resultSet.getMetaData();
			
			if(resultSet.next()) {
				map = new HashMap<String, Object>();
				int col = metaData.getColumnCount();
				String columnName, columnType;
				for (int i = 1; i <= col; i++) {
					columnName = metaData.getColumnName(i);
					columnType = metaData.getColumnTypeName(i);
					//Putil.printLog(metaData.getColumnLabel(i));
					//if (columnType.equals("TIMESTAMP")) {
					//	map.put(columnName.toLowerCase(), resultSet.getTimestamp(columnName));
					//} else 
					if (columnType.indexOf("INT")>=0) {
						map.put(columnName.toLowerCase(), resultSet.getInt(columnName));
					} else if (columnType.equals("DATETIME")) {
						if(resultSet.getTimestamp(columnName)!=null) {
							map.put(columnName.toLowerCase(), Putil.format2(resultSet.getTimestamp(columnName).getTime()));
							map.put(columnName.toLowerCase()+"_", Putil.format4(resultSet.getTimestamp(columnName).getTime()));
							map.put(columnName.toLowerCase()+"__", Putil.format5(resultSet.getTimestamp(columnName).getTime()));
						} else {
							map.put(columnName.toLowerCase(), "");
							map.put(columnName.toLowerCase()+"_", "");
							map.put(columnName.toLowerCase()+"__", "");
						}
					} else {
						map.put(columnName.toLowerCase(), resultSet.getString(columnName));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils2.close(resultSet, statement, connection);
		}

		return map;
	}
	
	public static int[] updateBatch(ArrayList<String> sql) {
		Connection connection = DbUtils2.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			if(sql==null || sql.size()==0) {
				return null;
			}
			for(String s:sql) {
				statement.addBatch(s);
			}
			return statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils2.close(resultSet, statement, connection);
		}

		return null;
	}
	
	public static int save(String sql) {
		int count = 0 ;
		Connection connection = DbUtils2.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			count = statement.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		} finally {
			DbUtils2.close(statement, connection);
		}

		return count;
	}

	public static void close(Object... args) {
		if (args != null) {
			for (Object o : args) {
				if (o == null)
					continue;

				if (o instanceof Connection) {
					Connection connection = (Connection) o;
					try {
						connection.close();
					} catch (SQLException e) {
						Putil.printLog(e.toString());;
					}
				} else if (o instanceof Statement) {
					Statement statement = (Statement) o;
					try {
						statement.close();
					} catch (SQLException e) {
						Putil.printLog(e.toString());;
					}
				} else if (o instanceof ResultSet) {
					ResultSet resultSet = (ResultSet) o;
					try {
						resultSet.close();
					} catch (SQLException e) {
						Putil.printLog(e.toString());;
					}
				}
			}
		}
	}
}
