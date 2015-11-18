package de.bvb.mydemo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import de.bvb.mydemo.R;
import de.bvb.mydemo.ui.widget.TopBar;

public class DetailActivity extends Activity {

	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_detail);
		initView();
	}

	private void initView() {
		TopBar topBar = (TopBar) findViewById(R.id.top_bar_detail);
		topBar.setTopBarTitle("详情");
		tv = (TextView) findViewById(R.id.tv_detail);
		String text = getIntent().getStringExtra("tv");
		tv.setText(text);
	}

}
