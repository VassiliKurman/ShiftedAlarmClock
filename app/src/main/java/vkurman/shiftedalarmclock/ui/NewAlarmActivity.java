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

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.models.Type;

/**
 * NewAlarmActivity is activity to create new Alarm.
 *
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
public class NewAlarmActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.button_save) Button buttonSave;
    @BindView(R.id.button_cancel) Button buttonCancel;
    @BindView(R.id.picker_alarm_type) NumberPicker pickerAlarmType;
    @BindView(R.id.container_for_fragment) FrameLayout fragmentContainer;
    @BindView(R.id.tv_alarm_name) TextView tvAlarmName;
    @BindView(R.id.tv_tone_and_volume) TextView tvToneName;
    @BindView(R.id.tv_vibrate) TextView tvVibrationName;
    @BindView(R.id.switch_tone_and_volume) Switch switchTone;
    @BindView(R.id.switch_vibrate) Switch switchVibrate;
    @BindView(R.id.switch_say_time) Switch switchSayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        // Binding views
        ButterKnife.bind(this);
        // Setting OnClickListeners for buttons
        buttonSave.setOnClickListener(this);

        pickerAlarmType.setMinValue(0);
        pickerAlarmType.setMaxValue(2);
        pickerAlarmType.setDisplayedValues(Type.asStringArray());
        pickerAlarmType.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker pickerAlarmType, int oldVal, int newVal) {
                Snackbar.make(pickerAlarmType, Type.values()[newVal].toString(), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        // Setting OnCheckedChangeListeners
        switchTone.setOnCheckedChangeListener(this);
        switchVibrate.setOnCheckedChangeListener(this);
        switchSayTime.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSave) {
            Snackbar.make(buttonSave, "Save clicked", Snackbar.LENGTH_SHORT)
                    .setAction("Save", null).show();
        } else if(view == buttonCancel) {
            Snackbar.make(buttonCancel, "Cancel clicked", Snackbar.LENGTH_SHORT)
                    .setAction("Cancel", null).show();
            finish();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == switchTone) {
            Snackbar.make(buttonSave, "Tone switched " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Tone", null).show();
        } else if(buttonView == switchVibrate) {
            Snackbar.make(buttonSave, "Vibrate switched " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Vibrate", null).show();
        } else if(buttonView == switchSayTime) {
            Snackbar.make(buttonSave, "Say Time switched " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Say", null).show();
        }
    }
}