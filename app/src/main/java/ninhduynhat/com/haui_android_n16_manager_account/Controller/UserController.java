package ninhduynhat.com.haui_android_n16_manager_account.Controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.UserObject;

public class UserController {

//    private DatabaseHelper databaseHelper;
//
//    public UserController(){
//
//    }
//
//    private void loadUserData(int userId) {
//        UserObject user = databaseHelper.getUser(userId);
//        if (user != null) {
//            soDuTextView.setText(String.valueOf(user.getMoneyForStudying()));
//            soTienConNoTextView.setText(String.valueOf(user.getDebtMoney()));
//        }
//        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//        Cursor cursor = db.query("USER",
//                new String[]{"UserID", "Username", "Password", "PhoneNumber", "LivingExpenses", "MoneyForStudying", "DebtMoney"},
//                "UserID" + "=?",
//                new String[]{String.valueOf(userId)},
//                null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            UserObject user = new UserObject(
//                    cursor.getInt(cursor.getColumnIndexOrThrow("UserID")),
//                    cursor.getString(cursor.getColumnIndexOrThrow("Username")),
//                    cursor.getString(cursor.getColumnIndexOrThrow("Password")),
//                    cursor.getString(cursor.getColumnIndexOrThrow("PhoneNumber")),
//                    cursor.getDouble(cursor.getColumnIndexOrThrow("LivingExpenses")),
//                    cursor.getDouble(cursor.getColumnIndexOrThrow("MoneyForStudying")),
//                    cursor.getDouble(cursor.getColumnIndexOrThrow("DebtMoney"))
//            );
//            cursor.close();
//            return user;
//        } else {
//            return null;
//        }
//    }


}
