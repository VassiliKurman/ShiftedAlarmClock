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

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.adapters.AlarmsAdapter;
import vkurman.shiftedalarmclock.interfaces.ResultListener;

/**
 * MainActivity is displaying list of created alarms.
 *
 * Created by Vassili Kurman on 17/11/2018.
 * Version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ResultListener {

    // Binding views
    @BindView(R.id.recyclerview_alarms) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    // Adapter for RecyclerView
    private AlarmsAdapter alarmsAdapter;

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

        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == fab) {
            Intent intent = new Intent(this, NewAlarmActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResultClick(long id) {
        Snackbar.make(fab, "FAB clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}