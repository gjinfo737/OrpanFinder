package com.northwoods.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

public class SQLiteDAL {

	private SQLiteDatabase db;

	public String LookUpReferenceString(String table, String keyName,
			Long KeyValue, String refName) {

		String retString = "";

		if (!openTheDB())
			return retString;
		Cursor etCursor = db.rawQuery("Select " + refName + " From " + table
				+ " where " + keyName + " = " + KeyValue, null);

		if (etCursor.moveToFirst()) {
			do {

				retString = etCursor
						.getString(etCursor.getColumnIndex(refName));

			} while (etCursor.moveToNext());
			etCursor.close();
		}

		return retString;

	}

	public Cursor GetTableValues(String table, String where) {
		if (!openTheDB())
			return null;
		String strSelect = "Select * from " + table;

		if (where != "") {
			strSelect += " " + where;
		}

		Cursor cursor = db.rawQuery(strSelect, null);

		return cursor;

	}

	private boolean openTheDB() {
		if (db == null) {
			Log.w("", "DB was null");
			try {
				db = OpenDB();
				return true;
			} catch (Exception e) {
				Log.e("", "ERROR OPENING DB");
				return false;
			}
		} else {
			try {
				if (!db.isOpen()) {
					db = OpenDB();
				}
				return true;
			} catch (Exception e) {
				Log.e("", "ERROR OPENING DB");
				return false;
			}
		}
	}

	public long GetMaxValue(String table, String key) {

		if (!openTheDB())
			return 0;
		String strSelect = "Select max(" + key + ") from " + table;
		long max = 0;

		Cursor cursor = db.rawQuery(strSelect, null);

		if (cursor.moveToFirst()) {
			do {
				max = cursor.getLong(0);

			} while (cursor.moveToNext());
		}
		cursor.close();
		cursor.deactivate();

		return max;

	}

	public long GetMinValue(String table, String key) {
		if (!openTheDB())
			return 0;
		String strSelect = "Select min(" + key + ") from " + table;
		long min = 0;

		Cursor cursor = db.rawQuery(strSelect, null);

		if (cursor.moveToFirst()) {
			do {
				min = cursor.getLong(0);

			} while (cursor.moveToNext());
		}
		cursor.close();
		cursor.deactivate();

		return min;
	}

	public SQLiteDatabase OpenDB() throws Exception {

		return SQLiteDatabase.openDatabase(
				"/" + Environment.getExternalStorageDirectory()
						+ "/CoPilot/Package/Copilot.db", null, 0);

	}

	public void CloseDB() {

		db.close();
	}

	public void UpdateTableValues(String table, ContentValues values,
			String where) {
		if (!openTheDB())
			return;
		db.update(table, values, where, null);

	}

	public void DeleteTableValues(String table, String where) {
		if (!openTheDB())
			return;
		db.delete(table, where, null);

	}

	public void InsertTableValues(String table, ContentValues values) {
		if (!openTheDB())
			return;
		try {
			db.insert(table, null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}
	}

}
