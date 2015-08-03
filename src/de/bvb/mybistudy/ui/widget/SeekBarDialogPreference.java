package de.bvb.mybistudy.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import de.bvb.mybistudy.R;

public class SeekBarDialogPreference extends DialogPreference implements OnSeekBarChangeListener {
	// private static final String TAG = "SeekBarDialogPreference";

	private Drawable mMyIcon;
	private TextView value;
	private SeekBar seekBar;
	// 如果要修改最大值和最小值的话，那么可以在这里修改。或者是调用函数setMax、setMin
	private int mMax = 100, mMin = 0;
	private int mProgress;

	public SeekBarDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		setDialogLayoutResource(R.layout.prefs_layout_seekbar_dialog);

		// Steal the XML dialogIcon attribute's value
		mMyIcon = getDialogIcon();
		setDialogIcon(null);
	}

	public SeekBarDialogPreference(Context context) {
		this(context, null);
	}

	public void setMax(int max) {
		if (max != mMax) {
			mMax = max;
			notifyChanged();
		}
	}

	public void setMin(int min) {
		if (min != mMin) {
			mMin = min;
			notifyChanged();
		}
	}

	/**
	 * Saves the progress to the {@link SharedPreferences}.
	 * 
	 * @param text
	 *            The progress to save
	 */
	public void setProgress(int progress) {
		final boolean wasBlocking = shouldDisableDependents();
		mProgress = progress;
		persistInt(mProgress);
		final boolean isBlocking = shouldDisableDependents();
		if (isBlocking != wasBlocking) {
			notifyDependencyChange(isBlocking);
		}
	}

	/**
	 * Gets the text from the {@link SharedPreferences}.
	 * 
	 * @return The current preference value.
	 */
	public int getProgress() {
		return mProgress;
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		mProgress = getPersistedInt(getProgress());
		setSummary(String.valueOf(mProgress));
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		final ImageView iconView = (ImageView) view.findViewById(android.R.id.icon);
		value = (TextView) view.findViewById(R.id.value);
		if (mMyIcon != null) {
			iconView.setImageDrawable(mMyIcon);
		} else {
			iconView.setVisibility(View.GONE);
		}
		seekBar = getSeekBar(view);
		seekBar.setMax(mMax - mMin);
		seekBar.setProgress(mProgress);
		seekBar.setOnSeekBarChangeListener(this);
		value.setText(String.valueOf(mProgress));
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		// 如果没按下确定按钮就返回null
		if (!positiveResult) {
			return;
		}
		if (shouldPersist()) {
			persistInt(mProgress);
			setSummary(String.valueOf(mProgress));
		}
		// 提交数据
		notifyChanged();
	}

	protected static SeekBar getSeekBar(View dialogView) {
		return (SeekBar) dialogView.findViewById(R.id.seekbar);
	}

	@Override
	public CharSequence getSummary() {
		String summary = super.getSummary().toString();
		int value = getPersistedInt(mProgress);
		return String.format(summary, value);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		value.setText(String.valueOf(progress));

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO 自动生成的方法存根
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO 自动生成的方法存根
		mProgress = seekBar.getProgress() + mMin;
	}
}