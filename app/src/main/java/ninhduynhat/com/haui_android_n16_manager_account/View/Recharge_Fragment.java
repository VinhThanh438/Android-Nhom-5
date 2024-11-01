package ninhduynhat.com.haui_android_n16_manager_account.View;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Login_Account;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;


public class Recharge_Fragment extends Fragment {

    EditText editText;
    Button buttonNap, btnQuayLai;
    TextView txtCongno, title;

    Spinner phuongThucThanhToanSpinner;
    EditText  soTaiKhoanEditText, tenNganHangEditText, chiNhanhNganHangEditText;
    LinearLayout bankTransferLayout;
    NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    private void getWidget(View view){
        editText = view.findViewById(R.id.editNapTien);
        buttonNap = view.findViewById(R.id.btnNapTien);
        btnQuayLai = view.findViewById(R.id.btnQuayLaiMHNap);
        txtCongno = view.findViewById(R.id.txtGoiYTien);
        title = view.findViewById(R.id.textviewTitle);

        bankTransferLayout = view.findViewById(R.id.bank_transfer_layout);
        soTaiKhoanEditText = view.findViewById(R.id.edit_text_bank_account);
        tenNganHangEditText = view.findViewById(R.id.edit_text_bank_name);
        chiNhanhNganHangEditText = view.findViewById(R.id.edit_text_bank_branch);
        phuongThucThanhToanSpinner = view.findViewById(R.id.spinner_payment_method);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge, container, false);

        getWidget(view);
        editText.requestFocus();

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LUU_TRANG_THAI_NGUOI_DUNG",MODE_PRIVATE);
        String user= sharedPreferences.getString("UserName","");

        int userId = databaseHelper.getUserByUsername(user).getUserID();

        UserObject userObject = databaseHelper.getUserById(userId);

        setTextView(userObject);

        // Sử dụng NumberFormat để định dạng số tiền
//        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
//        txtCongno.setText(numberFormat.format(userObject.getDebtMoney()) +" VND");
//        txtCongno.setText(numberFormat.format(userObject.getDebtMoney()));


        //Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phuongThucThanhToanSpinner.setAdapter(adapter);

        phuongThucThanhToanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMethod = parent.getItemAtPosition(position).toString();
                if (selectedMethod.equals("Nạp tiền bằng ngân hàng")) {
                    bankTransferLayout.setVisibility(View.VISIBLE);
                } else {
                    bankTransferLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bankTransferLayout.setVisibility(View.GONE);
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        buttonNap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double tienNap = Double.parseDouble(editText.getText().toString());

                    if (tienNap <= 0) {
                        showDialog("Số tiền nhập không hợp lệ. Vui lòng nhập số tiền lớn hơn 0");
                        editText.setText("");
                    } else {
                        String selectedMethod = phuongThucThanhToanSpinner.getSelectedItem().toString();
                        if (selectedMethod.equals("Nạp tiền bằng ngân hàng")) {
                            String soTaiKhoan = soTaiKhoanEditText.getText().toString().trim();
                            String tenNganHang = tenNganHangEditText.getText().toString().trim();
                            String chiNhanhNganHang = chiNhanhNganHangEditText.getText().toString().trim();

                            if (soTaiKhoan.isEmpty() || tenNganHang.isEmpty() || chiNhanhNganHang.isEmpty()) {
                                showDialog("Vui lòng nhập đầy đủ thông tin ngân hàng");
                                return;
                            }
                        }

                        userObject.setMoneyForStudying(tienNap + userObject.getMoneyForStudying());
                        databaseHelper.updateUser(userObject);
                        editText.setText("");
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                                R.array.payment_methods, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        phuongThucThanhToanSpinner.setAdapter(adapter);
//                        Toast.makeText(getContext(), "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                        setTextView(userObject);
                        showDialog("Nạp tiền thành công");
                        // Quay lại màn hình công nợ
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        fragmentManager.popBackStack();
                    }
                } catch (NumberFormatException e) {
//                    Toast.makeText(getContext(), "Vui lòng nhập số tiền hợp lệ.", Toast.LENGTH_SHORT).show();
                    showDialog("Vui lòng nhập số tiền hợp lệ");
                    editText.setText("");
                }
            }
        });

        return view;
    }

    private void setTextView(UserObject userObject){
//        if(userObject.getMoneyForStudying() < userObject.getDebtMoney()){
//            txtCongno.setText(numberFormat.format(userObject.getDebtMoney() - userObject.getMoneyForStudying()) +" VND");
//        }
//        else{
//            title.setText("Số tiền còn nợ: ");
//            txtCongno.setText(numberFormat.format(userObject.getDebtMoney()) +" VND");
//        }
        txtCongno.setText(numberFormat.format(userObject.getDebtMoney()) +" VND");
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}