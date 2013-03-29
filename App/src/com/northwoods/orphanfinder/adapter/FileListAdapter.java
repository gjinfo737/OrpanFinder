package com.northwoods.orphanfinder.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.R.drawable;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.northwoods.orphanfinder.R.id;
import com.northwoods.orphanfinder.R.layout;
import com.northwoods.orphanfinder.adapter.MediaTyper.MediaType;

public class FileListAdapter extends BaseAdapter {
	private static final int THUMBNAIL_WIDTH = 300;
	private static final int THUMBNAIL_HEIGHT = 120;
	List<File> files = new ArrayList<File>();
	MediaTyper mediaTyper = new MediaTyper();
	Activity activity;

	public FileListAdapter(Activity activity) {
		this.activity = activity;

	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public int getCount() {
		return files.size();
	}

	public Object getItem(int position) {
		return files.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = activity.getLayoutInflater().inflate(
				layout.grid_file_item, null);
		TextView tv = (TextView) convertView
				.findViewById(id.textView_file_path);
		ImageView mediaTypeImageView = (ImageView) convertView
				.findViewById(id.imageView_media_type);
		final File directory = (File) getItem(position);
		final String docTypeName = DocTyper.getDoctypeName(directory);
		tv.setText(docTypeName);
		Bitmap mediaTypeImageBitmap = getImageResource(directory);
		mediaTypeImageView.setImageBitmap(mediaTypeImageBitmap);

		return convertView;
	}

	private Bitmap getImageResource(File directory) {
		MediaType mediaType = mediaTyper.getMediaType(directory);
		int mediaTypeImageResource = drawable.ic_lock_lock;
		if (mediaType.equals(MediaType.IMAGE)) {
			File[] imageFiles = directory.listFiles();
			List<Bitmap> bitmaps = new ArrayList<Bitmap>();
			for (File file : imageFiles) {
				if (mediaTyper.isFileWithExtension(file, MediaTyper.GIF)
						|| mediaTyper.isFileWithExtension(file, MediaTyper.PNG)
						|| mediaTyper.isFileWithExtension(file, MediaTyper.JPG)) {
					bitmaps.add(BitmapFactory.decodeFile(file.getAbsolutePath()));
				}
			}
			Bitmap bigMap = Bitmap.createBitmap(THUMBNAIL_WIDTH,
					THUMBNAIL_HEIGHT, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bigMap);
			int x = 0;
			int width = THUMBNAIL_WIDTH / bitmaps.size();
			for (Bitmap bitmap : bitmaps) {
				c.drawBitmap(bitmap, x, 0, null);
				RectF dst = new RectF(x, 0, x + THUMBNAIL_WIDTH,
						THUMBNAIL_HEIGHT);
				c.drawBitmap(bitmap, null, dst, null);
				x += width;
			}
			mediaTypeImageResource = drawable.ic_menu_gallery;
			return bigMap;
		} else if (mediaType.equals(MediaType.AUDIO)) {
			mediaTypeImageResource = drawable.ic_media_play;
		}

		return BitmapFactory.decodeResource(activity.getResources(),
				mediaTypeImageResource);
	}
}
