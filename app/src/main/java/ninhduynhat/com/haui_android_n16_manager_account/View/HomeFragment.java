package ninhduynhat.com.haui_android_n16_manager_account.View;

import static android.content.Context.MODE_PRIVATE;
import static ninhduynhat.com.haui_android_n16_manager_account.Login_Account.LUU_TRANG_THAI_NGUOI_DUNG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import android.widget.CalendarView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ninhduynhat.com.haui_android_n16_manager_account.Adapters.ChiPhiSpiner_Adapter;
import ninhduynhat.com.haui_android_n16_manager_account.Adapters.Chi_Phi_Adapter;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.ExpensesObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.LoaiChiPhi;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;


public class HomeFragment extends Fragment {

    private RecyclerView rcl_Chi_Phi;
    private Chi_Phi_Adapter chiPhiAdapter;
    private CircleImageView home_imgAvartar;
    private FloatingActionButton floating_add;
    private SharedPreferences sharedPreferences;
    private LoaiChiPhi loaiChiPhi[];
    private DatabaseHelper databaseHelper;
    private TextView home_txtTen,soduhientai;
    private CalendarView calendar_view_calendar;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_home, container, false);
        rcl_Chi_Phi=rootView.findViewById(R.id.rcl_Chi_Phi);
        databaseHelper= new DatabaseHelper(getActivity());
        UserObject userObject= new UserObject();
        userObject=getDataUserName();

        //lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        Date date = new Date(nam-1900,thang,ngay);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = df.format(date);

        rcl_Chi_Phi.setLayoutManager(new LinearLayoutManager(getActivity()));
        chiPhiAdapter = new Chi_Phi_Adapter();

        chiPhiAdapter.setData(getActivity(),setDataCholistKhoanchi(userObject.getUserID(),dateString));
//        set adapter cho list chi phí
        rcl_Chi_Phi.setAdapter(chiPhiAdapter);
        chiPhiAdapter.notifyDataSetChanged();


        return rootView;
    }


    private UserObject getDataUserName(){
        sharedPreferences =getActivity().getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG,MODE_PRIVATE);
        databaseHelper= new DatabaseHelper(getActivity());
        UserObject userObject= new UserObject();
        String user_name=sharedPreferences.getString("UserName","");
        userObject=databaseHelper.getUserByUsername_Home(user_name);
        return userObject;
    }

    //hàm lấy dữ liệu khoản chi từ csdl
    private List<ExpensesObject> setDataCholistKhoanchi(int userid,String dateOfMonth) {
        databaseHelper= new DatabaseHelper(getActivity());
        return databaseHelper.getExpensesObjectOfDate(userid,dateOfMonth);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        home_imgAvartar=view.findViewById(R.id.home_imgAvartar);
        floating_add=view.findViewById(R.id.floating_add);
        home_txtTen=view.findViewById(R.id.home_txtTen);
        soduhientai=view.findViewById(R.id.soduhientai);
        calendar_view_calendar=view.findViewById(R.id.calendar_view_calendar);


        calendar_view_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                xuLyHienThiChiPhiTheoNgay(year,month,dayOfMonth);
            }
        });

        UserObject userObjects=new UserObject();
        userObjects=getDataUserName();
        home_txtTen.setText(userObjects.getFullname());
        if(userObjects!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            String formattedNumber = numberFormat.format(userObjects.getLivingExpenses());
            soduhientai.setText("Số dư: "+formattedNumber+" VND");
        }else {
            soduhientai.setText("Số dư: 0 VND");
        }


        home_imgAvartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), man_hinh_thontincanhan.class);
                startActivity(intent);
            }
        });
        //thêm khoản chi
        floating_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),add_chi_phi.class);
                startActivity(intent);
            }
        });

        rcl_Chi_Phi.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy>0){
                    floating_add.hide();
                }else {
                    floating_add.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    private void xuLyHienThiChiPhiTheoNgay(int year, int month, int dayOfMonth){
        databaseHelper= new DatabaseHelper(getActivity());
        UserObject userObject= new UserObject();
        userObject=getDataUserName();
        rcl_Chi_Phi.setLayoutManager(new LinearLayoutManager(getActivity()));
        chiPhiAdapter = new Chi_Phi_Adapter();
        Date date = new Date(year-1900,month,dayOfMonth);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = df.format(date);
        chiPhiAdapter.setData(getActivity(),setDataCholistKhoanchi(userObject.getUserID(),dateString));
        //set adapter cho list chi phí
        rcl_Chi_Phi.setAdapter(chiPhiAdapter);
        chiPhiAdapter.notifyDataSetChanged();
    }


    private void xuLySuaXoaChiPhi(){

    }

    @Override
    public void onResume() {
        super.onResume();
        databaseHelper= new DatabaseHelper(getActivity());
        UserObject userObject= new UserObject();
        userObject=getDataUserName();
        //lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        Date date = new Date(nam-1900,thang,ngay);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = df.format(date);
        rcl_Chi_Phi.setLayoutManager(new LinearLayoutManager(getActivity()));
        chiPhiAdapter = new Chi_Phi_Adapter();
        chiPhiAdapter.setData(getActivity(),setDataCholistKhoanchi(userObject.getUserID(),dateString));
        //set adapter cho list chi phí
        rcl_Chi_Phi.setAdapter(chiPhiAdapter);
        chiPhiAdapter.notifyDataSetChanged();

        if(userObject!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            String formattedNumber = numberFormat.format(userObject.getLivingExpenses());
            soduhientai.setText("Số dư: "+formattedNumber+" VND");
        }else {
            soduhientai.setText("Số dư: 0 VND");
        }
        
        Bitmap bitmap=StringToBitMap(userObject.getImage());
        if(bitmap!=null){
            home_imgAvartar.setImageBitmap(bitmap);
        }

    }

    @androidx.annotation.Nullable
    private Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}