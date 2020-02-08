package com.suryaheho.projectb.ui.transaksi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.suryaheho.projectb.Helper.RetrofitHelper;
import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.barang.BarangViewModel;
import com.suryaheho.projectb.ui.transaksi.Adapter.AdapterTransaksiList;
import com.suryaheho.projectb.ui.transaksi.Model.ModelTransaksiList;
import com.suryaheho.projectb.ui.user.UserViewModel;

import butterknife.BindView;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class TransaksiFragment extends Fragment implements  AdapterTransaksiList.AdapterTransaksiListCallback {

    public static final String TAG = TransaksiFragment.class.getSimpleName();

    private TransaksiViewModel transaksiViewModel;
    private BarangViewModel barangViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private Button btnCreateUser;
    private RecyclerView.LayoutManager layoutManager;
    private TextView title;
    private ModelTransaksiList modelTransaksiList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transaksiViewModel =
                ViewModelProviders.of(this).get(TransaksiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = root.findViewById(R.id.rv_main);
        title = root.findViewById(R.id.title);
        btnCreateUser = root.findViewById(R.id.btnCreateUser);
        layoutManager = new LinearLayoutManager(getContext());
        setupListData();
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderBarangActivity.class);
                startActivity(intent);
            }
        });
        title.setText("List Transaksi");
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupListData();
    }

    private void setupListData() {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        Single<ResponseBody> testObservable = retrofitHelper.api().listTransaksi();
        testObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseBody s) {
                        try {
                            String response = s.string();
                            Log.e(TAG, "onSuccess: " + response);
                            Gson gson = new Gson();
                            ModelTransaksiList modelBarang = gson.fromJson(response.replaceAll("<!-- data User -->", ""), ModelTransaksiList.class);
                            if (modelBarang.getData().size() > 0) {
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(layoutManager);
                                AdapterTransaksiList adapterListBarang = new AdapterTransaksiList(getContext(), -1, modelBarang.getData(), TransaksiFragment.this);
                                recyclerView.setAdapter(adapterListBarang);
                            } else {
                                Toast.makeText(getContext(), "Data Tidak Ada!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onSuccess: Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onRowAdapterTransaksiListClicked(int position) {

    }
}