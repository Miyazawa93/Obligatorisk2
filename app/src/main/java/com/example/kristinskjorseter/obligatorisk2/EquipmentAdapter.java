package com.example.kristinskjorseter.obligatorisk2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kristinskjorseter on 11/02/16.
 */

public class EquipmentAdapter extends ArrayAdapter<Equipment> {

    private final ArrayList<Equipment> list;
    private final Context context;

    public EquipmentAdapter(Context context, ArrayList<Equipment> list) {
        super(context, R.layout.my_list_item, list);
        this.list =  list;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup viewGroup){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View titleView = inflater.inflate(R.layout.my_list_item, viewGroup, false);


        TextView textType = (TextView)titleView.findViewById(R.id.type);
        TextView textBrand = (TextView)titleView.findViewById(R.id.brand);
        TextView textItNO = (TextView)titleView.findViewById(R.id.it_no);
        TextView textModel = (TextView)titleView.findViewById(R.id.model);

        textType.setText(list.get(position).getType());
        textBrand.setText(list.get(position).getBrand());
        textItNO.setText(list.get(position).getIt_no());
        textModel.setText(list.get(position).getModel());

        return titleView;
    }

}
