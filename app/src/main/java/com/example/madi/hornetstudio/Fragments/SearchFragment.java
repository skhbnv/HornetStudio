package com.example.madi.hornetstudio.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.madi.hornetstudio.Activities.RegisterPageActivity;
import com.example.madi.hornetstudio.R;

public class SearchFragment extends Fragment implements View.OnClickListener
{
    private Button mRegisterButton;
    private Button mLoginButton;
    private EditText mName;
    private EditText mPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mRegisterButton = getView().findViewById(R.id.register_button);
        mLoginButton = getView().findViewById(R.id.sign_in_button);

        mName = getView().findViewById(R.id.name_to_register);
        mPhone = getView().findViewById(R.id.phone_to_register);

        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                Intent intent = new Intent(getContext(), RegisterPageActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_in_button:
                break;
        }
    }
}
