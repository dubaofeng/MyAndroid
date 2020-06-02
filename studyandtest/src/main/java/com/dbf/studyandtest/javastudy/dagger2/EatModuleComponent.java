package com.dbf.studyandtest.javastudy.dagger2;

import dagger.Component;

@Apple.AppleSigle
@Component(modules = AppleDagger2Module.class)
public interface EatModuleComponent {
    void inject(Eat eat);
}
