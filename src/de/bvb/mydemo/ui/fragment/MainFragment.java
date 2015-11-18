package de.bvb.mydemo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.bvb.mydemo.R;
import de.bvb.mydemo.ui.BaseFragment;
import de.bvb.mydemo.ui.widget.SlidingMenuLayout;

public class MainFragment extends BaseFragment { 

    private ListView contentList;
    private ArrayAdapter<String> contentListAdapter;
    private String[] contentItems = { "Content Item 1", "Content Item 2", "Content Item 3", "Content Item 4", "Content Item 5", "Content Item 6",
            "Content Item 7", "Content Item 8", "Content Item 9", "Content Item 10", "Content Item 11", "Content Item 12", "Content Item 13",
            "Content Item 14", "Content Item 15", "Content Item 16" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        SlidingMenuLayout menuLayout = (SlidingMenuLayout) view.findViewById(R.id.bidir_sliding_layout);
        contentList = (ListView) view.findViewById(R.id.contentList);
        contentListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, contentItems);
        contentList.setAdapter(contentListAdapter);
        menuLayout.setScrollEvent(contentList);
        contentList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), contentItems[position], 0).show();
            }
        });
        return view;
    }
}
