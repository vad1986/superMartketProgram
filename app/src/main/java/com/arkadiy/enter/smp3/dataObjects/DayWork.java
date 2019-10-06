package com.arkadiy.enter.smp3.dataObjects;

public class DayWork {
    private int day;
    private String start;
    private String end;
    private String sum;

    public DayWork(int day, String start, String end, String sum) {
        this.day = day;
        this.start = start;
        this.end = end;
        this.sum = sum;
    }

    public String getDay() {
        return String.valueOf(day);
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String calculetCum(){



        return "";
    }
}
