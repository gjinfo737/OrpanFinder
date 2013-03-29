package com.northwoods.data;

import static com.northwoods.data.DalConstants.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.northwoods.data.DalConstants.Tables;
import com.northwoods.orphanfinder.IFinderDAL;

public class FinderDAL implements IFinderDAL {

	private static SQLiteDAL sqlite = new SQLiteDAL();

	public boolean isOrphaned(String filePath) {
		boolean result = true;

		Cursor cursor = sqlite.GetTableValues(Tables.TRANS_DOCUMENT, "");

		if (cursor != null && cursor.moveToFirst()) {
			do {
				String currentPath = getStringValueFromColumn(cursor,
						DalConstants.FILE_PATH);

				if (filePath.contains(currentPath))
					result = false;

			} while (cursor.moveToNext());

			cursor.close();
		}

		return result;
	}

	public long getProgramIdByEventId(long eventId) {
		long programId = 0;

		Cursor cursor = sqlite.GetTableValues(Tables.REF_EVENT_TYPE, WHERE
				+ PK_REF_EVENT_TYPE + EQUALS + eventId);

		if (cursor != null && cursor.moveToFirst()) {
			programId = getLongValueFromColumn(cursor, FK_REF_PROGRAM_TYPE);
			cursor.close();
		}

		return programId;
	}

	public List<CoPilotObject> getEventTypes(String docTypeName) {
		ArrayList<CoPilotObject> result = new ArrayList<CoPilotObject>();
		Cursor cursor = sqlite.GetTableValues(Tables.REF_EVENT_TYPE, "");

		if (cursor != null && cursor.moveToFirst()) {
			do {
				String eventTypeName = getStringValueFromColumn(cursor,
						DalConstants.EVENT_TYPE_NAME);

				long eventTypeId = getLongValueFromColumn(cursor,
						DalConstants.PK_REF_EVENT_TYPE);
				if (!doesEventTypeHaveDocType(eventTypeId,
						getDocTypeId(docTypeName)))
					continue;

				CoPilotObject item = new CoPilotObject();
				item.setId(eventTypeId);
				item.setName(eventTypeName);
				result.add(item);

			} while (cursor.moveToNext());

			cursor.close();
		}

		return result;
	}

	private boolean doesEventTypeHaveDocType(long eventTypeId, long docTypeId) {
		boolean found = false;
		Cursor cursor = sqlite.GetTableValues(
				Tables.JOIN_REF_EVENT_TYPE_CAPTURE_DOCUMENT_TYPE, WHERE
						+ FK_REF_DOCUMENT_TYPE + EQUALS + docTypeId + AND
						+ FK_REF_EVENT_TYPE + EQUALS + eventTypeId);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		cursor = sqlite.GetTableValues(
				Tables.JOIN_REF_EVENT_TYPE_AUDIO_DOCUMENT_TYPE, WHERE
						+ FK_REF_DOCUMENT_TYPE + EQUALS + docTypeId + AND
						+ FK_REF_EVENT_TYPE + EQUALS + eventTypeId);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return found;
	}

	private long getDocTypeId(String docTypeName) {
		long pkDocType = 0;
		Cursor cursor = sqlite.GetTableValues(Tables.REF_DOCUMENT_TYPE, WHERE
				+ DOCUMENT_TYPE_NAME + EQUALS + "'" + docTypeName + "'");
		if (cursor != null && cursor.moveToFirst())
			pkDocType = getLongValueFromColumn(cursor, PK_REF_DOCUMENT_TYPE);
		cursor.close();

		return pkDocType;
	}

	public List<CoPilotObject> getCasesByEventId(long eventId) {
		ArrayList<CoPilotObject> result = new ArrayList<CoPilotObject>();

		long programTypeId = getProgramIdByEventId(eventId);

		Cursor cursorCase = sqlite.GetTableValues(Tables.TRANS_CASE, WHERE
				+ FK_PROGRAM_TYPE + EQUALS + programTypeId);

		if (cursorCase != null && cursorCase.moveToFirst()) {
			do {
				String localName = getStringValueFromColumn(cursorCase,
						LOCAL_CASE_NUMBER);

				String stateName = getStringValueFromColumn(cursorCase,
						STATE_CASE_NUMBER);

				String caseName = String
						.format("%s (%s)", stateName, localName);

				long caseId = getLongValueFromColumn(cursorCase, PK_TRANS_CASE);

				CoPilotObject item = new CoPilotObject();
				item.setId(caseId);
				item.setName(caseName);
				result.add(item);

			} while (cursorCase.moveToNext());

			cursorCase.close();
		}

		return result;
	}

	public List<CoPilotObject> getMembersByCaseId(long caseId) {
		ArrayList<CoPilotObject> result = new ArrayList<CoPilotObject>();

		Cursor cursor = sqlite.GetTableValues(DalConstants.CASE_MEMBERS_VIEW,
				WHERE + FK_TRANS_CASE + EQUALS + caseId);

		if (cursor != null && cursor.moveToFirst()) {
			do {
				String firstName = getStringValueFromColumn(cursor, FIRST_NAME);
				String lastName = getStringValueFromColumn(cursor, LAST_NAME);

				String memberName = String.format("%s %s", firstName, lastName);

				long memberId = getLongValueFromColumn(cursor, PK_TRANS_MEMBER);

				CoPilotObject item = new CoPilotObject();
				item.setId(memberId);
				item.setName(memberName);
				result.add(item);
			} while (cursor.moveToNext());

			cursor.close();
		}

		return result;
	}

	private String getStringValueFromColumn(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	private long getLongValueFromColumn(Cursor cursor, String columnName) {
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}

	public boolean isOrphaned(File directory) {
		return isOrphaned(directory.getAbsolutePath());
	}
}
