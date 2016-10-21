package database.statistics;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import domain.product.enums.Products;
import exception.DatabaseException;

public interface IStatisticsDatabase {
	
	public EnumMap<Products, Integer> getStatistics(LocalDate date) throws DatabaseException;
	public Map<LocalDate, EnumMap<Products, Integer>> getAllStatistics() throws DatabaseException;
	public void addStatistics(EnumMap<Products, Integer> s) throws DatabaseException;
	public void updateStatistics(EnumMap<Products, Integer> s) throws DatabaseException;
	public void deleteStatistics(LocalDate date) throws DatabaseException;
	
	public void open() throws DatabaseException;
	public void close() throws DatabaseException;
}
