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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.aac.AlarmDatabase;
import vkurman.shiftedalarmclock.aac.AlarmViewModel;
import vkurman.shiftedalarmclock.aac.AlarmViewModelFactory;
import vkurman.shiftedalarmclock.aac.AppExecutors;
import vkurman.shiftedalarmclock.models.Alarm;
import vkurman.shiftedalarmclock.models.Pattern;
import vkurman.shiftedalarmclock.models.Snooze;
import vkurman.shiftedalarmclock.models.Type;
import vkurman.shiftedalarmclock.models.Vibration;

/**
 * NewAlarmActivity is activity to create new Alarm.
 *
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
public class NewAlarmActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, NameDialogFragment.NameDialogListener,
        TimeChangeListener, DateChangeListener {

    public static final String EXTRA_ALARM_ID = "alarmId";
    public static final String EXTRA_ALARM_NEW = "new";
    public static final long DEFAULT_ALARM_ID = -1L;
    public static final boolean DEFAULT_ALARM_NEW = false;

    private static final String TAG = NewAlarmActivity.class.getSimpleName();

    // Views
    @BindView(R.id.button_save) Button buttonSave;
    @BindView(R.id.button_cancel) Button buttonCancel;
    @BindView(R.id.picker_alarm_type) NumberPicker pickerAlarmType;
    @BindView(R.id.tv_alarm_name) TextView tvAlarmName;
    @BindView(R.id.tv_tone_and_volume) TextView tvToneName;
    @BindView(R.id.tv_vibrate) TextView tvVibrationName;
    @BindView(R.id.switch_tone_and_volume) Switch switchTone;
    @BindView(R.id.switch_vibrate) Switch switchVibrate;
    @BindView(R.id.switch_say_time) Switch switchSayTime;
    // FragmentManager
    private FragmentManager fragmentManager;
    // Database reference
    private AlarmDatabase mDb;
    // Alarm id
    private long mAlarmId;
    // New alarm
    private boolean mNew;
    // Alarm
    private Alarm mAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        // Getting ref to database
        mDb = AlarmDatabase.getInstance(getApplicationContext());
        // Binding views
        ButterKnife.bind(this);
        // Getting ref to FragmentManager
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState != null && savedInstanceState.containsKey(EXTRA_ALARM_ID)) {
            Log.e(TAG, "Retrieving data from savedInstanceState!");
            mAlarmId = savedInstanceState.getLong(EXTRA_ALARM_ID, DEFAULT_ALARM_ID);
            mNew = savedInstanceState.getBoolean(EXTRA_ALARM_NEW, DEFAULT_ALARM_NEW);
            // Load alarm from database
            loadAlarm();
        } else {
            Intent intent = getIntent();
            if(intent != null && intent.hasExtra(EXTRA_ALARM_ID)) {
                mAlarmId = intent.getLongExtra(EXTRA_ALARM_ID, DEFAULT_ALARM_ID);
                mNew = intent.getBooleanExtra(EXTRA_ALARM_NEW, DEFAULT_ALARM_NEW);
                // Load alarm from database
                loadAlarm();
            } else {
                Log.e(TAG, "No intent or no data set in intent!");
                if(mAlarm == null) {
                    Log.e(TAG, "Creating new alarm!");
                    mAlarm = createAlarm();
                    Log.e(TAG, "New alarm created!");
                    mAlarmId = mAlarm.getId();
                    mNew = true;
                }
            }
            populateUI(mAlarm);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(EXTRA_ALARM_ID, mAlarmId);
        outState.putBoolean(EXTRA_ALARM_NEW, mNew);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSave && mAlarm != null) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if(mNew) {
                        // Save alarm
                        mDb.alarmDao().save(mAlarm);
                    } else {
                        // Update alarm
                        mDb.alarmDao().update(mAlarm);
                    }
                }
            });
        } else if(view == buttonCancel) {
            // Delete alarm if it new one
            if(mNew && mAlarm != null) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.alarmDao().delete(mAlarm);
                    }
                });
            }
        }
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == switchTone) {
            Snackbar.make(buttonSave, "Tone switched " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Tone", null).show();

            mAlarm.setGraduallyIncreaseVolume(isChecked);
        } else if(buttonView == switchVibrate) {
            Snackbar.make(buttonSave, "Vibrate switched " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Vibrate", null).show();

            mAlarm.getVibration().setVibrationEnabled(isChecked);
        } else if(buttonView == switchSayTime) {
            Snackbar.make(buttonSave, "Say Time switched " + (isChecked ? "ON" : "OFF"), Snackbar.LENGTH_SHORT)
                    .setAction("Say", null).show();

            mAlarm.setSayTime(isChecked);
        }
    }

    private void loadAlarm() {
        AlarmViewModelFactory factory = new AlarmViewModelFactory(mDb, mAlarmId);
        final AlarmViewModel viewModel = ViewModelProviders.of(this, factory).get(AlarmViewModel.class);
        viewModel.getAlarm().observe(this, new Observer<Alarm>() {
            @Override
            public void onChanged(@Nullable Alarm alarm) {
                viewModel.getAlarm().removeObserver(this);
                Log.d(TAG, "Receiving database update from LiveData");
                mAlarm = alarm;
                populateUI(mAlarm);
            }
        });
    }

    /**
     * Setting UI.
     *
     * @param alarm - Alarm
     */
    private void populateUI(final Alarm alarm) {
        if(alarm == null) {
            return;
        }

        if(alarm.getPattern() == null || alarm.getPattern().getPattern().length == 1) {
            SingleFragment singleFragment = new SingleFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.container_for_fragment, singleFragment)
                    .commit();
            // Setting date in fragment same as alarm
            Calendar cal = Calendar.getInstance();
            cal.setTime(alarm.getPattern().getStartDate());
            // TODO update text fields
//            singleFragment.tvDate.setText(AlarmUtils.formatDate(cal));
//            singleFragment.tvTime.setText(AlarmUtils.formatTime(cal));

            singleFragment.setTimeChangeListener(this);
            singleFragment.setDateChangeListener(this);
        } else if(alarm.getPattern().getPattern().length == 7) {
            Pattern pattern = alarm.getPattern();
            WeekFragment weekFragment = new WeekFragment();
            // Adding fragment to activity
            fragmentManager.beginTransaction()
                    .add(R.id.container_for_fragment, weekFragment)
                    .commit();
            // Setting checkboxes
//            fragment.checkboxMonday.setChecked(pat.getPatternValue(0));
//            fragment.checkboxTuesday.setChecked(pat.getPatternValue(1));
//            fragment.checkboxWednesday.setChecked(pat.getPatternValue(2));
//            fragment.checkboxThursday.setChecked(pat.getPatternValue(3));
//            fragment.checkboxFriday.setChecked(pat.getPatternValue(4));
//            fragment.checkboxSaturday.setChecked(pat.getPatternValue(5));
//            fragment.checkboxSunday.setChecked(pat.getPatternValue(6));

            weekFragment.setTimeChangeListener(this);
        } else {
            // TODO add custom fragment
        }

        tvAlarmName.setText(alarm.getName());

        pickerAlarmType.setMinValue(0);
        pickerAlarmType.setMaxValue(2);
        pickerAlarmType.setDisplayedValues(Type.asStringArray());
        // Setting alarm tone
        if(alarm.getTone() != null) {
            tvToneName.setText(alarm.getTone());
        }
        // TODO set volume
        // TODO set switch for graduate volume increase
        // Setting vibration
        if(alarm.getVibration() != null) {
            tvVibrationName.setText(alarm.getVibration().getVibrationName());
            switchVibrate.setChecked(alarm.getVibration().isVibrationEnabled());
        } else {
            switchVibrate.setChecked(false);
        }
        switchSayTime.setChecked(alarm.isSayTime());

        // Setting listeners
        tvAlarmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNameRequest();
            }
        });

        pickerAlarmType.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker pickerAlarmType, int oldVal, int newVal) {
                Snackbar.make(pickerAlarmType, Type.values()[newVal].toString(), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                switch (newVal) {
                    case 0:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container_for_fragment, new SingleFragment())
                                .commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container_for_fragment, new WeekFragment())
                                .commit();
                        break;
                    case 2:
                        break;
                }
            }
        });
        // Setting OnClickListeners for buttons
        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        // Setting OnCheckedChangeListeners
        switchTone.setOnCheckedChangeListener(this);
        switchVibrate.setOnCheckedChangeListener(this);
        switchSayTime.setOnCheckedChangeListener(this);
    }

    /**
     * Creating new Alarm object.
     *
     * @return Alarm
     */
    private Alarm createAlarm() {
        long id = Calendar.getInstance().getTimeInMillis();
        String tone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
        int volume = 100;
        Pattern pattern = new Pattern();
        pattern.setStartDate(Calendar.getInstance().getTime());
        pattern.setPattern(new boolean[]{true});

        Snooze snooze = new Snooze();
        Vibration vibration = new Vibration();

        return new Alarm(id, "Alarm", true, false, tone, volume,
                false, false, pattern, snooze, vibration);
    }

    /**
     * Method to show text input dialog for name.
     */
    public void onNameRequest() {
        // Create an instance of the dialog fragment and show it
        NameDialogFragment dialog = new NameDialogFragment();
        dialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog){
        if(dialog instanceof NameDialogFragment) {
            NameDialogFragment d = (NameDialogFragment) dialog;
            String name = d.getName();
            if(!TextUtils.isEmpty(name)) {
                mAlarm.setName(name);
                tvAlarmName.setText(name);
            } else {
                Toast.makeText(this, R.string.text_name_not_specified, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTimeChanged(int hourOfDay, int minute) {
        if(mAlarm == null) {
            return;
        }
        Calendar cal = Calendar.getInstance();
        if(mAlarm.getPattern().getStartDate() != null) {
            cal.setTime(mAlarm.getPattern().getStartDate());
        }
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        mAlarm.getPattern().setStartDate(cal.getTime());
    }

    @Override
    public void onDateChanged(int year, int month, int day) {
        if(mAlarm == null) {
            return;
        }
        Calendar cal = Calendar.getInstance();
        if(mAlarm.getPattern().getStartDate() != null) {
            cal.setTime(mAlarm.getPattern().getStartDate());
        }
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        mAlarm.getPattern().setStartDate(cal.getTime());
    }
}