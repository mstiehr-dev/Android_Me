package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.List;


public class MasterListFragment extends Fragment {

    private OnImageClickListener onImageClickListener;

    public MasterListFragment() {
    }

    public static MasterListFragment newInstance() {
        return new MasterListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        final RecyclerView grid = rootView.findViewById(R.id.gridview);
        grid.setLayoutManager(new GridLayoutManager(getContext(), 3));

        List<Integer> allImages = AndroidImageAssets.getAll();
        final RecyclerView.Adapter adapter = new MasterListAdapter(getContext(), allImages);
        grid.setAdapter(adapter);


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnImageClickListener) {
            this.onImageClickListener = (OnImageClickListener) context;
        }
    }

    interface OnImageClickListener {
        void onImageSelected(int pos);
    }


}
