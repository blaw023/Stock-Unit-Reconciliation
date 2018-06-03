package stockreconciliation.api;

import java.util.Map;

import stockreconciliation.model.ReconciliationInput;
import stockreconciliation.model.Transaction;
import stockreconciliation.model.TransactionCode;

public class TransactionProcessor {
	
	private final String cashKeyIdentifier = "Cash";
	private Map<String, Double> tempReconInput;
	
	public TransactionProcessor() {}

    //Method that is called when iterating through each transaction
	//Based on transaction code type, it will call private method to do calculations on expected D1-POS. 
	public void processEachTransaction(Transaction transaction, ReconciliationInput reconInput) {
		
		//Doing this for readability purposes in the private methods.
		//(reconInput.DayPositions.dayZeroAccountInfo) is long!
		tempReconInput = reconInput.DayPositions.dayZeroAccountInfo;
		
		switch(transaction.getTransactionCode()) {
			case TransactionCode.BUY: 
				buyStock(transaction);
				break;
			case TransactionCode.SELL: 
				sellStock(transaction);
				break;
			case TransactionCode.FEE: 
				stockFees(transaction);
				break;
			case TransactionCode.DEPOSIT:
			case TransactionCode.DIVIDEND: {
				insertIntoAccount(transaction);
				break;
			}
			default:
				break;
		   }
	}
	
	private void buyStock(Transaction trans) {
		
		if(tempReconInput.containsKey(trans.getStockSymbol())) {
			
			tempReconInput.put(trans.getStockSymbol(), 
					tempReconInput.get(trans.getStockSymbol()) + trans.getAmountOfShares());
		}
		else {
			tempReconInput.put(trans.getStockSymbol(), trans.getAmountOfShares());
		}
		
			
		tempReconInput.put(cashKeyIdentifier, tempReconInput.get(cashKeyIdentifier) - trans.getTotalValue());
	
	}
	
	private void sellStock(Transaction trans) {
		
		if(tempReconInput.containsKey(trans.getStockSymbol())) {
			
			tempReconInput.put(trans.getStockSymbol(), 
					tempReconInput.get(trans.getStockSymbol()) - trans.getAmountOfShares());
			
		}
		else {
			tempReconInput.put(trans.getStockSymbol(), trans.getAmountOfShares());
		}
		
		
		tempReconInput.put(cashKeyIdentifier, tempReconInput.get(cashKeyIdentifier) + trans.getTotalValue());
		
	}
	
	private void stockFees(Transaction trans) {
		
		tempReconInput.put(cashKeyIdentifier, tempReconInput.get(cashKeyIdentifier) - trans.getTotalValue());	
	}
	
	private void insertIntoAccount(Transaction trans) {
		
		tempReconInput.put(cashKeyIdentifier, tempReconInput.get(cashKeyIdentifier) + trans.getTotalValue());	
	}
		
	
}
