package com.northwoods.orphanfinder.fileskim;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

	public static String readFullFileContents(File file, boolean withComments) {
		try {
			return readFile(file.getAbsolutePath(), withComments);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String readFile(String path, boolean withComments)
			throws IOException {
		File file = new File(path);
		// ~
		// String contents = "";
		// BufferedReader br = new BufferedReader(new FileReader(file));
		// String line;
		// while ((line = br.readLine()) != null) {
		// // process the line.
		// if (withComments || lineIsNotComment(line))
		// contents += "\n\r" + line;
		// }
		// br.close();
		// return contents;
		// ~

		FileInputStream stream = new FileInputStream(file);
		try {
			int length = (int) file.length();
			byte[] buffer = new byte[length];
			stream.read(buffer, 0, length);
			return new String(buffer);
		} finally {
			stream.close();
		}
	}

}
