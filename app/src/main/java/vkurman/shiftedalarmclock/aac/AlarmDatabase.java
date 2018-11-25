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

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import vkurman.shiftedalarmclock.models.Alarm;

/**
 * AlarmDatabase
 * Created by Vassili Kurman on 24/11/2018.
 * Version 1.0
 */
@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, BooleanArrayConverter.class, LongArrayConverter.class})
public abstract class AlarmDatabase extends RoomDatabase {

    private static final String TAG = AlarmDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "alarms";
    private static AlarmDatabase sInstance;

    public static AlarmDatabase getInstance(Context context) {
        if(sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AlarmDatabase.class, DATABASE_NAME).build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    /**
     * Returns AlarmDao - an Alarm Data Access Object.
     *
     * @return AlarmDao
     */
    public abstract AlarmDao alarmDao();
}