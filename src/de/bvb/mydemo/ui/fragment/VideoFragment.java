package de.bvb.mydemo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.bvb.mydemo.R;
import de.bvb.mydemo.adapter.MeAdapter;
import de.bvb.mydemo.entity.MeBean;
import de.bvb.mydemo.ui.BaseFragment;
import de.bvb.mydemo.ui.activity.SearchActivity;
import de.bvb.mydemo.ui.widget.TopBar;
import de.bvb.mydemo.ui.widget.TopBar.TopBarClickListener;

public class VideoFragment extends BaseFragment {

    private MeAdapter adapter;
    private List<MeBean> datas;
    private ListView lv;
    private TopBar tp;

    @Override
    public void onDestroy() {
        super.onDestroy();
        tp.setTopBarRightGone();
    }

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
