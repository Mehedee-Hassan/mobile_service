package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;

import java.util.List;

/**
 * Created by Mhr on 9/29/2015.
 */
public class ProjectActivityListViewAdapter extends BaseAdapter {

    private Activity context;
    private List<String> list;
    private LayoutInflater layoutInflater = null;


    public ProjectActivityListViewAdapter(Activity context , List<String> list){

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

        ProjectListViewHolder viewHolder ;

        View tempView = convertView;

        //ProjectListViewHolder projectListViewHolder = new ProjectListViewHolder(tempView);


        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tempView = li.inflate(R.layout.project_list_row_layout,null);

            viewHolder = new ProjectListViewHolder(tempView);
            tempView.setTag(viewHolder);


        }
        else{
            viewHolder = (ProjectListViewHolder) tempView.getTag();
        }

        viewHolder.rowText.setText(list.get(position));



        return tempView;
    }


    class ProjectListViewHolder{
        TextView rowText ;

        public ProjectListViewHolder(View base){
            rowText = (TextView) base.findViewById(R.id.textview_rowLayout);
        }

    }


}
