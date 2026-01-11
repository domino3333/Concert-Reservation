package mvc.dto;

import mvc.model.genre.ConcertGenre;

import java.sql.Date;


/**
 * 예매 내역 JOIN 조회용 DTO
 */
public class ReservationDto {

    private int reservationId;
    private int memberId;
    private int concertId;
    private Date reservationDate;
    private Date cancelledDate;
    private String userName;
    private int myAge;
    private String mySeat;
    private Date joinDate;
    private String concertName;
    private ConcertGenre concertGenre;
    private int accessAge;

    public ReservationDto(int reservationId, int memberId, int concertId, Date reservationDate, Date cancelledDate, String userName, int myAge, String mySeat, Date joinDate, String concertName, ConcertGenre concertGenre, int accessAge) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.concertId = concertId;
        this.reservationDate = reservationDate;
        this.cancelledDate = cancelledDate;
        this.userName = userName;
        this.myAge = myAge;
        this.mySeat = mySeat;
        this.joinDate = joinDate;
        this.concertName = concertName;
        this.concertGenre = concertGenre;
        this.accessAge = accessAge;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMyAge() {
        return myAge;
    }

    public void setMyAge(int myAge) {
        this.myAge = myAge;
    }

    public String getMySeat() {
        return mySeat;
    }

    public void setMySeat(String mySeat) {
        this.mySeat = mySeat;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public ConcertGenre getConcertGenre() {
        return concertGenre;
    }

    public void setConcertGenre(ConcertGenre concertGenre) {
        this.concertGenre = concertGenre;
    }

    public int getAccessAge() {
        return accessAge;
    }

    public void setAccessAge(int accessAge) {
        this.accessAge = accessAge;
    }
}
