package com.range.electionapp;

public class Candidate {
    String cname;
    String pname;
    int age;
    String desc;
    long logo_id;

    public long getLogo_id() {
        return logo_id;
    }

    public void setLogo_id(long logo_id) {
        this.logo_id = logo_id;
    }

    public Candidate(String cname, String pname, int age, String desc, long logo_id) {
        this.cname = cname;
        this.pname = pname;
        this.age = age;
        this.logo_id=logo_id;
        this.desc = desc;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
