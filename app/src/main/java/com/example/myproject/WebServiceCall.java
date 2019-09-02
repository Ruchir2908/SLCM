package com.example.myproject;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;

/**
 * Created by BRPL2 on 28-08-2018.
 */

public class WebServiceCall {

    private final String TAG = WebServiceCall.class.getName();


    public Object SendRequest(Map<String , String> req){

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        Set<String> set = req.keySet();

        for(String key : set){
            PropertyInfo p = new PropertyInfo();
            p.setNamespace(NAMESPACE);
            p.setType(PropertyInfo.STRING_CLASS);
            p.setName(key);
            p.setValue(req.get(key));
            request.addProperty(p);
        }
        Log.i(TAG, "Request: "+request.toString());

        return getResponse(request);
    }
    public Object getResponse(SoapObject request){

        try{
            //Declare the version of the SOAP request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            //Needed to make the internet call
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
//               Thread.sleep(2000);
                //this is the actual part that will call the webservice
                androidHttpTransport.call(SOAP_ACTION, envelope);
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return null;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            // Get the SoapResult from the envelope body.
            SoapObject result = (SoapObject)envelope.bodyIn;

            if(result != null) {
                Log.i(TAG, "Result:" + result.getProperty(0).toString());
                return result;
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }


    public Object SendRequestNEW(Map<String , String> req){

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_NEW);
        Set<String> set = req.keySet();

        for(String key : set){
            PropertyInfo p = new PropertyInfo();
            p.setNamespace(NAMESPACE);
            p.setType(PropertyInfo.STRING_CLASS);
            p.setName(key);
            p.setValue(req.get(key));
            request.addProperty(p);
        }
        Log.i(TAG, "Request: "+request.toString());

        return getResponseNEW(request);
    }
    public Object getResponseNEW(SoapObject request){

        try{
            //Declare the version of the SOAP request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            //Needed to make the internet call
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {
//               Thread.sleep(2000);
                //this is the actual part that will call the webservice
                androidHttpTransport.call(SOAP_ACTION_NEW, envelope);
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return null;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            // Get the SoapResult from the envelope body.
            SoapObject result = (SoapObject)envelope.bodyIn;

            if(result != null) {
                Log.i(TAG, "Result:" + result.getProperty(0).toString());
                return result;//.getProperty(0).toString();
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
    }
}
