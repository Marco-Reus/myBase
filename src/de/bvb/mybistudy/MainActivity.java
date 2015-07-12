package de.bvb.mybistudy;

import android.content.Intent;
import android.os.Bundle;
import de.bvb.mybistudy.ui.activity.BaseActivity;
import de.bvb.mybistudy.ui.activity.SettingsActivity;
import de.bvb.mybistudy.util.ToastUtil;
import de.bvb.mybistudy.widget.TopBar;
import de.bvb.mybistudy.widget.TopBar.TopBarClickListener;

public class MainActivity extends BaseActivity {

	private TopBar topBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initData();
		initEvent();

	}

	private void initView() {
		topBar = (TopBar) findViewById(R.id.top_bar_main);
	}

	private void initData() {
		topBar.setTopBarTitle(this.getResources().getString(R.string.txt_tab1));
		topBar.setTopBarRightIcon(R.drawable.settings);
	}

	private void initEvent() {
		topBar.setTopBarClickListener(new TopBarClickListener() {

			@Override
			public void onClickRight() {
				ToastUtil.showShort(MainActivity.this, "进入设置界面");
				startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			}
		});
	}

}
