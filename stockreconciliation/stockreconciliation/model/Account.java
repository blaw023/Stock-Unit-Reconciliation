package stockreconciliation.model;

import java.util.HashMap;
import java.util.Map;

/*
 * Class that represents recon.in account as a whole.
 * Sole purpose is to add new positions to prospective maps as file is being read in. 
 */
public class Account {
	
	public Map<String, Double> dayZeroAccountInfo = new HashMap<String, Double>();
	public Map<String, Double> dayOneAccountInfo  = new HashMap<String, Double>();
	
	//Called when adding new position from ReconciliationFileReader
	//Puts Key/Value(Symbol, AmoutOfShares) into map for D0-POS.
	public void processDayZeroAccountInfo(Position position) {
		
		dayZeroAccountInfo.put(position.getStockSymbol(), position.getAmountOfShares());
	}
	
	//Called when adding new position from ReconciliationFileReader
	//Puts Key/Value(Symbol, AmoutOfShares) into map for D1-POS.
	public void processDayOneAccountInfo(Position position) {
		
		dayOneAccountInfo.put(position.getStockSymbol(), position.getAmountOfShares());
	}
}
