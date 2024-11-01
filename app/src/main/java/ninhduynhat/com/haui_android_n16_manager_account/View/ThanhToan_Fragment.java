package ninhduynhat.com.haui_android_n16_manager_account.View;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import ninhduynhat.com.haui_android_n16_manager_account.Adapters.PayingTuitionAdapter;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Login_Account;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PayingTuitionObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class ThanhToan_Fragment extends Fragment {


    private RecyclerView recyclerView;
    private PayingTuitionAdapter adapter;
    private List<PayingTuitionObject> payingTuitionList;
    private DatabaseHelper databaseHelper;
    private UserObject currentUser;
    private Button btnThanhToanFinal, btnQuayLai;
    private RadioButton rbChonTatCa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_toan, container, false);

        recyclerView = view.findViewById(R.id.chi_tiet_recycler_view);
        btnThanhToanFinal = view.findViewById(R.id.btnThanhToanFinal);
        btnQuayLai = view.findViewById(R.id.btnQuayLai);
        rbChonTatCa = view.findViewById(R.id.radioButton);

        databaseHelper = new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LUU_TRANG_THAI_NGUOI_DUNG", getContext().MODE_PRIVATE);
        String userName = sharedPreferences.getString("UserName", "");

        currentUser = databaseHelper.getUserByUsername(userName);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();

        rbChonTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < adapter.getItemCount(); i++) {
//                    adapter.getSelectedPositions().add(i);
//                }
//                adapter.notifyDataSetChanged();

            if (adapter.getSelectedPositions().size() == payingTuitionList.size()) {
                adapter.getSelectedPositions().clear();
                rbChonTatCa.setChecked(false);
            } else {
                for (int i = 0; i < payingTuitionList.size(); i++) {
                    adapter.getSelectedPositions().add(i);
                }
            }
            adapter.notifyDataSetChanged();
            }
        });

        btnThanhToanFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paySelectedTuition();
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    private void loadData() {
        payingTuitionList = databaseHelper.getUnpaidTuitionList(currentUser.getUserID());
        adapter = new PayingTuitionAdapter(getContext(), payingTuitionList);
        recyclerView.setAdapter(adapter);
    }

    private void paySelectedTuition() {
        Set<Integer> selectedPositions = adapter.getSelectedPositions();
        double totalPayment = 0;

        if (selectedPositions.isEmpty()) {
//            Toast.makeText(getContext(), "Vui lòng chọn ít nhất một môn học để thanh toán", Toast.LENGTH_SHORT).show();
            showDialog("Vui lòng chọn ít nhất một môn học để thanh toán");
            return;
        }

        for (int position : selectedPositions) {
            PayingTuitionObject tuition = payingTuitionList.get(position);
            totalPayment += tuition.getAmount();
        }

        if (currentUser.getMoneyForStudying() < totalPayment) {
//            Toast.makeText(getContext(), "Số dư không đủ để thanh toán", Toast.LENGTH_SHORT).show();
            showDialog("Số dư không đủ để thanh toán");
            return;
        }

        for (int position : selectedPositions) {
            PayingTuitionObject tuition = payingTuitionList.get(position);
            tuition.setPaided(true);
            databaseHelper.updatePayingTuition(tuition);
        }

        double newDebt = currentUser.getDebtMoney() - totalPayment;
        currentUser.setMoneyForStudying(currentUser.getMoneyForStudying() - totalPayment);
        currentUser.setDebtMoney(newDebt);
        databaseHelper.updateUser(currentUser);

//        Toast.makeText(getContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
        showDialog("Thanh toán thành công!");
        rbChonTatCa.setChecked(false);
        loadData();
    }
    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}