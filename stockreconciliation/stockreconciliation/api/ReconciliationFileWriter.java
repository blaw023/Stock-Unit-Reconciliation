package stockreconciliation.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import stockreconciliation.model.Account;
import stockreconciliation.model.Position;

/*
 * Takes in key/pair value of the result of the compared positions(Actual(D1- POS) && Expected(D1-POS))
 * Proceeds to write results to recon.out file.
 */
public class ReconciliationFileWriter {
	
	private static final String fileName = "/home/blaw023/Public/Branden/recon.out";
	
	
	public void writeReconOutFile(Map<String, Double> comparedPositions) throws IOException {
		String dataSeparator = " ";
		NumberFormat formatPositionValue  = new DecimalFormat("##.##");
		
		Files.write(Paths.get(fileName), () -> comparedPositions.entrySet().stream()
				.<CharSequence>map(e -> e.getKey() + dataSeparator + formatPositionValue.format(e.getValue()))
				.iterator());	
	}
}
	

