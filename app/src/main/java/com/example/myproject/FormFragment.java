package com.example.myproject;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.DynamicsProcessing;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.myproject.MainActivity.locationManager;

public class FormFragment extends Fragment {

    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.areaTitleTextView)
    TextView areaTitleTextView;
    @BindView(R.id.cCentreTitleTextView)
    TextView cCentreTitleTextView;
    @BindView(R.id.cCategoryTitleTextView)
    TextView cCategoryTitleTextView;
    @BindView(R.id.cCategoryTextView)
    TextView cCategoryTextView;
    @BindView(R.id.typeTitleTextView)
    TextView typeTitleTextView;
    @BindView(R.id.callerNameTitleTextView)
    TextView callerNameTitleTextView;
    @BindView(R.id.callerAddTitleTextView)
    TextView callerAddTitleTextView;
    @BindView(R.id.divisionTitleTextView)
    TextView divisionTitleTextView;
    @BindView(R.id.ccPhoneTitleTextView)
    TextView ccPhoneTitleTextView;
    @BindView(R.id.callerPhoneTitleTextView)
    TextView callerPhoneTitleTextView;
    @BindView(R.id.alternatePhoneTitleTextView)
    TextView alternatePhoneTitleTextView;
    @BindView(R.id.remarksTitleTextView)
    TextView remarksTitleTextView;
    @BindView(R.id.locationTitleTextView)
    TextView locationTitleTextView;
    @BindView(R.id.locationTextView)
    TextView locationTextView;
    @BindView(R.id.imageTitleTextView)
    TextView imageTitleTextView;
    @BindView(R.id.cCentreTextView)
    TextView cCentreTextView;
    @BindView(R.id.divisionTextView)
    TextView divisionTextView;
    @BindView(R.id.ccPhoneTextView)
    TextView ccPhoneTextView;
    @BindView(R.id.areaTextView)
    TextView areaTextView;
    @BindView(R.id.signTitleTextView)
    TextView signTitleTextView;

    @BindView(R.id.typeSpinner)
    Spinner typeSpinner;

    @BindView(R.id.callerNameEditText)
    EditText callerNameEditText;
    @BindView(R.id.callerAddEditText)
    EditText callerAddEditText;
    @BindView(R.id.callerPhoneEditText)
    EditText callerPhoneEditText;
    @BindView(R.id.alternatePhoneEditText)
    EditText alternatePhoneEditText;
    @BindView(R.id.remarksEditText)
    EditText remarksEditText;

    @BindView(R.id.cameraImageView)
    ImageView cameraImageView;
    @BindView(R.id.signImageView)
    ImageView signImageView;

    @BindView(R.id.signButton)
    Button signButton;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.resetButton)
    Button resetButton;
    @BindView(R.id.cameraButton)
    Button cameraButton;

    @BindView(R.id.linearParent)
    LinearLayout linearParent;

//    LocationManager locationManager;
    AlertDialog alertDialog, emptyFieldDialog;
    private AlertDialog.Builder builder, emptyFieldBuilder;

    DBHelper helper;
    SQLiteDatabase database;

    boolean dontAskAgain = false;
    String[] permissions = { Manifest.permission.ACCESS_FINE_LOCATION };
    Context context;
    Response responseXY;
    ResponseFaultCat responseFaultCat;

    String lat, lng;
    boolean calledXY = true;
    boolean calledFaultCat = true;

    ArrayList<String> category;
    ArrayList<ResponseFaultCat> types;
    Map<String,String> mapXY;
    Map<String,String> mapFaultCat;

