package ninhduynhat.com.haui_android_n16_manager_account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.View.Quen_Mat_Khau;

public class Login_Account extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TextView canhBaoDangNhap;
    private TextView txtchuyendangkys, quenMatKhau;
    private EditText edt_TenDangNhap, edt_MatKhau;
    private Button btn_DangNhapManHinh;
    public static final String LUU_TRANG_THAI_NGUOI_DUNG = "LUU_TRANG_THAI_NGUOI_DUNG";
    private String usernamedangnhap, matkhaudangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findId();

        SharedPreferences sharedPreferences = getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG, MODE_PRIVATE);
        boolean islogin = sharedPreferences.getBoolean("isLogin", false);
        usernamedangnhap = sharedPreferences.getString("UserName", "");
        matkhaudangnhap = sharedPreferences.getString("PassWord", "");
        if (islogin) {
            String user = sharedPreferences.getString("UserName", "");
            String pass = sharedPreferences.getString("PassWord", "");
            edt_TenDangNhap.setText(user);
            edt_MatKhau.setText(pass);
        }

        // Chuyển màn hình đăng ký
        txtchuyendangkys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(Login_Account.this, Sign_Account.class);
                startActivity(intents);
            }
        });

        // Nút đăng nhập
        btn_DangNhapManHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginState();
            }
        });
        quenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Account.this, Quen_Mat_Khau.class);
                startActivity(intent);
            }
        });
    }

    private void findId() {
        txtchuyendangkys = findViewById(R.id.txtchuyendangkys);
        edt_TenDangNhap = findViewById(R.id.edt_TenDangNhap);
        edt_MatKhau = findViewById(R.id.edt_MatKhau);
        btn_DangNhapManHinh = findViewById(R.id.btnDangNhapManHinh);
        canhBaoDangNhap = findViewById(R.id.canhBaoDangNhap);
        quenMatKhau = findViewById(R.id.quenMatKhau);
    }

    public void saveLoginState() {
        if (edt_TenDangNhap.getText().toString().isEmpty() || edt_MatKhau.getText().toString().isEmpty()) {
            Toast.makeText(this, "Chưa điền tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseHelper = new DatabaseHelper(this);
        boolean check = databaseHelper.checkUserName_Password(edt_TenDangNhap.getText().toString(), edt_MatKhau.getText().toString());

        if (check) {
            Intent intent = new Intent(Login_Account.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            canhBaoDangNhap.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG, MODE_PRIVATE).edit();
        if (edt_TenDangNhap.getText().toString().isEmpty() || edt_MatKhau.getText().toString().isEmpty()) {
            editor.putString("UserName", usernamedangnhap);
            editor.putString("PassWord", matkhaudangnhap);
            Log.e("check luu thong tin", usernamedangnhap + " mật khẩu là: " + matkhaudangnhap);
            editor.commit();
        } else {
            editor.putString("UserName", edt_TenDangNhap.getText().toString());
            editor.putString("PassWord", edt_MatKhau.getText().toString());
            editor.commit();
        }
    }
}
