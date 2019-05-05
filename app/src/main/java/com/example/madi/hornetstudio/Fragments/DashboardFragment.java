package com.example.madi.hornetstudio.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madi.hornetstudio.Activities.SalonPageActivity;
import com.example.madi.hornetstudio.Adapters.Adapter;
import com.example.madi.hornetstudio.ItemClickListener;
import com.example.madi.hornetstudio.Models.SalonsCardInfo;
import com.example.madi.hornetstudio.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment implements ItemClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private static List<SalonsCardInfo> mData;

    public static void setmData(List<SalonsCardInfo> mData) {
        DashboardFragment.mData = mData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectToDatabase();
    }


    void connectToDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference baseRef = database.getReference("salons");
        Log.d(TAG, "run: ");
        baseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<SalonsCardInfo> data = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()){           //user id
                    SalonsCardInfo order = child.getValue(SalonsCardInfo.class);
                    data.add(order);
                }
                Log.d(TAG, "onDataChange: "+data.get(0).getAddress());
                onDataLoaded(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.toException().toString());
            }
        });
    }

    private void onDataLoaded(List<SalonsCardInfo> data) {
        mData = data;

        mRecyclerView = getView().findViewById(R.id.salons_recycler);
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter(mData, this, getContext());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(SalonsCardInfo salon) {
        Intent intent = new Intent(getContext(), SalonPageActivity.class);
        intent.putExtra("salon_object",salon);
        startActivity(intent);
    }
}
