package ninhduynhat.com.haui_android_n16_manager_account.Model;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;

public class PayingTuitionObject {

    public static final double TIENMOTTINCHI = 415000;

    private int PayingTuitionId;
    private int UserID;
    private int SubjectID;
    private String SubjectName;
    private double Amount;
    private boolean IsPaided;


    public PayingTuitionObject(){

    }

    public PayingTuitionObject(int payingTuitionId, int userID, int subjectID, String subjectName, double amount, boolean isPaided) {
        PayingTuitionId = payingTuitionId;
        UserID = userID;
        SubjectID = subjectID;
        SubjectName = subjectName;
        Amount = amount;
        IsPaided = isPaided;
    }
    public PayingTuitionObject(int userID, int subjectID, String subjectName, double amount, boolean isPaided) {
        UserID = userID;
        SubjectID = subjectID;
        SubjectName = subjectName;
        Amount = amount;
        IsPaided = isPaided;
    }

    public int getPayingTuitionId() {
        return PayingTuitionId;
    }

    public void setPayingTuitionId(int payingTuitionId) {
        PayingTuitionId = payingTuitionId;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(int subjectID) {
        SubjectID = subjectID;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public boolean isPaided() {
        return IsPaided;
    }

    public void setPaided(boolean paided) {
        IsPaided = paided;
    }

}
