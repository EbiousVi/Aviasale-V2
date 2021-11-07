package com.ebious.aviasale.domain.apiDto.resp;

public class SeatDto {
    private String seatNo;
    private Boolean isFree;

    public SeatDto() {
    }

    public SeatDto(String seatNo, Boolean isFree) {
        this.seatNo = seatNo;
        this.isFree = isFree;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatDto seatDto = (SeatDto) o;

        if (!seatNo.equals(seatDto.seatNo)) return false;
        return isFree.equals(seatDto.isFree);
    }

    @Override
    public int hashCode() {
        int result = seatNo.hashCode();
        result = 31 * result + isFree.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "S{" +
                "seatNo='" + seatNo + '\'' +
                ", isFree=" + isFree +
                '}';
    }
}
