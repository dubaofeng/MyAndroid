package com.dbf.studyandtest.designmodel.builder;

public
/**
 *Created by dbf on 2020/6/21 
 *describe:
 */
interface Builder {
    Builder createCpu(String cpu);

    Builder createMainboard(String mainBoard);

    Builder createHardDisk(String hardDisk);

    Builder createMemory(String memory);

    Computer getComputer();
}
