package ninhduynhat.com.haui_android_n16_manager_account.View;

import static ninhduynhat.com.haui_android_n16_manager_account.Login_Account.LUU_TRANG_THAI_NGUOI_DUNG;

import android.app.DatePickerDialog;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ninhduynhat.com.haui_android_n16_manager_account.Adapters.ChiPhiSpiner_Adapter;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.LoaiChiPhi;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class add_chi_phi extends AppCompatActivity {

    private EditText mo_ta_chi_phi,gia_chi_phi;
    private TextView date_picker,thoi_gian_mua;
    private Spinner loai_chi_phi;
    private Button thoatDialogthemchiphi,nhanThemChiPhi;
    private LoaiChiPhi loaiChiPhi[];
    private DatabaseHelper databaseHelper;
    private String loaichi= "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_chi_phi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findId();
        //chọn ngày thêm chi phí
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog dialog = new DatePickerDialog(add_chi_phi.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            Date date = new Date(year-1900,month,dayOfMonth);
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String dateString = df.format(date);
                            thoi_gian_mua.setText(dateString);
                        }
                        catch (Exception e){
                            Toast.makeText(add_chi_phi.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, nam, thang, ngay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Chọn thời gian");
                dialog.show();

            }
        });
        
        
        //nút thêm chức năng thêm khoản chi
        nhanThemChiPhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xulyDauVao()){
                    finish();
                }


            }
        });
        //thoát chức năng thêm chi phí
        thoatDialogthemchiphi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        //spiner chọn lại chi phí
        
        loai_chi_phi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        gia_chi_phi=findViewById(R.id.gia_chi_phi);
        date_picker=findViewById(R.id.date_picker);
        loai_chi_phi= findViewById(R.id.loai_chi_phi);
        thoi_gian_mua=findViewById(R.id.thoi_gian_mua);
        mo_ta_chi_phi=findViewById(R.id.mo_ta_chi_phi);
        thoatDialogthemchiphi=findViewById(R.id.thoatDialogthemchiphi);
        nhanThemChiPhi=findViewById(R.id.nhanThemChiPhi);
        loaiChiPhi=LoaiChiPhi.values();
        ChiPhiSpiner_Adapter chiPhiSpinerAdapter=new ChiPhiSpiner_Adapter(add_chi_phi.this,loaiChiPhi);
        loai_chi_phi.setAdapter(chiPhiSpinerAdapter);

        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        Date date = new Date(nam-1900,thang,ngay);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = df.format(date);
        thoi_gian_mua.setText(dateString);
    }

    private UserObject getDataUserName(){
        sharedPreferences =getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG,MODE_PRIVATE);
        databaseHelper= new DatabaseHelper(add_chi_phi.this);
        UserObject userObject= new UserObject();
        String user_name=sharedPreferences.getString("UserName","");
        userObject=databaseHelper.getUserByUsername_Home(user_name);
        return userObject;
    }


    private boolean xulyDauVao(){
        if(mo_ta_chi_phi.getText().toString().isEmpty()&&gia_chi_phi.getText().toString().isEmpty()){
            Toast.makeText(this, "Phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        double gia= Double.parseDouble(gia_chi_phi.getText().toString());
        if(gia<1000){
            Toast.makeText(this, "Chi phí phải lớn hơn 1000", Toast.LENGTH_SHORT).show();
            return false;
        }
        UserObject userObject=new UserObject();
        userObject=getDataUserName();

        String ngaymua=thoi_gian_mua.getText().toString();
        String mota=mo_ta_chi_phi.getText().toString();
        databaseHelper= new DatabaseHelper(add_chi_phi.this);
        double sodumoi=userObject.getLivingExpenses()-gia;
        if(sodumoi>=0){
            databaseHelper.update_LivingExpenses(userObject.getUserID(),sodumoi);
            databaseHelper.insertUser_KhoanChi(userObject.getUserID(),loaichi,gia,ngaymua,mota);
            return true;

        }else {
            Toast.makeText(this, "Số dư không đủ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
