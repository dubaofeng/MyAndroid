package com.dbf.studyandtest.designmodel.builder;

import android.app.AlertDialog;
import android.content.Context;

import okhttp3.Request;

public
/**
 *Created by dbf on 2020/6/22 
 *describe:
 */
class ComputerBuilder implements Builder {
    private Computer computer;

    public ComputerBuilder() {
        computer = new Computer();
    }

    @Override
    public Builder createCpu(String cpu) {
        computer.setCpu(cpu);
        return this;
    }

    @Override
    public Builder createMainboard(String mainBoard) {
        computer.setCpu(mainBoard);
        return this;
    }

    @Override
    public Builder createHardDisk(String hardDisk) {
        computer.setCpu(hardDisk);
        return this;
    }

    @Override
    public Builder createMemory(String memory) {
        computer.setCpu(memory);
        return this;
    }

    @Override
    public Computer getComputer() {
        return computer;
    }
}
