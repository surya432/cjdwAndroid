package com.suryaheho.projectb.ui.barang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.suryaheho.projectb.Helper.RetrofitHelper;
import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.ScanBarkodeActivity;
import com.suryaheho.projectb.ui.barang.ModelBarang.ModelBarang;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FormBarangActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_BARCODE = 12;
    public static final String TAG = FormBarangActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.editText4)
    EditText editText4;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.editText5)
    EditText editText5;
    @BindView(R.id.btnSimpanBarang)
    Button btnSimpanBarang;
    private String idBarang = "";
    private ModelBarang.DataBean modelBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_barang);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        btnSimpanBarang.setOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Form Barang Create");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if (getIntent().hasExtra("ModelBarang")) {
            modelBarang = getIntent().getParcelableExtra("ModelBarang");
            textView3.setText(modelBarang.getBarcode());
            editText4.setText(modelBarang.getStockBarang());
            editText5.setText(modelBarang.getHargaBarang());
            editText.setText(modelBarang.getNBarang());
            idBarang = modelBarang.getIBarang();
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

    @OnClick(R.id.imageView2)
    public void onViewClicked() {
        Intent intent = new Intent(FormBarangActivity.this, ScanBarkodeActivity.class);
        startActivityForResult(intent, REQUEST_BARCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_BARCODE && data.hasExtra("BARCODE")) {
                textView3.setText(data.getStringExtra("BARCODE"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(textView3.getText().toString())) {
            Toast.makeText(this, "Kode Barang Kosong", Toast.LENGTH_SHORT).show();
        } else if (isKosong(editText)) {
            Toast.makeText(this, "Isi Nama Barang", Toast.LENGTH_SHORT).show();
        } else if (isKosong(editText4)) {
            Toast.makeText(this, "Isi Stock Barang", Toast.LENGTH_SHORT).show();
        } else if (isKosong(editText5)) {
            Toast.makeText(this, "Isi Harga Barang", Toast.LENGTH_SHORT).show();
        } else {
            RetrofitHelper retrofitHelper = new RetrofitHelper();
            Single<ResponseBody> testObservable = null;
            if (idBarang.equals("")) {
                testObservable = retrofitHelper.api().createBarang(textView3.getText().toString(), editText.getText().toString(), editText4.getText().toString(), editText5.getText().toString());
            } else {
                testObservable = retrofitHelper.api().updateBarang(idBarang, textView3.getText().toString(), editText.getText().toString(), editText4.getText().toString(), editText5.getText().toString());
            }
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
                                Toast.makeText(FormBarangActivity.this, "Berhasil Disimpan.", Toast.LENGTH_SHORT).show();
                                finish();

                            } catch (Exception e) {
                                Log.e(TAG, "onSuccess: Error " + e.getMessage());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                            Toast.makeText(FormBarangActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private boolean isKosong(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }


}
