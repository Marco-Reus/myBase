package de.bvb.mydemo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import de.bvb.mydemo.ui.BaseActivity;
import de.bvb.mydemo.ui.activity.SettingsActivity;
import de.bvb.mydemo.ui.fragment.MainFragment;
import de.bvb.mydemo.ui.fragment.MatchFragment;
import de.bvb.mydemo.ui.fragment.MeFragment;
import de.bvb.mydemo.ui.fragment.VideoFragment;
import de.bvb.mydemo.ui.widget.TopBar;
import de.bvb.mydemo.ui.widget.TopBar.TopBarClickListener;
import de.bvb.mydemo.util.ToastUtil;

public class MainActivity extends BaseActivity implements OnClickListener {

	public static int selectedColor = Color.parseColor("#f00000");
	public static int unselectedColor = Color.parseColor("#ffffff");

	private TopBar topBar;
	private RadioButton[] rbs;
	private Fragment fragments[];
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context=this;
		
		initView();
		initData();
		initEvent();
		setChoiceItem(3);
	}

	private void initView() {
		topBar = (TopBar) findViewById(R.id.top_bar_main);
		rbs = new RadioButton[] { (RadioButton) findViewById(R.id.rb_0), (RadioButton) findViewById(R.id.rb_1), (RadioButton) findViewById(R.id.rb_2),
		        (RadioButton) findViewById(R.id.rb_3) };
		fragments = new Fragment[rbs.length];
	}

	private void initData() {
		topBar.setTopBarTitle(this.getResources().getString(R.string.txt_tab1));
	//	topBar.setTopBarRightIcon(R.drawable.settings);
	}

	private void initEvent() {
		//topBar.setTopBarClickListener(topBarClickListener);
		for (int i = 0; i < rbs.length; i++) {
			rbs[i].setOnClickListener(this);
		}
	}

	private TopBarClickListener topBarClickListener = new TopBarClickListener() {

		@Override
		public void onClickRight() {
			ToastUtil.show(MainActivity.this, "进入设置界面");
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
		}
	};

	@Override
	public void onClick(View v) {
		for (int i = 0; i < rbs.length; i++) {
			if (v.getId() == rbs[i].getId()) {
				setChoiceItem(i);
			}
		}
	}

	private void setChoiceItem(int index) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		for (int i = 0; i < rbs.length; i++) {
			rbs[i].setBackgroundColor(unselectedColor);
			if (fragments[i] != null) {
				transaction.remove(fragments[i]);
			}
		}
		if (fragments[index] == null) {
			switch (index) {
			case 0:
				fragments[index] = new MainFragment();
				break;
			case 1:
				fragments[index] = new MatchFragment();
				break;
			case 2:
				fragments[index] = new VideoFragment();
				break;
			case 3:
				fragments[index] = new MeFragment();
				break;
			}
		}
		transaction.add(R.id.fl_main, fragments[index]);
		rbs[index].setBackgroundColor(selectedColor);
		transaction.show(fragments[index]);
		transaction.commit();
	}
}
