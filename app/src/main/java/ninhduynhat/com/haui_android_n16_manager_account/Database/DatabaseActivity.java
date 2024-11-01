package ninhduynhat.com.haui_android_n16_manager_account.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ninhduynhat.com.haui_android_n16_manager_account.R;

public class DatabaseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView = findViewById(R.id.textView);
        dbHelper = new DatabaseHelper(this);

        // Khởi tạo cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Khởi tạo lại cơ sở dữ liệu
//        dbHelper.onCreate(db);

//        // Thêm dữ liệu vào bảng User
        db.execSQL("INSERT INTO USER (Username, Password, Fullname, PhoneNumber, LivingExpenses, MoneyForStudying, DebtMoney) " +
                "VALUES ('quangkedo', 'Quang123', \"Nguyễn Minh Quang\", '0334973459', 1000000.0, 10000000.0, 000000.0)");
        db.execSQL("INSERT INTO USER (Username, Password, Fullname, PhoneNumber, LivingExpenses, MoneyForStudying, DebtMoney) " +
                "VALUES ('trunghuynh', 'Huynh123', \"Bùi Trung Huỳnh\", '0334567899', 1000000.0, 10000000.0, 000000.0)");

        //thêm dữ liệu cho bảng khoản chi
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '1', \"Thực phẩm & Đồ uống\", 10000.0, '06/06/2024', \"Gà rán\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '1', \"Sức khỏe\", 15000.0, '05/06/2024', \"Thuốc đau đầu\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '1', \"Chi phí nhà ở\", 1500000.0, '04/06/2024', \"Tiền trọ\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '1', \"Đầu tư\", 200000.0, '03/06/2024', \"Chơi chứng khoán\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '1', \"Chi phí đi lại\", 80000.0, '02/06/2024', \"Về quê\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '1', \"Du lịch\", 500000.0, '01/06/2024', \"Đi chùa Bái Đính\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '1', \"Chi phí khác\", 10000.0, '08/06/2024', \"Công đức chùa\")");


        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Thực phẩm & Đồ uống\", 12000.0, '18/06/2024', \"Bánh mì thịt\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Sức khỏe\", 15000.0, '18/06/2024', \"Thuốc cảm cúm\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí nhà ở\", 1500000.0, '04/06/2024', \"Tiền trọ\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Đầu tư\", 200000.0, '18/06/2024', \"Chứng khoán\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí đi lại\", 200000.0, '02/06/2024', \"Về quê\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Du lịch\", 500000.0, '01/06/2024', \"Đi du lịch\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí khác\", 10000.0, '18/06/2024', \"Xe hư\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí khác\", 10000.0, '18/06/2024', \"Xe hư\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí khác\", 10000.0, '07/06/2024', \"Xe hư\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí khác\", 10000.0, '18/06/2024', \"Xe hư\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí khác\", 10000.0, '07/06/2024', \"Xe hư\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí đi lại\", 200000.0, '18/06/2024', \"Về quê\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí đi lại\", 200000.0, '02/06/2024', \"Về quê\")");
        db.execSQL("INSERT INTO EXPENSES (UserID,ExpensesType, AmountSpent, DateSpent, Description) " +
                "VALUES ( '2', \"Chi phí đi lại\", 200000.0, '18/06/2024', \"Về quê\")");
//
//        // Truy vấn dữ liệu từ bảng User
//        Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
//        StringBuilder stringBuilder = new StringBuilder();
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow("UserID"));
//                String username = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
//                String password = cursor.getString(cursor.getColumnIndexOrThrow("Password"));
//                String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("PhoneNumber"));
//                double soDuSinhHoat = cursor.getDouble(cursor.getColumnIndexOrThrow("LivingExpenses"));
//                double soDuCongNo = cursor.getDouble(cursor.getColumnIndexOrThrow("MoneyForStudying"));
//                double tienNoCong = cursor.getDouble(cursor.getColumnIndexOrThrow("DebtMoney"));
//
//                stringBuilder.append("ID: ").append(id).append(", Username: ").append(username)
//                        .append(", Password: ").append(password).append(", PhoneNumber: ").append(phoneNumber)
//                        .append(", LivingExpenses: ").append(soDuSinhHoat).append(", MoneyForStudying: ").append(soDuCongNo)
//                        .append(", DebtMoney: ").append(tienNoCong).append("\n");
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        // Hiển thị dữ liệu
//        textView.setText(stringBuilder.toString());