//    String location;

    FormFragmentCallBack listener;

    public void findById(View view){
        titleTextView = view.findViewById(R.id.titleTextView);
        areaTitleTextView = view.findViewById(R.id.areaTitleTextView);
        cCentreTitleTextView = view.findViewById(R.id.cCentreTitleTextView);
        cCategoryTitleTextView = view.findViewById(R.id.cCategoryTitleTextView);
        cCategoryTextView = view.findViewById(R.id.cCategoryTextView);
        typeTitleTextView = view.findViewById(R.id.typeTitleTextView);
        callerNameTitleTextView = view.findViewById(R.id.callerNameTitleTextView);
        callerAddTitleTextView = view.findViewById(R.id.callerAddTitleTextView);
        imageTitleTextView = view.findViewById(R.id.imageTitleTextView);
        divisionTitleTextView = view.findViewById(R.id.divisionTitleTextView);
        ccPhoneTitleTextView = view.findViewById(R.id.ccPhoneTitleTextView);
        callerPhoneTitleTextView = view.findViewById(R.id.callerPhoneTitleTextView);
        alternatePhoneTitleTextView = view.findViewById(R.id.alternatePhoneTitleTextView);
        remarksTitleTextView = view.findViewById(R.id.remarksTitleTextView);
        locationTitleTextView = view.findViewById(R.id.locationTitleTextView);
        locationTextView = view.findViewById(R.id.locationTextView);
        cCentreTextView = view.findViewById(R.id.cCentreTextView);
        typeSpinner = view.findViewById(R.id.typeSpinner);
        areaTextView = view.findViewById(R.id.areaTextView);
        callerNameEditText = view.findViewById(R.id.callerNameEditText);
        callerAddEditText = view.findViewById(R.id.callerAddEditText);
        divisionTextView = view.findViewById(R.id.divisionTextView);
        ccPhoneTextView = view.findViewById(R.id.ccPhoneTextView);
        callerPhoneEditText = view.findViewById(R.id.callerPhoneEditText);
        alternatePhoneEditText = view.findViewById(R.id.alternatePhoneEditText);
        remarksEditText = view.findViewById(R.id.remarksEditText);
        cameraImageView = view.findViewById(R.id.cameraImageView);
        signButton = view.findViewById(R.id.signButton);
        registerButton = view.findViewById(R.id.registerButton);
        resetButton = view.findViewById(R.id.resetButton);
        cameraButton = view.findViewById(R.id.cameraButton);
    }

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {


        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Street Light Complaint");
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        super.onAttach(context);
        this.context = context;

        helper = DBHelper.getInstance(getContext());
        database = helper.getReadableDatabase();

//        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.CONSUMER_TABLE_NAME,null,null);

        types = new ArrayList<>();
        category = new ArrayList<>();

        builder = new AlertDialog.Builder(getContext());
        emptyFieldBuilder = new AlertDialog.Builder(getContext());

        defineDialog();
        defineEmptyFieldDialog();

//        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

//        this.locationManager = locationManager;

        responseXY = new Response();
        responseFaultCat = new ResponseFaultCat();

        mapXY = new HashMap<>();
        mapFaultCat = new HashMap<>();

        mapXY.put("Y","28.532391");//28.532391 - lat
        mapXY.put("X","77.276828");//77.276828 - lng

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            dontAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(((MainActivity)getActivity()),permissions[0]);
            if(dontAskAgain){
                alertDialog.show();
            }else
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }else{
            alertDialog.dismiss();
            lat = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + "";
            lng = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude() +"";
            if(calledXY){
                mapXY.put("Y","28.532391");//28.532391 - lat
                mapXY.put("X","77.276828");//77.276828 - lng
                new MyAsyncTaskXY().execute(mapXY);
                calledXY = false;
            }
            if(calledFaultCat){
                new MyAsyncTaskFaultCat().execute(mapFaultCat);
                calledFaultCat = false;
            }


//            locationTextView.setText("LATITUDE: " + lat + "\n" + "LONGITUDE: " + lng);
            Float dis = Float.valueOf("10.0");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lat = location.getLatitude()+"";
                    lng = location.getLongitude()+"";
                    locationTextView.setText("LAT: " + lat + " " + "LONG: " + lng);
                    if(calledXY){
                        mapXY.put("Y","28.532391");//28.532391 - lat
                        mapXY.put("X","77.276828");//77.276828 - lng
                        new MyAsyncTaskXY().execute(mapXY);
                        calledXY = false;
                    }
                    if(calledFaultCat){
                        new MyAsyncTaskFaultCat().execute(mapFaultCat);
                        calledFaultCat = false;
                    }
                }
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) { }
                @Override
                public void onProviderEnabled(String s) { }
                @Override
                public void onProviderDisabled(String s) { }
            });
        }

        Log.i("DATA","SIZE: "+types.size());

    }

    private class MyAsyncTaskXY extends AsyncTask<Map<String,String>,Void,Object> {

        ProgressDialog progressDialog;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.create();
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        @Override
        protected Object doInBackground(Map<String, String>... maps) {
            return new WebServiceCall().SendRequestNEW(maps[0]);
        }

        @Override
        protected void onPostExecute(Object resp) {
            super.onPostExecute(resp);
            progressDialog.dismiss();

            SoapObject soapObject = (SoapObject) resp;
            SoapObject object = (SoapObject) soapObject.getProperty(0);
            SoapObject diffgram = (SoapObject) object.getProperty("diffgram");
            SoapObject NewDataSet = (SoapObject) diffgram.getProperty("NewDataSet");
            SoapObject Table1 = (SoapObject) NewDataSet.getProperty("Table1");
            responseXY.setObjectId(Table1.getProperty("OBJECTID").toString());
            responseXY.setFacilityId(Table1.getProperty("FACILITYID").toString());
            responseXY.setComplaintCentre(Table1.getProperty("COMPLAINT_CENTRE").toString());//cc
            responseXY.setContactInf(Table1.getProperty("CONTACTINF").toString());
            responseXY.setDivCode(Table1.getProperty("DIV_CD").toString());
            responseXY.setDivName(Table1.getProperty("DIV_NAME").toString());//division
            responseXY.setName(Table1.getProperty("NAME").toString());//area
            responseXY.setComPhoneNo(Table1.getProperty("Com_PhoneNo").toString());//cc phone

            cCentreTextView.setText(responseXY.getComplaintCentre());
            divisionTextView.setText(responseXY.getDivName());
            areaTextView.setText(responseXY.getName());
            ccPhoneTextView.setText(responseXY.getComPhoneNo());
            cCategoryTextView.setText("STREET LIGHT");

        }
    }

    private class MyAsyncTaskFaultCat extends AsyncTask<Map<String,String>, Void, Object>{

        ProgressDialog progressDialog;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.create();
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        @Override
        protected Object doInBackground(Map<String, String>... maps) {
            return new WebServiceCall().SendRequest(maps[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Object resp) {
            super.onPostExecute(resp);
            progressDialog.dismiss();
            SoapObject soapObject = (SoapObject) resp;
            SoapObject object = (SoapObject) soapObject.getProperty(0);
            SoapObject diffgram = (SoapObject) object.getProperty("diffgram");
            SoapObject NewDataSet = (SoapObject) diffgram.getProperty("NewDataSet");
            Log.i("DATA","NDS: "+NewDataSet.toString());
//            SoapObject Table1 = (SoapObject) NewDataSet.getProperty("Table1");
            for(int i=0;i<NewDataSet.getPropertyCount();i++){
                SoapObject Table1 = (SoapObject) NewDataSet.getProperty(i);
                ResponseFaultCat responseFaultCat = new ResponseFaultCat();
                responseFaultCat.setID(Table1.getProperty("ID").toString());
                responseFaultCat.setCategory(Table1.getProperty("CATEGORY").toString());
                Log.i("DATA","WEB: "+responseFaultCat.getCategory());
                types.add(responseFaultCat);
                Log.i("DATA","AL: "+types.get(i).getCategory());
            }
            Log.i("DATA","SIZE: "+types.size());
            category.add("Select an option...");
            for(int i=0;i<types.size();i++){
                Log.i("DATA","AL1: "+types.get(i).getCategory());
                category.add(types.get(i).getCategory());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,category){
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view;
                    if(position==0){
                        TextView textView = new TextView(getContext());
                        textView.setVisibility(View.GONE);
                        textView.setHeight(0);
                        view = textView;
                    }else{
                        view = super.getDropDownView(position,null,parent);
                    }
                    return view;
                }
            };
            typeSpinner.setAdapter(adapter);

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(getArguments()!=null){
//            location = getArguments().getString("Location");
//        }

        if(calledXY){
            mapXY.put("Y","28.532391");//28.532391
            mapXY.put("X","77.276828");//77.276828
            new MyAsyncTaskXY().execute(mapXY);
            calledXY = false;
        }
        if(calledFaultCat){
            new MyAsyncTaskFaultCat().execute(mapFaultCat);
            calledFaultCat = false;
        }

    }

    /*@RequiresApi(api = Build.VERSION_CODES.M)*/
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View output = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.bind(this,output);

       /* NotificationChannel channel = null;
        NotificationManager notificationManager = null;
        notificationManager = getContext().getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("MyOwnChannel", "MyNotificationIs", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(getContext(),"MyOwnChannel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My Notification")
                .setContentText("Notification for sound")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(1,nBuilder.build());

        types = new ArrayList<>();
        types.add("Select an option");
        for(int i=0;i<10;i++){
            types.add("TYPE NUMBER: "+(i+1));
        }



        findById(output);







        callerNameEditText.setCursorVisible(false);
        callerAddEditText.clearFocus();
        callerPhoneEditText.clearFocus();
        alternatePhoneEditText.clearFocus();
        remarksEditText.clearFocus();



        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }else {
            locationTextView.setText(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + " " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
            Float dis = Float.valueOf("10.0");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Double lat = location.getLatitude();
                    Double lng = location.getLongitude();
                    locationTextView.setText("LAT: " + lat + " " + "LONG: " + lng);
                }
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) { }
                @Override
                public void onProviderEnabled(String s) { }
                @Override
                public void onProviderDisabled(String s) { }
            });
        }*/

        typeTitleTextView.setText(Html.fromHtml("Type of Fault: " + "<font color=\"#ff0000\">" + "* " + "</font>"));
        callerNameTitleTextView.setText(Html.fromHtml("Caller Name: " + "<font color=\"#ff0000\">" + "* " + "</font>"));
        callerAddTitleTextView.setText(Html.fromHtml("Caller Address: " + "<font color=\"#ff0000\">" + "* " + "</font>"));
        callerPhoneTitleTextView.setText(Html.fromHtml("Caller Phone: " + "<font color=\"#ff0000\">" + "* " + "</font>"));

//        areaTextView.setText("String");

//        cCategoryTextView.setText("String");

//        divisionTextView.setText("String");

//        ccPhoneTextView.setText("String");

//        cCentreTextView.setText("String");

//        typeAdapter = ArrayAdapter.createFromResource(getContext(),typeOfFault,R.layout.support_simple_spinner_dropdown_item);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getContext().getPackageManager())!=null){
                    startActivityForResult(intent,1);
                }
            }
        });

        signButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                /*MyIntentService service = new MyIntentService();
                MyService service = new MyService();
                Intent intent = new Intent(getContext(),MyIntentService.class);
                Log.i("SONG", "Status Button");
//                if (!isMyServiceRunning(MyIntentService.class)) {
                    getContext().startService(intent);
                }
                Intent intent = new Intent(getContext(), MyIntentService2.class);
                getContext().startService(intent);


                final MyService2.MyBinder[] binder = new MyService2.MyBinder[1];
                Intent intent1 = new Intent(getContext(),MyService2.class);
                ServiceConnection connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        binder[0] = (MyService2.MyBinder)iBinder;
                        Log.i("TAG","Here");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {

                    }
                };
                if(binder!=null){

                }
                getContext().bindService(intent1,connection,getContext().BIND_AUTO_CREATE);
                getContext().startService(intent1);*/


