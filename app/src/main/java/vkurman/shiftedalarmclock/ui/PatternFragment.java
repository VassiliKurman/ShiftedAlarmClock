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

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.interfaces.DateChangeListener;
import vkurman.shiftedalarmclock.interfaces.PatternChangeListener;
import vkurman.shiftedalarmclock.utils.AlarmUtils;

/**
 * PatternFragment is a fragment for custom patterns.
 *
 * Created by Vassili Kurman on 13/01/2019.
 * Version 1.0
 */
public class PatternFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

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

    @BindView(R.id.pattern_container)
    FrameLayout tvContainer;
    @BindView(R.id.tv_pattern_date)
    TextView tvDate;
    @BindView(R.id.tv_pattern_time)
    TextView tvTime;

    public PatternFragment(){
        // Required empty public constructor
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
            mPattern = AlarmUtils.formatStringToPattern(getArguments().getString(AlarmUtils.ARG_PATTERN));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pattern, container, false);
        ButterKnife.bind(this, view);

        tvDate.setText(AlarmUtils.formatDate(mCalendar));
        tvTime.setText(AlarmUtils.formatTime(mCalendar));
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);

//        GridLayout gridLayout = new GridLayout(getActivity());
//        gridLayout.setColumnCount(mPattern.length > 7 ? 7 : mPattern.length);
//        gridLayout.setRowCount(mPattern.length > 7 ? (int) Math.ceil(mPattern.length / 7) : 1);
        LinearLayout layout = new LinearLayout(getContext());
        for(boolean pattern: mPattern) {
            CheckBox checkbox = new CheckBox(getActivity());
            checkbox.setChecked(pattern);
            checkbox.setOnCheckedChangeListener(this);
            layout.addView(checkbox);
        }
        tvContainer.addView(layout);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == tvDate) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.dateSetListener = this;
            datePickerFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "datePicker");
        } else if(view == tvTime) {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.timeSetListener = this;
            timePickerFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "timePicker");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        // Trigger pattern change listener to update
        onPatternChanged();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar tempCal = (Calendar) mCalendar.clone();
        tempCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        tempCal.set(Calendar.MINUTE, minute);

        if (tempCal.after(Calendar.getInstance())) {
            Snackbar.make(tvDate, "Time set before current time!", Snackbar.LENGTH_SHORT)
                    .setAction("Date", null).show();
            return;
        }

        mCalendar = tempCal;
        tvTime.setText(AlarmUtils.formatTime(hourOfDay, minute));
        if(mDateChangeListener != null) {
            mDateChangeListener.onDateChanged(mCalendar);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar tempCal = (Calendar) mCalendar.clone();
        tempCal.set(Calendar.YEAR, year);
        tempCal.set(Calendar.MONTH, month);
        tempCal.set(Calendar.DAY_OF_MONTH, day);

        if (tempCal.before(Calendar.getInstance())) {
            Snackbar.make(tvDate, "Date set before current date!", Snackbar.LENGTH_SHORT)
                    .setAction("Date", null).show();
            return;
        }

        mCalendar = tempCal;
        tvDate.setText(AlarmUtils.formatDate(year, month, day));
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