package mvc.model;

import java.sql.Date;

public class Member {

    private String name;
    private int age;
    private Date joinDate;
    private int memberId;

    public Member() {
        this(null,0,null);
    }

    public Member(String name, int age, Date joinDate) {
        this.memberId = -1;
        this.name = name;
        this.age = age;
        this.joinDate = joinDate;
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
