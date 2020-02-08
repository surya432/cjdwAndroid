package com.suryaheho.projectb.ui.barang.AdapterBarang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.barang.ModelBarang.ModelBarang;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterBarang extends
        RecyclerView.Adapter<AdapterBarang.ViewHolder> {

    private static final String TAG = AdapterBarang.class.getSimpleName();


    private Context context;
    private List<ModelBarang.DataBean> list;
    private AdapterBarangCallback mAdapterCallback;
    private int result = -1;

    public AdapterBarang(Context context, int result, List<ModelBarang.DataBean> list, AdapterBarangCallback adapterCallback) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelBarang.DataBean item = list.get(position);
        holder.textView2.setText(item.getNBarang());

        holder.tvHarga2.setText("Rp."+ item.getHargaBarang());
        holder.tvStok2.setText(item.getStockBarang());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onRowAdapterBarangClicked(item);
            }
        }); holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onRowAdapterBarangClickedDelete(item);
            }
        });
    }

    public void addItems(List<ModelBarang.DataBean> items) {
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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface AdapterBarangCallback {
        void onRowAdapterBarangClicked(ModelBarang.DataBean position);
        void onRowAdapterBarangClickedDelete(ModelBarang.DataBean position);
    }
}