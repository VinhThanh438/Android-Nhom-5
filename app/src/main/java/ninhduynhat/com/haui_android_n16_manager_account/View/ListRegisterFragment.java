package ninhduynhat.com.haui_android_n16_manager_account.View;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ninhduynhat.com.haui_android_n16_manager_account.Adapters.PayingTuitionAdapter;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PayingTuitionObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;


public class ListRegisterFragment extends Fragment {


    private RecyclerView recyclerView;
    private PayingTuitionAdapter adapter;
    private List<PayingTuitionObject> registerList;
    private DatabaseHelper databaseHelper;
    private UserObject currentUser;
    private Button btnhuydangky, btnQuayLai;
    private RadioButton rbChonTatCa;
    private int userId;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_register, container, false);

        recyclerView = view.findViewById(R.id.chi_tiet_recycler_view);
        btnhuydangky = view.findViewById(R.id.btnHuyDangKy);
        btnQuayLai = view.findViewById(R.id.btnQuayLai);
        rbChonTatCa = view.findViewById(R.id.radioButton);

        databaseHelper = new DatabaseHelper(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getInt("userId", -1);
        }

        currentUser = databaseHelper.getUserById(userId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();

        rbChonTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelectedPositions().size() == registerList.size()) {
                    adapter.getSelectedPositions().clear();
                    rbChonTatCa.setChecked(false);
                } else {
                    for (int i = 0; i < registerList.size(); i++) {
                        adapter.getSelectedPositions().add(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        btnhuydangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedSubject();
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
    //tải dữ liệu
    private void loadData() {
        registerList = databaseHelper.getRegisteredSubjects(userId);
        adapter = new PayingTuitionAdapter(getContext(), registerList);
        recyclerView.setAdapter(adapter);
    }

private void deleteSelectedSubject() {
    Set<Integer> selectedPositions = adapter.getSelectedPositions();
    if (selectedPositions.isEmpty()) {
        Toast.makeText(getContext(), "Vui lòng chọn ít nhất một môn để hủy đăng ký", Toast.LENGTH_SHORT).show();
        return;
    }

    // Xác nhận hành động hủy đăng ký
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("Xác nhận hủy đăng ký");
    builder.setMessage("Bạn có chắc chắn muốn hủy đăng ký các môn này?");
    builder.setPositiveButton("Đồng ý", (dialog, which) -> {
        List<PayingTuitionObject> selectedSubjects = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedSubjects.add(registerList.get(position));
        }

        for (PayingTuitionObject selected : selectedSubjects) {
            databaseHelper.cancelRegistrationAndUpdateDebt(userId, selected.getSubjectID());
        }

        Toast.makeText(getContext(), "Hủy đăng ký thành công", Toast.LENGTH_SHORT).show();
        adapter.clearSelection();
        rbChonTatCa.setChecked(false);
        loadData(); // Tải lại dữ liệu sau khi hủy đăng ký
    });
    builder.setNegativeButton("Hủy", (dialog, which) -> {
        // Không làm gì, chỉ đóng dialog
    });
    builder.create().show();
}
    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
    public ListRegisterFragment() {
        // Required empty public constructor
    }

}