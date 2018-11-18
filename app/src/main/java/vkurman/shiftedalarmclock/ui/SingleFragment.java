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
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
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
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        tvDate.setText(AlarmUtils.formatDate(year, month, day));
    }

    /**
     * DatePickerFragment from https://developer.android.com/guide/topics/ui/controls/pickers
     */
    public static class DatePickerFragment extends DialogFragment {

        DatePickerDialog.OnDateSetListener dateSetListener;

        @Override
        @NonNull
        public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), dateSetListener, year, month, day);
        }
    }

    /**
     * TimePickerFragment from https://developer.android.com/guide/topics/ui/controls/pickers
     */
    public static class TimePickerFragment extends DialogFragment {

        TimePickerDialog.OnTimeSetListener timeSetListener;

        @Override
        @NonNull
        public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), timeSetListener, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }
}
