package com.suryaheho.projectb.ui.transaksi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.suryaheho.projectb.Helper.RetrofitHelper;
import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.ScanBarkodeActivity;
import com.suryaheho.projectb.ui.transaksi.Adapter.AdapterCariBarangTransaksi;
import com.suryaheho.projectb.ui.transaksi.Model.ModelCariBarang;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CariBarangActivity extends AppCompatActivity implements AdapterCariBarangTransaksi.AdapterCariBarangTransaksiCallback {

    public static final int REQUEST_BARCODE = 01;
    public static final String TAG = CariBarangActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.guideline3)
    Guideline guideline3;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_barang);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

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
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setupCariBarang(editText2.getText().toString(), "name");
                    return true;

                }
                return false;
            }
        });
    }

    private void setupCariBarang(String toString, String name) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        Single<ResponseBody> testObservable = null;

        testObservable = retrofitHelper.api().CariBarang(toString, name);

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
                            ModelCariBarang modelCariBarang = gson.fromJson(response, ModelCariBarang.class);
                            if (modelCariBarang.getData().size() > 0) {
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CariBarangActivity.this));
                                AdapterCariBarangTransaksi adapterListBarang = new AdapterCariBarangTransaksi(CariBarangActivity.this, -1, modelCariBarang.getData(), CariBarangActivity.this);
                                recyclerView.setAdapter(adapterListBarang);
                            } else {
                                Toast.makeText(CariBarangActivity.this, "Barang Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                            }
//                            Toast.makeText(CariBarangActivity.this, "Berhasil Disimpan.", Toast.LENGTH_SHORT).show();
//                            finish();

                        } catch (Exception e) {
                            Log.e(TAG, "onSuccess: Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        Toast.makeText(CariBarangActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_BARCODE) {
                editText2.setText(data.getStringExtra("BARCODE"));
                setupCariBarang(data.getStringExtra("BARCODE"), "barcode");
            }
        }
    }

    public void scanBarkode(View view) {
        Intent intent = new Intent(CariBarangActivity.this, ScanBarkodeActivity.class);
        startActivityForResult(intent, REQUEST_BARCODE);

    }


    @Override
    public void onRowAdapterCariBarangTransaksiClicked(ModelCariBarang.DataBean position) {
        Intent intent = getIntent();
        intent.putExtra("DATA", position);
        setResult(RESULT_OK, intent);
        finish();
    }
}
