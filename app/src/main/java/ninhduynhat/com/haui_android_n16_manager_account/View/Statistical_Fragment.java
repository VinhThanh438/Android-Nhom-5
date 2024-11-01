package ninhduynhat.com.haui_android_n16_manager_account.View;

import static android.content.Context.MODE_PRIVATE;
import static ninhduynhat.com.haui_android_n16_manager_account.Login_Account.LUU_TRANG_THAI_NGUOI_DUNG;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.Collator;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.DashboardViewModel;
import ninhduynhat.com.haui_android_n16_manager_account.Model.ExpensesObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
//import ninhduynhat.com.haui_android_n16_manager_account.R;
import ninhduynhat.com.haui_android_n16_manager_account.databinding.FragmentThongKeBinding;


public class Statistical_Fragment extends Fragment {
    private FragmentThongKeBinding binding;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    private TextView displayDate,displayMonth,displayYear;
    ListView listMonthlyExpense;
    //    private DBHandler dbHandler;
    private Button btnDaily, btnmonthly, btnyearly;

    private View screen1, screen2, screen3, screen4;
    int[] colorClassArray = new int[]{Color.LTGRAY, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.MAGENTA, Color.RED};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentThongKeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        displayDate=binding.displayDate;
        displayMonth=binding.displayMonth;
        displayYear=binding.displayYear;
        screen1 = binding.dailyStatistic;
        screen2 = binding.weeklyStatistic;
        screen3 = binding.monthlyStatistic;
        screen4 = binding.yearlyStatistic;
        btnDaily = binding.btndaily;
        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
                showScreen1();

            }
        });
