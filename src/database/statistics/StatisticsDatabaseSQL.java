package database.statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import domain.product.enums.Products;
import domain.statistics.Statistic;
import exception.DatabaseException;

public class StatisticsDatabaseSQL implements IStatisticsDatabase {
	
	// basis voor initialiseren databaseconnectie
	private Connection connection;
	private PreparedStatement statement;
	private Properties properties;
	private static final String URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7139719";
	private volatile static StatisticsDatabaseSQL uniqueInstance;
	
	private StatisticsDatabaseSQL() {
		// properties voor verbinding maken
		this.properties = new Properties();
		this.properties.setProperty("user", "sql7139719");
		this.properties.setProperty("password", "nT6fJKVEci");
		this.properties.setProperty("ssl", "true");
		this.properties.setProperty("sslfactory", "org.mysql.ssl.NonValidatingFactory");

		// connectie maken met gegeven databank met opgegeven credentials
		// beschreven in properties
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static StatisticsDatabaseSQL getInstance() {
		if(uniqueInstance == null) {
			synchronized (StatisticsDatabaseSQL.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new StatisticsDatabaseSQL();
				}
			}
		}
		return uniqueInstance;
	}

	@Override
	public Statistic getStatistic(LocalDate date) throws DatabaseException {
		this.open();

		Statistic statistic = null;
		String sql = "SELECT * FROM statistics " + "WHERE date ='" + date + "'";

		try {
			this.statement = this.connection.prepareStatement(sql);
			ResultSet result = this.statement.executeQuery();
			
			//Get column count
			ResultSetMetaData meta = result.getMetaData();
			final int columnCount = meta.getColumnCount();
			
			if (result.next()) {
				Map<String, Integer> stats = new HashMap<String, Integer>();
				
				for (int col = 2; col <= columnCount; col++) {
					Integer value = result.getInt(col);
					String colName = meta.getColumnName(col);
					stats.put(colName, value);
				}
				
				statistic = new Statistic(date, stats);
			} else {
				throw new DatabaseException("There is no statistic with the given date");
			}

			result.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}

		return statistic;
	}

	@Override
	public List<Statistic> getAllStatistics() throws DatabaseException {
		this.open();

		List<Statistic> statistics = new ArrayList<Statistic>();
		String sql = "SELECT * FROM statistics";

		try {
			this.statement = this.connection.prepareStatement(sql);
			ResultSet result = this.statement.executeQuery();
			
			//Get column count
			ResultSetMetaData meta = result.getMetaData();
			final int columnCount = meta.getColumnCount();
			
			boolean empty = true;
			while (result.next()) {
				empty = false;
				LocalDate date = LocalDate.parse(result.getString("date"));
				Map<String, Integer> stats = new HashMap<String, Integer>();
				
				for (int col = 2; col <= columnCount; col++) {
					Integer value = result.getInt(col);
					String colName = meta.getColumnName(col);
					stats.put(colName, value);
				}
				
				statistics.add(new Statistic(date, stats));
			}
			
			if(empty) {
				throw new DatabaseException("There are no statistics");
			}

			result.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}

		return statistics;
	}

	@Override
	public void addStatistics(Statistic statistic) throws DatabaseException {
		if(statisticExists(statistic.getDate())) {
			throw new DatabaseException("This statistic already exists");
		}
		
		this.open();

		String sql = "INSERT INTO statistics(date";
		
		for(String type : statistic.getStats().keySet()) {
			sql += ", " + type;
		}
		
		sql += ") VALUES(?";
		
		for(int value : statistic.getStats().values()) {
			sql += ", ?";
		}
		
		sql += ")";
		
		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.setString(1, statistic.getDate().toString());
			
			int colCount = 2;
			for(int value : statistic.getStats().values()) {
				this.statement.setString(colCount, "" + value);
				colCount++;
			}
			
			this.statement.execute();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}
	}

	@Override
	public void updateStatistics(Statistic statistic) throws DatabaseException {
		if(!statisticExists(statistic.getDate())) {
			throw new DatabaseException("This statistic does not exist");
		}
		
		this.open();

		String sql = "UPDATE statistics SET(";
		
		for(Map.Entry<String, Integer> stat : statistic.getStats().entrySet()) {
			sql += stat.getKey() + "=" + stat.getValue() + ",";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		
		sql += ") WHERE date='" + statistic.getDate() + "'";
		
		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.execute();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}
	}

	@Override
	public void deleteStatistics(LocalDate date) throws DatabaseException {
		if(!statisticExists(date)) {
			throw new DatabaseException("This statistic does not exist");
		}
		
		String sql = "DELETE FROM statistics " + "WHERE date ='" + date.toString() + "'";

		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.execute();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}
	}

	public void open() throws DatabaseException {
		try {
			// make connection
			this.connection = DriverManager.getConnection(URL, this.properties);

			// does table exist?
			java.sql.DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "statistics", null);
			if (!tables.next()) {
				createStatisticsTable();
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public void close() throws DatabaseException {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} 
	}
	
	public boolean statisticExists(LocalDate date) throws DatabaseException {
		this.open();

		String sql = "SELECT * FROM statistics " + "WHERE date ='" + date + "'";

		try {
			this.statement = this.connection.prepareStatement(sql);
			ResultSet result = this.statement.executeQuery();

			return result.next();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.close();
		}
	}
	
	private void createStatisticsTable() throws DatabaseException {
		String sql = "CREATE TABLE statistics (date VARCHAR(255) NOT NULL";
		
		for(Products p : Products.values()) {
			sql += ", " + p.getName() + " int";
		}
		
		sql +=	", PRIMARY KEY ( date ))";
		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	private void createExtraColumn(String name) throws DatabaseException {
		String sql = "ALTER TABLE statistics ADD COLUMN " + name + " int";

		try {
			this.statement = this.connection.prepareStatement(sql);
			this.statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

}
