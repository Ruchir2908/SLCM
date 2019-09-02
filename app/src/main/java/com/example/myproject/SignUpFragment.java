package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SignUpFragment extends Fragment {

    DBHelper helper;
    SQLiteDatabase database;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        helper = DBHelper.getInstance(getContext());
        database = helper.getWritableDatabase();

        TextView alreadyRegistered = view.findViewById(R.id.alreadyRegistered);
        TextView guest = view.findViewById(R.id.guest);
        final EditText nameEditText, phoneEditText, usernameEditText, password1EditText, password2EditText;
        Button signupButton;

        nameEditText = view.findViewById(R.id.nameEditText);
        phoneEditText = view.findViewById(R.id.phoneNoEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        password1EditText = view.findViewById(R.id.passwordEditText);
        password2EditText = view.findViewById(R.id.confirmPasswordEditText);
        signupButton = view.findViewById(R.id.signupButton);

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new FormFragment()).addToBackStack(null).commit();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nameEditText.getText()!=null && phoneEditText.getText()!=null && usernameEditText.getText()!=null && password1EditText.getText()!=null && password2EditText.getText()!=null) {
                    if(nameEditText.getText().toString().length()==0){
                        Toast.makeText(getContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(phoneEditText.getText().toString().length()<10 || phoneEditText.getText().toString().length()>10){
                        Toast.makeText(getContext(), "Enter valid number", Toast.LENGTH_SHORT).show();
                    }else if(usernameEditText.getText().toString().length()==0){
                        Toast.makeText(getContext(), "Enter valid username", Toast.LENGTH_SHORT).show();
                    }else if(password1EditText.getText().toString().length()==0 || password2EditText.getText().toString().length()==0){
                        Toast.makeText(getContext(), "Enter valid password", Toast.LENGTH_SHORT).show();
                    }else if(!password1EditText.getText().toString().equals(password2EditText.getText().toString())){
                        Toast.makeText(getContext(), "Passwords do not match !", Toast.LENGTH_SHORT).show();
                    }else{

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBHelper.CONSUMER_NAME,nameEditText.getText().toString());
                        contentValues.put(DBHelper.CONSUMER_PHONE,phoneEditText.getText().toString());
                        contentValues.put(DBHelper.CONSUMER_USERNAME,usernameEditText.getText().toString());
                        contentValues.put(DBHelper.CONSUMER_PASSWORD,password1EditText.getText().toString());
                        long insertVal = database.insert(DBHelper.CONSUMER_TABLE_NAME,null,contentValues);
                        Log.i("DB",insertVal+"");
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).addToBackStack(null).commit();
                    }

                }
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Sign Up");
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public interface OnFragmentInteractionListener {
    }
}
