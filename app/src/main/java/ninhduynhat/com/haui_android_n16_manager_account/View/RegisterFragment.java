package ninhduynhat.com.haui_android_n16_manager_account.View;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ninhduynhat.com.haui_android_n16_manager_account.Adapters.SubjectAdapter;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PayingTuitionObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.SubjectObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class RegisterFragment extends Fragment {
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private List<SubjectObject> subjects;
    private Spinner spinnerSemester;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private int userId;
    Button btnRegister, btnListRegister;

    private void getWidget(View view) {
        btnRegister = view.findViewById(R.id.buttonRegister);
        spinnerSemester = view.findViewById(R.id.spinnerSemester);
        btnListRegister = view.findViewById(R.id.btnDanhsachdangky);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSubjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getWidget(view);

        databaseHelper = new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();

        if (getContext() != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LUU_TRANG_THAI_NGUOI_DUNG", MODE_PRIVATE);
            String user = sharedPreferences.getString("UserName", "");

            UserObject userObject = databaseHelper.getUserByUsername(user);
            userId = userObject != null ? userObject.getUserID() : -1; // Thêm kiểm tra null
        } else {
            Log.e("Context Error", "getContext() returned null in onCreateView");
        }

        // Tạo các kỳ học
        String[] semestersArray = {"Kỳ 1", "Kỳ 2", "Kỳ 3", "Kỳ 4", "Kỳ 5", "Kỳ 6", "Kỳ 7", "Kỳ 8"};

        // Thêm dữ liệu vào spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, semestersArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapter);

        // Nạp các môn học cho học kỳ đã chọn
        subjects = fetchSubjectsForSemester(1); // Default to semester 1
        subjectAdapter = new SubjectAdapter(subjects, getContext());
        recyclerView.setAdapter(subjectAdapter);

        // Xử lý sự kiện chọn item trong spinner
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedSemester = position + 1;

                // Lấy các môn học cho học kỳ hiện tại
                List<SubjectObject> fetchedSubjects = fetchSubjectsForSemester(selectedSemester);
                Log.d("RegisterFragment", "Fetched subjects: " + fetchedSubjects.size());

                // Cập nhật danh sách môn học cho học kỳ mới được chọn
                subjects.clear();
                subjects.addAll(fetchedSubjects);
                subjectAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                // Đặt lại các mục đã chọn cho học kỳ mới
                Set<Integer> selectedPositions = subjectAdapter.getSelectedPositionsForSemester(selectedSemester);
                subjectAdapter.setSelectedSubjectsForSemester(selectedSemester, selectedPositions);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nếu cần thì thực hiện thao tác khi không có mục nào được chọn
            }
        });

        // Sự kiện kích nút đăng ký
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSelectedSubjects();
            }
        });

        // Sự kiện kích nút danh sách đăng ký
        btnListRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCancelRegisterFragment();
            }
        });

        return view;
    }

    // Chuyển sang fragment danh sách đăng ký
    private void navigateToCancelRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            ListRegisterFragment cancelRegisterFragment = new ListRegisterFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            cancelRegisterFragment.setArguments(bundle);

            transaction.replace(R.id.fragment_container, cancelRegisterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    // Lấy danh sách các môn học thuộc một học kỳ
    private List<SubjectObject> fetchSubjectsForSemester(int semester) {
        List<SubjectObject> subjects = new ArrayList<>();
        Cursor cursor = db.query("SUBJECT", null, "Semester = ?", new String[]{String.valueOf(semester)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int subjectId = cursor.getInt(cursor.getColumnIndexOrThrow("SubjectId"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("SubjectName"));
                byte credits = (byte) cursor.getInt(cursor.getColumnIndexOrThrow("StudyCredits"));
                subjects.add(new SubjectObject(subjectId, name, credits, semester));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return subjects;
    }

    private void registerSelectedSubjects() {
        try {
            double totalDebt = 0;

            // Duyệt qua từng học kỳ
            for (int semester = 1; semester <= spinnerSemester.getAdapter().getCount(); semester++) {
                // Lấy danh sách các vị trí đã chọn cho học kỳ hiện tại
                Set<Integer> selectedPositions = subjectAdapter.getSelectedPositionsForSemester(semester);

                if (!selectedPositions.isEmpty()) {
                    // Lấy danh sách môn học của học kỳ hiện tại
                    List<SubjectObject> subjects = fetchSubjectsForSemester(semester);

                    // Duyệt qua các môn học đã chọn
                    for (int position : selectedPositions) {
                        SubjectObject subject = subjects.get(position);

                        // Tính toán tiền học phí
                        double tuitionFee = subject.getStudyCredits() * 415000;
                        PayingTuitionObject payingTuition = new PayingTuitionObject(userId, subject.getSubjectId(), subject.getSubjectName(), tuitionFee, false);

                        // Kiểm tra nếu công nợ vượt quá 15 triệu
                        UserObject user = databaseHelper.getUserById(userId);
                        if (user != null && user.getDebtMoney() + tuitionFee > 15000000) {
                            Toast.makeText(getContext(), "Tổng tiền công nợ vượt quá 15 triệu, không thể đăng ký thêm môn học!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Nếu không vượt quá, thực hiện đăng ký
                        databaseHelper.insertPayingTuition(userId, payingTuition.getSubjectID(), payingTuition.getSubjectName(), payingTuition.getAmount(), payingTuition.isPaided() ? 1 : 0);
                        totalDebt += tuitionFee;
                    }
                }
            }

            // Cập nhật công nợ của người dùng
            UserObject user = databaseHelper.getUserById(userId);
            if (user != null) {
                double newDebt = user.getDebtMoney() + totalDebt;
                user.setDebtMoney(newDebt);
                databaseHelper.updateUser(user);
                Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                // Xóa các mục đã chọn trong Adapter cho tất cả các học kỳ
                subjectAdapter.clearAllSelections();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
        }
    }
}
