package com.northwoods.orphanfinder;

import java.io.File;

import com.northwoods.data.FinderDAL;
import com.northwoods.orphanfinder.adapter.DocTyper;

public class MediaAttacher implements Runnable {
	private long memberPK;
	private long casePK;
	private File file;
	private FinderDAL finderDAL;

	public MediaAttacher(long memberPK, long casePK, File file) {
		this.finderDAL = new FinderDAL();
		this.memberPK = memberPK;
		this.casePK = casePK;
		this.file = file;
	}

	@Override
	public void run() {
		attach();
	}

	private void attach() {
		finderDAL.addDocumentRecord(file, memberPK, casePK,
				DocTyper.getDoctypeName(file));
	}

}
