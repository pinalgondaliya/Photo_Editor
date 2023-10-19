package com.example.photoeditor.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.Adapter.SpiralAdapter;
import com.example.photoeditor.ModelClass.MainModel;
import com.example.photoeditor.R;

import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes7.dex */
public class SpiralFragment extends Fragment {
    ArrayList<MainModel.Datum.Category_Model> list;
    onFragmentListener onFragmentListener;
    RecyclerView recyclerView;

    /* loaded from: classes7.dex */
    public interface onFragmentListener {
        void fragmentClick(int i);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spiral,container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        String c_name = getArguments().getString("category");
        ArrayList<MainModel.Datum.Category_Model> arrayList = (ArrayList) getArguments().getSerializable("arraylist");
        this.list = arrayList;
        Collections.shuffle(arrayList);
        SpiralAdapter spiralAdapter = new SpiralAdapter(requireActivity(), this.list, c_name, new SpiralAdapter.Passposition() { // from class: com.example.photoareditor.Fragment.SpiralFragment.1
            @Override // com.example.photoareditor.Adapter.SpiralAdapter.Passposition
            public void OnClick(int position) {
                Log.e("ddddd", "OnClick: " + position);
                SpiralFragment.this.onFragmentListener.fragmentClick(position);
            }
        });
        this.recyclerView.setAdapter(spiralAdapter);
        return view;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.onFragmentListener = (onFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }
}
