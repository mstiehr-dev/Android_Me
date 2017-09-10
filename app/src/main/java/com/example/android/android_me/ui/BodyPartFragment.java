package com.example.android.android_me.ui;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.DragAndDropSwitcherImpl;
import com.example.android.android_me.R;

import java.util.ArrayList;


public class BodyPartFragment extends Fragment {
    private static final String TAG = "BodyPartFragment";

    private final static String KEY_IMAGES = "IMAGES";
    private final static String KEY_INDEX = "INDEX";
    private final static String KEY_CONTAINER = "CONTAINER_ID";

    private ArrayList<Integer> mImages;
    private int mSelectedImage;
    private int mContainerId;

    DragAndDropSwitcher mDragAndDropSwitcher;

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
            mContainerId = savedInstanceState.getInt(KEY_CONTAINER);
        }
        if(mImages == null){
            mImages = getArguments().getIntegerArrayList(KEY_IMAGES);
            mSelectedImage = getArguments().getInt(KEY_INDEX);
        }

        if(container != null) {
            mContainerId = container.getId();
        }

        this.mDragAndDropSwitcher = (DragAndDropSwitcher) getActivity();

        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        final ImageView imageView = rootView.findViewById(R.id.body_part_image_view);
        imageView.setImageResource(mImages.get(mSelectedImage));
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData data = ClipData.newPlainText("image_id", mImages.get(mSelectedImage).toString()); // not really needed
                View.DragShadowBuilder builder = new View.DragShadowBuilder(view);
                view.startDrag(data, builder, view, 0);

                mDragAndDropSwitcher.registerDrag(mContainerId, getTag());

                return true;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImages == null)
                    return;

                mSelectedImage = (mSelectedImage +1) % mImages.size();
                imageView.setImageResource(mImages.get(mSelectedImage));
            }
        });
        imageView.setOnDragListener(new MyOnDragListener());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putIntegerArrayList(KEY_IMAGES, mImages);
        outState.putInt(KEY_INDEX, mSelectedImage);
        outState.putInt(KEY_CONTAINER, mContainerId);

        super.onSaveInstanceState(outState);
    }

    private class MyOnDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            Log.d(TAG, "onDrag: action: " + action);

            switch(action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(TAG, "onDrag: entered");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(TAG, "onDrag: exited");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "onDrag: drop");
                    mDragAndDropSwitcher.registerDrop(mContainerId, getTag());
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "onDrag: ended");
                    break;
                default:
                    return false; // something went wrong
            }

            return true;
        }
    }

    public interface DragAndDropSwitcher {
        void registerDrag(int container, String tag);
        void registerDrop(int container, String tag);
    }
}
