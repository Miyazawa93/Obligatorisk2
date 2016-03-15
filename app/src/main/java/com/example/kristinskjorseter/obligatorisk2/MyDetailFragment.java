package com.example.kristinskjorseter.obligatorisk2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kristinskjorseter on 09/02/16.
 */
public class MyDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            return inflater.inflate(R.layout.mydetailfragment, container, false);
    }
    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void showDetails(Equipment equipment) {

        View viewDetails = getActivity().findViewById(R.id.details);


        TextView textType = (TextView)viewDetails.findViewById(R.id.type);
        TextView textBrand = (TextView)viewDetails.findViewById(R.id.brand);
        TextView textItNO = (TextView)viewDetails.findViewById(R.id.it_no);
        TextView textModel = (TextView)viewDetails.findViewById(R.id.model);
        TextView textDescription = (TextView)viewDetails.findViewById(R.id.description);
        TextView textAcquired = (TextView)viewDetails.findViewById(R.id.equired);

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageView);

        textType.setText("Type: " + equipment.getType());
        textBrand.setText("Brand: " + equipment.getBrand());
        textItNO.setText("Item-number: " + equipment.getIt_no());
        textModel.setText("Model: " + equipment.getModel());
        textDescription.setText("Description: " + equipment.getDescription());
        textAcquired.setText("Acquired: " + equipment.getAquired());

        new ImageDownloader(equipment.getImage_url(), imageView).execute();

    }
}