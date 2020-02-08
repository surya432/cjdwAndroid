package com.suryaheho.projectb.ui.user.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelListUser {

    private List<DataItem> data;

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public static class DataItem implements Parcelable {
        /**
         * iUser : 1
         * nUser : q1
         * pasUser : q1
         * tglAdd : 2020-01-21 07:57:02
         */

        private String iUser;
        private String nUser;
        private String pasUser;
        private String tglAdd;

        public String getIUser() {
            return iUser;
        }

        public void setIUser(String iUser) {
            this.iUser = iUser;
        }

        public String getNUser() {
            return nUser;
        }

        public void setNUser(String nUser) {
            this.nUser = nUser;
        }

        public String getPasUser() {
            return pasUser;
        }

        public void setPasUser(String pasUser) {
            this.pasUser = pasUser;
        }

        public String getTglAdd() {
            return tglAdd;
        }

        public void setTglAdd(String tglAdd) {
            this.tglAdd = tglAdd;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.iUser);
            dest.writeString(this.nUser);
            dest.writeString(this.pasUser);
            dest.writeString(this.tglAdd);
        }

        public DataItem() {
        }

        protected DataItem(Parcel in) {
            this.iUser = in.readString();
            this.nUser = in.readString();
            this.pasUser = in.readString();
            this.tglAdd = in.readString();
        }

        public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
            @Override
            public DataItem createFromParcel(Parcel source) {
                return new DataItem(source);
            }

            @Override
            public DataItem[] newArray(int size) {
                return new DataItem[size];
            }
        };
    }
}
