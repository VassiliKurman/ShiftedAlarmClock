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

import android.arch.persistence.room.TypeConverter;

/**
 * LongArrayConverter
 * Created by Vassili Kurman on 25/11/2018.
 * Version 1.0
 */
public class LongArrayConverter {
    /**
     * Converts String to long[] object.
     *
     * @param text - String
     * @return long[]
     */
    @TypeConverter
    public static long[] toArray(String text) {
        if(text == null || text.isEmpty())
            return null;
        String[] str = text.split("\\s+");
        long[] pattern = new long[str.length];
        for(int i = 0; i < str.length; i++) {
            pattern[i] = Long.parseLong(str[i]);
        }
        return pattern;
    }

    /**
     * Returns String object from long[] array.
     *
     * @param array - long[]
     * @return String
     */
    @TypeConverter
    public static String toString(long[] array){
        StringBuilder sb = new StringBuilder();
        for(long l :  array) {
            sb.append(l).append(" ");
        }
        return sb.toString();
    }
}