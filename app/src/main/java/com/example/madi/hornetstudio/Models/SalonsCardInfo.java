package com.example.madi.hornetstudio.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SalonsCardInfo implements Parcelable{
    private String salon_name;
    private int vote_counter;
    private int sum_of_feedbacks;
    private String address;
    private String instagram;
    private ArrayList<String> services;

    private String backPoster;

    public SalonsCardInfo(){ }

    protected SalonsCardInfo(Parcel in) {
        salon_name = in.readString();
        vote_counter = in.readInt();
        sum_of_feedbacks = in.readInt();
        address = in.readString();
        instagram = in.readString();
        services = in.createStringArrayList();
        backPoster = in.readString();
    }

    public static final Creator<SalonsCardInfo> CREATOR = new Creator<SalonsCardInfo>() {
        @Override
        public SalonsCardInfo createFromParcel(Parcel in) {
            return new SalonsCardInfo(in);
        }

        @Override
        public SalonsCardInfo[] newArray(int size) {
            return new SalonsCardInfo[size];
        }
    };

    public String getBackPoster() {
        return backPoster;
    }

    public SalonsCardInfo(String salon_name, int vote_counter, int sum_of_feedbacks, String address,
                          String instagram, ArrayList<String> services, String backPoster) {
        this.salon_name = salon_name;
        this.vote_counter = vote_counter;
        this.sum_of_feedbacks = sum_of_feedbacks;
        this.address = address;
        this.instagram = instagram;
        this.services = services;
        this.backPoster = backPoster;
    }

    public String getSalon_name() {
        return salon_name;
    }

    public void setSalon_name(String salon_name) {
        this.salon_name = salon_name;
    }

    public int getVote_counter() {
        return vote_counter;
    }

    public void setVote_counter(int vote_counter) {
        this.vote_counter = vote_counter;
    }

    public int getSum_of_feedbacks() {
        return sum_of_feedbacks;
    }

    public void setSum_of_feedbacks(int sum_of_feedbacks) {
        this.sum_of_feedbacks = sum_of_feedbacks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public void setBackPoster(String backPoster) {
        this.backPoster = backPoster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(salon_name);
        dest.writeInt(vote_counter);
        dest.writeInt(sum_of_feedbacks);
        dest.writeString(address);
        dest.writeString(instagram);
        dest.writeStringList(services);
        dest.writeString(backPoster);
    }
}