//        btnweekly = binding.btnweekly;
        btnmonthly = binding.btnmonthly;
        btnmonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthPickerDialog();
                showScreen3();
            }
        });
        btnyearly = binding.btnyearly;
        btnyearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYearPickerDialog();
                showScreen4();
            }
        });
        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PieChart dailyPieChart, weeklyPieChart, monthlyPieChart, yearlyPiechart;
        LineChart yearlyLineChart, monthlyLineChart;

        ListView dailyList, weeklyList, monthlyList, yearlyList;
        //lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        Date date = new Date(nam-1900,thang,ngay);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = df.format(date);
        displayDate.setText(dateString);

        UserObject userObject= new UserObject();
        userObject=getDataUserName();
        List<ExpensesObject>  list=databaseHelper.getExpensesObjectOfDate(userObject.getUserID(),dateString);

        Collator collator = Collator.getInstance(Locale.FRENCH);
        collator.setStrength(Collator.PRIMARY);
        float n1=0,n2=0,n3=0,n4=0,n5=0,n6=0,n7=0;
        for(int i=0;i<list.size();i++){
            if(collator.compare(list.get(i).getExpensesType(),"Thực phẩm & Đồ uống")==0){
                n1+= (float) list.get(i).getAmountSpent();
            } else if (collator.compare(list.get(i).getExpensesType(),"Sức khỏe")==0) {
                n2+= (float) list.get(i).getAmountSpent();
            } else if (collator.compare(list.get(i).getExpensesType(),"Chi phí nhà ở")==0) {
                n3+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Đầu tư")==0) {
                n4+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Chi phí đi lại")==0) {
                n5+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Du lịch")==0) {
                n6+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Chi phí khác")==0) {
                Log.e("check dữ liệu chi phí khác",n7+"");
                n7+= (float) list.get(i).getAmountSpent();
            }

        }
        ArrayList<PieEntry> dataVals = new ArrayList<>();
        dataVals.add(new PieEntry(n1,"Thực phẩm & Đồ uống"));
        dataVals.add(new PieEntry(n2,"Sức khỏe"));
        dataVals.add(new PieEntry(n3,"Chi phí nhà ở"));
        dataVals.add(new PieEntry(n4,"Đầu tư"));
        dataVals.add(new PieEntry(n5,"Chi phí đi lại"));
        dataVals.add(new PieEntry(n6,"Du lịch"));
        dataVals.add(new PieEntry(n7,"Chi phí khác"));

        List<PieEntry> filteredDataVals = new ArrayList<>();
        for (PieEntry entry : dataVals) {
            if (entry.getValue() != 0) {
                filteredDataVals.add(entry);
            }
        }


        //Nhập dữ liệu cho biểu đồ thống kê ngày //
        dailyPieChart = binding.dailyPieChart;
        PieDataSet piedataset = new PieDataSet(filteredDataVals,"");
        piedataset.setColors(colorClassArray);
        PieData pieData = new PieData(piedataset);
        dailyPieChart.setData(pieData);
        dailyPieChart.setCenterTextSize(25);
        dailyPieChart.setUsePercentValues(true);
        dailyPieChart.setCenterText("daily expense");
        dailyPieChart.invalidate();
        NumberFormat numberFormat = NumberFormat.getInstance();
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Thực phẩm & Đồ uống - "+numberFormat.format(n1)+" VND");
        list1.add("Sức khỏe - "+numberFormat.format(n2)+" VND");
        list1.add("Chi phí nhà ở - "+numberFormat.format(n3)+" VND");
        list1.add("Đầu tư - "+numberFormat.format(n4)+" VND");
        list1.add("Chi phí đi lại - "+numberFormat.format(n5)+" VND");
        list1.add("Du lịch - "+numberFormat.format(n6)+" VND");
        list1.add("Chi phí khác - "+numberFormat.format(n7)+" VND");

        ArrayList<String> List = new ArrayList<>();
        List.addAll(list1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, List);
        dailyList = binding.listDailyExpense;
        dailyList.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }


    private void showScreen1() {
        screen1.setVisibility(View.VISIBLE);
        screen2.setVisibility(View.GONE);
        screen3.setVisibility(View.GONE);
        screen4.setVisibility(View.GONE);
    }

    private void showScreen3() {

        screen1.setVisibility(View.GONE);
        screen2.setVisibility(View.GONE);
        screen3.setVisibility(View.VISIBLE);
        screen4.setVisibility(View.GONE);
    }
    private void showScreen4() {

        screen1.setVisibility(View.GONE);
        screen2.setVisibility(View.GONE);
        screen3.setVisibility(View.GONE);
        screen4.setVisibility(View.VISIBLE);
    }

    //hàm hiển thị thống kê theo ngày
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                try {
                    Date date = new Date(year-1900,month,dayOfMonth);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = df.format(date);
                    dailydata1(dateString);
                }
                catch (Exception e){

                }
            }
        }, nam, thang, ngay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle("Chọn thời gian");
        dialog.show();
    }

    private void dailydata1(String ngayhientai){
        databaseHelper= new DatabaseHelper(getActivity());

        displayDate.setText(ngayhientai);
        PieChart dailyPieChart;
        ListView dailyList;

        UserObject userObject= new UserObject();
        userObject=getDataUserName();
        List<ExpensesObject>  list=databaseHelper.getExpensesObjectOfDate(userObject.getUserID(),ngayhientai);
        Collator collator = Collator.getInstance(Locale.FRENCH);
        collator.setStrength(Collator.PRIMARY);
        float n1=0,n2=0,n3=0,n4=0,n5=0,n6=0,n7=0;
        for(int i=0;i<list.size();i++){
            if(collator.compare(list.get(i).getExpensesType(),"Thực phẩm & Đồ uống")==0){
                n1+= (float) list.get(i).getAmountSpent();
            } else if (collator.compare(list.get(i).getExpensesType(),"Sức khỏe")==0) {
                n2+= (float) list.get(i).getAmountSpent();
            } else if (collator.compare(list.get(i).getExpensesType(),"Chi phí nhà ở")==0) {
                n3+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Đầu tư")==0) {
                n4+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Chi phí đi lại")==0) {
                n5+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Du lịch")==0) {
                n6+= (float) list.get(i).getAmountSpent();
            }else if (collator.compare(list.get(i).getExpensesType(),"Chi phí khác")==0) {
                Log.e("check dữ liệu chi phí khác",n7+"");
                n7+= (float) list.get(i).getAmountSpent();
            }

        }


        ArrayList<PieEntry> dataVals = new ArrayList<>();
        dataVals.add(new PieEntry(n1,"Thực phẩm & Đồ uống"));
        dataVals.add(new PieEntry(n2,"Sức khỏe"));
        dataVals.add(new PieEntry(n3,"Chi phí nhà ở"));
        dataVals.add(new PieEntry(n4,"Đầu tư"));
        dataVals.add(new PieEntry(n5,"Chi phí đi lại"));
        dataVals.add(new PieEntry(n6,"Du lịch"));
        dataVals.add(new PieEntry(n7,"Chi phí khác"));
        List<PieEntry> filteredDataVals = new ArrayList<>();
        for (PieEntry entry : dataVals) {
            if (entry.getValue() != 0) {
                filteredDataVals.add(entry);
            }
        }


        //Nhập dữ liệu cho biểu đồ thống kê ngày //
        dailyPieChart = binding.dailyPieChart;
        PieDataSet piedataset = new PieDataSet(filteredDataVals,"");
        piedataset.setColors(colorClassArray);
        PieData pieData = new PieData(piedataset);
        dailyPieChart.setData(pieData);
        dailyPieChart.setCenterTextSize(25);
        dailyPieChart.setUsePercentValues(true);
        dailyPieChart.setCenterText("daily expense");
        dailyPieChart.invalidate();

        NumberFormat numberFormat = NumberFormat.getInstance();
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Thực phẩm & Đồ uống - "+numberFormat.format(n1) +" VND");
        list1.add("Sức khỏe - "+numberFormat.format(n2)+" VND");
        list1.add("Chi phí nhà ở - "+numberFormat.format(n3)+" VND");
        list1.add("Đầu tư - "+numberFormat.format(n4)+" VND");
        list1.add("Chi phí đi lại - "+numberFormat.format(n5)+" VND");
        list1.add("Du lịch - "+numberFormat.format(n6)+" VND");
        list1.add("Chi phí khác - "+numberFormat.format(n7)+" VND");

        ArrayList<String> List = new ArrayList<>();
        List.addAll(list1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, List);
        dailyList = binding.listDailyExpense;
        dailyList.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }



    private UserObject getDataUserName(){
        sharedPreferences =getActivity().getSharedPreferences(LUU_TRANG_THAI_NGUOI_DUNG,MODE_PRIVATE);
        databaseHelper= new DatabaseHelper(getActivity());
        UserObject userObject= new UserObject();
        String user_name=sharedPreferences.getString("UserName","");
        userObject=databaseHelper.getUserByUsername_Home(user_name);
        return userObject;
    }
    

