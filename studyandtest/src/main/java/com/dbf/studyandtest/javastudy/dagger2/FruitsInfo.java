package com.dbf.studyandtest.javastudy.dagger2;

import javax.inject.Inject;

public class FruitsInfo {
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Inject
    public FruitsInfo() {
        from = "本地";
    }
}
