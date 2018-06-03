package stockreconciliation.model;

/*
 * Object make up for D1-TRN from recon.in
 */
public class Transaction extends Position {

	private String transactionCode;
	private double totalValue;
	
	public Transaction(String stockSymbol, String transactionCode, double amountOfShares, double totalValue) {
		super(stockSymbol, amountOfShares);		
		this.transactionCode = transactionCode;
		this.totalValue = totalValue;
	}
		
	public String getTransactionCode() {
		return transactionCode;
	}
	
	public double getTotalValue() {
		return totalValue;
	}
	
}
