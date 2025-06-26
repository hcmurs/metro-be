package org.alfred.ticketservice.model.enums;

public enum Duration {
    ONE_DAY(1, "1 Day"),
    THREE_DAYS(3, "3 Days"),
    ONE_WEEK(7, "1 Week"),
    SINGLE(0, "Single Ticket"),
    ONE_MONTH(30, "1 Month"),
    STUDENT(30, "Student Ticket");


    private final int durationInDays;
    private final String description;

    Duration(int durationInDays, String description) {
        this.durationInDays = durationInDays;
        this.description = description;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public String getDescription() {
        return description;
    }
}
