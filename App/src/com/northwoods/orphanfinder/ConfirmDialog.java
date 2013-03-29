package com.northwoods.orphanfinder;

import com.northwoods.orphanfinder.R.string;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

public class ConfirmDialog {

	private static final int STR_RES_ENG0_NO = string.eng_0_no;
	private static final int STR_RES_ENG0_YES = string.eng_0_yes;

	public static void AlertSingleButtonDialog(Context context,
			CharSequence title, CharSequence message, CharSequence buttonText,
			final Runnable okRunnable) {
		final AlertDialog alert = new AlertDialog.Builder(context).create();
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setButton(Dialog.BUTTON_NEUTRAL, buttonText,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (okRunnable != null)
							okRunnable.run();
						alert.cancel();
					}
				});
		alert.show();
	}

	/**
	 * No title
	 * 
	 * @param context
	 * @param message
	 * @param positiveMsg
	 * @param negativeMsg
	 * @param positive
	 * @param negative
	 * @param cancel
	 */
	public static void AlertDialog(Context context, String message,
			String positiveMsg, String negativeMsg, final Runnable positive,
			final Runnable negative, final Runnable cancel) {
		AlertDialog(context, null, message, positiveMsg, negativeMsg, positive,
				negative, cancel);
	}

	/**
	 * Full
	 * 
	 * @param context
	 * @param title
	 *            can be null.
	 * @param message
	 * @param positiveMsg
	 * @param negativeMsg
	 * @param positive
	 * @param negative
	 * @param cancel
	 */
	public static void AlertDialog(Context context, String title,
			String message, String positiveMsg, String negativeMsg,
			final Runnable positive, final Runnable negative,
			final Runnable cancel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) {
			builder.setTitle(title);
		}
		builder.setMessage(message)
				.setCancelable(true)
				.setPositiveButton(positiveMsg,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								if (positive != null) {
									positive.run();
								}
							}
						})
				.setNegativeButton(negativeMsg,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								if (negative != null) {
									negative.run();
								}
							}
						});
		AlertDialog alert = builder.create();
		alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if (cancel != null) {
					cancel.run();
				}

			}
		});
		alert.show();
	}

	public static String getUnsavedChangesTitle(Context context) {
		return getUnsavedChangesTitle(context.getResources());
	}

	public static String getUnsavedChangesTitle(Resources res) {
		return res.getString(string.eng_0_unsaved_changes);
	}

	public static String getYes(Context context) {
		return getYes(context.getResources());
	}

	public static String getYes(Resources res) {
		return res.getString(STR_RES_ENG0_YES);
	}

	public static String getNo(Context context) {
		return getNo(context.getResources());
	}

	public static String getNo(Resources res) {
		return res.getString(STR_RES_ENG0_NO);
	}
}
