package ninhduynhat.com.haui_android_n16_manager_account.View;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import ninhduynhat.com.haui_android_n16_manager_account.Adapters.TransactionHistoryAdapter;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Login_Account;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PayingTuitionObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;


public class Cong_No_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private TransactionHistoryAdapter transactionAdapter;
    private List<PayingTuitionObject> transactionList;
    private DatabaseHelper databaseHelper;
    TextView soDuTextView;
    TextView soTienConNoTextView;
    Button btnThanhToan;
    Button btnNaptien;

    private void getWidget(View view){
        btnThanhToan = view.findViewById(R.id.btnThanhToan);
        btnNaptien = view.findViewById(R.id.btnNap);
        soDuTextView = view.findViewById(R.id.txtSoDuHocTap);
        soTienConNoTextView = view.findViewById(R.id.txtCongNo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cong__no_, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionList = new ArrayList<>();

        transactionAdapter = new TransactionHistoryAdapter(transactionList);
        recyclerView.setAdapter(transactionAdapter);

        getWidget(view);

        databaseHelper = new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LUU_TRANG_THAI_NGUOI_DUNG",MODE_PRIVATE);
        String user= sharedPreferences.getString("UserName","");

        int userId = databaseHelper.getUserByUsername(user).getUserID();
        loadUserData(userId);
        loadTransactionHistory(userId);

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThanhToan_Fragment chiTietFragment = new ThanhToan_Fragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, chiTietFragment); // Assuming your fragment container has this ID
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnNaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recharge_Fragment rechargeFragment = new Recharge_Fragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, rechargeFragment); // Assuming your fragment container has this ID
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void loadUserData(int userId) {
        UserObject user = databaseHelper.getUserById(userId);
        if (user != null) {
            soDuTextView.setText(String.format("%,.0f VND", user.getMoneyForStudying()));
            soTienConNoTextView.setText(String.format("%,.0f VND", user.getDebtMoney()));
        }
    }

    private void loadTransactionHistory(int userId) {
        transactionList.clear();
        transactionList.addAll(databaseHelper.getPaidTuitionList(userId));
        transactionAdapter.notifyDataSetChanged();
    }
}