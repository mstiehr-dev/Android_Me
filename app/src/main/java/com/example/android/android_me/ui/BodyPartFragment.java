package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.ArrayList;


public class BodyPartFragment extends Fragment {
    private final static String KEY_IMAGES = "IMAGES";
    private final static String KEY_INDEX = "INDEX";

    private ArrayList<Integer> mImages;
    private int mSelectedImage = 0;

    public BodyPartFragment() {
    }

    public static BodyPartFragment newInstance(ArrayList<Integer> images, int selected) {
        // currently no arguments
        // later we put those arguments in a bundle and append it accordingly
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(KEY_IMAGES, images);
        bundle.putInt(KEY_INDEX, selected);

        BodyPartFragment fragment = new BodyPartFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mImages = savedInstanceState.getIntegerArrayList(KEY_IMAGES);
            mSelectedImage = savedInstanceState.getInt(KEY_INDEX);
        } else {
            mImages = getArguments().getIntegerArrayList(KEY_IMAGES);
            mSelectedImage = getArguments().getInt(KEY_INDEX);
        }

        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        final ImageView imageView = rootView.findViewById(R.id.body_part_image_view);
        imageView.setImageResource(mImages.get(mSelectedImage));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImages == null)
                    return;

                mSelectedImage = (mSelectedImage +1) % mImages.size();
                imageView.setImageResource(mImages.get(mSelectedImage));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putIntegerArrayList(KEY_IMAGES, mImages);
        outState.putInt(KEY_INDEX, mSelectedImage);

        super.onSaveInstanceState(outState);
    }
}
