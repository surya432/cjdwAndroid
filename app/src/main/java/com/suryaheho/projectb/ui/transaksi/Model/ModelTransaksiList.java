package com.suryaheho.projectb.ui.transaksi.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ModelTransaksiList implements Parcelable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * iTransaksi : 24
         * iUser : 2
         * nUser : q2
         * tglAdd : 2020-02-04 22:05:10
         * detail : [{"barcode":"6789012345","nBarang":"morfin","qtyDetail":"2","hargaDetail":"2000","totalSum":"4000"},{"barcode":"ABC-abc-1234","nBarang":"test","qtyDetail":"4","hargaDetail":"2000","totalSum":"8000"}]
         */

        private String iTransaksi;
        private String iUser;
        private String nUser;
        private String tglAdd;
        private List<DetailBean> detail;

        public String getITransaksi() {
            return iTransaksi;
        }

        public void setITransaksi(String iTransaksi) {
            this.iTransaksi = iTransaksi;
        }

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

        public String getTglAdd() {
            return tglAdd;
        }

        public void setTglAdd(String tglAdd) {
            this.tglAdd = tglAdd;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean implements Parcelable {
            /**
             * barcode : 6789012345
             * nBarang : morfin
             * qtyDetail : 2
             * hargaDetail : 2000
             * totalSum : 4000
             */

            private String barcode;
            private String nBarang;
            private String qtyDetail;
            private String hargaDetail;
            private String totalSum;

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

            public String getQtyDetail() {
                return qtyDetail;
            }

            public void setQtyDetail(String qtyDetail) {
                this.qtyDetail = qtyDetail;
            }

            public String getHargaDetail() {
                return hargaDetail;
            }

            public void setHargaDetail(String hargaDetail) {
                this.hargaDetail = hargaDetail;
            }

            public String getTotalSum() {
                return totalSum;
            }

            public void setTotalSum(String totalSum) {
                this.totalSum = totalSum;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.barcode);
                dest.writeString(this.nBarang);
                dest.writeString(this.qtyDetail);
                dest.writeString(this.hargaDetail);
                dest.writeString(this.totalSum);
            }

            public DetailBean() {
            }

            protected DetailBean(Parcel in) {
                this.barcode = in.readString();
                this.nBarang = in.readString();
                this.qtyDetail = in.readString();
                this.hargaDetail = in.readString();
                this.totalSum = in.readString();
            }

            public static final Creator<DetailBean> CREATOR = new Creator<DetailBean>() {
                @Override
                public DetailBean createFromParcel(Parcel source) {
                    return new DetailBean(source);
                }

                @Override
                public DetailBean[] newArray(int size) {
                    return new DetailBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.iTransaksi);
            dest.writeString(this.iUser);
            dest.writeString(this.nUser);
            dest.writeString(this.tglAdd);
            dest.writeList(this.detail);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.iTransaksi = in.readString();
            this.iUser = in.readString();
            this.nUser = in.readString();
            this.tglAdd = in.readString();
            this.detail = new ArrayList<DetailBean>();
            in.readList(this.detail, DetailBean.class.getClassLoader());
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
        dest.writeList(this.data);
    }

    public ModelTransaksiList() {
    }

    protected ModelTransaksiList(Parcel in) {
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ModelTransaksiList> CREATOR = new Parcelable.Creator<ModelTransaksiList>() {
        @Override
        public ModelTransaksiList createFromParcel(Parcel source) {
            return new ModelTransaksiList(source);
        }

        @Override
        public ModelTransaksiList[] newArray(int size) {
            return new ModelTransaksiList[size];
        }
    };
}
