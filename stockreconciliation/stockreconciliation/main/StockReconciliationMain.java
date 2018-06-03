package stockreconciliation.main;


import java.io.IOException;
import java.util.Map;

import stockreconciliation.api.ReconciliationFileReader;
import stockreconciliation.api.ReconciliationFileWriter;
import stockreconciliation.api.UnitReconciliation;
import stockreconciliation.model.*;

public class StockReconciliationMain {
	
	private static final String fileName = "******";

	public static void main(String[] args) throws IOException {
		ReconciliationFileReader fileReader = new ReconciliationFileReader();
		ReconciliationInput reconInput = fileReader.read(fileName);
		
		UnitReconciliation unitRecon = new UnitReconciliation(reconInput);
		unitRecon.transactionIterator();
		
		ReconciliationFileWriter fileWriter = new ReconciliationFileWriter();
		fileWriter.writeReconOutFile(reconInput.DayPositions.dayZeroAccountInfo);
		
		System.out.println("Recon.out file complete");
	}

}
