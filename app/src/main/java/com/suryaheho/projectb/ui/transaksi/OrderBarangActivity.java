package com.suryaheho.projectb.ui.transaksi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.suryaheho.projectb.Helper.RetrofitHelper;
import com.suryaheho.projectb.Helper.SessionManager;
import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.transaksi.Adapter.AdapterKeranjangTransaksi;
import com.suryaheho.projectb.ui.transaksi.Model.ModelCariBarang;
import com.suryaheho.projectb.ui.transaksi.Model.ModelKeranjangTransaksi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class OrderBarangActivity extends AppCompatActivity implements AdapterKeranjangTransaksi.AdapterKeranjangTransaksiCallback {

    public static final int REQUEST_ADD_BARANG = 01;
    public static final String TAG = OrderBarangActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.tvNamaBarang)
    TextView tvNamaBarang;
    @BindView(R.id.tvHarga)
    TextView tvHarga;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.tvTotalPembayaran)
    TextView tvTotalPembayaran;
    @BindView(R.id.btnMin)
    Button btnMin;
    @BindView(R.id.edtQty)
    EditText edtQty;
    @BindView(R.id.btnPlus)
    Button btnPlus;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btnAddKeranjang)
    ImageButton btnAddKeranjang;
    @BindView(R.id.lnPreOrder)
    LinearLayout lnPreOrder;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    private List<ModelKeranjangTransaksi> modelKeranjang = new ArrayList<>();
    private ModelCariBarang.DataBean modelCariBarangData;
    private Boolean isEditOrder = false;
    private int editPosision = 0;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_barang);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupAwal();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Jualan..");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderBarangActivity.this, CariBarangActivity.class);
                startActivityForResult(intent, REQUEST_ADD_BARANG);
            }
        });
        setupBtnQty(btnMin);
        setupBtnQty(btnPlus);
        edtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 1) {
                    if (s.equals("0")) {
                        btnMin.setEnabled(false);
                    } else if (Integer.parseInt(s.toString()) <= 1) {
                        btnMin.setEnabled(false);
                    } else if (Integer.parseInt(s.toString()) >= 1) {
                        btnMin.setEnabled(true);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnAddKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalSumDetailItem = Integer.parseInt(edtQty.getText().toString().trim()) * Integer.parseInt(modelCariBarangData.getHargaBarang());
                if (isEditOrder) {
                    modelKeranjang.set(editPosision, new ModelKeranjangTransaksi(modelCariBarangData.getIBarang(), modelCariBarangData.getNBarang(), edtQty.getText().toString(), modelCariBarangData.getHargaBarang(), String.valueOf(totalSumDetailItem)));
                } else {
                    modelKeranjang.add(new ModelKeranjangTransaksi(modelCariBarangData.getIBarang(), modelCariBarangData.getNBarang(), edtQty.getText().toString(), modelCariBarangData.getHargaBarang(), String.valueOf(totalSumDetailItem)));
                }
                setupAwal();
                setupBarangKeranjang();
            }
        });
        sessionManager = new SessionManager(this);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject order = new JSONObject();
                    order.put("createby", sessionManager.getPID());
                    JSONArray jsonArr = new JSONArray();
                    for (ModelKeranjangTransaksi m : modelKeranjang) {
                        JSONObject pnObj = new JSONObject();
                        pnObj.put("hargaDetail", m.getHargaDetail());
                        pnObj.put("idBarang", m.getId());
                        pnObj.put("namaDetail", m.getNamaDetail());
                        pnObj.put("qtyDetail", m.getQtyDetail());
                        pnObj.put("totalSumDetail", m.getTotalSumDetail());
                        jsonArr.put(pnObj);
                    }
                    order.put("detail", jsonArr);
                    Log.e(TAG, "onClick: " + order);
                    setupInsert(order.toString());
                } catch (Exception e) {
                    Log.e(TAG, "onClick: " + e.getMessage());
                }
            }
        });
    }

    private void setupInsert(String json) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        Single<ResponseBody> testObservable = null;
        testObservable = retrofitHelper.api().InsertTransaksi(json);
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
                            Toast.makeText(OrderBarangActivity.this, "Sukses Di Insert", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onSuccess: " + response);
                            finish();
//                            Gson gson = new Gson();
//                            ModelCariBarang modelCariBarang = gson.fromJson(response, ModelCariBarang.class);
//                            if (modelCariBarang.getData().size() > 0) {
//                                recyclerView.setHasFixedSize(true);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(CariBarangActivity.this));
//                                AdapterCariBarangTransaksi adapterListBarang = new AdapterCariBarangTransaksi(CariBarangActivity.this, -1, modelCariBarang.getData(), CariBarangActivity.this);
//                                recyclerView.setAdapter(adapterListBarang);
//                            } else {
//                                Toast.makeText(CariBarangActivity.this, "Barang Tidak Ditemukan", Toast.LENGTH_SHORT).show();
//                            }
//                            Toast.makeText(CariBarangActivity.this, "Berhasil Disimpan.", Toast.LENGTH_SHORT).show();
//                            finish();

                        } catch (Exception e) {
                            Log.e(TAG, "onSuccess: Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        Toast.makeText(OrderBarangActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupBtnQty(final Button btnAction) {
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMin.setEnabled(true);
                btnPlus.setEnabled(true);
                if (Integer.parseInt(edtQty.getText().toString().trim()) <= 0) {
                    btnMin.setEnabled(false);
                    btnPlus.setEnabled(true);
                } else {
                    if (btnAction.getId() == btnPlus.getId()) {
                        if (Integer.parseInt(edtQty.getText().toString().trim()) >= Integer.parseInt(modelCariBarangData.getStockBarang())) {
                            btnMin.setEnabled(true);
                            btnPlus.setEnabled(false);
                            Toast.makeText(OrderBarangActivity.this, "Barang TIdak mencukupi!", Toast.LENGTH_SHORT).show();
                        } else {
                            edtQty.setText(Integer.parseInt(edtQty.getText().toString().trim()) + 1 + "");
                        }
                    } else {
                        edtQty.setText(Integer.parseInt(edtQty.getText().toString().trim()) - 1 + "");
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_ADD_BARANG) {
                modelCariBarangData = data.getParcelableExtra("DATA");
                tvNamaBarang.setText(modelCariBarangData.getNBarang());
                tvHarga.setText("Rp. " + modelCariBarangData.getHargaBarang());
                edtQty.setText("1");
                lnPreOrder.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupAwal() {
        lnPreOrder.setVisibility(View.GONE);
        textView9.setVisibility(View.GONE);
        tvTotalPembayaran.setVisibility(View.GONE);
        btnSimpan.setVisibility(View.GONE);
        isEditOrder = false;
        setupBarangKeranjang();
    }

    private void setupBarangKeranjang() {
        if (modelKeranjang.isEmpty()) {
            Log.e(TAG, "setupBarangKeranjang: data null " + isEditOrder);
        } else {
            int totalPembayaran = 0;
            for (ModelKeranjangTransaksi m : modelKeranjang) {
                totalPembayaran = totalPembayaran + Integer.parseInt(m.getTotalSumDetail());
            }
            Log.e(TAG, "setupBarangKeranjang: " + totalPembayaran);
            tvTotalPembayaran.setText("Rp." + totalPembayaran);
            btnSimpan.setVisibility(View.VISIBLE);
            textView9.setVisibility(View.VISIBLE);
            tvTotalPembayaran.setVisibility(View.VISIBLE);
            rvMain.setHasFixedSize(true);
            rvMain.setLayoutManager(new LinearLayoutManager(OrderBarangActivity.this));
            AdapterKeranjangTransaksi adapterListBarang = new AdapterKeranjangTransaksi(OrderBarangActivity.this, -1, modelKeranjang, OrderBarangActivity.this);
            rvMain.setAdapter(adapterListBarang);
        }
    }

    public void btnCariBarang(View view) {
        Intent intent = new Intent(getApplicationContext(), CariBarangActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRowAdapterKeranjangTransaksiClicked(ModelKeranjangTransaksi position) {

    }

    @Override
    public void onRowAdapterKeranjangTransaksiClickedDelete(int position) {
        modelKeranjang.remove(position);
        setupBarangKeranjang();
    }

    @Override
    public void onRowAdapterKeranjangTransaksiClickedEdit(ModelKeranjangTransaksi position, int Posision) {
        tvNamaBarang.setText(position.getNamaDetail());
        editPosision = Posision;
        isEditOrder = true;
        tvHarga.setText("Rp. " + position.getHargaDetail());
        edtQty.setText(position.getQtyDetail());
        lnPreOrder.setVisibility(View.VISIBLE);
    }
}
