package ninhduynhat.com.haui_android_n16_manager_account.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PlanObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class UpdatePlanActivity extends Activity {
    PlanObject plan;
    EditText editTextTotalBudget, editTextSavedBudget, editTextDeadline, editTextDescribe;
    TextView textViewName;
    Spinner spinner;
    // ProgressBar progressBar;
    ImageView imageViewDatePicker;
    Button btnUpdate, btnBack;

    // Danh sách các loại kế hoạch
    List<String> type = Arrays.asList("Small", "Middle", "Big", "Short-term", "Long-term");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_target);
        setWidget(); // Gọi phương thức để khởi tạo các widget
        plan = getIntent().getParcelableExtra("PlanObject"); // Lấy dữ liệu kế hoạch từ Intent
        setupDataForFragment(); // Thiết lập dữ liệu cho giao diện
        editTextSavedBudgetEvent(); // Thiết lập sự kiện cho editTextSavedBudget
        setupSpinner(); // Thiết lập spinner
        editTextDeadlineEvent(); // Thiết lập sự kiện cho editTextDeadline
        btnUpdateEvent(); // Thiết lập sự kiện cho nút Update
        btnBackEvent(); // Thiết lập sự kiện cho nút Back
        imageViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(); // Gọi phương thức chọn ngày khi click vào ImageView
            }
        });
    }

    // Phương thức thiết lập sự kiện cho nút Back
    private void btnBackEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Đóng Activity khi nhấn nút Back
            }
        });
    }

    // Phương thức thiết lập sự kiện cho nút Update
    private void btnUpdateEvent() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Kiểm tra nếu các trường thông tin không được để trống
                    if (editTextTotalBudget.getText().toString().equals("")) {
                        Toast.makeText(UpdatePlanActivity.this, "Total Budget cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextSavedBudget.getText().toString().equals("")) {
                        Toast.makeText(UpdatePlanActivity.this, "Saved Budget cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextDeadline.getText().toString().equals("")) {
                        Toast.makeText(UpdatePlanActivity.this, "Deadline cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        // Tạo đối tượng PlanObject mới với các giá trị từ giao diện
                        PlanObject newtarget = new PlanObject(plan.getPlanId(), textViewName.getText().toString(), Double.parseDouble(editTextTotalBudget.getText().toString()), Double.parseDouble(editTextSavedBudget.getText().toString()), editTextDeadline.getText().toString(), spinner.getSelectedItem().toString(), editTextDescribe.getText().toString());
                        DatabaseHelper.getInstance(UpdatePlanActivity.this).updatePlan(newtarget); // Cập nhật kế hoạch trong cơ sở dữ liệu
                        Toast.makeText(UpdatePlanActivity.this, "Updated Target successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Đóng Activity sau khi cập nhật thành công
                    }
                } catch (Exception e) {
                    Toast.makeText(UpdatePlanActivity.this, "Update target Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Phương thức thiết lập sự kiện cho editTextDeadline
    private void editTextDeadlineEvent() {
        editTextDeadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickDate(); // Mở DatePicker khi editTextDeadline nhận focus
                    view.clearFocus(); // Xóa focus sau khi chọn ngày
                }
            }
        });
    }

    // Phương thức thiết lập adapter cho Spinner
    private void setupSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(UpdatePlanActivity.this, android.R.layout.simple_spinner_item, type);
        spinner.setAdapter(spinnerAdapter);
    }

    // Phương thức thiết lập sự kiện cho editTextSavedBudget
    private void editTextSavedBudgetEvent() {
        editTextSavedBudget.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editTextSavedBudget.getText().toString().equals("")) {
                    editTextSavedBudget.setText("0"); // Đặt giá trị mặc định là 0 nếu để trống
                }

                // Tính toán và cập nhật tiến độ
                int progressPercent = (int) (Double.parseDouble(editTextSavedBudget.getText().toString()) / Double.parseDouble(editTextTotalBudget.getText().toString()) * 100);
                // progressBar.setProgress(progressPercent);
            }
        });
    }

    // Phương thức thiết lập dữ liệu cho giao diện từ đối tượng PlanObject
    private void setupDataForFragment() {
        textViewName.setText(plan.getPlanName());
        editTextSavedBudget.setText(String.valueOf(plan.getAmoutReached()));
        editTextTotalBudget.setText(String.valueOf(plan.getAmoutNeeded()));
        editTextDeadline.setText(plan.getTimeLine());
        editTextDescribe.setText(plan.getDescription());
        spinner.setSelection(type.indexOf(plan.getPlanType()));
    }

    // Phương thức khởi tạo các widget từ giao diện
    private void setWidget() {
        textViewName = findViewById(R.id.planname);
        editTextTotalBudget = findViewById(R.id.editTextTotalBudget);
        editTextSavedBudget = findViewById(R.id.editTextSavedBudget);
        editTextDeadline = findViewById(R.id.editTextDeadline);
        editTextDescribe = findViewById(R.id.editTextDescribe);
        imageViewDatePicker = findViewById(R.id.imageView);
        spinner = findViewById(R.id.spinner);
        // progressBar = findViewById(R.id.progressBar);
        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    // Phương thức mở DatePicker để chọn ngày
    public void pickDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Calendar calendar = Calendar.getInstance();
        int year, month, day;
        try {
            Date date = df.parse(editTextDeadline.getText().toString());
            calendar.setTime(date); // Thiết lập ngày hiện tại cho DatePicker từ editTextDeadline
        } catch (ParseException e) {
            e.printStackTrace();
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UpdatePlanActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editTextDeadline.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year); // Cập nhật editTextDeadline với ngày đã chọn
                    }
                },
                year, month, day);
        datePickerDialog.show(); // Hiển thị DatePickerDialog
    }
}
