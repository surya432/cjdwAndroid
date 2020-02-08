package com.suryaheho.projectb.ui.transaksi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.transaksi.Model.ModelKeranjangTransaksi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterKeranjangTransaksi extends
        RecyclerView.Adapter<AdapterKeranjangTransaksi.ViewHolder> {

    private static final String TAG = AdapterKeranjangTransaksi.class.getSimpleName();


    private Context context;
    private List<ModelKeranjangTransaksi> list;
    private AdapterKeranjangTransaksiCallback mAdapterCallback;
    private int result = -1;

    public AdapterKeranjangTransaksi(Context context, int result, List<ModelKeranjangTransaksi> list, AdapterKeranjangTransaksiCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.result = result;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_barang,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelKeranjangTransaksi item = list.get(position);
        holder.textView2.setText(item.getNamaDetail());
        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onRowAdapterKeranjangTransaksiClickedDelete(position);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onRowAdapterKeranjangTransaksiClickedEdit(item, position);
            }
        });
        int totalBarang = Integer.parseInt(item.getHargaDetail()) * Integer.parseInt(item.getQtyDetail());
        holder.tvHarga2.setText("Rp." + totalBarang);
        holder.tvHarga3.setVisibility(View.GONE);
        holder.tvStok.setVisibility(View.GONE);
        holder.tvStok2.setText(item.getQtyDetail() + " @ " + item.getHargaDetail());
        holder.cnstraitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: constraidLayout");
                mAdapterCallback.onRowAdapterKeranjangTransaksiClicked(item);
            }
        });
    }

    public void addItems(List<ModelKeranjangTransaksi> items) {
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

    public interface AdapterKeranjangTransaksiCallback {
        void onRowAdapterKeranjangTransaksiClicked(ModelKeranjangTransaksi position);

        void onRowAdapterKeranjangTransaksiClickedDelete(int position);

        void onRowAdapterKeranjangTransaksiClickedEdit(ModelKeranjangTransaksi position, int i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView2)
        TextView textView2;
        @BindView(R.id.tvStok)
        TextView tvStok;
        @BindView(R.id.tvHarga2)
        TextView tvHarga2;
        @BindView(R.id.tvHarga3)
        TextView tvHarga3;
        @BindView(R.id.tvStok2)
        TextView tvStok2;
        @BindView(R.id.imageButton)
        ImageButton imageButton;
        @BindView(R.id.imageButton2)
        ImageButton imageButton2;
        @BindView(R.id.guideline2)
        Guideline guideline2;
        @BindView(R.id.cnstraitLayout)
        LinearLayout cnstraitLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}