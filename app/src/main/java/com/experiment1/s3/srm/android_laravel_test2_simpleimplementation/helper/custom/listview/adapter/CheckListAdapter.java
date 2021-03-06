package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.custom.listview.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.constants.GlobalVars;
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
    int checklistSize ;
    int indexNumber ;

    GlobalVars globalVars ;
    public CheckListAdapter(Activity context, List<CheckList> values) {
        super(context, R.layout.check_list_row_layout ,values);


        this.checkListValues = values;
        this.context = context;
        this.checklistSize = this.checkListValues.size();

       draftDBHelper = new SubmitActDraftDBHelper(context);

        globalVars = (GlobalVars) context.getApplication();

        Log.d(" =========== ", globalVars.getPermit().server_permit_id+"");


        savedCheckListDraft = draftDBHelper.getCheckListTabData(  globalVars.getPermit().id);


//        savedCheckListDraft = draftDBHelper.getCheckListTabData(  globalVars.getPermit().server_permit_id);

//        Log.d(" =========== ", savedCheckListDraft.size()+"");
//        Log.d(" =========== ", savedCheckListDraft.get(0).question+"");

        //todo need to solve
        //why multiple time loading same row ?
        // temporary solution ...
        indexNumber = -(this.checkListValues.size() * 2)+1;

    }


    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        super.setNotifyOnChange(notifyOnChange);


        //todo need to solve
        //why multiple time loading same row ?
        // temporary solution ...

        indexNumber = -(this.checkListValues.size() * 2)+1;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CheckListViewHolder checkListViewHolder ;//= new CheckListViewHolder(parent);;
        View rowView;




        if(convertView == null){

            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.check_list_row_layout ,null);

            checkListViewHolder = new CheckListViewHolder(rowView);;

            checkListViewHolder.questionIdTv = (TextView) rowView.findViewById(R.id.id_textView);
            checkListViewHolder.questionTv = (TextView) rowView.findViewById(R.id.question_textView);

            checkListViewHolder.yes = (ImageButton) rowView.findViewById(R.id.yes_imageButton);
            checkListViewHolder.no = (ImageButton) rowView.findViewById(R.id.no_imageButton);
            checkListViewHolder.na = (ImageButton) rowView.findViewById(R.id.na_imageButton);




            checkListViewHolder.yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CheckList checkList  = (CheckList) checkListViewHolder.yes.getTag();
                    checkList.setYesOptions(1); //1 = yes



                    if(checkList.yesOptions == 1) {

                        checkListViewHolder.yes.setSelected(true);
                        checkListViewHolder.no.setSelected(false);
                        checkListViewHolder.na.setSelected(false);


                    }
                    else {
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

                    if(checkList.yesOptions == 2) {

                        checkListViewHolder.no.setSelected(true);
                        checkListViewHolder.na.setSelected(false);
                        checkListViewHolder.yes.setSelected(false);

                    }
                    else {


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

                    if(checkList.yesOptions == 3) {

                        checkListViewHolder.na.setSelected(true);
                        checkListViewHolder.no.setSelected(false);
                        checkListViewHolder.yes.setSelected(false);

                    }
                    else {


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

            extractDraftIfExesits(position, checkListViewHolder);

        }
        else {

            rowView = convertView;
            checkListViewHolder = new CheckListViewHolder(rowView);;

            ((CheckListViewHolder)rowView.getTag()).yes.setTag(checkListValues.get(position));
            ((CheckListViewHolder)rowView.getTag()).no.setTag(checkListValues.get(position));
            ((CheckListViewHolder)rowView.getTag()).na.setTag(checkListValues.get(position));





        }

        CheckListViewHolder checkListViewHolder1 = (CheckListViewHolder) rowView.getTag();


        checkListViewHolder1.questionTv.setText((String) checkListValues.get(position).question);
        checkListViewHolder1.questionIdTv.setText(
//                (indexNumber++)
//                checkListValues.get(position).extra_added_sno

                (position+1)
                +".");

        Log.d("index ==" , " index = " + indexNumber);

        Log.d("YesOption", " == " + checkListValues.get(position).yesOptions);




        return rowView;

    }





    private void extractDraftIfExesits(int position, CheckListViewHolder checkListViewHolder) {
        {

            //=== setup back color by selection

            Log.d(" ========= ^^^ ", ""+savedCheckListDraft.get(position).yesOptions);

            if(savedCheckListDraft.get(position).yesOptions == 1) {

                checkListViewHolder.yes.setSelected(true);
            }
            else if(savedCheckListDraft.get(position).yesOptions == 2){
                checkListViewHolder.no.setSelected(true);

            }
            else if(savedCheckListDraft.get(position).yesOptions == 3){
                checkListViewHolder.na.setSelected(true);

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
