package com.suryaheho.projectb.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.suryaheho.projectb.Helper.RetrofitHelper;
import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.user.Model.ModelListUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FormUserActivity extends AppCompatActivity {

    public static final String TAG = FormUserActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nUser)
    TextInputEditText nUser;
    @BindView(R.id.pasUser)
    TextInputEditText pasUser;
    private ModelListUser.DataItem modelListUser;
    private String iUser = "";

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Form Barang");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if (getIntent().hasExtra("ModelListUser")) {
            modelListUser = getIntent().getParcelableExtra("ModelListUser");
            Log.e(TAG, "onCreate: " + modelListUser.getNUser());
            nUser.setText(modelListUser.getNUser() + "");
            pasUser.setText(modelListUser.getPasUser() + "");
            iUser = modelListUser.getIUser();
        }
    }

    public void btnSimpanUser(View view) {
        if (TextUtils.isEmpty(nUser.getText().toString())) {
            Toast.makeText(this, "Isi Nuser Dulu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pasUser.getText().toString())) {
            Toast.makeText(this, "Isi pasUser Dulu", Toast.LENGTH_SHORT).show();
        } else {
            RetrofitHelper retrofitHelper = new RetrofitHelper();
            Single<ResponseBody> testObservable = null;
            if (iUser.equals("")) {
                testObservable = retrofitHelper.api().CreateUser(nUser.getText().toString(), pasUser.getText().toString());
            } else {
                testObservable = retrofitHelper.api().UpdateUser(iUser, nUser.getText().toString(), pasUser.getText().toString());
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
                                Toast.makeText(FormUserActivity.this, "Berhasil Disimpan ", Toast.LENGTH_SHORT).show();
                                finish();

                            } catch (Exception e) {
                                Log.e(TAG, "onSuccess: Error " + e.getMessage());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                            Toast.makeText(FormUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
