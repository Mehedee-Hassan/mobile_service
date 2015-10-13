package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.PTWForView;

import java.util.List;

/**
 * Created by Mhr on 10/2/2015.
 */
public class PermitToWorkActListAdapter extends BaseAdapter {

    private Activity context;
    private List<PTWForView> list;
    private LayoutInflater layoutInflater = null;


    public PermitToWorkActListAdapter(Activity context, List<PTWForView> list){

        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PTWHolder viewHolder ;

        View tempView = convertView;



        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tempView = li.inflate(R.layout.ptw_before_permit_row_layout,null);

            viewHolder = new PTWHolder(tempView);
            tempView.setTag(viewHolder);


        }
        else{
            viewHolder = (PTWHolder) tempView.getTag();
        }


        PTWForView tempObj = list.get(position);
        viewHolder.rowTextTitle.setText(tempObj.rowTextTitle);
        viewHolder.rowTextDescription.setText(tempObj.rowTextDescription);
        viewHolder.rowTextLocation.setText(tempObj.rowTextLocation);
        viewHolder.rowTextTime.setText(tempObj.rowTextTime);



        return tempView;
    }


    class PTWHolder{
        TextView rowTextTitle ;
        TextView rowTextTime ;
        TextView rowTextDescription ;
        TextView rowTextLocation ;

        public PTWHolder(View base){
            rowTextTitle = (TextView) base.findViewById(R.id.permitIdtextView2);
            rowTextTime = (TextView) base.findViewById(R.id.start_time_end_time_textView9);
            rowTextDescription = (TextView) base.findViewById(R.id.permit_workdes_textView10);
            rowTextLocation = (TextView) base.findViewById(R.id.permit_location_textView11);
        }

    }


}