//hiển thị thống kê theo tháng
    private void showMonthPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                try {
                    Date date = new Date(year-1900,month,dayOfMonth);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = df.format(date);
                    Date dates = df.parse(dateString);
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
                    String months = monthFormat.format(date);
                    monthlydata1(months);
                }
                catch (Exception e){

                }
            }
        }, nam, thang, ngay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle("Chọn thời gian");
        dialog.show();
    }

    private void monthlydata1(String thanghientai){

        displayMonth.setText("Thống kê theo tháng: "+thanghientai);
        PieChart  monthlyPiechart;
        ListView  monthlyList;
        databaseHelper= new DatabaseHelper(getActivity());

        UserObject userObject= new UserObject();
        userObject=getDataUserName();
        //danh sách các loại chi tiêu trong một tháng
        List<ExpensesObject>  list_1= databaseHelper.getExpensesObjectOfMonth(userObject.getUserID(),thanghientai);

        Collator collator = Collator.getInstance(Locale.FRENCH);
        collator.setStrength(Collator.PRIMARY);
        float n1=0,n2=0,n3=0,n4=0,n5=0,n6=0,n7=0;
        for(int i=0;i<list_1.size();i++){
            if(collator.compare(list_1.get(i).getExpensesType(),"Thực phẩm & Đồ uống")==0){
                n1+= (float) list_1.get(i).getAmountSpent();
            } else if (collator.compare(list_1.get(i).getExpensesType(),"Sức khỏe")==0) {
                n2+= (float) list_1.get(i).getAmountSpent();
            } else if (collator.compare(list_1.get(i).getExpensesType(),"Chi phí nhà ở")==0) {
                n3+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Đầu tư")==0) {
                n4+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Chi phí đi lại")==0) {
                n5+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Du lịch")==0) {
                n6+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Chi phí khác")==0) {
                Log.e("check dữ liệu chi phí khác",n7+"");
                n7+= (float) list_1.get(i).getAmountSpent();
            }

        }

        ArrayList<PieEntry> dataVals = new ArrayList<>();
        dataVals.add(new PieEntry(n1,"Thực phẩm & Đồ uống"));
        dataVals.add(new PieEntry(n2,"Sức khỏe"));
        dataVals.add(new PieEntry(n3,"Chi phí nhà ở"));
        dataVals.add(new PieEntry(n4,"Đầu tư"));
        dataVals.add(new PieEntry(n5,"Chi phí đi lại"));
        dataVals.add(new PieEntry(n6,"Du lịch"));
        dataVals.add(new PieEntry(n7,"Chi phí khác"));



        //Nhập dữ liệu cho biểu đồ thống kê tháng //
        List<PieEntry> filteredDataVals = new ArrayList<>();
        for (PieEntry entry : dataVals) {
            if (entry.getValue() != 0) {
                filteredDataVals.add(entry);
            }
        }


        monthlyPiechart = binding.monthlyPieChart;
        PieDataSet piedataset3 = new PieDataSet(filteredDataVals,"");
        piedataset3.setColors(colorClassArray);
        PieData pieData3 = new PieData(piedataset3);
        monthlyPiechart.setData(pieData3);
        monthlyPiechart.setCenterTextSize(25);
        monthlyPiechart.setUsePercentValues(true);
        monthlyPiechart.setCenterText("monthly expense");
        monthlyPiechart.invalidate();



        NumberFormat numberFormat = NumberFormat.getInstance();
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("Thực phẩm & Đồ uống - "+numberFormat.format(n1)+" VND");
        list3.add("Sức khỏe - "+numberFormat.format(n2)+" VND");
        list3.add("Chi phí nhà ở - "+numberFormat.format(n3)+" VND");
        list3.add("Đầu tư - "+numberFormat.format(n4)+" VND");
        list3.add("Chi phí đi lại - "+numberFormat.format(n5)+" VND");
        list3.add("Du lịch - "+numberFormat.format(n6)+" VND");
        list3.add("Chi phí khác - "+numberFormat.format(n7)+" VND");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list3);
        monthlyList = binding.listMonthlyExpense;
        monthlyList.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();

    }




