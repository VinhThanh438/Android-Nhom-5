package ninhduynhat.com.android_n5_manager_account.View;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ninhduynhat.com.android_n5_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.android_n5_manager_account.Model.SubjectObject;
import ninhduynhat.com.android_n5_manager_account.Model.UserObject;
import ninhduynhat.com.android_n5_manager_account.R;

public class RegisterFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayAdapter<String> subjectAdapter; // Thay đổi thành ArrayAdapter
    private List<SubjectObject> subjects;
    private List<String> subjectNames; // Danh sách tên môn học
    private Spinner spinnerSemester;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private int userId;
    private Button btnRegister, btnListRegister;

    private void getWidget(View view) {
        btnRegister = view.findViewById(R.id.buttonRegister);
        spinnerSemester = view.findViewById(R.id.spinnerSemester);
        btnListRegister = view.findViewById(R.id.btnDanhsachdangky);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initializeUI(view);
        loadUserId();
        setupSemesterSpinner();
        setupRecyclerView();
        setupButtonListeners();
        return view;
    }

    private void initializeUI(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewSubjects);
        getWidget(view);
        databaseHelper = new DatabaseHelper(getContext());
        db = databaseHelper.getReadableDatabase();
    }

    private void loadUserId() {
        if (getContext() != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LUU_TRANG_THAI_NGUOI_DUNG", getContext().MODE_PRIVATE);
            String user = sharedPreferences.getString("UserName", "");
            UserObject userObject = databaseHelper.getUserByUsername(user);
            userId = userObject != null ? userObject.getUserID() : -1; // Kiểm tra null
        } else {
            Log.e("Context Error", "getContext() returned null in onCreateView");
        }
    }

    private void setupSemesterSpinner() {
        String[] semestersArray = {"Kỳ 1", "Kỳ 2", "Kỳ 3", "Kỳ 4", "Kỳ 5", "Kỳ 6", "Kỳ 7", "Kỳ 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, semestersArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapter);
        spinnerSemester.setOnItemSelectedListener(new SemesterSelectedListener());
    }

    private void setupRecyclerView() {
        subjects = fetchSubjectsForSemester(1); // Mặc định chọn kỳ 1
        subjectNames = getSubjectNames(subjects);

        subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, subjectNames);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                // Thiết lập tên môn học
                TextView textView = (TextView) holder.itemView;
                textView.setText(subjectNames.get(position));
            }

            @Override
            public int getItemCount() {
                return subjectNames.size();
            }
        });

        subjectAdapter.notifyDataSetChanged(); // Thông báo cho adapter biết có dữ liệu mới
    }


    private List<String> getSubjectNames(List<SubjectObject> subjects) {
        List<String> names = new ArrayList<>();
        for (SubjectObject subject : subjects) {
            names.add(subject.getSubjectName());
        }
        return names;
    }

    private void setupButtonListeners() {
        btnRegister.setOnClickListener(v -> registerSelectedSubjects());
        btnListRegister.setOnClickListener(v -> navigateToCancelRegisterFragment());
    }

    private class SemesterSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int selectedSemester = position + 1;
            List<SubjectObject> fetchedSubjects = fetchSubjectsForSemester(selectedSemester);
            subjects.clear();
            subjects.addAll(fetchedSubjects);
            subjectNames.clear(); // Xóa danh sách tên môn học cũ
            subjectNames.addAll(getSubjectNames(subjects));
            subjectAdapter.notifyDataSetChanged();

            if (subjectNames.isEmpty()) {
                showSnackbar("Không tìm thấy môn học nào cho kỳ " + selectedSemester);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Không làm gì khi không có mục nào được chọn
        }
    }

    private void navigateToCancelRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            ListRegisterFragment cancelRegisterFragment = new ListRegisterFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            cancelRegisterFragment.setArguments(bundle);
            transaction.replace(R.id.fragment_container, cancelRegisterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private List<SubjectObject> fetchSubjectsForSemester(int semester) {
        List<SubjectObject> subjects = new ArrayList<>();
        try (Cursor cursor = db.query("SUBJECT", null, "Semester = ?", new String[]{String.valueOf(semester)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int subjectId = cursor.getInt(cursor.getColumnIndexOrThrow("SubjectId"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("SubjectName"));
                    byte credits = (byte) cursor.getInt(cursor.getColumnIndexOrThrow("StudyCredits"));
                    subjects.add(new SubjectObject(subjectId, name, credits, semester));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            showSnackbar("Lỗi khi lấy dữ liệu cho kỳ " + semester);
        }
        return subjects;
    }

    private void showSnackbar(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void registerSelectedSubjects() {
        // Chức năng đăng ký môn học sẽ ở đây
    }
}
