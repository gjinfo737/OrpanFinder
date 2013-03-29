package com.northwoods.orphanfinder.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaTyper {
	public static final String GIF = "gif";
	public static final String PNG = "png";
	public static final String JPG = "jpg";

	public enum MediaType {
		IMAGE, AUDIO, UNKNOWN
	}

	public MediaType getMediaType(File directory) {
		if (isImage(directory))
			return MediaType.IMAGE;
		if (isAudio(directory))
			return MediaType.AUDIO;
		return MediaType.AUDIO;
	}

	private boolean isImage(File directory) {

		if (hasFileType(directory, JPG))
			return true;
		if (hasFileType(directory, PNG))
			return true;
		if (hasFileType(directory, GIF))
			return true;

		return false;
	}

	private boolean isAudio(File directory) {

		if (hasFileType(directory, "wav"))
			return true;
		if (hasFileType(directory, "mp3"))
			return true;
		if (hasFileType(directory, "3gp"))
			return true;

		return false;
	}

	private boolean hasFileType(File directory, String extension) {
		return getAllFilesWithExtension(directory, "." + extension).length > 0;
	}

	private File[] getAllFilesWithExtension(File directory, String extension) {
		File[] allFiles = directory.listFiles();
		List<File> fileList = new ArrayList<File>();
		for (File file : allFiles) {

			if (isFileWithExtension(file, extension))
				fileList.add(file);
		}
		File[] featureFiles = new File[fileList.size()];
		for (int i = 0; i < fileList.size(); i++) {
			featureFiles[i] = fileList.get(i);
		}
		return featureFiles;
	}

	public boolean isFileWithExtension(File file, String extension) {
		boolean isFeatureFile = !file.isDirectory()
				&& file.getName().contains(extension);

		return isFeatureFile;
	}
}
