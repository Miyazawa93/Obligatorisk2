package com.example.kristinskjorseter.obligatorisk2;

/**
 * Created by kristinskjorseter on 05/02/16.
 */

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MyListFragment extends ListFragment implements OnItemClickListener {

    private ArrayList<Equipment> equipmentArrayList;
    private StringBuilder serverResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mylistfragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        downloadUsingWorkerThread();
        EquipmentAdapter equipmentAdapter = new EquipmentAdapter(getActivity(), equipmentArrayList);
        setListAdapter(equipmentAdapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
        MyDetailFragment myDetailFragment = (MyDetailFragment) getFragmentManager().findFragmentById(R.id.details);
        myDetailFragment.showDetails(equipmentArrayList.get(position));
    }



    private void downloadUsingWorkerThread() {
        Thread thread = new Thread(null, runInBackground, "Bakgrunn");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private final Runnable runInBackground = new Runnable() {
        public void run() {
            connectToServer();
        }
    };

    private void connectToServer() {

        String myURL = "http://kark.hin.no:8088/d3330log_backend/getTestEquipment";
        HttpURLConnection conn=null;
        serverResponse = new StringBuilder();

        try {
            URL url = new URL(myURL);
            conn = (HttpURLConnection) url.openConnection();    //Bruker GET som default.
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                readServerResponse(conn.getInputStream());
            } else {
                serverResponse.append("Feilmelding fra server: " + conn.getResponseMessage() + "(" + String.valueOf(responseCode) + ")");
            }

        } catch (SocketTimeoutException ste) {
            ste.printStackTrace();
            serverResponse.append("Socket timeout exception:" + ste.getMessage());
        } catch (MalformedURLException e) {
            Log.d("HTTP-test", "Malformed URL Exception.");
            serverResponse.append(e.getMessage());
        } catch (IOException e) {
            Log.d("HTTP-test", "IO Exception: " + e.getMessage());
            serverResponse.append(e.getMessage());
        } catch (Exception e) {
            Log.d("HTTP-test", "Exception: " + e.getMessage());
            serverResponse.append(e.getMessage());
        } finally {
            if (conn!=null)
                conn.disconnect();
        }

    }


    private void readServerResponse(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Gson gson = new Gson();
        equipmentArrayList = gson.fromJson(br, new TypeToken<List<Equipment>>(){}.getType());

    }

}