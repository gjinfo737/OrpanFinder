package com.northwoods.orphanfinder.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaTyper {
	public static final String _3GP = "3gp";
	public static final String MP3 = "mp3";
	public static final String WAV = "wav";
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

		if (hasFileType(directory, WAV))
			return true;
		if (hasFileType(directory, MP3))
			return true;
		if (hasFileType(directory, _3GP))
			return true;

		return false;
	}

	private boolean hasFileType(File directory, String extension) {
		return getAllFilesWithExtension(directory, "." + extension).length > 0;
	}

	public File[] getAllFilesWithExtension(File directory, String extension) {
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

	public boolean isMediaFile(File file) {
		return isFileWithExtension(file, _3GP)
				|| isFileWithExtension(file, MP3)
				|| isFileWithExtension(file, WAV)
				|| isFileWithExtension(file, JPG)
				|| isFileWithExtension(file, GIF)
				|| isFileWithExtension(file, PNG);
	}
}
