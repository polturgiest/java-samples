package com.geek;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SampleCSV {
	public static void main(String[] args) {
		SampleCSV a = new SampleCSV();
		a.run();
	}

	private void run() {
		CSVParser c = null;
		Scanner sc = null;
		FileWriter fw = null;
		Reader fr = null;
		URL er = null;
		
		try {
			String[] header = new String[] {
					"cdatetime",
					"address",
					"district",
					"beat",
					"grid",
					"crimedescr",
					"ucr_ncic_code",
					"latitude",
					"longitude"
			};

			
			er = new URL("http://samplecsvs.s3.amazonaws.com/SacramentocrimeJanuary2006.csv");
			sc = new Scanner(er.openStream());
			
			File tempFile = File.createTempFile("temp", "csv");
			fw = new FileWriter(tempFile, true);
			
			boolean first = true;
			while (sc.hasNext()) {
				if (first) {
					fw.write(sc.nextLine());
					first = false;
				} else {
					fw.write(System.getProperty("line.separator") + sc.nextLine());
				}
			}
			fw.flush();
			
			fr = new FileReader(tempFile);
			
			c = new CSVParser(
					fr, 
					CSVFormat.DEFAULT
					.withHeader(header)
					.withFirstRecordAsHeader()
				);
			
			
			int i = 1;
			
			Map<String, Integer> hMap = c.getHeaderMap();
			
			for (CSVRecord rw : c.getRecords()) {
				StringBuilder sb = new StringBuilder();
				sb.append(i++).append(") ");
				
				for (String hd : hMap.keySet()) {
					String col = null;
					if (hd.equalsIgnoreCase("beat")) col = rw.get(hd).trim();
					else col = rw.get(hd);
						
					sb.append(hd).append("=").append(col).append("; ");
				}
				
				System.out.println(sb.toString());
			}
			
			tempFile.deleteOnExit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Utils.close(c, sc, fr, fw, er);
		}
	}
}
