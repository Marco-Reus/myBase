package de.bvb.mybistudy.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.ui.BaseFragment;
import de.bvb.mybistudy.util.ToastUtil;

public class MatchFragment extends BaseFragment implements OnClickListener {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_match, null);

		show = (Button) view.findViewById(R.id.show);
		dismiss = (Button) view.findViewById(R.id.dismiss);
		show.setOnClickListener(this);
		dismiss.setOnClickListener(this);
		return view;
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showData((String) msg.obj);
				break;
			case 1:
				ToastUtil.showShort(context, "网络错误");
				break;
			}
			 dismissProgressDialog();
		}

	};
	private Button show;
	private Button dismiss;

	private String getData() {
		SystemClock.sleep(3000);
		return "this is result";
	};

	private void showData(String data) {
		if (!TextUtils.isEmpty(data)) {
			dismiss.setText(data);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.show:
			showProgressDialog("ing");
			new Thread() {
				public void run() {
					String data = getData();
					Message.obtain(handler, 0, data).sendToTarget();
				};
			}.start();

			break;
		case R.id.dismiss:
			showProgressDialog("正在加载阿斯蒂芬卡萨丁发送到发送克莱顿法蜀都赋");
			new Thread() {
				public void run() {
					String data = getData();
					Message.obtain(handler, 1, data).sendToTarget();
				};
			}.start();
			break;
		}
	}
}
