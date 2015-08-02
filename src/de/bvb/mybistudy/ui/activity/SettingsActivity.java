package de.bvb.mybistudy.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.ui.BaseActivity;
import de.bvb.mybistudy.ui.fragment.SettingsFragment;
import de.bvb.mybistudy.ui.widget.TopBar;
import de.bvb.mybistudy.util.LogUtil;
import de.bvb.mybistudy.util.ToastUtil;

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
