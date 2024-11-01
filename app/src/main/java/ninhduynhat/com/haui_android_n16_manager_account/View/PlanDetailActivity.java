package ninhduynhat.com.haui_android_n16_manager_account.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ninhduynhat.com.haui_android_n16_manager_account.R;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PlanObject;
import ninhduynhat.com.haui_android_n16_manager_account.View.UpdatePlanActivity;

public class PlanDetailActivity extends AppCompatActivity {
    private Button btnDelete, btnUpdate;
    private PlanObject plan;
    private TextView textViewTargetName, textViewTargetDeadline, textViewProgressPercent, tvTotalBudget, tvSavedBudget, textViewDescribe, tvType;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_detail);
        setWidget(); // Gọi phương thức để khởi tạo các widget

        // Lấy dữ liệu từ Intent
        plan = getIntent().getParcelableExtra("Target");
        if (plan == null) {
            Log.e("PlanDetailActivity", "PlanObject is null. Cannot load plan details.");
            Toast.makeText(this, "Không thể tải thông tin mục tiêu", Toast.LENGTH_LONG).show();
            return;
        }

        if (plan != null) {
            setDataForActivity(); // Thiết lập dữ liệu cho giao diện
            setupButtonListeners(); // Thiết lập sự kiện cho các nút
        } else {
            Log.e("PlanDetailActivity", "PlanObject is null. Cannot set data for activity.");
            Toast.makeText(this, "Không thể tải thông tin mục tiêu", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc Activity nếu không có dữ liệu hợp lệ
        }
    }

    // Phương thức khởi tạo các widget từ giao diện
    private void setWidget() {
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        textViewTargetName = findViewById(R.id.planName);
        textViewTargetDeadline = findViewById(R.id.textViewDeadline);
        textViewProgressPercent = findViewById(R.id.progress_percent);
        progressBar = findViewById(R.id.progress_bar);
        tvTotalBudget = findViewById(R.id.textViewTotalBudget);
        tvSavedBudget = findViewById(R.id.textViewSavedBudget);
        tvType = findViewById(R.id.textViewType);
        textViewDescribe = findViewById(R.id.textViewDescribe);
    }

    // Phương thức thiết lập sự kiện cho các nút
    private void setupButtonListeners() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plan != null) {
                    DatabaseHelper.getInstance(PlanDetailActivity.this).deletePlan(plan); // Xóa kế hoạch khỏi cơ sở dữ liệu
                    Toast.makeText(PlanDetailActivity.this, "Xóa mục tiêu thành công", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng Activity sau khi xóa thành công
                } else {
                    Log.e("PlanDetailActivity", "PlanObject is null. Cannot delete plan.");
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plan != null) {
                    Intent intent = new Intent(PlanDetailActivity.this, UpdatePlanActivity.class);
                    intent.putExtra("PlanObject", (Parcelable) plan); // Truyền dữ liệu kế hoạch sang UpdatePlanActivity
                    startActivity(intent);
                    finish(); // Đóng Activity hiện tại sau khi chuyển sang UpdatePlanActivity
                } else {
                    Log.e("PlanDetailActivity", "PlanObject is null. Cannot update plan.");
                }
            }
        });
    }

    // Phương thức thiết lập dữ liệu cho giao diện từ đối tượng PlanObject
    private void setDataForActivity() {
        Toast.makeText(this, plan.getPlanType(), Toast.LENGTH_LONG).show();
        if (plan != null) {
            textViewTargetDeadline.setText("Thời gian: " + plan.getTimeLine());
            textViewTargetName.setText(plan.getPlanName());
            int percent = (int) ((plan.getAmoutReached() / plan.getAmoutNeeded()) * 100);
            textViewProgressPercent.setText(percent + "%");
            tvTotalBudget.setText("Số tiền cần có: " + plan.getAmoutNeeded());
            tvSavedBudget.setText("Số tiền đã có: " + plan.getAmoutReached());
            tvType.setText("Loại mục tiêu: " + plan.getPlanType());
            progressBar.setProgress(percent);
            textViewDescribe.setText(plan.getDescription());
        } else {
            Log.e("PlanDetailActivity", "PlanObject is null. Cannot set data for activity.");
        }
    }
}
