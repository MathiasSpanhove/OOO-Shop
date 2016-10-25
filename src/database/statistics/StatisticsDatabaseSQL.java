package database.statistics;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import domain.product.enums.Products;
import exception.DatabaseException;

public class StatisticsDatabaseSQL implements IStatisticsDatabase {
	
	private volatile static StatisticsDatabaseSQL uniqueInstance;
	private EnumMap<Products, Integer> statistics;
	
	private StatisticsDatabaseSQL() {
		this.statistics = new EnumMap<Products, Integer>(Products.class);
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
	public EnumMap<Products, Integer> getStatistics(LocalDate date) throws DatabaseException {
		return statistics;
	}

	@Override
	public Map<LocalDate, EnumMap<Products, Integer>> getAllStatistics() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStatistics(EnumMap<Products, Integer> s) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStatistics(EnumMap<Products, Integer> s) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteStatistics(LocalDate date) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void open() throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws DatabaseException {
		// TODO Auto-generated method stub

	}

}
