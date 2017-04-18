package com.geek;

import java.io.Closeable;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.commons.csv.CSVParser;

public class Utils {

	public static void close(Object... objs) {
		try {
			if (objs == null) return;
			for (Object o : objs) {
				if (o != null) {
					if (o instanceof CSVParser) {
						((CSVParser) o).close();
					} else if (o instanceof Connection) {
						((Connection) o).close();
					} else if (o instanceof ResultSet) {
						((ResultSet) o).close();
					} else if (o instanceof Statement) {
						((Statement) o).close();
					} else if (o instanceof Scanner) {
						((Scanner) o).close();
					} else if (o instanceof FileReader) {
						((FileReader) o).close();
					} else if (o instanceof FileWriter) {
						((FileWriter) o).close();
					} else if (o instanceof Closeable) {
						((Closeable) o).close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
