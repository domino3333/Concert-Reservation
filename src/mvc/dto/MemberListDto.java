package mvc.dto;

import mvc.model.genre.ConcertGenre;

import java.sql.Date;

public class MemberListDto {
    private int memberId;
    private String userName;
    private int myAge;
    private Date joinDate;

    private Integer reservationId;   // 예약 없을 수 있음
    private String concertName;
    private String mySeat;
    private Date reservationDate;
    private ConcertGenre concertGenre;
    private Integer accessAge;


    @Override
    public String toString() {
        return String.format(
                "회원ID=%d | 이름=%s | 나이=%d | 가입일=%s | 예매ID=%s | 좌석=%s | 예매일=%s | 콘서트=%s | 장르=%s | 관람가=%s",
                memberId,
                userName,
                myAge,
                joinDate,
                reservationId != null ? reservationId : "-",
                mySeat != null ? mySeat : "-",
                reservationDate != null ? reservationDate : "-",
                concertName != null ? concertName : "-",
                concertGenre != null ? concertGenre : "-",
                accessAge != null ? accessAge : "-"
        );
    }

    public Integer getAccessAge() {
        return accessAge;
    }

    public void setAccessAge(Integer accessAge) {
        this.accessAge = accessAge;
    }

    public ConcertGenre getConcertGenre() {
        return concertGenre;
    }

    public void setConcertGenre(ConcertGenre concertGenre) {
        this.concertGenre = concertGenre;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public String getMySeat() {
        return mySeat;
    }

    public void setMySeat(String mySeat) {
        this.mySeat = mySeat;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }
}
