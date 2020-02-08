package com.suryaheho.projectb.ui.user;

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
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.google.gson.Gson;
import com.suryaheho.projectb.Helper.RetrofitHelper;
import com.suryaheho.projectb.R;
import com.suryaheho.projectb.ui.user.Adapter.AdapterListUser;
import com.suryaheho.projectb.ui.user.Model.ModelListUser;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UserFragment extends Fragment implements AdapterListUser.AdapterListUserCallback, View.OnClickListener {

    public static final String TAG = UserFragment.class.getSimpleName();
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private Button btnCreateUser;
    private LayoutManager layoutManager;
    private TextView title;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        userViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        recyclerView = root.findViewById(R.id.rv_main);
        title = root.findViewById(R.id.title);
        btnCreateUser = root.findViewById(R.id.btnCreateUser);
        layoutManager = new LinearLayoutManager(getContext());
        setupListData();
        btnCreateUser.setOnClickListener(this);
        title.setText("List User");
        return root;

    }

    private void setupListData() {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        Single<ResponseBody> testObservable = retrofitHelper.api().listUser();
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
                            ModelListUser modelListUser = gson.fromJson(response.replaceAll("<!-- data User -->", ""), ModelListUser.class);
                            if (modelListUser.getData().size() > 0) {
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(layoutManager);
                                AdapterListUser adapterListUser = new AdapterListUser(getContext(), -1, modelListUser.getData(), UserFragment.this);
                                recyclerView.setAdapter(adapterListUser);
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
    public void onResume() {
        super.onResume();
        setupListData();
    }

    @Override
    public void onRowAdapterListUserClicked(ModelListUser.DataItem position) {
        Intent intent = new Intent(getContext(), FormUserActivity.class);
        intent.putExtra("ModelListUser", position);
        getActivity().startActivity(intent);
    }

    @Override
    public void onRowAdapterListUserClickedDelete(ModelListUser.DataItem position) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        Single<ResponseBody> testObservable = retrofitHelper.api().userDelete(position.getIUser());
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
//                            Gson gson = new Gson();
                            Toast.makeText(getContext(), "User Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
//                            ModelListUser modelListUser = gson.fromJson(response.replaceAll("<!-- data User -->", ""), ModelListUser.class);
                            setupListData();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateUser:
                startActivity(new Intent(getContext(), FormUserActivity.class));
                break;
        }
    }
}