package com.internetitem.config.server;

import java.io.FileReader;
import java.io.IOException;

public class FileUtility {
	public static String slurp(String filename) throws IOException {
		StringBuilder builder = new StringBuilder();
		int numRead;
		char[] buf = new char[2048];
		try (FileReader reader = new FileReader(filename)) {
			while ((numRead = reader.read(buf)) > 0) {
				builder.append(buf, 0, numRead);
			}
		}
		return builder.toString();
	}
}
