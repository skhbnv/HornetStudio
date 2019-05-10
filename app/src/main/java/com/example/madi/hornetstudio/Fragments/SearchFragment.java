package com.example.madi.hornetstudio.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madi.hornetstudio.Activities.RegisterPageActivity;
import com.example.madi.hornetstudio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

public class SearchFragment extends Fragment implements View.OnClickListener
{
    private TextView mRegisterButton;
    private Button mSignInButton;
    private EditText mPassword;
    private EditText mConfirmToRegister;
    private EditText mPhone;
    FirebaseAuth mAuth;

    private TextView mSalonRegButton;
    private TextView mNameNamespace;
    private TextView mTextViewToHide;

    private LinearLayout mLayoutToHide;
    private LinearLayout mLayoutRecycler;

    private Button hiddenRegButton;


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
        if (mAuth.getCurrentUser() != null){
            changeContentToSignedIn();
        }
    }

    private void initUI() {
        mAuth = FirebaseAuth.getInstance();
        mRegisterButton = getView().findViewById(R.id.register_button);
        mSalonRegButton = getView().findViewById(R.id.salon_reg_button);

        mSignInButton = getView().findViewById(R.id.sign_in_button);

        hiddenRegButton = getView().findViewById(R.id.hidden_reg_button);
        mLayoutRecycler = getView().findViewById(R.id.recycler_layout);

        mPhone = getView().findViewById(R.id.phone_to_register);
        mPassword = getView().findViewById(R.id.password_to_register);
        mConfirmToRegister = getView().findViewById(R.id.confirm_to_register);

        mNameNamespace = getView().findViewById(R.id.name_namespace);
        mTextViewToHide = getView().findViewById(R.id.number_namespace);
        mLayoutToHide = getView().findViewById(R.id.layout_to_hide);

        hiddenRegButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        mSalonRegButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                signInJustRegisteredPerson();
                break;
            case R.id.register_button:
                if(mRegisterButton.getText().equals("Назад")){
                    hiddenRegButton.setVisibility(View.GONE);
                    mConfirmToRegister.setVisibility(View.GONE);
                    mSignInButton.setVisibility(View.VISIBLE);
                    mSalonRegButton.setVisibility(View.GONE);
                    mRegisterButton.setText("Зарегистрироваться");

                }else{
                    hiddenRegButton.setVisibility(View.VISIBLE);
                    mConfirmToRegister.setVisibility(View.VISIBLE);
                    mSignInButton.setVisibility(View.GONE);
                    mSalonRegButton.setVisibility(View.VISIBLE);

                    mRegisterButton.setText("Назад");
                }
                //                mSignInButton.requestLayout();
                break;
            case R.id.salon_reg_button:
                Intent intent = new Intent(getContext(), RegisterPageActivity.class);
                startActivity(intent);
                break;
            case R.id.hidden_reg_button:

                Log.d("___", "onClick: clicked hidden regbut");

                mConfirmToRegister.getText();
                if (mConfirmToRegister.getText().toString().equals(mPassword.getText().toString())){
                    mAuth.createUserWithEmailAndPassword(mPhone.getText().toString()+"@m.kz",
                            mPassword.getText().toString()).addOnCompleteListener
                            (new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "successfully added",
                                                Toast.LENGTH_SHORT).show();
                                        signInJustRegisteredPerson();

                                    }else{
                                        Toast.makeText(getContext(), task.getException().toString(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else {
                    Toast.makeText(getContext(), "passwords does not match",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void signInJustRegisteredPerson() {
        mAuth.signInWithEmailAndPassword(mPhone.getText().toString()+"@m.kz",
                mPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            changeContentToSignedIn();

                        }else{
                            Toast.makeText(getContext(), task.getException().toString(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void changeContentToSignedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String number[] = currentUser.getEmail().split("@");
        mNameNamespace.setText(number[0]);
        mTextViewToHide.setText(R.string.signed_in);
        mLayoutToHide.setVisibility(View.GONE);
        mLayoutRecycler.setVisibility(View.VISIBLE);
    }


}
