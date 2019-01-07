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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.utils.AlarmUtils;

/**
 * SingleFragment is a simple {@link Fragment} subclass.
 */
public class SingleFragment extends Fragment implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String ARG_CALENDAR = "Calendar";
    /**
     * Alarm set date.
     */
    private Calendar mCalendar;

    private DateChangeListener dateChangeListener;
    private TimeChangeListener timeChangeListener;

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public SingleFragment() {
        // Required empty public constructor
        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(getArguments().getLong(ARG_CALENDAR));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single, container, false);
        ButterKnife.bind(this, view);

        tvDate.setText(AlarmUtils.formatDate(mCalendar));
        tvTime.setText(AlarmUtils.formatTime(mCalendar));

        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);

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
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tvTime.setText(AlarmUtils.formatTime(hourOfDay, minute));
        if(timeChangeListener != null) {
            timeChangeListener.onTimeChanged(hourOfDay, minute);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        tvDate.setText(AlarmUtils.formatDate(year, month, day));
        if(dateChangeListener != null) {
            dateChangeListener.onDateChanged(year, month, day);
        }
    }

    public void setTimeChangeListener(TimeChangeListener timeChangeListener) {
        this.timeChangeListener = timeChangeListener;
    }

    public void setDateChangeListener(DateChangeListener dateChangeListener) {
        this.dateChangeListener = dateChangeListener;
    }
}
