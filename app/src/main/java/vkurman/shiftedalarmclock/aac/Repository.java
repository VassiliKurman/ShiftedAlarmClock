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
package vkurman.shiftedalarmclock.aac;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import vkurman.shiftedalarmclock.models.Alarm;

/**
 * Repository - single point of access to data.
 * Created by Vassili Kurman on 24/11/2018.
 * Version 1.0
 */
@Singleton
public class Repository {

    private AlarmDatabase mDatabase;

    @Inject
    public Repository(AlarmDatabase database) {
        mDatabase = database;
    }

    public LiveData<Alarm> getAlarm(int alarmId) {
        return mDatabase.alarmDao().load(alarmId);
    }

    public LiveData<List<Alarm>> getAlarms() {
        return mDatabase.alarmDao().loadAll();
    }

    public void save(Alarm... alarms) {
        mDatabase.alarmDao().save(alarms);
    }

    public void update(Alarm alarm) {
        mDatabase.alarmDao().update(alarm);
    }

    public void delete(Alarm alarm) {
        mDatabase.alarmDao().delete(alarm);
    }
}