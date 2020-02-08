package com.suryaheho.projectb.ui.barang.ModelBarang;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ModelBarang implements Parcelable {


    /**
     * message : OK
     * data : [{"iBarang":"1","barcode":"1234567890","nBarang":"sabu","stockBarang":"9","hargaBarang":"1000"},{"iBarang":"2","barcode":"6789012345","nBarang":"morfin","stockBarang":"18","hargaBarang":"2000"},{"iBarang":"3","barcode":"1234509876","nBarang":"ganja","stockBarang":"30","hargaBarang":"3000"},{"iBarang":"4","barcode":"ABC-abc-1234","nBarang":"test","stockBarang":"16","hargaBarang":"2000"},{"iBarang":"5","barcode":"ABC-abc-1234","nBarang":"test","stockBarang":"12","hargaBarang":"2000"}]
     */

    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * iBarang : 1
         * barcode : 1234567890
         * nBarang : sabu
         * stockBarang : 9
         * hargaBarang : 1000
         */

        private String iBarang;
        private String barcode;
        private String nBarang;
        private String stockBarang;
        private String hargaBarang;

        public String getIBarang() {
            return iBarang;
        }

        public void setIBarang(String iBarang) {
            this.iBarang = iBarang;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getNBarang() {
            return nBarang;
        }

        public void setNBarang(String nBarang) {
            this.nBarang = nBarang;
        }

        public String getStockBarang() {
            return stockBarang;
        }

        public void setStockBarang(String stockBarang) {
            this.stockBarang = stockBarang;
        }

        public String getHargaBarang() {
            return hargaBarang;
        }

        public void setHargaBarang(String hargaBarang) {
            this.hargaBarang = hargaBarang;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.iBarang);
            dest.writeString(this.barcode);
            dest.writeString(this.nBarang);
            dest.writeString(this.stockBarang);
            dest.writeString(this.hargaBarang);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.iBarang = in.readString();
            this.barcode = in.readString();
            this.nBarang = in.readString();
            this.stockBarang = in.readString();
            this.hargaBarang = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeList(this.data);
    }

    public ModelBarang() {
    }

    protected ModelBarang(Parcel in) {
        this.message = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<ModelBarang> CREATOR = new Creator<ModelBarang>() {
        @Override
        public ModelBarang createFromParcel(Parcel source) {
            return new ModelBarang(source);
        }

        @Override
        public ModelBarang[] newArray(int size) {
            return new ModelBarang[size];
        }
    };
}
