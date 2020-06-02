package com.dbf.studyandtest.javastudy.dagger2;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppleDagger2Module {
    public AppleDagger2Module() {
        DaggerAppleDagger2ModuleComponent.builder().build().inject(this);
    }

    @Apple.AppleSigle
    @Local
    @Provides
    public Apple provideLocalApple() {
        Apple apple = new Apple();
        apple.appleType("本地");
        return apple;
    }

    @Inject
    public FruitsInfo appleInfo;

    @From
    @Provides
    public Apple provideFromApple(FruitsInfo fruitsInfo) {
        Apple apple = new Apple();
        fruitsInfo.setFrom("进口");
        appleInfo.setFrom("自家种的");
        apple.appleType(appleInfo.getFrom());
        return apple;
    }

    @Qualifier
    public @interface From {
    }

    @Qualifier
    public @interface Local {
    }
}
