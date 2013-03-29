package com.northwoods.orphanfinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.northwoods.data.FinderDAL;
import com.northwoods.data.CoPilotObject;
import com.northwoods.orphanfinder.R.id;
import com.northwoods.orphanfinder.adapter.DocTyper;
import com.northwoods.orphanfinder.adapter.FileListAdapter;
import com.northwoods.orphanfinder.adapter.IRefresher;
import com.northwoods.orphanfinder.adapter.ObjectListAdapter;
import com.northwoods.orphanfinder.fileskim.FileLister;
import com.northwoods.orphanfinder.fileskim.OrphanListBuilder;

public class FinderActivity extends Activity {
	private static final String EVENT_TYPES = "Event Types";
	private static final String CASES = "Cases";
	private static final String MEMBERS = "Members";
	private ListView listView;
	private GridView gridView;
	private FileListAdapter gridAdapter;
	private Button btnRefresh;
	private IRefresher refresher;
	private ProgressBar progressBar;
	private long eventTypePK = -1L;
	private long casePK = -1L;
	private long memberPK = -1L;
	private File currentlySelectedFile;
	private TextView textViewListType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finder);
		getActionBar().hide();
		this.refresher = new FinderRefresher();
		this.textViewListType = (TextView) findViewById(id.tv_list_type);
		setListType();
		setupProgressBar();
		setupRefreshButton();
		setupListView();
		setupGridView();
		clearPKs();

		handleRefresh();

	}

	private void clearPKs() {
		eventTypePK = -1L;
		casePK = -1L;
		memberPK = -1L;
	}

	private void setupListView() {
		listView = (ListView) findViewById(id.listView_listitems);
		listView.setOnItemClickListener(new CoPilotListItemClickListener());
	}

	private void setupProgressBar() {
		this.progressBar = (ProgressBar) findViewById(id.progressBar_finding);
	}

	private void setupGridView() {
		gridView = (GridView) findViewById(id.gridView_orphans);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				File directory = (File) gridView.getAdapter().getItem(position);
				currentlySelectedFile = directory;
				String docTypeName = DocTyper.getDoctypeName(directory);
				Log.i("", "selected: " + docTypeName);
				refresher.onDirectoryClick(directory, docTypeName);
			}
		});
	}

	private void setupRefreshButton() {
		btnRefresh = ((Button) findViewById(id.button_refresh));
		btnRefresh.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleRefresh();
			}

		});
	}

	private void handleRefresh() {
		onStartRefresh();
		Thread refreshThread = new Thread(new Runnable() {
			public void run() {
				refresh();
				runOnUiThread(new Runnable() {
					public void run() {
						onEndRefresh();
					}
				});
			}
		});
		refreshThread.start();
	}

	private void onEndRefresh() {
		gridView.setAdapter(gridAdapter);
		refreshObjectList(null, null);
		btnRefresh.setEnabled(true);
		hideProgressBar();
	}

	private void onStartRefresh() {
		showProgressBar();
		btnRefresh.setEnabled(false);
	}

	private void showProgressBar() {
		progressBar.setVisibility(View.VISIBLE);
	}

	private void hideProgressBar() {
		progressBar.setVisibility(View.INVISIBLE);
	}

	private void refreshObjectList(File file, String docTypeName) {
		List<CoPilotObject> items = new ArrayList<CoPilotObject>();
		if (currentlySelectedFile != null) {
			items = getCoPilotObjectItems();
		}
		ObjectListAdapter objectListAdapter = new ObjectListAdapter(this);
		objectListAdapter.setItems(items);
		listView.setAdapter(objectListAdapter);

		setListType();
	}

	private void setListType() {
		if (currentlySelectedFile == null) {
			textViewListType.setText("");
			return;
		}
		if (casePK != -1) {
			textViewListType.setText(MEMBERS);
		} else if (eventTypePK != -1) {
			textViewListType.setText(CASES);
		} else {
			textViewListType.setText(EVENT_TYPES);
		}
	}

	private List<CoPilotObject> getCoPilotObjectItems() {
		List<CoPilotObject> arrayList = new ArrayList<CoPilotObject>();
		FinderDAL finderDAL = new FinderDAL();

		if (casePK != -1) {
			arrayList.addAll(finderDAL.getMembersByCaseId(casePK));
		} else if (eventTypePK != -1) {
			arrayList.addAll(finderDAL.getCasesByEventId(eventTypePK));
		} else {
			arrayList.addAll(finderDAL.getEventTypes(DocTyper
					.getDoctypeName(currentlySelectedFile)));
		}

		return arrayList;
	}

	private void refresh() {
		currentlySelectedFile = null;
		clearPKs();
		OrphanListBuilder builder = new OrphanListBuilder(new FinderDAL(),
				new FileLister(Environment.getExternalStorageDirectory()
						+ File.separator + "CoPilot" + File.separator
						+ "Package" + File.separator + "DocImages"));
		builder.buildOrphanedList();
		gridAdapter = new FileListAdapter(this);
		gridAdapter.setFiles(builder.getOrphanedDirectories());

	}

	private class FinderRefresher implements IRefresher {

		public void onDirectoryClick(File file, String docTypeName) {
			refreshObjectList(file, docTypeName);
		}
	}

	private class CoPilotListItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			if (eventTypePK == -1) {
				clearPKs();
				CoPilotObject item = (CoPilotObject) listView.getAdapter()
						.getItem(position);
				eventTypePK = item.getId();
			} else if (casePK == -1) {
				memberPK = -1;
				CoPilotObject item = (CoPilotObject) listView.getAdapter()
						.getItem(position);
				casePK = item.getId();
			} else if (memberPK == -1) {
				CoPilotObject item = (CoPilotObject) listView.getAdapter()
						.getItem(position);
				memberPK = item.getId();
				ConfirmDialog.AlertDialog(FinderActivity.this,
						"Do you want to attach this document to this member?",
						"Yes", "No", null, null, null);
			}
			if (currentlySelectedFile != null)
				refreshObjectList(currentlySelectedFile,
						DocTyper.getDoctypeName(currentlySelectedFile));
		}
	}
}
