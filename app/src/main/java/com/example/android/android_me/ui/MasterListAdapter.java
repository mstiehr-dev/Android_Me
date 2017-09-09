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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


// Custom adapter class that displays a list of Android-Me images in a GridView
public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.AndroidViewHolder> {

    // Keeps track of the context and list of images to display
    private Context mContext;
    private MasterListFragment.OnImageClickListener onImageClickListener;
    private List<Integer> mImageIds;

    /**
     * Constructor method
     * @param imageIds The list of images to display
     */
    public MasterListAdapter(Context context, List<Integer> imageIds) {
        mContext = context;
        mImageIds = imageIds;
        onImageClickListener = (MasterListFragment.OnImageClickListener) context;
    }


    @Override
    public AndroidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        imageView.setTag("image_view");
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);

        AndroidViewHolder viewHolder = new AndroidViewHolder(imageView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AndroidViewHolder holder, final int position) {
        ImageView imageView = holder.itemView.findViewWithTag("image_view");
        imageView.setImageResource(mImageIds.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClickListener.onImageSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageIds.size();
    }

    /**
     * Creates a new ImageView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            imageView = new ImageView(mContext);
            // Define the layout parameters
//            imageView.setAdjustViewBounds(true);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        // Set the image resource and return the newly created ImageView
        imageView.setImageResource(mImageIds.get(position));
        return imageView;
    }

    class AndroidViewHolder extends RecyclerView.ViewHolder {

        public AndroidViewHolder(View itemView) {
            super(itemView);
        }
    }
}
