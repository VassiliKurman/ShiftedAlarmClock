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
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import vkurman.shiftedalarmclock.models.Alarm;

/**
 * AlarmViewModel
 * Created by Vassili Kurman on 24/11/2018.
 * Version 1.0
 */
public class AlarmViewModel extends ViewModel {

    private Alarm alarm;
    private Repository repository;

    // instructing Dagger 2 to provide the Repository parameter
    @Inject
    public AlarmViewModel(Repository repo) {
        this.repository = repo;
    }

    public void init(int alarmId) {
        if(this.alarm != null) {
            return;
        }
        alarm = repository.getAlarm(alarmId);
    }

    public Alarm getAlarm() {
        return this.alarm;
    }
}