package cis350.upenn.edu.cathealthapp.Core;

import java.io.Serializable;

public class TodayPageInfo implements Serializable{
    //    private boolean foodFirstB = false;
//    private boolean foodSecondB = false;
//    private boolean foodThirdB = false;
    private boolean[] foodBs = new boolean[TodayPage.foodCnt];
    //    private String foodFirst = "";
//    private String foodSecond = "";
//    private String foodThird = "";
    private String[] foods = new String[TodayPage.foodCnt];
    //    private boolean medicationFirstB = false;
//    private boolean medicationSecondB = false;
//    private boolean medicationThirdB = false;
    private boolean[] medicationBs = new boolean[TodayPage.medicineCnt];
    //    private String medicationFirst = "";
//    private String medicationSecond = "";
//    private String medicationThird = "";
    private String[] medications = new String[TodayPage.medicineCnt];
    //    private boolean restroom1 = false;
//    private boolean restroom2 = false;
    private boolean[] restrooms = new boolean[TodayPage.toiletCnt];
    private double catWeight = 0;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private String reminder = "";
    private boolean isDone = false;

    public TodayPageInfo() {
        for (int i = 0; i < TodayPage.foodCnt; i++)
            foods[i] = "";

        for (int i = 0; i < TodayPage.medicineCnt; i++)
            medications[i] = "";
    }

    //SETTERS
//    public void setFoodFirstB(boolean b) {
//        foodFirstB = b;
//    }
//
//    public void setFoodSecondB(boolean b) {
//        foodSecondB = b;
//    }
//
//    public void setFoodThirdB(boolean b) {
//        foodThirdB = b;
//    }
//
//    public void setFoodFirst(String s) {
//        foodFirst = s;
//    }
//
//    public void setFoodSecond(String s) {
//        foodSecond = s;
//    }
//
//    public void setFoodThird(String s) {
//        foodThird = s;
//    }
//
//    public void setMedicationFirstB(boolean b) {
//        medicationFirstB = b;
//    }
//
//    public void setMedicationSecondB(boolean b) {
//        medicationSecondB = b;
//    }
//
//    public void setMedicationThirdB(boolean b) {
//        medicationThirdB = b;
//    }
//
//    public void setMedicationFirst(String s) {
//        medicationFirst = s;
//    }
//
//    public void setMedicationSecond(String s) {
//        medicationSecond = s;
//    }
//
//    public void setMedicationThird(String s) {
//        medicationThird = s;
//    }
//
//    public void setRestroom1(boolean b) {
//        restroom1 = b;
//    }
//
//    public void setRestroom2(boolean b) {
//        restroom2 = b;
//    }

    public void setCatWeight(double i) {
        catWeight = i;
    }

    public void setReminder(String remind) {
        reminder = remind;
    }

    //GETTERS
//    public boolean getFoodFirstB() {
//        return foodFirstB;
//    }
//
//    public boolean getFoodSecondB() {
//        return foodSecondB;
//    }
//
//    public boolean getFoodThirdB() {
//        return foodThirdB;
//    }
//
//    public String getFoodFirst() {
//        return foodFirst;
//    }
//
//    public String getFoodSecond() {
//        return foodSecond;
//    }
//
//    public String getFoodThird() {
//        return foodThird;
//    }
//
//    public boolean getMedicationFirstB() {
//        return medicationFirstB;
//    }
//
//    public boolean getMedicationSecondB() {
//        return medicationSecondB;
//    }
//
//    public boolean getMedicationThirdB() {
//        return medicationThirdB;
//    }
//
//    public String getMedicationFirst() {
//        return medicationFirst;
//    }
//
//    public String getMedicationSecond() {
//        return medicationSecond;
//    }
//
//    public String getMedicationThird() {
//        return medicationThird;
//    }
//
//    public boolean getRestroom1() {
//        return restroom1;
//    }
//

    public boolean[] getFoodBs() {
        return foodBs;
    }

    public void setFoodB(int index, boolean b) {
        this.foodBs[index] = b;
    }

    public String getFood(int index) {
        return foods[index];
    }

    public void setFood(int index, String food) {
        this.foods[index] = food;
    }

    public boolean getMedicationB(int index) {
        return medicationBs[index];
    }

    public void setMedicationB(int index, boolean b) {
        this.medicationBs[index] = b;
    }

    public String getMedication(int index) {
        return medications[index];
    }

    public void setMedication(int index, String med) {
        this.medications[index] = med;
    }

    public boolean getRestroom(int index) {
        return restrooms[index];
    }

    public void setRestroom(int index, boolean b) {
        this.restrooms[index] = b;
    }
//    public boolean getRestroom2() {
//        return restroom2;
//    }

    public double getCatWeight() {
        return catWeight;
    }

    public String getReminder() {
        return reminder;
    }
}