//hiển thị thống kê theo năm
    private void showYearPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                try {
                    Date date = new Date(year-1900,month,dayOfMonth);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = df.format(date);
                    Date dates = df.parse(dateString);
                    SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy");
                    String months = monthFormat.format(date);
                    yearlydata1(months);

                }
                catch (Exception e){

                }
            }
        }, nam, thang, ngay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle("Chọn thời gian");
        dialog.show();
    }

    private void yearlydata1(String year){
        displayYear.setText("Thống kê theo năm: "+year);
        PieChart  yearlyPiechart;
        ListView yearlyList;
        databaseHelper= new DatabaseHelper(getActivity());

        UserObject userObject= new UserObject();
        userObject=getDataUserName();
        //danh sách các loại chi tiêu trong một tháng
        List<ExpensesObject>  list_1= databaseHelper.getExpensesObjectOfYear(userObject.getUserID(),year);

        Collator collator = Collator.getInstance(Locale.FRENCH);
        collator.setStrength(Collator.PRIMARY);
        float n1=0,n2=0,n3=0,n4=0,n5=0,n6=0,n7=0;
        for(int i=0;i<list_1.size();i++){
            if(collator.compare(list_1.get(i).getExpensesType(),"Thực phẩm & Đồ uống")==0){
                n1+= (float) list_1.get(i).getAmountSpent();
            } else if (collator.compare(list_1.get(i).getExpensesType(),"Sức khỏe")==0) {
                n2+= (float) list_1.get(i).getAmountSpent();
            } else if (collator.compare(list_1.get(i).getExpensesType(),"Chi phí nhà ở")==0) {
                n3+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Đầu tư")==0) {
                n4+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Chi phí đi lại")==0) {
                n5+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Du lịch")==0) {
                n6+= (float) list_1.get(i).getAmountSpent();
            }else if (collator.compare(list_1.get(i).getExpensesType(),"Chi phí khác")==0) {
                Log.e("check dữ liệu chi phí khác",n7+"");
                n7+= (float) list_1.get(i).getAmountSpent();
            }

        }

        ArrayList<PieEntry> dataVals = new ArrayList<>();
        dataVals.add(new PieEntry(n1,"Thực phẩm & Đồ uống"));
        dataVals.add(new PieEntry(n2,"Sức khỏe"));
        dataVals.add(new PieEntry(n3,"Chi phí nhà ở"));
        dataVals.add(new PieEntry(n4,"Đầu tư"));
        dataVals.add(new PieEntry(n5,"Chi phí đi lại"));
        dataVals.add(new PieEntry(n6,"Du lịch"));
        dataVals.add(new PieEntry(n7,"Chi phí khác"));

        List<PieEntry> filteredDataVals = new ArrayList<>();
        for (PieEntry entry : dataVals) {
            if (entry.getValue() != 0) {
                filteredDataVals.add(entry);
            }
        }

        ////////////////////////////
        //Nhập dữ liệu cho biểu đồ thống kê năm //
        yearlyPiechart = binding.yearlyPieChart;
        PieDataSet piedataset4 = new PieDataSet(filteredDataVals,"");
        piedataset4.setColors(colorClassArray);
        PieData pieData4 = new PieData(piedataset4);
        yearlyPiechart.setData(pieData4);
        yearlyPiechart.setCenterTextSize(25);
        yearlyPiechart.setUsePercentValues(true);
        yearlyPiechart.setCenterText("yearly expense");
        yearlyPiechart.invalidate();


        NumberFormat numberFormat = NumberFormat.getInstance();
        ArrayList<String> list4 = new ArrayList<>();
        list4.add("Thực phẩm & Đồ uống - "+numberFormat.format(n1)+" VND");
        list4.add("Sức khỏe - "+numberFormat.format(n2)+" VND");
        list4.add("Chi phí nhà ở - "+numberFormat.format(n3)+" VND");
        list4.add("Đầu tư - "+numberFormat.format(n4)+" VND");
        list4.add("Chi phí đi lại - "+numberFormat.format(n5)+" VND");
        list4.add("Du lịch - "+numberFormat.format(n6)+" VND");
        list4.add("Chi phí khác - "+numberFormat.format(n7)+" VND");

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list4);
        yearlyList = binding.listYearlyExpense;
        yearlyList.setAdapter(adapter4);
        adapter4.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}