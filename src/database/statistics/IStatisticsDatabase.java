package database.statistics;

import java.time.LocalDate;
import java.util.List;
import domain.statistics.Statistic;
import exception.DatabaseException;

public interface IStatisticsDatabase {
	
	public Statistic getStatistic(LocalDate date) throws DatabaseException;
	public List<Statistic> getAllStatistics() throws DatabaseException;
	public void addStatistics(Statistic statistic) throws DatabaseException;
	public void updateStatistics(Statistic statistic) throws DatabaseException;
	public void deleteStatistics(LocalDate date) throws DatabaseException;
	
	public void open() throws DatabaseException;
	public void close() throws DatabaseException;
	
	public boolean statisticExists(LocalDate now) throws DatabaseException;
}
