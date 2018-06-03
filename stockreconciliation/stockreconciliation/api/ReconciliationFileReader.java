package stockreconciliation.api;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import stockreconciliation.model.Position;
import stockreconciliation.model.ReconciliationInput;
import stockreconciliation.model.ReconciliationLineType;
import stockreconciliation.model.Transaction;

/*
 * Class that takes in recon.in file and creates data structure by reading data line by line. 
 */
public class ReconciliationFileReader {
	
	private String currentFileBeingProcessed;
	private ReconciliationInput reconciliationInputData = new ReconciliationInput();
	
	public ReconciliationFileReader() { }
	
	public ReconciliationInput read(String fileName) {
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName)))
		{			
			stream.forEach((line) -> {
				ReconciliationLineType lineType = getLineType(line);
				
				switch(lineType)
				{
					case Header:
						currentFileBeingProcessed = line;
						break;
					case Position:
						reconciliationInputData = createNewPosition(reconciliationInputData, line, currentFileBeingProcessed);
						break;
					case Transaction:
						reconciliationInputData = createNewTransaction(reconciliationInputData, line);
					default:
						break;					
				}
			});
					 			
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return reconciliationInputData;
	}
	
	//Method used to decipher what type of data is on given line
	//Returns enum representation of line
	private ReconciliationLineType getLineType(String line) {
		if(line.contains("-"))
			return ReconciliationLineType.Header;
		else if(line.split(" ").length == 2)
			return ReconciliationLineType.Position;
		else if(line.split(" ").length == 4)
			return ReconciliationLineType.Transaction;
		else
			return ReconciliationLineType.EmptyLine;
	}
	
	//Method that creates new position and adds it to a hashmap.
	private ReconciliationInput createNewPosition(ReconciliationInput reconciliationData, String line, String fileType)
	{
		String[] positionAsArray  = line.split(" ");
		Position positionToAdd = new Position(positionAsArray[0], Double.parseDouble(positionAsArray[1]));
		
		if(fileType.equals("D0-POS"))
			reconciliationData.DayPositions.processDayZeroAccountInfo(positionToAdd);
		else
			reconciliationData.DayPositions.processDayOneAccountInfo(positionToAdd);
		
		return reconciliationData;
	}
	
	//Method that creates a new transaction and adds it to a list. 
	private ReconciliationInput createNewTransaction(ReconciliationInput reconciliationData, String line)
	{
		String[] transactionAsArray  = line.split(" ");
		
		Transaction transactionToAdd = new Transaction(transactionAsArray[0], 
				transactionAsArray[1], 
				Double.parseDouble(transactionAsArray[2]), 
				Double.parseDouble(transactionAsArray[3]));
		
		reconciliationData.DayOneTransactions.add(transactionToAdd);
		
		return reconciliationData;
	}
}
