package ninhduynhat.com.haui_android_n16_manager_account.View;

import static ninhduynhat.com.haui_android_n16_manager_account.Login_Account.LUU_TRANG_THAI_NGUOI_DUNG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ninhduynhat.com.haui_android_n16_manager_account.Adapters.ChiPhiSpiner_Adapter;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.ExpensesObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.LoaiChiPhi;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class sua_chi_phi extends AppCompatActivity {
    private EditText mo_ta_chi_phi_2,gia_chi_phi_2;
    private TextView date_picker_3,thoi_gian_mua_3;
    private Spinner loai_chi_phi_2;
    private Button thoatDialogsuachiphi_2,nhanSuaChiPhi_2;
    private LoaiChiPhi loaiChiPhi[];
    SharedPreferences sharedPreferences;
    private String loaichi= "";
    DatabaseHelper databaseHelper;
    int user_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_chi_phi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findId();
        Intent intent= getIntent();
        user_id=intent.getIntExtra("UserId",0);
        Collator collator = Collator.getInstance(Locale.FRENCH);
        collator.setStrength(Collator.PRIMARY);
        try {
            databaseHelper = new DatabaseHelper(sua_chi_phi.this);
        ExpensesObject expensesObjects=databaseHelper.getExpensesByIdExpense(user_id);
        gia_chi_phi_2.setText(expensesObjects.getAmountSpent()+"");
        thoi_gian_mua_3.setText(expensesObjects.getDateSpent());
        mo_ta_chi_phi_2.setText(expensesObjects.getDescription());
        LoaiChiPhi loaiChiPhi1[]=LoaiChiPhi.values();
        for (int i=0;i<loaiChiPhi1.length;i++){
            if(collator.compare(loaiChiPhi1[i].tenchiphi,expensesObjects.getExpensesType())==0 ){
                loai_chi_phi_2.setSelection(i);
            }
        }
        }catch (Exception e){
            Log.e("check lỗi lấy dữ liệu",""+e);
        }



        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        Date date = new Date(nam-1900,thang,ngay);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateStrings = df.format(date);
        thoi_gian_mua_3.setText(dateStrings);
        date_picker_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(sua_chi_phi.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            Date date = new Date(year-1900,month,dayOfMonth);
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String dateString = df.format(date);
                            thoi_gian_mua_3.setText(dateString);
                        }
                        catch (Exception e){
                            Toast.makeText(sua_chi_phi.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, nam, thang, ngay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Chọn thời gian");
                dialog.show();

            }
        });


        //nút thêm chức năng thêm khoản chi
        nhanSuaChiPhi_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xulyDauVao()){
                    finish();
                }
            }
        });
        //thoát chức năng thêm chi phí
        thoatDialogsuachiphi_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //spiner chọn lại chi phí

        loai_chi_phi_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoaiChiPhi loaiChiPhi1[]=LoaiChiPhi.values();
                loaichi=loaiChiPhi1[position].tenchiphi;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void findId(){
        date_picker_3=findViewById(R.id.date_picker_3);
        loai_chi_phi_2= findViewById(R.id.loai_chi_phi_2);
        thoi_gian_mua_3=findViewById(R.id.thoi_gian_mua_3);
        mo_ta_chi_phi_2=findViewById(R.id.mo_ta_chi_phi_2);
        thoatDialogsuachiphi_2=findViewById(R.id.thoatDialogsuachiphi_2);
        nhanSuaChiPhi_2=findViewById(R.id.nhanSuaChiPhi_2);
        gia_chi_phi_2=findViewById(R.id.gia_chi_phi_2);

        loaiChiPhi=LoaiChiPhi.values();
        ChiPhiSpiner_Adapter chiPhiSpinerAdapter=new ChiPhiSpiner_Adapter(sua_chi_phi.this,loaiChiPhi);
        loai_chi_phi_2.setAdapter(chiPhiSpinerAdapter);
    }
    private UserObject getDataUserName(){
        sharedPreferences =getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG,MODE_PRIVATE);
        databaseHelper= new DatabaseHelper(sua_chi_phi.this);
        UserObject userObject= new UserObject();
        String user_name=sharedPreferences.getString("UserName","");
        userObject=databaseHelper.getUserByUsername_Home(user_name);
        return userObject;
    }

    private boolean xulyDauVao(){
        databaseHelper= new DatabaseHelper(sua_chi_phi.this);
        if(mo_ta_chi_phi_2.getText().toString().isEmpty()||gia_chi_phi_2.getText().toString().isEmpty()){
            Toast.makeText(this, "Phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            ExpensesObject expensesObject=databaseHelper.getExpensesByIdExpense(user_id);
            double gia= Double.parseDouble(gia_chi_phi_2.getText().toString());
            if(gia<1000){
                Toast.makeText(this, "Chi phí phải lớn hơn 1000", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                UserObject userObject=new UserObject();
                userObject=getDataUserName();

                String ngaymua=thoi_gian_mua_3.getText().toString();
                String mota=mo_ta_chi_phi_2.getText().toString();

                double sodumoi=userObject.getLivingExpenses()-gia+expensesObject.getAmountSpent();
                if(sodumoi>=0){
                    databaseHelper.update_LivingExpenses(userObject.getUserID(),sodumoi);
                    databaseHelper.updateKhoanChi(user_id,loaichi,gia,ngaymua,mota);
                    return true;
                }else {
                    Toast.makeText(this, "Số dư không đủ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

    }
}