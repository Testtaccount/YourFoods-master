package com.trannguyentanthuan2903.yourfoods.search_user.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 10/17/2017.
 */

public class SearchStore implements Parcelable {
    String linkPhotoStore;
    String idStore;
    String name;
    double lo;
    double la;

    public SearchStore() {
        super();
    }

    public SearchStore(String linkPhotoStore, String idStore, String name, double lo, double la) {
        this.linkPhotoStore = linkPhotoStore;
        this.idStore = idStore;
        this.name = name;
        this.lo = lo;
        this.la = la;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public double getLa() {
        return la;
    }

    public void setLa(double la) {
        this.la = la;
    }

    public String getLinkPhotoStore() {
        return linkPhotoStore;
    }

    public void setLinkPhotoStore(String linkPhotoStore) {
        this.linkPhotoStore = linkPhotoStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Parceble
    public SearchStore(Parcel parcel) {
        this.linkPhotoStore = parcel.readString();
        this.name = parcel.readString();
        this.lo = parcel.readDouble();
        this.la = parcel.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(linkPhotoStore);
        dest.writeString(name);
        dest.writeDouble(lo);
        dest.writeDouble(la);

    }

    public static final Creator<SearchStore> CREATOR = new Creator<SearchStore>() {
        @Override
        public SearchStore createFromParcel(Parcel source) {
            return new SearchStore(source);
        }

        @Override
        public SearchStore[] newArray(int size) {
            return new SearchStore[size];
        }
    };

}
