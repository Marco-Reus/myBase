package de.bvb.mydemo.ui.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import de.bvb.mydemo.R;

public class SeekBarPreference extends Preference implements OnSeekBarChangeListener {
	private TextView value;
	private int mProgress;
	private int mMax = 100;
	private boolean mTrackingTouch;

	public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setMax(mMax);
		setLayoutResource(R.layout.prefs_widget_seekbar);
	}

	public SeekBarPreference(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SeekBarPreference(Context context) {
		this(context, null);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
		seekBar.setMax(mMax);
		seekBar.setProgress(mProgress);
		seekBar.setEnabled(isEnabled());
		seekBar.setOnSeekBarChangeListener(this);
		value = (TextView) view.findViewById(R.id.value);
		value.setText(String.valueOf(mProgress));
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		setProgress(restoreValue ? getPersistedInt(mProgress) : (Integer) defaultValue);
	}

	public void setMax(int max) {
		if (max != mMax) {
			mMax = max;
			notifyChanged();
		}
	}

	public void setProgress(int progress) {
		setProgress(progress, true);
	}

	private void setProgress(int progress, boolean notifyChanged) {
		if (progress > mMax) {
			progress = mMax;
		}
		if (progress < 0) {
			progress = 0;
		}
		if (progress != mProgress) {
			mProgress = progress;
			persistInt(progress);
			if (notifyChanged) {
				notifyChanged();
			}
		}
	}

	public int getProgress() {
		return mProgress;
	}

	/**
	 * Persist the seekBar's progress value if callChangeListener returns true,
	 * otherwise set the seekBar's progress to the stored value
	 */
	void syncProgress(SeekBar seekBar) {
		int progress = seekBar.getProgress();
		if (progress != mProgress) {
			if (callChangeListener(progress)) {
				setProgress(progress, false);
			} else {
				seekBar.setProgress(mProgress);
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

		if (fromUser) {
			System.err.println("now value = " + progress);

		}
		if (fromUser && !mTrackingTouch) {
			syncProgress(seekBar);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		mTrackingTouch = true;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mTrackingTouch = false;
		if (seekBar.getProgress() != mProgress) {
			syncProgress(seekBar);
			value.setText(seekBar.getProgress() + "");
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		/*
		 * Suppose a client uses this preference type without persisting. We
		 * must save the instance state so it is able to, for example, survive
		 * orientation changes.
		 */

		final Parcelable superState = super.onSaveInstanceState();
		if (isPersistent()) {
			// No need to save instance state since it's persistent
			return superState;
		}

		// Save the instance state
		final SavedState myState = new SavedState(superState);
		myState.progress = mProgress;
		myState.max = mMax;
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (!state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}

		// Restore the instance state
		SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		mProgress = myState.progress;
		mMax = myState.max;
		notifyChanged();
	}

	/**
	 * SavedState, a subclass of {@link BaseSavedState}, will store the state of
	 * MyPreference, a subclass of Preference.
	 * <p>
	 * It is important to always call through to super methods.
	 */
	private static class SavedState extends BaseSavedState {
		int progress;
		int max;

		public SavedState(Parcel source) {
			super(source);

			// Restore the click counter
			progress = source.readInt();
			max = source.readInt();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			// Save the click counter
			dest.writeInt(progress);
			dest.writeInt(max);
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

	}
}
