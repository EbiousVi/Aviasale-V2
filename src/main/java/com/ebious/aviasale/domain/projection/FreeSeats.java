package com.ebious.aviasale.domain.projection;

public class FreeSeats {
    private Boolean free;
    private String seatNo;

    public FreeSeats() {
    }

    public FreeSeats(Boolean free, String seatNo) {
        this.free = free;
        this.seatNo = seatNo;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "FreeSeats{" +
                "isFree=" + free +
                ", SeatNo='" + seatNo + '\'' +
                '}';
    }
}
