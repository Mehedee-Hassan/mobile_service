package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.database.SubmitActDraftDBHelper;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model.view.CheckList;

import java.util.List;

/**
 * Created by Mhr on 10/6/2015.
 */
public class CheckListAdapter extends ArrayAdapter<CheckList> {

    private final List<CheckList> checkListValues;
    private List<CheckList> savedCheckListDraft;
    private final Activity context;
    SubmitActDraftDBHelper draftDBHelper;
    boolean draftIsEmptye;

    public CheckListAdapter(Activity context, List<CheckList> values) {
        super(context, R.layout.check_list_row_layout ,values);


        this.checkListValues = values;
        this.context = context;


       draftDBHelper = new SubmitActDraftDBHelper(context);

       savedCheckListDraft = draftDBHelper.getCheckListTabData();


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CheckListViewHolder checkListViewHolder = new CheckListViewHolder();;
        View rowView;




        if(convertView == null){

            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.check_list_row_layout ,null);


            checkListViewHolder.questionIdTv = (TextView) rowView.findViewById(R.id.id_textView);
            checkListViewHolder.questionTv = (TextView) rowView.findViewById(R.id.question_textView);

            checkListViewHolder.yes = (ImageButton) rowView.findViewById(R.id.yes_imageButton);
            checkListViewHolder.no = (ImageButton) rowView.findViewById(R.id.no_imageButton);
            checkListViewHolder.na = (ImageButton) rowView.findViewById(R.id.na_imageButton);


            extractDraftIfExesits(position, checkListViewHolder);


            checkListViewHolder.yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CheckList checkList  = (CheckList) checkListViewHolder.yes.getTag();
                    checkList.setYesOptions(1); //1 = yes



//                    if(!checkListViewHolder.yes.isSelected()) {
                    if(checkList.yesOptions == 1) {
                        checkListViewHolder.yes.setBackgroundColor(Color.GREEN);
                        checkListViewHolder.no.setBackgroundColor(Color.WHITE);
                        checkListViewHolder.na.setBackgroundColor(Color.WHITE);


                        checkListViewHolder.yes.setSelected(true);
                        checkListViewHolder.no.setSelected(false);
                        checkListViewHolder.na.setSelected(false);


                    }
                    else {
                        checkListViewHolder.yes.setBackgroundColor(Color.WHITE);
//                        checkListViewHolder.no.setBackgroundColor(Color.WHITE);
//                        checkListViewHolder.na.setBackgroundColor(Color.WHITE);
                        checkListViewHolder.yes.setSelected(false);

                        checkListViewHolder.no.setSelected(false);
                        checkListViewHolder.na.setSelected(false);



                    }
                }
            });

            checkListViewHolder.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    CheckList checkList  = (CheckList) checkListViewHolder.no.getTag();
                    checkList.setYesOptions(2); //2 = no

//                    if(!checkListViewHolder.no.isSelected()) {
                    if(checkList.yesOptions == 2) {
                        checkListViewHolder.no.setBackgroundColor(Color.RED);
                        checkListViewHolder.yes.setBackgroundColor(Color.WHITE);
                        checkListViewHolder.na.setBackgroundColor(Color.WHITE);

                        checkListViewHolder.no.setSelected(true);
                        checkListViewHolder.na.setSelected(false);
                        checkListViewHolder.yes.setSelected(false);

                    }
                    else {
                        checkListViewHolder.no.setBackgroundColor(Color.WHITE);
//                        checkListViewHolder.yes.setBackgroundColor(Color.WHITE);
//                        checkListViewHolder.na.setBackgroundColor(Color.WHITE);

                        checkListViewHolder.no.setSelected(false);

                        checkListViewHolder.na.setSelected(false);
                        checkListViewHolder.yes.setSelected(false);

                    }
                }
            });

            checkListViewHolder.na.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CheckList checkList  = (CheckList) checkListViewHolder.na.getTag();
                    checkList.setYesOptions(3); //3 = na
//                    if(!checkListViewHolder.na.isSelected()) {
                    if(checkList.yesOptions == 3) {
                        checkListViewHolder.na.setBackgroundColor(Color.YELLOW);
                        checkListViewHolder.no.setBackgroundColor(Color.WHITE);
                        checkListViewHolder.yes.setBackgroundColor(Color.WHITE);

                        checkListViewHolder.na.setSelected(true);
                        checkListViewHolder.no.setSelected(false);
                        checkListViewHolder.yes.setSelected(false);

                    }
                    else {
                        checkListViewHolder.na.setBackgroundColor(Color.WHITE);
//                        checkListViewHolder.no.setBackgroundColor(Color.WHITE);
//                        checkListViewHolder.yes.setBackgroundColor(Color.WHITE);

                        checkListViewHolder.na.setSelected(false);
                        checkListViewHolder.no.setSelected(false);
                        checkListViewHolder.yes.setSelected(false);

                    }
                }
            });



            rowView.setTag(checkListViewHolder);
            checkListViewHolder.yes.setTag(checkListValues.get(position));
            checkListViewHolder.no.setTag(checkListValues.get(position));
            checkListViewHolder.na.setTag(checkListValues.get(position));


        }
        else {

            rowView = convertView;

            ((CheckListViewHolder)rowView.getTag()).yes.setTag(checkListValues.get(position));
            ((CheckListViewHolder)rowView.getTag()).no.setTag(checkListValues.get(position));
            ((CheckListViewHolder)rowView.getTag()).na.setTag(checkListValues.get(position));

            extractDraftIfExesits(position, checkListViewHolder);


        }

        CheckListViewHolder checkListViewHolder1 = (CheckListViewHolder) rowView.getTag();


        checkListViewHolder1.questionTv.setText((String) checkListValues.get(position).question);
        checkListViewHolder1.questionIdTv.setText(""+checkListValues.get(position).id);


        Log.d("YesOption", " == " + checkListValues.get(position).yesOptions);




        return rowView;

    }

    private void extractDraftIfExesits(int position, CheckListViewHolder checkListViewHolder) {
        if(draftIsEmptye == false){

            //=== setup back color by selection

            if(savedCheckListDraft.get(position).yesOptions == 1)
                checkListViewHolder.yes.setBackgroundColor(Color.GREEN);
            else if(savedCheckListDraft.get(position).yesOptions == 2){
                checkListViewHolder.no.setBackgroundColor(Color.GREEN);
            }
            else if(savedCheckListDraft.get(position).yesOptions == 3){
                checkListViewHolder.na.setBackgroundColor(Color.GREEN);
            }
            //=================================


        }
    }

    class CheckListViewHolder{
        TextView questionIdTv ;
        TextView questionTv ;
        ImageButton yes;
        ImageButton no;
        ImageButton na;




        public CheckListViewHolder(){}


        public CheckListViewHolder(View base){
            questionIdTv = (TextView) base.findViewById(R.id.id_textView);
            questionTv = (TextView) base.findViewById(R.id.question_textView);

            yes = (ImageButton) base.findViewById(R.id.yes_imageButton);
            no = (ImageButton) base.findViewById(R.id.no_imageButton);
            na = (ImageButton) base.findViewById(R.id.na_imageButton);
        }



    }

}
