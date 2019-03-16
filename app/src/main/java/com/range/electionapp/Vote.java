package com.range.electionapp;

public class Vote {
    String vid;
    Candidate c;
    String uid;

    public Vote(String vid, Candidate c, String uid) {
        this.vid = vid;
        this.c = c;
        this.uid = uid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Candidate getC() {
        return c;
    }

    public void setC(Candidate c) {
        this.c = c;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
