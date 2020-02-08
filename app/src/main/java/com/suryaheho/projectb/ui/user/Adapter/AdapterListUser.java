package com.suryaheho.projectb.ui.user.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.user.Model.ModelListUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListUser extends
        RecyclerView.Adapter<AdapterListUser.ViewHolder> {

    private static final String TAG = AdapterListUser.class.getSimpleName();


    private Context context;
    private List<ModelListUser.DataItem> list;
    private AdapterListUserCallback mAdapterCallback;
    private int result = -1;

    public AdapterListUser(Context context, int result, List<ModelListUser.DataItem> list, AdapterListUserCallback adapterCallback) {
        this.context = context;
        this.list = list;
        this.result = result;
        this.mAdapterCallback = adapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_user,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelListUser.DataItem item = list.get(position);
        holder.textView2.setText(item.getNUser());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onRowAdapterListUserClicked(item);
            }
        });holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onRowAdapterListUserClickedDelete(item);
            }
        });
    }

    public void addItems(List<ModelListUser.DataItem> items) {
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

    public interface AdapterListUserCallback {
        void onRowAdapterListUserClicked(ModelListUser.DataItem position);
        void onRowAdapterListUserClickedDelete(ModelListUser.DataItem position);
    }
}