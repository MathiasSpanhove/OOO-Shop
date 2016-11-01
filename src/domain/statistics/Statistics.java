package domain.statistics;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import database.statistics.IStatisticsDatabase;
import database.statistics.StatisticsDatabaseSQL;
import domain.Shop;
import domain.customer.Observer;
import domain.product.Product;
import domain.product.enums.Products;
import exception.DatabaseException;
import exception.DomainException;

public class Statistics implements Observer {

	private IStatisticsDatabase statisticsDb;
	private Shop shop;
	
	public Statistics(Shop shop) throws DatabaseException, DomainException {
		this.shop = shop;
		shop.registerSubscriber(this);
		this.statisticsDb = StatisticsDatabaseSQL.getInstance();
	}

	@Override
	public void update(Object product) throws DatabaseException {
		if(product instanceof Product) {
			Product p = (Product) product;
			incrementStatistic(p);
		}
	}
	
	private void incrementStatistic(Product p) throws DatabaseException {
		boolean exists = statisticsDb.statisticExists(LocalDate.now());
		Statistic statistic = null;
		
		if(exists) {
			statistic = this.statisticsDb.getStatistic(LocalDate.now());
		} else {
			statistic = new Statistic(LocalDate.now(), new HashMap<String, Integer>());
		}

		String type = p.getClass().getSimpleName();
		statistic.increment(type);
		
		if(exists) {
			statisticsDb.updateStatistics(statistic);
		} else {
			statisticsDb.addStatistics(statistic);
		}
	}
	
	private Products getEnum(Object product) {
		return Products.valueOf(product.getClass().getSimpleName());
	}
	
	public String statsToString() throws DatabaseException {
		String output = "";

		for(Statistic s : statisticsDb.getAllStatistics()) {
			output += s.getDate() + " -";
			
			for(Map.Entry<String, Integer> stat : s.getStats().entrySet()) {
				output += " " + stat.getKey() + ": " + stat.getValue() + " |";
			}
			output = output.substring(0, output.length() - 1);
			output += "\n";
		}
		
		return output;
	}
}
