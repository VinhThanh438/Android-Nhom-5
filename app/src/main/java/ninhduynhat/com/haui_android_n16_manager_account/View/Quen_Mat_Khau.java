package ninhduynhat.com.haui_android_n16_manager_account.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Login_Account;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class Quen_Mat_Khau extends AppCompatActivity {
    private EditText edt_ten_dangNhap,edt_MatKhau,edt_XacNhanMK;
    private Button btn_QuenMK;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quen_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findId();
        btn_QuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDauVao();
            }
        });

    }
    private void findId(){
        edt_ten_dangNhap=findViewById(R.id.edt_ten_dangNhap);
        edt_MatKhau=findViewById(R.id.edt_MatKhau);
        edt_XacNhanMK=findViewById(R.id.edt_XacNhanMK);
        btn_QuenMK=findViewById(R.id.btn_QuenMK);
    }
    private void xuLyDauVao(){
        databaseHelper= new DatabaseHelper(this);
        if(edt_ten_dangNhap.getText().toString().isEmpty()||edt_MatKhau.getText().toString().isEmpty()||edt_XacNhanMK.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn phải điền đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean checkuser = databaseHelper.isUsernameDuplicate(edt_ten_dangNhap.getText().toString());
        if(!checkuser){
            Toast.makeText(this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(edt_MatKhau.getText().toString().equals(edt_XacNhanMK.getText().toString()))){
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        String pass = edt_MatKhau.getText().toString();
        if(pass.length()<6 ){
            Toast.makeText(this,"Mật khẩu phải có ít nhất 6 kí tự!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Character.isUpperCase(pass.charAt(0))){
            Toast.makeText(this,"Mật khẩu phải bát đầu bằng chữ cái in hoa!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Character.isDigit(pass.charAt(0))){
            Toast.makeText(this,"Mật khẩu không được bắt đầu bằng số!",Toast.LENGTH_SHORT).show();
            return;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : pass.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        if (!hasLetter || !hasDigit) {
            Toast.makeText(this, "Mật khẩu phải có cả chữ và số", Toast.LENGTH_SHORT).show();
            return;
        }
        UserObject user = databaseHelper.getUserByUsername(edt_ten_dangNhap.getText().toString());
        user.setPassword(edt_MatKhau.getText().toString());
        databaseHelper.updateUser(user);

        Intent intent = new Intent(Quen_Mat_Khau.this, Login_Account.class);
        startActivity(intent);

    }
}