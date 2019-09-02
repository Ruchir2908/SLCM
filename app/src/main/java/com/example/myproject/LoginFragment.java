package com.example.myproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment {

    DBHelper helper;
    SQLiteDatabase database;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        TextView newUser = view.findViewById(R.id.newUser);
//        TextView guest = view.findViewById(R.id.guest);
        Button logIn = view.findViewById(R.id.loginButton);
        final EditText username = view.findViewById(R.id.usernameEditText);
        final EditText password = view.findViewById(R.id.passwordEditText);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText()!=null && password.getText()!=null){

                    String usernameString = username.getText().toString();
                    String passwordString = password.getText().toString();
                    boolean correct = false;

                    Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.CONSUMER_TABLE_NAME, null, null);
                    while(cursor.moveToNext()){
                        if(usernameString.equals(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMER_USERNAME))) && passwordString.equals(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMER_PASSWORD)))){
                            correct = true;
                            break;
                        }
                    }

                    if(usernameString.length()==0/* || !usernameString.equals("ruchir29")*/){
                        Toast.makeText(getContext(), "Please enter valid username !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(passwordString.length()==0/* || !passwordString.equals("ruchir98")*/){
                        Toast.makeText(getContext(), "Please enter valid password !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(/*usernameString.equals("ruchir29") && passwordString.equals("ruchir98")*/correct){
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new FormFragment()).addToBackStack(null).commit();
                    }else{
                        Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new SignUpFragment()).addToBackStack(null).commit();
            }
        });

//        guest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new FormFragment()).addToBackStack(null).commit();
//            }
//        });

        return view;
    }

    @Override
    public void onAttach(Context context) {

        helper = DBHelper.getInstance(getContext());
        database = helper.getWritableDatabase();

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("LogIn");
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
