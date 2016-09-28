package domain;

import exception.DomainException;

public enum Products {
	CD {
		public double getPrice() {
			return (1.5);
		}

		@Override
		public Product createProduct(String title, int id) throws DomainException {
			return new CD(title, id);
		}
	},
	MOVIE {
		public double getPrice() {
			return 3;
		}

		@Override
		public Product createProduct(String title, int id) throws DomainException {
			return new Movie(title, id);
		}
	},
	GAME {
		public double getPrice() {
			return 5;
		}

		@Override
		public Product createProduct(String title, int id) throws DomainException {
			return new Game(title, id);
		}
	};
	
	public abstract double getPrice();
	public abstract Product createProduct(String title, int id) throws DomainException;

    public static String getProductCharValue(final char character)
    {
        for (Products type : Products.values())
            if (type.name().charAt(0) == character)
                return type.name();

        return null;
    }
}
