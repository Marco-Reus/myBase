package de.bvb.mybistudy.ui.fragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.ui.BaseFragment;
import de.bvb.mybistudy.ui.activity.FeedbackActivity;
import de.bvb.mybistudy.ui.activity.SettingsActivity;
import de.bvb.mybistudy.util.ToastUtil;

public class MeFragment extends BaseFragment {

	private View view;
	private GridView gridView;
	private List<Map<String, Object>> data;
	private SimpleAdapter adapter;
	private Class<?>[] intentTarget = { null, null, null, null, FeedbackActivity.class, SettingsActivity.class };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_me, null);
		initView();
		return view;
	}

	private void initView() {
		gridView = (GridView) view.findViewById(R.id.gv_me);
		// 图片封装为一个数组
		int[] icon = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
		        R.drawable.ic_launcher };
		String[] iconName = { "应用商店", "我的考勤", "HR信息", "我的收藏", "意见反馈", "设置" };
		String[] from = { "icon", "iconName" };
		int[] to = { R.id.iv_adapter_fragment_me, R.id.tv_adapter_fragment_me };
		data = new LinkedList<Map<String, Object>>();
		for (int i = 0; i < icon.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(from[0], icon[i]);
			map.put(from[1], iconName[i]);
			data.add(map);
		}
		adapter = new SimpleAdapter(context, data, R.layout.adapter_fragment_me_gridview, from, to);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(listener);
	}

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ToastUtil.show(context, data.get(position).get("iconName").toString());
			if (null != intentTarget[position]) {
				startActivity(new Intent(context, intentTarget[position]));
			}
		}
	};
}
