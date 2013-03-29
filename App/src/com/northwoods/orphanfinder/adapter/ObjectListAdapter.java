package com.northwoods.orphanfinder.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.northwoods.data.CoPilotObject;
import com.northwoods.orphanfinder.R.id;
import com.northwoods.orphanfinder.R.layout;

public class ObjectListAdapter extends BaseAdapter {
	List<CoPilotObject> items = new ArrayList<CoPilotObject>();
	Activity activity;

	public ObjectListAdapter(Activity activity) {
		this.activity = activity;
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = activity.getLayoutInflater();
		convertView = layoutInflater.inflate(layout.grid_file_item, null);
		TextView tv = (TextView) convertView
				.findViewById(id.textView_file_path);
		ImageView mediaTypeImageView = (ImageView) convertView
				.findViewById(id.imageView_media_type);
		mediaTypeImageView.setVisibility(View.GONE);
		tv.setText(getItem(position).toString());

		return convertView;
	}

	public List<CoPilotObject> getItems() {
		return items;
	}

	public void setItems(List<CoPilotObject> items) {
		this.items = items;
	}
}
