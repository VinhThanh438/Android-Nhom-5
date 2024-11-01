package ninhduynhat.com.haui_android_n16_manager_account.Model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.io.Serializable;

public class PlanObject implements Parcelable, Serializable {

    private int PlanId;
    private int UserID;
    private String PlanName;
    private double AmoutNeeded;
    private double AmoutReached;
    private String TimeLine;
    private String PlanType;
    private String Description;

    // Default constructor
    public PlanObject() {
    }

    public PlanObject(int planId, String planName, double amoutNeeded, double amoutReached, String timeLine, String planType, String description) {
        PlanId = planId;
        PlanName = planName;
        AmoutNeeded = amoutNeeded;
        AmoutReached = amoutReached;
        TimeLine = timeLine;
        PlanType = planType;
        Description = description;
    }

    // Constructor with parameters
    public PlanObject(int planId, int userID, String planName, double amoutNeeded, double amoutReached, String timeLine, String planType, String description) {
        PlanId = planId;
        UserID = userID;
        PlanName = planName;
        AmoutNeeded = amoutNeeded;
        AmoutReached = amoutReached;
        TimeLine = timeLine;
        PlanType = planType;
        Description = description;
    }

    // Constructor to create from Parcel
    protected PlanObject(Parcel in) {
        PlanId = in.readInt();
        UserID = in.readInt();
        PlanName = in.readString();
        AmoutNeeded = in.readDouble();
        AmoutReached = in.readDouble();
        TimeLine = in.readString();
        PlanType = in.readString();
        Description = in.readString();
    }

    // Parcelable Creator
    public static final Creator<PlanObject> CREATOR = new Creator<PlanObject>() {
        @Override
        public PlanObject createFromParcel(Parcel in) {
            return new PlanObject(in);
        }

        @Override
        public PlanObject[] newArray(int size) {
            return new PlanObject[size];
        }
    };

    // Getters and Setters
    public int getPlanId() {
        return PlanId;
    }

    public void setPlanId(int planId) {
        PlanId = planId;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public double getAmoutNeeded() {
        return AmoutNeeded;
    }

    public void setAmoutNeeded(double amoutNeeded) {
        AmoutNeeded = amoutNeeded;
    }

    public double getAmoutReached() {
        return AmoutReached;
    }

    public void setAmoutReached(double amoutReached) {
        AmoutReached = amoutReached;
    }

    public String getTimeLine() {
        return TimeLine;
    }

    public void setTimeLine(String timeLine) {
        TimeLine = timeLine;
    }

    public String getPlanType() {
        return PlanType;
    }

    public void setPlanType(String planType) {
        PlanType = planType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(PlanId);
        parcel.writeInt(UserID);
        parcel.writeString(PlanName);
        parcel.writeDouble(AmoutNeeded);
        parcel.writeDouble(AmoutReached);
        parcel.writeString(TimeLine);
        parcel.writeString(PlanType);
        parcel.writeString(Description);
    }
}
