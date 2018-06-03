package stockreconciliation.api;

import java.util.Map;

import stockreconciliation.model.*;

/*
 * Class that takes in the 3 main inputs(D0-POS, D1-TRN, D1-POS) which is represented as ReconciliationInput.
 * The purpose of this class is to delegate the iteration through transactions while creating the expected D1-POS.(ProcessTransactions).
 * Compare expected D1-POS && actual D1-POS and create a map of the differences.
 */
public class UnitReconciliation {
	
	ReconciliationInput reconInput;
	TransactionProcessor transProcesser = new TransactionProcessor();
	private final double removePositionsWithZeroVal = 0.0;
	
	public UnitReconciliation(ReconciliationInput reconInput) {
		this.reconInput = reconInput;
	}
	
	public void transactionIterator() {	
		
		reconInput.DayOneTransactions.stream()
			.forEach((transaction -> {
				transProcesser.processEachTransaction(transaction, reconInput);}));	
		
		compareDayPositions();	
	}
	
	//Method that compares actual D1-POS && expected D1-POS and dynamically creates the differences
	//between the two.
	private void compareDayPositions() {
		
		for(Map.Entry<String, Double> dayOnePosition : reconInput.DayPositions.dayOneAccountInfo.entrySet()) {
			if(reconInput.DayPositions.dayZeroAccountInfo.containsKey(dayOnePosition.getKey())) {
				
				reconInput.DayPositions.dayZeroAccountInfo.put(dayOnePosition.getKey(), 
						dayOnePosition.getValue() - 
						reconInput.DayPositions.dayZeroAccountInfo.get(dayOnePosition.getKey()));
			}
			else {
				
				reconInput.DayPositions.dayZeroAccountInfo.put(dayOnePosition.getKey(), dayOnePosition.getValue());
			}
		}
		
		reconInput.DayPositions.dayZeroAccountInfo.values().removeIf(val -> removePositionsWithZeroVal == val);
	}
}
