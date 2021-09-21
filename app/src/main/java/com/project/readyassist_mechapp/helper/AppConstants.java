package com.project.readyassist_mechapp.helper;

public class AppConstants {

    public static final String mStartDayCharge = "06:00 AM";
    public static final String mStartNightCharge = "08:00 PM";


    public static final int sIdle = 0;
    public static final int sInTransit = 1;
    public static final int sBusy = 2;
    public static final int sOnBreak = 3;
    public static final int sNotOnDuty = 4;

    public static final String[] arrayPopularServices = {
            "Battery Jumpstart",
            "Flat Tyre (Tube)",
            "Flat Tyre (Tubeless)",
            "Key Unlock Assistance",
            "Minor Repair",
            "Fuel Delivery",
            "Flatbed Towing",
            "Lift Towing",
            "Battery Recharge Addon",
            "Accelerator Cable Replacement",
            "Miscellaneous",
            "Valve pin change",
    };

    public static final String stateNewOrder = "New Order";
    public static final String stateAcknowledged = "Acknowledged";
    public static final String stateOnTheWay = "On The Way";
    public static final String stateLocationReached = "Location Reached";
    public static final String stateWorkInProgress = "Work In-Progress";
    public static final String stateSuccess = "Order Success";
    public static final String stateFailed = "Order Failed";


    public static final String swipeAcknowledge = "Acknowledge";
    public static final String swipeStartVisit = "Start Visit";
    public static final String swipeLocationReached = "Location Reached";
    public static final String swipeStartWork = "Start Work";
    public static final String swipeCompleted = "Completed";
    public static final String swipeSuccessful = "Successful";
    public static final String swipeFailed = "Failed";
    public static final String swipeCloseOrder = "Close Order";


    public static final String[] arrayFailedReason = {
            "Tools Missing", "Major Issue", "Special Tools Required", "Spares Required", "Other"
    };

    public static final String[] arrayPaymentType = {
            "Cash", "PayTm", "GPay", "PhonePe", "Discount"
    };

}
