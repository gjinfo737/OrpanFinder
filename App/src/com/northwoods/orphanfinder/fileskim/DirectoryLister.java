package com.northwoods.orphanfinder.fileskim;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryLister {

	public static final String CONSOLE_STR_READING_FILE = "Reading";
	private static boolean IS_JIT = true;

	private File rootOfAllFiles;
	private List<File> allDirectories;
	private static boolean STOP_ALL_EVENTS = false;

	public DirectoryLister(String root) {
		this.rootOfAllFiles = new File(root);

		reloadRoot();
	}

	public void reloadRoot() {
		allDirectories = new ArrayList<File>();
		if (rootOfAllFiles.exists() && rootOfAllFiles.isDirectory()) {
			loadAllDirectoriesFromDirectory(rootOfAllFiles);
		}
	}

	private void loadAllDirectoriesFromDirectory(final File rootFile) {

		File[] files = rootFile.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					allDirectories.add(files[i]);
				} else {
					loadAllDirectoriesFromDirectory(files[i]);
				}
			}
		}

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
