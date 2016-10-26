package domain.product.enums;

public enum Products {
	CD (1.5, "CD", "domain.product.CD"),
	MOVIE (3, "Movie", "domain.product.Movie"),
	GAME (5, "Game", "domain.product.Game");
	
	private double price;
	private String name, pathName;
	
	private Products(double price, String name, String pathName) {
		this.price = price;
		this.name = name;
		this.pathName = pathName;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String getName() {
		return this.name;
	}

	public String getPathName() {
		return this.pathName;
	}
    
    public static String[] getNames() {
    	String[] names = new String[Products.values().length];
    	for (int i = 0; i < Products.values().length; i++) {
    		names[i] = Products.values()[i].getName();
    	}
    	
    	return names;
    }
}
