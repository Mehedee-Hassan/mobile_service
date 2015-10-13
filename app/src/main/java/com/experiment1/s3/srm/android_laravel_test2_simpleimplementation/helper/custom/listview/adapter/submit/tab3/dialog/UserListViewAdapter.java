package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter.submit.tab3.dialog;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.User;

import java.util.List;

/**
 * Created by Mhr on 10/8/2015.
 */
public class UserListViewAdapter extends ArrayAdapter<User> {

    private final List<User> userValues;
    private final Activity context;

    public UserListViewAdapter(Activity context, List<User> values) {
        super(context, R.layout.submit_act_t3_dialog_user_t1_row_layout ,values);


        this.userValues = values;
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserViewHolder userViewHolder = new UserViewHolder();;
        View rowView;


        //todo when it will be null ?
        if(convertView == null){

            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.submit_act_t3_dialog_user_t1_row_layout ,null);




            userViewHolder.userNameCheckBox = (CheckBox) rowView.findViewById(R.id.user_name_CheckBox);


            rowView.setTag(userViewHolder);
            userViewHolder.userNameCheckBox.setTag(userValues.get(position));


        }
        else {

            rowView = convertView;

            ((UserViewHolder)rowView.getTag()).userNameCheckBox.setTag(userValues.get(position));



        }

        UserViewHolder userViewHolder1 = (UserViewHolder) rowView.getTag();
        userViewHolder1.userNameCheckBox.setText((String) userValues.get(position).name);


        Log.d("YesOption", " == " + userValues.get(position).name);



        return rowView;

    }

    class UserViewHolder{


        CheckBox userNameCheckBox;



        public UserViewHolder(){}


        public UserViewHolder(View base){
            userNameCheckBox = (CheckBox) base.findViewById(R.id.user_name_CheckBox);
        }



    }

}