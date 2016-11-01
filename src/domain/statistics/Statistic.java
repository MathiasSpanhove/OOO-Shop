package domain.statistics;

import java.time.LocalDate;
import java.util.Map;

public class Statistic {

	private LocalDate date;
	private Map<String, Integer> stats;
	
	public Statistic(LocalDate date, Map<String, Integer> stats) {
		this.date = date;
		this.stats = stats;
	}
	
	public void increment(String type) {
		int count = 0;
		if(this.stats.containsKey(type)) {
			count = stats.get(type);
		}
		count++;
		stats.put(type, count);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Map<String, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<String, Integer> stats) {
		this.stats = stats;
	}	
	
}
