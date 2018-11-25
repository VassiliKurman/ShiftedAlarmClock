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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.aac.AlarmDatabase;
import vkurman.shiftedalarmclock.aac.AlarmsViewModel;
import vkurman.shiftedalarmclock.aac.AppExecutors;
import vkurman.shiftedalarmclock.adapters.AlarmsAdapter;
import vkurman.shiftedalarmclock.interfaces.ResultListener;
import vkurman.shiftedalarmclock.models.Alarm;

/**
 * MainActivity is displaying list of created alarms.
 *
 * Created by Vassili Kurman on 17/11/2018.
 * Version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ResultListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Binding views
    @BindView(R.id.recyclerview_alarms) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    // Adapter for RecyclerView
    private AlarmsAdapter alarmsAdapter;
    // Database reference
    private AlarmDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Binding views
        ButterKnife.bind(this);
        // Setting recycle view for alarms
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        alarmsAdapter = new AlarmsAdapter(this);
        recyclerView.setAdapter(alarmsAdapter);

        /*
         * Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         * and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Alarm> alarms = alarmsAdapter.getAlarms();
                        mDb.alarmDao().delete(alarms.get(position));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

        fab.setOnClickListener(this);

        mDb = AlarmDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }

    /**
     * Retrieving tasks from database
     */
    private void setupViewModel() {
        AlarmsViewModel viewModel = ViewModelProviders.of(this).get(AlarmsViewModel.class);
        viewModel.getAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable List<Alarm> alarms) {
                Log.e(TAG, "Updating alarms from LiveData in ViewModel");
                alarmsAdapter.updateData(alarms);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == fab) {
            Intent intent = new Intent(this, NewAlarmActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResultClick(int id) {
        Snackbar.make(fab, "Item clicked" + id, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

        // Launch NewAlarmActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, NewAlarmActivity.class);
        intent.putExtra(NewAlarmActivity.EXTRA_ALARM_ID, id);
        startActivity(intent);
    }
}