package de.bvb.mydemo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.bvb.mydemo.R;
import de.bvb.mydemo.ui.BaseActivity;
import de.bvb.mydemo.ui.fragment.SettingsFragment;
import de.bvb.mydemo.ui.widget.TopBar;
import de.bvb.mydemo.util.LogUtil;
import de.bvb.mydemo.util.ToastUtil;

/** 设置 */
public class SettingsActivity extends BaseActivity implements OnClickListener {
	private Button btn_logout;
	private TopBar topBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initView();
	}

	private void initView() {
		topBar = (TopBar) findViewById(R.id.top_bar_settings);
		topBar.setTopBarTitle("设置");
		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(this);
		getFragmentManager().beginTransaction().add(R.id.fl_settings, new SettingsFragment()).commit();
	}

	@Override
	public void onClick(View v) {
		LogUtil.w(v.getPaddingLeft() + "==" + v.getPaddingBottom() + "==" + v.getHeight());
		ToastUtil.show(context, "注销当前账号");
	}
}
