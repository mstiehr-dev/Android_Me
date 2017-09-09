/*
* Copyright (C) 2017 The Android Open Source Project
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

package com.example.android.android_me.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.List;


// Custom adapter class that displays a list of Android-Me images in a GridView
public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.AndroidViewHolder> {
    private static final String TAG = "MasterListAdapter";

    private LayoutInflater mLayoutInflater;
    private MasterListFragment.MasterListClickListener masterListClickListener;
    private List<Integer> mImageIds;

    public MasterListAdapter(Context context, List<Integer> imageIds) {
        mLayoutInflater = LayoutInflater.from(context);
        mImageIds = imageIds;
        if(context instanceof MasterListFragment.MasterListClickListener) {
            masterListClickListener = (MasterListFragment.MasterListClickListener) context;
        }
    }


    @Override
    public AndroidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: had to create a new image view");
        View imageView = mLayoutInflater.inflate(R.layout.simple_android_list_item, parent, false);

        return new AndroidViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(final AndroidViewHolder holder, int position) {
        holder.imageView.setImageResource(mImageIds.get(position));
    }

    @Override
    public int getItemCount() {
        return mImageIds.size();
    }

    class AndroidViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public AndroidViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView;
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(masterListClickListener != null) {
                masterListClickListener.onImageSelected(getAdapterPosition());
            }
        }
    }
}
