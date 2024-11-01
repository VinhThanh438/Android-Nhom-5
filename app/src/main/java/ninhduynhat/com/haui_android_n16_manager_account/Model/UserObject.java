package ninhduynhat.com.haui_android_n16_manager_account.Model;

public class UserObject {

    private int UserID;
    private String UserName;
    private String Password;
    private String Fullname;
    private String PhoneNumber;
    private double LivingExpenses;
    private double MoneyForStudying;
    private double DebtMoney;
    private String Image;

    private static UserObject instance;

    public UserObject(){

    }

    public UserObject(String image) {
        Image = image;
    }

    public UserObject(int userID, String fullname, double livingExpenses, String image) {
        UserID = userID;
        Fullname = fullname;
        LivingExpenses = livingExpenses;
        Image = image;
    }

    public static synchronized UserObject getInstance() {
        if (instance == null) {
            instance = new UserObject();
        }
        return instance;
    }


    public UserObject(int userID, String userName, String password, String fullname, String phoneNumber, double livingExpenses, double moneyForStudying, double debtMoney, String image) {
        UserID = userID;
        UserName = userName;
        Password = password;
        Fullname = fullname;
        PhoneNumber = phoneNumber;
        LivingExpenses = livingExpenses;
        MoneyForStudying = moneyForStudying;
        DebtMoney = debtMoney;
        Image = image;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public double getLivingExpenses() {
        return LivingExpenses;
    }

    public void setLivingExpenses(double livingExpenses) {
        LivingExpenses = livingExpenses;
    }

    public double getMoneyForStudying() {
        return MoneyForStudying;
    }

    public void setMoneyForStudying(double moneyForStudying) {
        MoneyForStudying = moneyForStudying;
    }

    public double getDebtMoney() {
        return DebtMoney;
    }

    public void setDebtMoney(double debtMoney) {
        DebtMoney = debtMoney;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
