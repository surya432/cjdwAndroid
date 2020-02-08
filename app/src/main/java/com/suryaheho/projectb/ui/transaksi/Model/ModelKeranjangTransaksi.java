package com.suryaheho.projectb.ui.transaksi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelKeranjangTransaksi implements Parcelable {
    private String idBarang;
    private String namaDetail;
    private String qtyDetail;
    private String hargaDetail;
    private String totalSumDetail;

    public ModelKeranjangTransaksi(String id, String namaDetail, String qtyDetail, String hargaDetail, String totalSumDetail) {
        this.idBarang = id;
        this.namaDetail = namaDetail;
        this.qtyDetail = qtyDetail;
        this.hargaDetail = hargaDetail;
        this.totalSumDetail = totalSumDetail;
    }

    public String getId() {
        return idBarang;
    }

    public void setId(String id) {
        this.idBarang = id;
    }

    public String getNamaDetail() {
        return namaDetail;
    }

    public void setNamaDetail(String namaDetail) {
        this.namaDetail = namaDetail;
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

    public String getTotalSumDetail() {
        return totalSumDetail;
    }

    public void setTotalSumDetail(String totalSumDetail) {
        this.totalSumDetail = totalSumDetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idBarang);
        dest.writeString(this.namaDetail);
        dest.writeString(this.qtyDetail);
        dest.writeString(this.hargaDetail);
        dest.writeString(this.totalSumDetail);
    }

    protected ModelKeranjangTransaksi(Parcel in) {
        this.idBarang = in.readString();
        this.namaDetail = in.readString();
        this.qtyDetail = in.readString();
        this.hargaDetail = in.readString();
        this.totalSumDetail = in.readString();
    }

    public static final Parcelable.Creator<ModelKeranjangTransaksi> CREATOR = new Parcelable.Creator<ModelKeranjangTransaksi>() {
        @Override
        public ModelKeranjangTransaksi createFromParcel(Parcel source) {
            return new ModelKeranjangTransaksi(source);
        }

        @Override
        public ModelKeranjangTransaksi[] newArray(int size) {
            return new ModelKeranjangTransaksi[size];
        }
    };
}
