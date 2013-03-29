package com.northwoods.orphanfinder.fileskim;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLister {

	public static final String CONSOLE_STR_READING_FILE = "Reading";
	private static boolean IS_JIT = true;

	private File rootOfAllFiles;
	private List<File> allDirectories;

	public List<File> getAllDirectories() {
		return allDirectories;
	}

	private static boolean STOP_ALL_EVENTS = false;

	public FileLister(String root) {
		this.rootOfAllFiles = new File(root);

		reloadRoot();
	}

	public void reloadRoot() {
		allDirectories = new ArrayList<File>();
		if (rootOfAllFiles.exists() && rootOfAllFiles.isDirectory()) {
			loadAllFilesFromDirectory(rootOfAllFiles);
		}
	}

	// public File[] getAllFeatureFiles() {
	// return getAllFilesWithExtension(".feature");
	// }

	// public File[] getAllStepDefinitionFiles() {
	// File[] possibleSetpFiles = getAllFilesWithExtension(".rb");
	// List<File> realStepFilesList = new ArrayList<File>();
	//
	// for (int i = 0; i < possibleSetpFiles.length; i++) {
	// File file = possibleSetpFiles[i];
	// if (isARealStepFile(file))
	// realStepFilesList.add(file);
	// }
	//
	// File[] realStepFiles = new File[realStepFilesList.size()];
	// for (int i = 0; i < realStepFilesList.size(); i++) {
	// realStepFiles[i] = realStepFilesList.get(i);
	// }
	//
	// return realStepFiles;
	// }

	// private boolean isFileWithExtension(File file, String extension) {
	// boolean isFeatureFile = !file.isDirectory()
	// && file.getName().contains(extension);
	//
	// return isFeatureFile;
	// }

	// private File[] getAllFilesWithExtension(String extension) {
	// List<File> fileList = new ArrayList<File>();
	// for (File file : allDirectories) {
	//
	// if (isFileWithExtension(file, extension))
	// fileList.add(file);
	// }
	// File[] featureFiles = new File[fileList.size()];
	// for (int i = 0; i < fileList.size(); i++) {
	// featureFiles[i] = fileList.get(i);
	// }
	// return featureFiles;
	// }

	private void loadAllFilesFromDirectory(final File rootFile) {

		File[] files = rootFile.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					loadAllFilesFromDirectory(files[i]);
					if (isNotEmpty(files[i]))
						allDirectories.add(files[i]);
				}
			}
		}

	}

	private boolean isNotEmpty(File file) {
		return !isEmpty(file);
	}

	private boolean isEmpty(File file) {
		if (file.listFiles().length == 0)
			return true;

		if (file.listFiles().length == 1) {
			if (file.listFiles()[0].getName().contains("nomedia"))
				return true;
		}
		return false;
	}

	public static void resetStopAllEvents() {
		STOP_ALL_EVENTS = false;
	}

	public static void stopAllEvents() {
		STOP_ALL_EVENTS = true;
	}

	public static boolean isAllEventsCanceled() {
		return STOP_ALL_EVENTS;
	}

	public static boolean isJIT() {
		return IS_JIT;
	}

	public static void setJIT(boolean isJIT) {
		IS_JIT = isJIT;
	}

}
