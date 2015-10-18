package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.Permit;

import java.util.List;

/**
 * Created by Mhr on 10/2/2015.
 */
public class PermitToWorkActListAdapter extends BaseAdapter {

    private Activity context;
    private List<Permit> list;
    private LayoutInflater layoutInflater = null;


    public PermitToWorkActListAdapter(Activity context, List<Permit> list){

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
            tempView = li.inflate(R.layout.row_ptw_before_permit_layout,null);

            viewHolder = new PTWHolder(tempView);
            tempView.setTag(viewHolder);


        }
        else{
            viewHolder = (PTWHolder) tempView.getTag();
        }


        Permit tempObj = list.get(position);
        viewHolder.rowTextTitle.setText(tempObj.auto_gen_permit_no);
        viewHolder.rowTextDescription.setText(tempObj.work_activity);
        viewHolder.rowTextLocation.setText(tempObj.location);
        viewHolder.rowTextTime.setText(tempObj.start_time+" - "+tempObj.end_time);



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
