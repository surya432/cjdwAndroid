package com.suryaheho.projectb.ui.transaksi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.transaksi.Model.ModelTransaksiList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterTransaksiList extends
        RecyclerView.Adapter<AdapterTransaksiList.ViewHolder> {

    private static final String TAG = AdapterTransaksiList.class.getSimpleName();


    private Context context;
    private List<ModelTransaksiList.DataBean> list;
    private AdapterTransaksiListCallback mAdapterCallback;
    private int result = -1;

    public AdapterTransaksiList(Context context, int result, List<ModelTransaksiList.DataBean> list, AdapterTransaksiListCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.result = result;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_transaksi,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelTransaksiList.DataBean item = list.get(position);
        holder.tvNoSO.setText("No Transaksi " + item.getITransaksi());
        int TotalBarang = 0;
        for (ModelTransaksiList.DataBean.DetailBean listDetail : item.getDetail()) {
            TotalBarang = TotalBarang + Integer.parseInt(listDetail.getHargaDetail());
        }
        holder.tvTotalALl.setText("Rp." + TotalBarang);
        holder.tvTotalBarang.setText("Total Barang " + item.getDetail().size());
    }

    public void addItems(List<ModelTransaksiList.DataBean> items) {
        this.list.addAll(this.list.size(), items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        } else {
            if (result >= 1) {
                return Math.min(list.size(), result);
            } else {
                return list.size();
            }
        }
    }

    public void clear() {
        int size = this.list.size();
        this.list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public interface AdapterTransaksiListCallback {
        void onRowAdapterTransaksiListClicked(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.guideline)
        Guideline guideline;
        @BindView(R.id.tvNoSO)
        TextView tvNoSO;
        @BindView(R.id.tvTotalBarang)
        TextView tvTotalBarang;
        @BindView(R.id.tvTotalALl)
        TextView tvTotalALl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}