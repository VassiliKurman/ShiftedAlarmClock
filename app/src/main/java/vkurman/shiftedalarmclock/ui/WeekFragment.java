/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vkurman.shiftedalarmclock.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.utils.AlarmUtils;

/**
 * WeekFragment is a simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener {

    /**
     * Alarm set date.
     */
    private Calendar mCalendar;
    /**
     * Alarm set date.
     */
    private boolean[] mPattern;
    /**
     * Date change listener
     */
    private DateChangeListener mDateChangeListener;
    /**
     * Pattern change listener
     */
    private PatternChangeListener mPatternChangeListener;

    @BindView(R.id.checkbox_monday) CheckBox checkboxMonday;
    @BindView(R.id.checkbox_tuesday) CheckBox checkboxTuesday;
    @BindView(R.id.checkbox_wednesday) CheckBox checkboxWednesday;
    @BindView(R.id.checkbox_thursday) CheckBox checkboxThursday;
    @BindView(R.id.checkbox_friday) CheckBox checkboxFriday;
    @BindView(R.id.checkbox_saturday) CheckBox checkboxSaturday;
    @BindView(R.id.checkbox_sunday) CheckBox checkboxSunday;
    @BindView(R.id.tv_week_time) TextView tvTime;

    public WeekFragment() {
        // Required empty public constructor
        mPattern = new boolean[7];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(savedInstanceState.getLong(AlarmUtils.ARG_CALENDAR));
            mPattern = AlarmUtils.formatStringToPattern(savedInstanceState.getString(AlarmUtils.ARG_PATTERN));
        } else if (getArguments() != null) {
            mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(getArguments().getLong(AlarmUtils.ARG_CALENDAR));
            mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Log.e("WeekFragment", mCalendar.toString());
            mPattern = AlarmUtils.formatStringToPattern(getArguments().getString(AlarmUtils.ARG_PATTERN));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, view);

        checkboxMonday.setChecked(mPattern[0]);
        checkboxMonday.setOnCheckedChangeListener(this);
        checkboxTuesday.setChecked(mPattern[1]);
        checkboxTuesday.setOnCheckedChangeListener(this);
        checkboxWednesday.setChecked(mPattern[2]);
        checkboxWednesday.setOnCheckedChangeListener(this);
        checkboxThursday.setChecked(mPattern[3]);
        checkboxThursday.setOnCheckedChangeListener(this);
        checkboxFriday.setChecked(mPattern[4]);
        checkboxFriday.setOnCheckedChangeListener(this);
        checkboxSaturday.setChecked(mPattern[5]);
        checkboxSaturday.setOnCheckedChangeListener(this);
        checkboxSunday.setChecked(mPattern[6]);
        checkboxSunday.setOnCheckedChangeListener(this);

        tvTime.setText(AlarmUtils.formatTime(mCalendar));
        tvTime.setOnClickListener(this);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(AlarmUtils.ARG_CALENDAR, mCalendar.getTimeInMillis());
        outState.putString(AlarmUtils.ARG_PATTERN, AlarmUtils.formatPatternToString(mPattern));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        if(view == tvTime) {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.timeSetListener = this;
            timePickerFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "timePicker");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == checkboxMonday) {
            mPattern[0] = isChecked;
            Snackbar.make(checkboxMonday, "Monday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Monday", null).show();
        } else if(buttonView == checkboxTuesday) {
            mPattern[1] = isChecked;
            Snackbar.make(checkboxTuesday, "Tuesday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Tuesday", null).show();
        } else if(buttonView == checkboxWednesday) {
            mPattern[2] = isChecked;
            Snackbar.make(checkboxWednesday, "Wednesday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Wednesday", null).show();
        } else if(buttonView == checkboxThursday) {
            mPattern[3] = isChecked;
            Snackbar.make(checkboxThursday, "Thursday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Thursday", null).show();
        } else if(buttonView == checkboxFriday) {
            mPattern[4] = isChecked;
            Snackbar.make(checkboxFriday, "Friday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Friday", null).show();
        } else if(buttonView == checkboxSaturday) {
            mPattern[5] = isChecked;
            Snackbar.make(checkboxSaturday, "Saturday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Saturday", null).show();
        } else if(buttonView == checkboxSunday) {
            mPattern[6] = isChecked;
            Snackbar.make(checkboxSunday, "Sunday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Sunday", null).show();
        }
        // Trigger pattern change listener to update
        onPatternChanged();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tvTime.setText(AlarmUtils.formatTime(hourOfDay, minute));

        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        if(mDateChangeListener != null) {
            mDateChangeListener.onDateChanged(mCalendar);
        }
    }

    private void onPatternChanged() {
        if (mPatternChangeListener != null) {
            mPatternChangeListener.onPatternChanged(mPattern);
        }
    }

    /**
     * Setting {@link DateChangeListener}
     * @param dateChangeListener - {@link DateChangeListener}
     */
    public void setDateChangeListener(DateChangeListener dateChangeListener) {
        this.mDateChangeListener = dateChangeListener;
    }

    /**
     * Setting {@link PatternChangeListener}
     * @param patternChangeListener - {@link PatternChangeListener}
     */
    public void setPatternChangeListener(PatternChangeListener patternChangeListener) {
        this.mPatternChangeListener = patternChangeListener;
    }
}
