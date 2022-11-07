package com.sunanda.newroutine.application.somenath.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SourceForLaboratoryPOJO implements Serializable {
    
    @SerializedName("NoOfSourceCollect")
    private String NoOfSourceCollect;
    @SerializedName("DistrictID")
    private String DistrictID;
    @SerializedName("dist_code")
    private String dist_code;
    @SerializedName("DistrictName")
    private String DistrictName;
    @SerializedName("BlockId")
    private String BlockId;
    @SerializedName("block_code")
    private String block_code;
    @SerializedName("BlockName")
    private String BlockName;
    @SerializedName("PanchayatID")
    private String PanchayatID;
    @SerializedName("pan_code")
    private String pan_code;
    @SerializedName("PanchayatName")
    private String PanchayatName;
    @SerializedName("VillageID")
    private String VillageID;
    @SerializedName("Village_Code")
    private String Village_Code;
    @SerializedName("VillageName")
    private String VillageName;
    @SerializedName("HabId")
    private String HabId;
    @SerializedName("Habitation_Code")
    private String Habitation_Code;
    @SerializedName("HabitationName")
    private String HabitationName;
    @SerializedName("IsNotTestHab")
    private String IsNotTestHab;
    @SerializedName("IsCurrentFinancialYearsSource")
    private String IsCurrentFinancialYearsSource;
    @SerializedName("IsPreviousFinancialYearsSource")
    private String IsPreviousFinancialYearsSource;
    @SerializedName("xcount")
    private String xcount;
    @SerializedName("alredyAssign")
    private String alredyAssign;

    public String getNoOfSourceCollect() {
        return NoOfSourceCollect;
    }

    public void setNoOfSourceCollect(String noOfSourceCollect) {
        NoOfSourceCollect = noOfSourceCollect;
    }

    public String getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(String districtID) {
        DistrictID = districtID;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getBlockId() {
        return BlockId;
    }

    public void setBlockId(String blockId) {
        BlockId = blockId;
    }

    public String getBlock_code() {
        return block_code;
    }

    public void setBlock_code(String block_code) {
        this.block_code = block_code;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getPanchayatID() {
        return PanchayatID;
    }

    public void setPanchayatID(String panchayatID) {
        PanchayatID = panchayatID;
    }

    public String getPan_code() {
        return pan_code;
    }

    public void setPan_code(String pan_code) {
        this.pan_code = pan_code;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getVillageID() {
        return VillageID;
    }

    public void setVillageID(String villageID) {
        VillageID = villageID;
    }

    public String getVillage_Code() {
        return Village_Code;
    }

    public void setVillage_Code(String village_Code) {
        Village_Code = village_Code;
    }

    public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String villageName) {
        VillageName = villageName;
    }

    public String getHabId() {
        return HabId;
    }

    public void setHabId(String habId) {
        HabId = habId;
    }

    public String getHabitation_Code() {
        return Habitation_Code;
    }

    public void setHabitation_Code(String habitation_Code) {
        Habitation_Code = habitation_Code;
    }

    public String getHabitationName() {
        return HabitationName;
    }

    public void setHabitationName(String habitationName) {
        HabitationName = habitationName;
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
}