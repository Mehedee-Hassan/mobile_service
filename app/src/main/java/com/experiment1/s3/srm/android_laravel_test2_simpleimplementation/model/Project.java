package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mhr on 9/22/2015.
 */
public class Project implements Parcelable{

    public int id; // here id  =  server id
    public String name;

    public Project(){}

    public Project(int id ,String name){}

    public Project(Project projectAt) {
        this.id = projectAt.id;
        this.name= projectAt.name;
    }

    protected Project(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }


    public Project getProject(){
        return  this;
    }
}