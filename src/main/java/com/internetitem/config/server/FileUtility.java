package com.internetitem.config.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtility {
	public static String slurpFromClasspath(String filename) throws IOException {
		InputStream is = FileUtility.class.getResourceAsStream(filename);
		if (is == null) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		int numRead;
		char[] buf = new char[2048];
		try (InputStreamReader reader = new InputStreamReader(is, "UTF-8")) {
			while ((numRead = reader.read(buf)) > 0) {
				builder.append(buf, 0, numRead);
			}
		}
		return builder.toString();
	}
}
