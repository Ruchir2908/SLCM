package com.example.myproject;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FormFragment.FormFragmentCallBack, LoginFragment.OnFragmentInteractionListener, SplashFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener {

//    TextView textView;
//    LocationManager locationManager;
//    AlertDialog alertDialog;
//    private AlertDialog.Builder builder;
//    FrameLayout container;
//    public String location;

    @BindView(R.id.container)
    FrameLayout container;
    public static LocationManager locationManager;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

//    TelephonyManager telephonyManager;

    private Notification buildNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "long_running_service_channel");
        notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("RUCHIR")
                .setContentText("RUCHIR")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return notificationBuilder.build();
    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you really want to exit ?")
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                })
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setCancelable(false);
        alertDialog = builder.create();

        getSupportFragmentManager().beginTransaction().add(R.id.container, new SplashFragment()).addToBackStack(null).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.container,new FormFragment()).addToBackStack(null).commit();

//        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Toast.makeText(this, telephonyManager.getDeviceId(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, telephonyManager.getMeid(0), Toast.LENGTH_SHORT).show();
//        Log.i("ID","ID1: "+telephonyManager.getImei(0));
//        Log.i("ID","ID2: "+telephonyManager.getImei(1));
//        Toast.makeText(this, telephonyManager.getMeid(1), Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(this, MyService2.class);
//        intent.putExtra("notification_icon", buildNotification());
//        startService(intent);


//        Intent intent = new Intent(MainActivity.this, MyService2.class);
////        intent.setAction(Intent.SERVICE)
//        startService(intent);

//        container = findViewById(R.id.container);
//        container.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        Bundle bundle = new Bundle();
//        bundle.put


//        textView = findViewById(R.id.textView);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//        builder = new AlertDialog.Builder(this);
//        defineDialog();

//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
//            alertDialog.show();
//        }else{
//            alertDialog.dismiss();
//        }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
//        }else {

//            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + " " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
//            Bundle bundle = new Bundle();
//            bundle.putString("Location","LOC:"+location);
//            FormFragment fragment = new FormFragment();
//            fragment.setArguments(bundle);

//            textView.setText(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + " " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
//            Float dis = Float.valueOf("10.0");
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    Double lat = location.getLatitude();
//                    Double lng = location.getLongitude();
////                    textView.setText("LAT: " + lat + " " + "LONG: " + lng);
//                }
//                @Override
//                public void onStatusChanged(String s, int i, Bundle bundle) { }
//                @Override
//                public void onProviderEnabled(String s) { }
//                @Override
//                public void onProviderDisabled(String s) { }
//            });
//        }
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(alertDialog !=null && !alertDialog.isShowing() && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
//            alertDialog.show();
//        }else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            alertDialog.dismiss();
//
//            Bundle bundle = new Bundle();
//            bundle.putString("Location",location);
//            FormFragment fragment = new FormFragment();
//            fragment.setArguments(bundle);
//
////            textView.setText(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + " " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
//            Float dis = Float.valueOf("10.0");
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    Double lat = location.getLatitude();
//                    Double lng = location.getLongitude();
////                    textView.setText("LAT: " + lat + " " + "LONG: " + lng);
////                    Toast.makeText(MainActivity.this, "LAT: " + lat + " " + "LONG: " + lng, Toast.LENGTH_SHORT).show();
//                }
//                @Override
//                public void onStatusChanged(String s, int i, Bundle bundle) { }
//                @Override
//                public void onProviderEnabled(String s) { }
//                @Override
//                public void onProviderDisabled(String s) { }
//            });
//        }
//    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==2){
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                alertDialog.dismiss();
//            } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
//                boolean dontAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissions[0]);
//                if(dontAskAgain){
//                    alertDialog.show();
//                }
//            }
//        }
//    }

//    private void defineDialog(){
//        builder.setTitle("Location permission required")
//                .setMessage("This app requires location permission.")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            Intent intent = new Intent();
//                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            Uri uri = Uri.parse("package:com.example.myproject");
//                            intent.setData(uri);
//                            startActivity(intent);
//                        }else {
//                            alertDialog.dismiss();
//                            textView.setText(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + " " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
//                            Float dis = Float.valueOf("10.0");
//                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
//                                @Override
//                                public void onLocationChanged(Location location) {
//                                    Double lat = location.getLatitude();
//                                    Double lng = location.getLongitude();
//                                    textView.setText("LAT: " + lat + " " + "LONG: " + lng);
//                                    Toast.makeText(MainActivity.this, "LAT: " + lat + " " + "LONG: " + lng, Toast.LENGTH_SHORT).show();
//                                }
//                                @Override
//                                public void onStatusChanged(String s, int i, Bundle bundle) { }
//                                @Override
//                                public void onProviderEnabled(String s) { }
//                                @Override
//                                public void onProviderDisabled(String s) { }
//                            });
//                        }
//                    }
//                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//            }
//        });
//        builder.setCancelable(false);
//        alertDialog = builder.create();
//    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode==0){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            }
        }*/
//    }
//
//    @Override
//    protected void onDestroy() {
//        MyIntentService service = new MyIntentService();
//        service.onDestroy();
//        super.onDestroy();
//    }


    @Override
    public void onBackPressed() {

        alertDialog.show();

//        alertDialog = builder.create();
//        super.onBackPressed();
    }

    @Override
    public void onFormFragmentInteraction() {

    }


}