//                Toast.makeText(context, "Status page !!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,SignActivity.class);
                startActivityForResult(intent,2);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callerNameEditText.getText().toString().length()==0 || callerAddEditText.getText().toString().length()==0 ||
                        callerPhoneEditText.getText().toString().length()==0 || typeSpinner.getSelectedItemPosition()==0) {
                    if(callerNameEditText.getText().toString().length()==0){
                        Toast.makeText(getContext(), "Caller Name cannot be empty", Toast.LENGTH_SHORT).show();
                        callerNameEditText.setHint("Please enter name");
                        emptyFieldDialog.show();
                    }
                    if(callerAddEditText.getText().toString().length()==0){
                        Toast.makeText(getContext(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
                        callerAddEditText.setHint("Please enter address");
                        emptyFieldDialog.show();
                    }
                    if(callerPhoneEditText.getText().toString().length()==0){
                        Toast.makeText(getContext(), "Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
                        callerPhoneEditText.setHint("Please enter number");
                        emptyFieldDialog.show();
                    }
                    if(typeSpinner.getSelectedItemPosition()==0){
                        Toast.makeText(getContext(), "Spinner cannot be empty", Toast.LENGTH_SHORT).show();
                        emptyFieldDialog.show();
                    }
                }else{
                    AlertDialog.Builder registeredDialog = new AlertDialog.Builder(getContext());
                    registeredDialog.setTitle("Complaint registered successfully.")
                            .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    getActivity().getSupportFragmentManager().popBackStack();
                                    getActivity().finish();
                                }
                            });
                    registeredDialog.create();
                    registeredDialog.show();
                }


            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callerNameEditText.setText("");
                callerAddEditText.setText("");
                callerPhoneEditText.setText("");
                alternatePhoneEditText.setText("");
                remarksEditText.setText("");
                cameraImageView.setVisibility(View.GONE);
                imageTitleTextView.setVisibility(View.GONE);
                locationTextView.setText("UNABLE TO FETCH");

                typeSpinner.setSelection(0);

                linearParent.setFocusableInTouchMode(true);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                }else {
                    locationTextView.setText("LATITUDE: " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + "\n" + "LONGITUDE: " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
                    Float dis = Float.valueOf("10.0");
                    new MyAsyncTaskXY().execute(mapXY);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Double lat = location.getLatitude();
                            Double lng = location.getLongitude();
                            locationTextView.setText("LAT: " + lat + " " + "LONG: " + lng);
//                            new MyAsyncTask().execute(map);
                        }
                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) { }
                        @Override
                        public void onProviderEnabled(String s) { }
                        @Override
                        public void onProviderDisabled(String s) { }
                    });
                }
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container,new FormFragment()).commit();
            }
        });

        return output;
    }

    /*private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onDestroy() {
        Log.i("SONG", "FonDesroy");
        MyIntentService service = new MyIntentService();
        service.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(alertDialog !=null && !alertDialog.isShowing() && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            dontAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(((MainActivity)getActivity()),permissions[0]);
            if(dontAskAgain){
                alertDialog.show();
            }else
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }else if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            alertDialog.dismiss();
            locationTextView.setText(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + " " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
            Float dis = Float.valueOf("10.0");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Double lat = location.getLatitude();
                    Double lng = location.getLongitude();
                    locationTextView.setText("LAT: " + lat + " " + "LONG: " + lng);
                }
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) { }
                @Override
                public void onProviderEnabled(String s) { }
                @Override
                public void onProviderDisabled(String s) { }
            });
        }

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            dontAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(((MainActivity)getActivity()),permissions[0]);
            if(dontAskAgain){
                alertDialog.show();
            }else
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }else{
            alertDialog.dismiss();
            locationTextView.setText("LATITUDE: " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + "\n" + "LONGITUDE: " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
            Float dis = Float.valueOf("10.0");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Double lat = location.getLatitude();
                    Double lng = location.getLongitude();
                    locationTextView.setText("LAT: " + lat + " " + "LONG: " + lng);
                }
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) { }
                @Override
                public void onProviderEnabled(String s) { }
                @Override
                public void onProviderDisabled(String s) { }
            });
        }

        remarksEditText.clearFocus();
        remarksEditText.setFocusable(false);

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!=null){
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                cameraImageView.setVisibility(View.VISIBLE);
                imageTitleTextView.setVisibility(View.VISIBLE);
                Bitmap img = (Bitmap)bundle.get("data");
                cameraImageView.setImageBitmap(img);
            }
        }
        if(requestCode==2 && data!=null){
//            Bundle bundle = data.getExtras();
//            if(bundle!=null){
//                signImageView.setVisibility(View.VISIBLE);
//                signTitleTextView.setVisibility(View.VISIBLE);
//                Bitmap img = (Bitmap)bundle.get("data");
//                signImageView.setImageBitmap(img);
//            }
            signImageView.setVisibility(View.VISIBLE);
            signTitleTextView.setVisibility(View.VISIBLE);
            byte[] byteArray = data.getByteArrayExtra("data");
            Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            signImageView.setImageBitmap(img);
        }
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                alertDialog.dismiss();
            } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                dontAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(((MainActivity)getActivity()),permissions[0]);
                if(dontAskAgain){
                    alertDialog.show();
                }else
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().finish();
    }

    private void defineEmptyFieldDialog(){
        emptyFieldBuilder.setTitle("Required fields")
                .setMessage(Html.fromHtml("Please fill the fields marked " + "<font color=\"#ff0000\">" + "* " + "</font>"))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        emptyFieldDialog.dismiss();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callerNameEditText.setText("");
                callerAddEditText.setText("");
                callerPhoneEditText.setText("");
                alternatePhoneEditText.setText("");
                remarksEditText.setText("");
                cameraImageView.setVisibility(View.GONE);
                imageTitleTextView.setVisibility(View.GONE);
            }
        });
        emptyFieldDialog = emptyFieldBuilder.create();
    }

    private void defineDialog(){
        builder.setTitle("Location permission required")
                .setMessage("This app requires location permission.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.parse("package:com.example.myproject");
                            intent.setData(uri);
                            startActivity(intent);
                        }else {
                            alertDialog.dismiss();
                            locationTextView.setText("LATITUDE: " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() + "\n" + "LONGITUDE: " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
                            Float dis = Float.valueOf("10.0");
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, dis, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    Double lat = location.getLatitude();
                                    Double lng = location.getLongitude();
                                    locationTextView.setText("LAT: " + lat + " " + "LONG: " + lng);
                                    Toast.makeText(getContext(), "LAT: " + lat + " " + "LONG: " + lng, Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onStatusChanged(String s, int i, Bundle bundle) { }
                                @Override
                                public void onProviderEnabled(String s) { }
                                @Override
                                public void onProviderDisabled(String s) { }
                            });
                        }
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        });
        builder.setCancelable(false);
        alertDialog = builder.create();
    }

    public interface FormFragmentCallBack {
        void onFormFragmentInteraction();
    }
}
