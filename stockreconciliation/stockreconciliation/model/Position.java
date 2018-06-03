package stockreconciliation.model;

/*
 * Object make up for D0-POS and D1-POS from recon.in
 */
public class Position {

	private String stockSymbol;
	private double amountOfShares;
	
	public Position(String stockSymbol, double amountOfShares) {
		this.stockSymbol = stockSymbol;
		this.amountOfShares = amountOfShares;
	}
	

	public String getStockSymbol() {
		return stockSymbol;
	}
	
	public double getAmountOfShares() {
		return amountOfShares;
	}
}
