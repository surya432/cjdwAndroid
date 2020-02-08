package com.suryaheho.projectb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.suryaheho.projectb.Helper.ApiClient;
import com.suryaheho.projectb.Helper.GetDataService;
import com.suryaheho.projectb.Helper.RetrofitHelper;
import com.suryaheho.projectb.Helper.SessionManager;
import com.suryaheho.projectb.Model.ModelLoginRespond;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private ApiClient retrofitHelper;
    public void loginClick(View view) {
        if (TextUtils.isEmpty(edtUsername.getText().toString().trim())) {
            Toast.makeText(this, "Isi Username Dulu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
            Toast.makeText(this, "Isi Password Dulu", Toast.LENGTH_SHORT).show();
        } else {
            RetrofitHelper retrofitHelper = new RetrofitHelper();
            Call<ResponseBody> call;
            call = retrofitHelper.api().login(edtUsername.getText().toString(), edtPassword.getText().toString());
            retrofitHelper.callApi(call, new RetrofitHelper.ConnectionCallBack() {
                @Override
                public void onSuccess(Response<ResponseBody> body) {
                    try {
                        if (body.code() != 200) {
//                            Utils.serverError(LoginActivity.this, body.code());
                            Log.e(TAG, "onSuccess: " + body.code());
                            return;
                        }
                        String response = body.body().string();
                        Log.e(TAG, "onSuccess: "+response );
                        Gson gson = new Gson();
                        ModelLoginRespond modelLoginRespond= gson.fromJson(response, ModelLoginRespond.class);
                        if (modelLoginRespond.getMessage().equals("Success")) {
                            sessionManager.setLogin(true, modelLoginRespond.getUser(), modelLoginRespond.getUser(), modelLoginRespond.getUser(), modelLoginRespond.getUser());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "username atau password salah", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | NullPointerException | JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(int code, String error) {
                    Log.e(TAG, "onError: " + error);
                }
            });

        }
    }
}
