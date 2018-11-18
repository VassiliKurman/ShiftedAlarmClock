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
package vkurman.shiftedalarmclock.models;

/**
 * Type
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
public enum Type {
    Single, Week, Pattern;

    public static String[] asStringArray() {
        String[] s = new String[values().length];
        for(int i = 0; i < values().length; i++) {
            s[i] = values()[i].toString();
        }
        return s;
    }
}