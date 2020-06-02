package com.dbf.studyandtest.javastudy.dagger2;

import javax.inject.Inject;
import javax.inject.Named;

public class Eat {
    @AppleDagger2Module.Local
    @Inject
    public Apple locaAapple;

    @AppleDagger2Module.Local
    @Inject
    public Apple fromapple;

    public Eat() {
//        DaggerEatComponent.builder().build().inject(this);
        DaggerEatModuleComponent.builder().build().inject(this);
    }

    public void eatFoot() {
        locaAapple.eatFoot();
        fromapple.eatFoot();
    }
}
