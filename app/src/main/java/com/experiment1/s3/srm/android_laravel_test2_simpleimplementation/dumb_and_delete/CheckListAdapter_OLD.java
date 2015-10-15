package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.dumb_and_delete;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.CheckList;

import java.util.List;

/**
 * Created by Mhr on 10/3/2015.
 */
public class CheckListAdapter_OLD extends BaseAdapter {

    private Activity context;
    private List<CheckList> list;
    private LayoutInflater layoutInflater = null;
    CheckListViewHolder viewHolder ;

    public CheckListAdapter_OLD(Activity context, List<CheckList> list){

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



        View tempView = convertView;



        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tempView = li.inflate(R.layout.check_list_row_layout,null);

            viewHolder = new CheckListViewHolder(tempView);
            tempView.setTag(viewHolder);


        }
        else{
            viewHolder = (CheckListViewHolder) tempView.getTag();
        }


        final CheckList tempObj = list.get(position);

        viewHolder.questionIdTv.setText(tempObj.id+"");
        viewHolder.questionTv.setText(tempObj.question);



        viewHolder.na.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(viewHolder.na.isSelected() == false) {
                    viewHolder.na.setBackgroundColor(Color.GREEN);
                    viewHolder.na.setSelected(true);
                    viewHolder.yes.setSelected(false);
                    viewHolder.no.setSelected(false);
                    Log.d("==", "click na");

                    tempObj.yesOptions = 3;
                }
                else{
                    viewHolder.na.setBackgroundColor(Color.GRAY);
                    viewHolder.na.setSelected(false);
                    viewHolder.yes.setSelected(false);
                    viewHolder.no.setSelected(false);
                    Log.d("==", "click na");

                    tempObj.yesOptions = 1;
                }
            }
        });


        viewHolder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewHolder.no.setBackgroundColor(Color.GREEN);
                viewHolder.na.setColorFilter(Color.argb(255, 255, 255, 255));

                viewHolder.na.setSelected(false);
                viewHolder.yes.setSelected(false);
                viewHolder.no.setSelected(true);


                Log.d("==", "click no");
                tempObj.yesOptions = 2;
            }
        });


        viewHolder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.yes.setBackgroundColor(Color.GREEN);
                viewHolder.na.setSelected(false);
                viewHolder.yes.setSelected(true);
                viewHolder.no.setSelected(false);
                Log.d("==","click yes");

                tempObj.yesOptions = 1;
            }
        });


        return tempView;
    }


    class CheckListViewHolder{
        TextView questionIdTv ;
        TextView questionTv ;
        ImageButton yes;
        ImageButton no;
        ImageButton na;




        public CheckListViewHolder(View base){
            questionIdTv = (TextView) base.findViewById(R.id.id_textView);
            questionTv = (TextView) base.findViewById(R.id.question_textView);

            yes = (ImageButton) base.findViewById(R.id.yes_imageButton);
            no = (ImageButton) base.findViewById(R.id.no_imageButton);
            na = (ImageButton) base.findViewById(R.id.na_imageButton);
        }



        //bit crazy




    }


}
