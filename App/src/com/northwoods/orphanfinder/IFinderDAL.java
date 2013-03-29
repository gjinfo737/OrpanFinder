package com.northwoods.orphanfinder;

import java.io.File;

public interface IFinderDAL {

	public boolean isOrphaned(File directory);
}
