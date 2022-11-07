package com.sunanda.newroutine.application.somenath.pojo;

import java.io.Serializable;

public class HabitationPojo implements Serializable {

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String name;
    private String code;
    private String id;
    private String IsNotTestHab;
    private String IsCurrentFinancialYearsSource;
    private String IsPreviousFinancialYearsSource;
    private String xcount;
    private String alredyAssign;

    private String dist_code;
    private String block_code;
    private String pan_code;
    private String Village_Code;
    private String Habitation_Code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsNotTestHab() {
        return IsNotTestHab;
    }

    public void setIsNotTestHab(String isNotTestHab) {
        IsNotTestHab = isNotTestHab;
    }

    public String getIsCurrentFinancialYearsSource() {
        return IsCurrentFinancialYearsSource;
    }

    public void setIsCurrentFinancialYearsSource(String isCurrentFinancialYearsSource) {
        IsCurrentFinancialYearsSource = isCurrentFinancialYearsSource;
    }

    public String getIsPreviousFinancialYearsSource() {
        return IsPreviousFinancialYearsSource;
    }

    public void setIsPreviousFinancialYearsSource(String isPreviousFinancialYearsSource) {
        IsPreviousFinancialYearsSource = isPreviousFinancialYearsSource;
    }

    public String getXcount() {
        return xcount;
    }

    public void setXcount(String xcount) {
        this.xcount = xcount;
    }

    public String getAlredyAssign() {
        return alredyAssign;
    }

    public void setAlredyAssign(String alredyAssign) {
        this.alredyAssign = alredyAssign;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getBlock_code() {
        return block_code;
    }

    public void setBlock_code(String block_code) {
        this.block_code = block_code;
    }

    public String getPan_code() {
        return pan_code;
    }

    public void setPan_code(String pan_code) {
        this.pan_code = pan_code;
    }

    public String getVillage_Code() {
        return Village_Code;
    }

    public void setVillage_Code(String village_Code) {
        Village_Code = village_Code;
    }

    public String getHabitation_Code() {
        return Habitation_Code;
    }

    public void setHabitation_Code(String habitation_Code) {
        Habitation_Code = habitation_Code;
    }
}
