package com.northwoods.orphanfinder.fileskim;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.northwoods.orphanfinder.IFinderDAL;

public class OrphanListBuilder {
	IFinderDAL determineIfFileIsOrphaned;
	FileLister fileLister;
	List<File> orphanedDirectories = new ArrayList<File>();

	public OrphanListBuilder(IFinderDAL determineIfFileIsOrphaned,
			FileLister fileLister) {
		this.determineIfFileIsOrphaned = determineIfFileIsOrphaned;
		this.fileLister = fileLister;
	}

	public void buildOrphanedList() {
		List<File> allDirectories = fileLister.getAllDirectories();
		for (File directory : allDirectories) {
			if (determineIfFileIsOrphaned.isOrphaned(directory)) {
				orphanedDirectories.add(directory);
				Log.i("nomnom", "!!is an orphan " + directory.getAbsolutePath());
			} else {
				Log.i("nomnom", "not an orphan");
			}
		}
	}

	public List<File> getOrphanedDirectories() {
		return orphanedDirectories;
	}
}
