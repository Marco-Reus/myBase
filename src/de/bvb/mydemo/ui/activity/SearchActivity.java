package de.bvb.mydemo.ui.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;
import de.bvb.mydemo.R;
import de.bvb.mydemo.adapter.MeAdapter;
import de.bvb.mydemo.entity.MeBean;
import de.bvb.mydemo.ui.widget.TopBar;
import de.bvb.mydemo.ui.widget.TopBar.TopBarClickListener;

public class SearchActivity extends Activity {

	private ListView lv;
	private MeAdapter adapter;
	private List<MeBean> datas;
	private List<MeBean> result;
	private EditText et;
    private TopBar tp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);
		tp = (TopBar) findViewById(R.id.top_bar_search);
		tp.setTopBarTitle("搜索界面");
		tp.setTopBarRightText("搜索");
		et = (EditText) findViewById(R.id.et_search);
		datas = new LinkedList<MeBean>();
		result = new LinkedList<MeBean>();
		for (int i = 0; i < 10; i++) {
			datas.add(new MeBean(i, "  name" + i, R.drawable.ic_launcher));
		}
		tp.setTopBarClickListener(new TopBarClickListener() {

			@Override
			public void onClickRight() {
				String s = et.getText().toString().trim();
				if (!TextUtils.isEmpty(s)) {
					for (int i = 0; i < datas.size(); i++) {
						int code = datas.get(i).code;
						if (s.equals(code+"")) {
							result.clear();
							result.add(datas.get(i));
							adapter.setDatas(result);
						}
					}
				}
			}
		});

		lv = (ListView) findViewById(R.id.lv_search);
		adapter = new MeAdapter(this, R.layout.adapter_fragment_video_listview);
		lv.setAdapter(adapter);
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    tp.setTopBarRightGone();
	}
}
