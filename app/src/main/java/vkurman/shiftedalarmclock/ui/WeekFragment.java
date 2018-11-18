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

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, view);

        checkboxMonday.setOnCheckedChangeListener(this);
        checkboxTuesday.setOnCheckedChangeListener(this);
        checkboxWednesday.setOnCheckedChangeListener(this);
        checkboxThursday.setOnCheckedChangeListener(this);
        checkboxFriday.setOnCheckedChangeListener(this);
        checkboxSaturday.setOnCheckedChangeListener(this);
        checkboxSunday.setOnCheckedChangeListener(this);

        tvTime.setText(AlarmUtils.formatTime(Calendar.getInstance()));
        tvTime.setOnClickListener(this);

        return view;
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
            Snackbar.make(checkboxMonday, "Monday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Monday", null).show();
        } else if(buttonView == checkboxTuesday) {
            Snackbar.make(checkboxTuesday, "Tuesday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Tuesday", null).show();
        } else if(buttonView == checkboxWednesday) {
            Snackbar.make(checkboxWednesday, "Wednesday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Wednesday", null).show();
        } else if(buttonView == checkboxThursday) {
            Snackbar.make(checkboxThursday, "Thursday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Thursday", null).show();
        } else if(buttonView == checkboxFriday) {
            Snackbar.make(checkboxFriday, "Friday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Friday", null).show();
        } else if(buttonView == checkboxSaturday) {
            Snackbar.make(checkboxSaturday, "Saturday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Saturday", null).show();
        } else if(buttonView == checkboxSunday) {
            Snackbar.make(checkboxSunday, "Sunday checked " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Sunday", null).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        tvTime.setText(AlarmUtils.formatTime(hourOfDay, minute));
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
