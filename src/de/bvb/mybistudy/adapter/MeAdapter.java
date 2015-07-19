package de.bvb.mybistudy.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.entity.MeBean;
import de.bvb.mybistudy.ui.activity.DetailActivity;

public class MeAdapter extends BaseAdapter {

	private Context context;
	private int resource;
	private List<MeBean> datas;

	public MeAdapter(Context context, int resource) {
		super();
		this.context = context;
		this.resource = resource;
	}

	public void setDatas(List<MeBean> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return datas != null ? datas.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, resource, null);
			holder.tv0 = (TextView) convertView.findViewById(R.id.tv_0_adapter_fragment_me);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv_1_adapter_fragment_me);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_adapter_fragment_me);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv0.setText(datas.get(position).code+"");
		holder.tv1.setText(datas.get(position).name);
		holder.iv.setImageResource(datas.get(position).icon);

		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DetailActivity.class);
				intent.putExtra("tv", datas.get(position).toString());
				context.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView tv0;
		TextView tv1;
		ImageView iv;
	}

}
