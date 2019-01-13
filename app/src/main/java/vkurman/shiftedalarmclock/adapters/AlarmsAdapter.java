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
package vkurman.shiftedalarmclock.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.shiftedalarmclock.R;
import vkurman.shiftedalarmclock.interfaces.ResultListener;
import vkurman.shiftedalarmclock.models.Alarm;
import vkurman.shiftedalarmclock.utils.AlarmUtils;

/**
 * AlarmsAdapter
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.AlarmsViewHolder> {

    private List<Alarm> mAlarms;
    private ResultListener mResultListener;

    public class AlarmsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.time) TextView mTime;
        @BindView(R.id.name) TextView mName;
        @BindView(R.id.pattern) TextView mPattern;
        @BindView(R.id.switch_on_off) Switch mSwitch;

        AlarmsViewHolder(final View itemView, ResultListener resultListener) {
            super(itemView);
            // Binding views
            ButterKnife.bind(this, itemView);
            mResultListener = resultListener;
            // Set listener for Switch
            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        mAlarms.get(getAdapterPosition()).setActive(true);
                        // TODO update database
                        // TODO turn ON alarm
                    } else {
                        mAlarms.get(getAdapterPosition()).setActive(false);
                        // TODO update database
                        // TODO turn OFF alarm
                    }
                }
            });
            // Set separate click listeners to item
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mAlarms == null) {
                return;
            }
            int position = getAdapterPosition();
            if(position >= 0 && position < mAlarms.size()) {
                mResultListener.onResultClick(mAlarms.get(position).getId());
            }
        }
    }

    /**
     * Constructor.
     *
     * @param resultListener - {@link ResultListener}
     */
    public AlarmsAdapter(ResultListener resultListener) {
        mResultListener = resultListener;
    }

    @NonNull
    @Override
    public AlarmsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_layout;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new AlarmsViewHolder(view, mResultListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmsViewHolder holder, int position) {
        if(position >= 0 && position < mAlarms.size()) {
            final Alarm alarm = mAlarms.get(position);

            Calendar cal = Calendar.getInstance();
            cal.setTime(alarm.getPattern().getStartDate());

            holder.mTime.setText(AlarmUtils.formatTime(cal));
            holder.mName.setText(alarm.getName());
            if(alarm.getPattern().getPattern().length == 1) {
                holder.mPattern.setText(AlarmUtils.formatShortDate(cal));
            } else {
                holder.mPattern.setText(AlarmUtils.formatPattern(alarm.getPattern()));
            }
            holder.mSwitch.setChecked(alarm.isActive());
        }
    }

    @Override
    public int getItemCount() {
        return mAlarms == null ? 0 : mAlarms.size();
    }

    public void updateData(List<Alarm> alarms) {
        mAlarms = alarms;
        notifyDataSetChanged();
    }

    /**
     * Returns list of items attached to this adapter.
     *
     * @return List
     */
    public List<Alarm> getAlarms() {
        return mAlarms;
    }
}