package stockreconciliation.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Custom object that encapsulates D0-POS, D1-TRN, and D1-POS. 
 */
public class ReconciliationInput {
	
	public Account DayPositions;
	public List<Transaction> DayOneTransactions;
	
	public ReconciliationInput()
	{
		DayOneTransactions = new ArrayList<Transaction>();
		DayPositions = new Account();
	}
}
