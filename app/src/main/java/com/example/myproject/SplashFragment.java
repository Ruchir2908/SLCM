package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashFragment extends Fragment {

    String title = "Street Light Complaint Management";

    int a = title.length();
    int i = 0;

    @BindView(R.id.tv_title)
    TextView tv_title;

    public SplashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        callHandler();
        return view;
    }

    private void callHandler(){
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run () {
                 Log.i("NUM", "Handler");
//                    Intent mainIntent = new Intent(getContext(),LoginFragment.class);
                 if (i < a) {
                     setText("" + title.charAt(i));
                     i = i + 1;
                     callHandler();
                 } else if (i == a) {
//                     getActivity().getSupportFragmentManager().popBackStack();
                     getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, new SignUpFragment()).addToBackStack(null).commit();
                 } else {
                     callHandler(); i=i+1;
                 }
             }
         },250);
    }

    @Override
    public void onAttach(Context context) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        getActivity().finish();

    }

    public interface OnFragmentInteractionListener {

    }

    private void setText(String text){
        tv_title.setText(tv_title.getText()+text);
    }

}
