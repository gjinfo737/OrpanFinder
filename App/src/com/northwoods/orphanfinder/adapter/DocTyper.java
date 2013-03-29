package com.northwoods.orphanfinder.adapter;

import java.io.File;

public class DocTyper {

	public static String getDoctypeName(File directory) {
		String absolutePath = directory.getAbsolutePath();
		String dirName = findDirectoryname(absolutePath);
		int indexOfH = dirName.indexOf("-");
		dirName = dirName.substring(0, indexOfH).trim();

		dirName = dirName.replace(File.separator, "");

		return dirName;
	}

	private static String findDirectoryname(String absolutePath) {
		int lastIndexOf = absolutePath.lastIndexOf(File.separator);
		return absolutePath.substring(lastIndexOf);
	}

}
