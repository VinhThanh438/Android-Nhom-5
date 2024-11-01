package ninhduynhat.com.haui_android_n16_manager_account.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ninhduynhat.com.haui_android_n16_manager_account.R;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PlanObject;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewPlanActivity extends AppCompatActivity {
    // Khai báo các biến cho các thành phần giao diện
    Button btnCreate, btnDelete;
    EditText editTextName, editTextTotalBudget, editTextSavedBudget, editTextDate, editTextDescribe;
    Spinner spinner;
    SeekBar seekBar;
    ImageView imageViewDatePicker;
    List<String> type = Arrays.asList("Small", "Middle", "Big", "Short-term", "Long-term");  // Dữ liệu cho spinner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_target); // Thiết lập layout cho activity
        setWidget(); // Khởi tạo các thành phần giao diện
        editTextDateOnFocusEvent(); // Thiết lập sự kiện cho editTextDate khi focus
        editTextSavedBudgetOnFocusEvent(); // Thiết lập sự kiện cho editTextSavedBudget khi focus
        setUpSpinner(); // Thiết lập dữ liệu cho spinner
        btnCreateEvent(); // Thiết lập sự kiện cho nút tạo mới
        btnDeleteEvent(); // Thiết lập sự kiện cho nút xóa
        imageViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(); // Mở DatePicker khi người dùng nhấn vào imageViewDatePicker
            }
        });
    }

    private void btnCreateEvent() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Kiểm tra các trường nhập liệu và hiển thị thông báo nếu trống
                    if (editTextName.getText().toString().equals("")) {
                        Toast.makeText(AddNewPlanActivity.this, "Name can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextTotalBudget.getText().toString().equals("")) {
                        Toast.makeText(AddNewPlanActivity.this, "Total Budget can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextSavedBudget.getText().toString().equals("")) {
                        Toast.makeText(AddNewPlanActivity.this, "Saved Budget can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextDate.getText().toString().equals("")) {
                        Toast.makeText(AddNewPlanActivity.this, "Deadline can not empty", Toast.LENGTH_SHORT).show();
                    } else {
                        // Tạo đối tượng PlanObject mới và thêm vào cơ sở dữ liệu
                        PlanObject plan = new PlanObject(
                                0,
                                editTextName.getText().toString(),
                                Double.parseDouble(editTextTotalBudget.getText().toString()),
                                Double.parseDouble(editTextSavedBudget.getText().toString()),
                                editTextDate.getText().toString(),
                                spinner.getSelectedItem().toString(),
                                editTextDescribe.getText().toString()  // Sử dụng giá trị từ editTextDescribe
                        );
                        DatabaseHelper.getInstance(AddNewPlanActivity.this).addPlan(plan);
                        Toast.makeText(AddNewPlanActivity.this, "New Target added successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Đóng activity sau khi thêm thành công
                    }
                } catch (Exception e) {
                    Toast.makeText(AddNewPlanActivity.this, "Add target Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void btnDeleteEvent() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa các trường nhập liệu
                editTextName.setText("");
                editTextDate.setText("");
                editTextSavedBudget.setText("0");
                editTextTotalBudget.setText("0");
                editTextDescribe.setText("");
            }
        });
    }

    private void setUpSpinner() {
        // Thiết lập adapter và gán dữ liệu cho spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(AddNewPlanActivity.this, android.R.layout.simple_spinner_item, type);
        spinner.setAdapter(spinnerAdapter);
    }

    private void editTextSavedBudgetOnFocusEvent() {
        editTextSavedBudget.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editTextSavedBudget.getText().toString().equals("")) {
                    editTextSavedBudget.setText("0");
                }

                // Cập nhật tiến trình của seekBar theo tỷ lệ ngân sách đã tiết kiệm và ngân sách tổng
                int progressprecent = (int) (Double.parseDouble(editTextSavedBudget.getText().toString()) / Double.parseDouble(editTextTotalBudget.getText().toString()) * 100);
                //progressBar.setProgress(progressprecent);
            }
        });
    }

    private void editTextDateOnFocusEvent() {
        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickDate(); // Mở DatePicker khi editTextDate được focus
                    view.clearFocus();
                }
            }
        });
    }

    private void setWidget() {
        // Khởi tạo các thành phần giao diện
        btnCreate = findViewById(R.id.btn_create);
        btnDelete = findViewById(R.id.btn_delete);
        editTextName = findViewById(R.id.editTextName);
        editTextTotalBudget = findViewById(R.id.editTextTotalBudget);
        editTextSavedBudget = findViewById(R.id.editTextSavedBudget);
        editTextDate = findViewById(R.id.editTextDate);
        editTextDescribe = findViewById(R.id.editTextDescribe); // Thêm dòng này
        spinner = findViewById(R.id.spinner);
        imageViewDatePicker = findViewById(R.id.imageViewDatePicker);
    }

    public void pickDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Calendar calendar = Calendar.getInstance();
        int year, month, day;
        try {
            // Thiết lập ngày hiện tại nếu editTextDate trống
            Date date = df.parse(editTextDate.getText().toString());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo và hiển thị DatePickerDialog để chọn ngày
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddNewPlanActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
}
