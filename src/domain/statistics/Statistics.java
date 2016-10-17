package domain.statistics;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import domain.Shop;
import domain.customer.Observer;
import domain.product.Product;
import domain.product.enums.Products;
import exception.DatabaseException;
import exception.DomainException;

public class Statistics implements Observer {

	private Map<LocalDate, EnumMap<Products, Integer>> statistics;
	private Shop shop;
	
	public Statistics(Shop shop) throws DatabaseException, DomainException {
		this.shop = shop;
		shop.registerSubscriber(this);
		Map<LocalDate, EnumMap<Products, Integer>> statistics = new HashMap<LocalDate, EnumMap<Products, Integer>>();
	}

	@Override
	public void update(Object product) {
		if(product instanceof Product) {
			EnumMap<Products, Integer> statsOfToday = statistics.get(LocalDate.now());
			Integer count = statsOfToday.get(getEnum(product));
			count++;
			statsOfToday.put(getEnum(product), count);
			statistics.put(LocalDate.now(), statsOfToday);
		}
		
	}
	
	private Products getEnum(Object product) {
		return Products.valueOf(product.getClass().getSimpleName());
	}
}
