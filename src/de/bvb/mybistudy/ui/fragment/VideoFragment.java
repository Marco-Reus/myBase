package de.bvb.mybistudy.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.adapter.MeAdapter;
import de.bvb.mybistudy.entity.MeBean;
import de.bvb.mybistudy.ui.BaseFragment;
import de.bvb.mybistudy.ui.activity.SearchActivity;
import de.bvb.mybistudy.ui.widget.TopBar;
import de.bvb.mybistudy.ui.widget.TopBar.TopBarClickListener;

public class VideoFragment extends BaseFragment {

	private MeAdapter adapter;
	private List<MeBean> datas;
	private ListView lv;
	private TopBar tp;

	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, null);

		tp = (TopBar) getActivity().findViewById(R.id.top_bar_main);
		tp.setTopBarTitle("列表");
		tp.setTopBarRightText("搜索");
		tp.setTopBarClickListener(new TopBarClickListener() {

			@Override
			public void onClickRight() {
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});

		lv = (ListView) view.findViewById(R.id.lv_fragment_me);
		adapter = new MeAdapter(getActivity(), R.layout.adapter_fragment_video_listview);
		lv.setAdapter(adapter);

		getData();

		return view;
	}

	private void getData() {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				datas = new ArrayList<MeBean>();
				for (int i = 0; i < 10; i++) {
					datas.add(new MeBean(i, "  name" + i, R.drawable.ic_launcher));
				}
			}
		});
		adapter.setDatas(datas);
	}
}
