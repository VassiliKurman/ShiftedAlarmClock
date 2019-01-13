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

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Objects;

import vkurman.shiftedalarmclock.R;

/**
 * NumberDialogPicker
 * Created by Vassili Kurman on 13/01/2019.
 * Version 1.0
 */
public class NumberDialogPicker extends DialogFragment {
    /**
     * Reference to the number picked by user
     */
    private int mNumber;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NumberDialogListener {
        void onDialogNumberSet(NumberDialogPicker dialog);
        void onDialogNumberCancel(NumberDialogPicker dialog);
    }

    // Use this instance of the interface to deliver action events
    private NumberDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the TrackNameDialogListener so we can send events to the host
            mListener = (NumberDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NameDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.dialog_number_picker, null);
        final NumberPicker np = view.findViewById(R.id.number_picker);
        np.setMaxValue(31);
        np.setMinValue(2);
        np.setWrapSelectorWheel(false);

        builder.setView(view)
                .setTitle(R.string.dialog_title_number_pattern)
                // Add action buttons
                .setPositiveButton(R.string.dialog_set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mNumber = np.getValue();
                        if(mListener != null) {
                            mListener.onDialogNumberSet(NumberDialogPicker.this);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(mListener != null) {
                            mListener.onDialogNumberCancel(NumberDialogPicker.this);
                            NumberDialogPicker.this.getDialog().cancel();
                        }
                    }
                });
        return builder.create();
    }

    /**
     * Returns number picked by user.
     *
     * @return int - name
     */
    public int getNumber() {
        return mNumber;
    }
}