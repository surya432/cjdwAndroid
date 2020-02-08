package com.suryaheho.projectb.ui.transaksi.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ModelCariBarang implements Parcelable {

    /**
     * message : OK
     * data : [{"iBarang":"2","barcode":"6789012345","nBarang":"morfin","stockBarang":"20","hargaBarang":"20000"}]
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
        public DataBean(String iBarang, String barcode, String nBarang, String stockBarang, String hargaBarang) {
            this.iBarang = iBarang;
            this.barcode = barcode;
            this.nBarang = nBarang;
            this.stockBarang = stockBarang;
            this.hargaBarang = hargaBarang;
        }

        /**
         * iBarang : 2
         * barcode : 6789012345
         * nBarang : morfin
         * stockBarang : 20
         * hargaBarang : 20000
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

    public ModelCariBarang() {
    }

    protected ModelCariBarang(Parcel in) {
        this.message = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ModelCariBarang> CREATOR = new Parcelable.Creator<ModelCariBarang>() {
        @Override
        public ModelCariBarang createFromParcel(Parcel source) {
            return new ModelCariBarang(source);
        }

        @Override
        public ModelCariBarang[] newArray(int size) {
            return new ModelCariBarang[size];
        }
    };
}
