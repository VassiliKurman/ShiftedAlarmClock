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
 * BooleanArrayConverter
 * Created by Vassili Kurman on 25/11/2018.
 * Version 1.0
 */
public class BooleanArrayConverter {
    /**
     * Converts String to boolean[] object.
     *
     * @param text - String
     * @return boolean[]
     */
    @TypeConverter
    public static boolean[] toArray(String text) {
        if(text == null || text.isEmpty())
            return null;
        String[] str = text.split("\\s+");
        boolean[] pattern = new boolean[str.length];
        for(int i = 0; i < str.length; i++) {
            pattern[i] = str[i].equals("1");
        }
        return pattern;
    }

    /**
     * Returns String object from boolean[] array.
     *
     * @param array - boolean[]
     * @return String
     */
    @TypeConverter
    public static String toString(boolean[] array){
        StringBuilder sb = new StringBuilder();
        for(boolean b : array) {
            sb.append(b ? "1 " : "0 ");
        }
        return sb.toString();
    }
}