//        // Chèn dữ liệu thử vào bảng USER
//        long userId1 = dbHelper.insertUser("john_doe", "password123", "1234567890", 1000.0, 500.0, 200.0);
//        long userId2 = dbHelper.insertUser("jane_doe", "password456", "0987654321", 2000.0, 1500.0, 300.0);
//
//        // Chèn dữ liệu thử vào bảng EXPENSES
//        dbHelper.insertExpenses((int) userId1, "Food", 100.0, "2023-05-31", "Lunch");
//        dbHelper.insertExpenses((int) userId2, "Transport", 50.0, "2023-05-31", "Bus ticket");
//
//        // Chèn dữ liệu thử vào bảng PLANNING
//        dbHelper.insertPlanning("Save for bike", 1000.0, 500.0, "2024-12-31", "Personal");
//        dbHelper.insertPlanning("Vacation", 2000.0, 1500.0, "2024-06-30", "Family");
//
//        // Chèn dữ liệu thử vào bảng SUBJECT
        long subjectId1 = dbHelper.insertSubject("Lập trình C++", 3, 1);
        long subjectId2 = dbHelper.insertSubject("English 1", 5, 1);
        long subjectId3 = dbHelper.insertSubject("Triết học", 3, 1);
        long subjectId4 = dbHelper.insertSubject("Hệ thống cơ sở dữ liệu", 4, 1);
        long subjectId5 = dbHelper.insertSubject("Kiến trúc máy tính", 3, 1);
        long subjectId6 = dbHelper.insertSubject("Hệ điều hành", 3, 1);
        long subjectId7 = dbHelper.insertSubject("Phân tích thiết kế phần mềm", 3, 1);
        long subjectId8 = dbHelper.insertSubject("Lập trình Java", 3, 2);
        long subjectId9 = dbHelper.insertSubject("Lập trình .NET", 3, 2);
        long subjectId10 = dbHelper.insertSubject("Kinh tế chính trị", 2, 2);
        long subjectId11 = dbHelper.insertSubject("Kỹ thuật số", 3, 2);
        long subjectId11_1 = dbHelper.insertSubject("English 2", 5, 2);
        long subjectId12 = dbHelper.insertSubject("English 3", 5, 3);
        long subjectId13 = dbHelper.insertSubject("Lập trình Java nâng cao", 3, 3);
        long subjectId13_1 = dbHelper.insertSubject("Chủ nghĩa xã hội", 3, 3);
        long subjectId13_2 = dbHelper.insertSubject("Trí tuệ nhân tạo", 3, 3);
        long subjectId13_3 = dbHelper.insertSubject("Tối ưu hóa", 3, 3);
        long subjectId13_4 = dbHelper.insertSubject("Xác xuất thống kê", 3, 3);
        long subjectId14 = dbHelper.insertSubject("English 4", 5, 4);
        long subjectId15 = dbHelper.insertSubject("Phân tích dữ liệu lớn", 3, 4);
        long subjectId16 = dbHelper.insertSubject("Công nghệ đa phương tiện", 3, 4);
        long subjectId17 = dbHelper.insertSubject("Ứng dụng thuật toán", 3, 4);
        long subjectId18 = dbHelper.insertSubject("Lịch sử đảng", 2, 4);
        long subjectId19 = dbHelper.insertSubject("Lập trình game", 3, 4);
        long subjectId20 = dbHelper.insertSubject("English 5", 5, 5);
        long subjectId21 = dbHelper.insertSubject("Phát triển ứng dụng trên thiết bị di động", 3, 5);
        long subjectId22 = dbHelper.insertSubject("Tư tưởng Hồ Chí Minh", 2, 5);
        long subjectId23 = dbHelper.insertSubject("Thiết kế đồ họa 2D", 3, 5);
        long subjectId24 = dbHelper.insertSubject("Thực tập cơ sở ngành", 3, 5);
        long subjectId25 = dbHelper.insertSubject("English 6", 5, 6);
        long subjectId26 = dbHelper.insertSubject("Quản trị mạn trên hệ điều hành Windows", 3, 6);
        long subjectId27 = dbHelper.insertSubject("Phát luật đại cương", 3, 6);
        long subjectId28 = dbHelper.insertSubject("Tương tác người máy", 3, 6);
        long subjectId29 = dbHelper.insertSubject("An ninh mạng", 3, 6);
        long subjectId30 = dbHelper.insertSubject("Thiết kế web nâng cao", 3, 6);
        long subjectId31 = dbHelper.insertSubject("Kiểm thử phần mềm", 3, 7);
        long subjectId32 = dbHelper.insertSubject("Đồ án chuyên ngành", 3, 7);
        long subjectId33 = dbHelper.insertSubject("Tích hợp hệ thống phần mềm", 3, 7);
        long subjectId34 = dbHelper.insertSubject("Lập trình web bằng Java", 3, 7);
        long subjectId35 = dbHelper.insertSubject("Đảm bảo chất lượng phần mềm", 3, 7);
        long subjectId36 = dbHelper.insertSubject("Lập trình PHP nâng cao", 3, 7);
        long subjectId37 = dbHelper.insertSubject("Đồ án tốt nghiệp", 9, 8);
        long subjectId38 = dbHelper.insertSubject("Thực tập doanh nghiệp", 6, 8);
//
//        // Chèn dữ liệu thử vào bảng PayingTuition
//        dbHelper.insertPayingTuition( 1,1, "C++", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 2,"English", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 3,"English", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 11,"Android", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 5,"English", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 13,"Java", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 7,"English", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 6,"English", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 8,"English", 415000, 0);
//        dbHelper.insertPayingTuition( 1, 8,"English", 415000, 1);

        // Xóa tất cả dữ liệu từ bảng USER (ví dụ)
        // dbHelper.deleteAllFromTable("USER");
//        dbHelper.deleteAllFromTable("SUBJECT");

        //Xóa bảng
//        dbHelper.dropTable("SUBJECT");

//        // Hiển thị thông tin chèn vào thành công
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("Inserted User IDs: ").append(userId1).append(", ").append(userId2).append("\n");
//        stringBuilder.append("Inserted Subject IDs: ").append(subjectId1).append(", ").append(subjectId2).append("\n");
//        textView.setText(stringBuilder.toString());
    }

}