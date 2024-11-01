package ninhduynhat.com.haui_android_n16_manager_account;

import android.content.Intent;
import android.os.Bundle;
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

public class Sign_Account extends AppCompatActivity {
    private TextView chuyenmanhinhdangnhap;
    private EditText edt_UserName_Sign,edt_Password_Sign,edt_Password_Sign_Confirm,edt_FullName_Sign;
    private Button btn_Sign_In;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findId();
        chuyenmanhinhdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_Account.this,Login_Account.class);
                startActivity(intent);
            }
        });

        btn_Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInPutSignIn();
            }
        });
    }
    private void findId(){
        btn_Sign_In=findViewById(R.id.btn_Sign_In);
        chuyenmanhinhdangnhap = findViewById(R.id.chuyenmanhinhdangnhap);
        edt_UserName_Sign = findViewById(R.id.edt_UserName_Sign);
        edt_Password_Sign= findViewById(R.id.edt_Password_Sign);
        edt_Password_Sign_Confirm= findViewById(R.id.edt_Password_Sign_Confirm);
        edt_FullName_Sign=findViewById(R.id.edt_FullName_Sign);
    }
    private void checkInPutSignIn(){

         db = new DatabaseHelper(Sign_Account.this);
        boolean checkuser = db.isUsernameDuplicate(edt_UserName_Sign.getText().toString());
        if(edt_UserName_Sign.getText().toString().isEmpty()||
                edt_Password_Sign.getText().toString().isEmpty()||
                edt_Password_Sign_Confirm.getText().toString().isEmpty()||
                edt_FullName_Sign.getText().toString().isEmpty()
        ){
            Toast.makeText(this, "Phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edt_UserName_Sign.getText().toString().length()<6){
            Toast.makeText(this, "Tên đăng nhập phải lớn hơn 5 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(edt_Password_Sign.getText().toString().equals(edt_Password_Sign_Confirm.getText().toString()))){
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        String pass = edt_Password_Sign.getText().toString();
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

        if(checkuser){
            Toast.makeText(Sign_Account.this,"Username đã tồn tại",Toast.LENGTH_SHORT).show();
            return;
        }
        db.insertUser_sign_1(edt_UserName_Sign.getText().toString(),edt_Password_Sign.getText().toString(),edt_FullName_Sign.getText().toString());
        Intent intent = new Intent(Sign_Account.this,Login_Account.class);
        startActivity(intent);
    }
}