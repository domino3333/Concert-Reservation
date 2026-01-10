package mvc.model;

import java.sql.Date;

public class Member {

    private String name;
    private int age;
    private Date joinDate;
    private int memberId;
    private Concert concert;
    private String mySeat;

    public Member() {
        this(null, 0, null);
    }

    public Member(String name, int age, Date joinDate) {
        this.memberId = -1;
        this.name = name;
        this.age = age;
        this.joinDate = joinDate;
        this.concert = null;
        this.mySeat = null;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public String getMySeat() {
        return mySeat;
    }

    public void setMySeat(String mySeat) {
        this.mySeat = mySeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Member [name=" + name + ", age=" + age + "]";
    }


}
