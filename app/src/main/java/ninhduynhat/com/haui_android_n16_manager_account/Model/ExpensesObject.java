package ninhduynhat.com.haui_android_n16_manager_account.Model;

public class ExpensesObject {

    private int ExpensesID;
    private int UserID;
    private String ExpensesType;
    private double AmountSpent;
    private String DateSpent;
    private String Description;

    public ExpensesObject() {
    }

    public ExpensesObject(String expensesType, double amountSpent) {
        ExpensesType = expensesType;
        AmountSpent = amountSpent;
    }

    public ExpensesObject(int expensesID, String expensesType, double amountSpent, String dateSpent, String description) {
        ExpensesID = expensesID;
        ExpensesType = expensesType;
        AmountSpent = amountSpent;
        DateSpent = dateSpent;
        Description = description;
    }

    public ExpensesObject(String expensesType, double amountSpent, String dateSpent, String description) {
        ExpensesType = expensesType;
        AmountSpent = amountSpent;
        DateSpent = dateSpent;
        Description = description;
    }

    public ExpensesObject(int expensesID, int userID, String expensesType, double amountSpent, String dateSpent, String description) {
        ExpensesID = expensesID;
        UserID = userID;
        ExpensesType = expensesType;
        AmountSpent = amountSpent;
        DateSpent = dateSpent;
        Description = description;
    }

    public int getExpensesID() {
        return ExpensesID;
    }

    public void setExpensesID(int expensesID) {
        ExpensesID = expensesID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getExpensesType() {
        return ExpensesType;
    }

    public void setExpensesType(String expensesType) {
        ExpensesType = expensesType;
    }

    public double getAmountSpent() {
        return AmountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        AmountSpent = amountSpent;
    }

    public String getDateSpent() {
        return DateSpent;
    }

    public void setDateSpent(String dateSpent) {
        DateSpent = dateSpent;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
