package com.sunanda.newroutine.application.somenath.pojo;

import java.io.Serializable;

public class HabCovUncovPojo implements Serializable {

	private String id;
	private String villCodeUnique;
	private String villCode;
	private String habitationCode;
	private String habitationName;
	private String touched;
	private String noSourceTestedInFY;
	private String lastTouchedDate;
	private String touchedPrevious;
	private String noSourceTestedInPreviousFY;
	private String lastPreviousTouchedDate;
	private boolean alredyAssign =  false;
	private boolean isExpanded =  false;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setVillCodeUnique(String villCodeUnique){
		this.villCodeUnique = villCodeUnique;
	}

	public String getVillCodeUnique(){
		return villCodeUnique;
	}

	public void setVillCode(String villCode){
		this.villCode = villCode;
	}

	public String getVillCode(){
		return villCode;
	}

	public void setHabitationCode(String habitationCode){
		this.habitationCode = habitationCode;
	}

	public String getHabitationCode(){
		return habitationCode;
	}

	public void setHabitationName(String habitationName){
		this.habitationName = habitationName;
	}

	public String getHabitationName(){
		return habitationName;
	}

	public void setTouched(String touched){
		this.touched = touched;
	}

	public String getTouched(){
		return touched;
	}

	public void setNoSourceTestedInFY(String noSourceTestedInFY){
		this.noSourceTestedInFY = noSourceTestedInFY;
	}

	public String getNoSourceTestedInFY(){
		return noSourceTestedInFY;
	}

	public void setLastTouchedDate(String lastTouchedDate){
		this.lastTouchedDate = lastTouchedDate;
	}

	public String getLastTouchedDate(){
		return lastTouchedDate;
	}

	public void setTouchedPrevious(String touchedPrevious){
		this.touchedPrevious = touchedPrevious;
	}

	public String getTouchedPrevious(){
		return touchedPrevious;
	}

	public void setNoSourceTestedInPreviousFY(String noSourceTestedInPreviousFY){
		this.noSourceTestedInPreviousFY = noSourceTestedInPreviousFY;
	}

	public String getNoSourceTestedInPreviousFY(){
		return noSourceTestedInPreviousFY;
	}

	public void setLastPreviousTouchedDate(String lastPreviousTouchedDate){
		this.lastPreviousTouchedDate = lastPreviousTouchedDate;
	}

	public String getLastPreviousTouchedDate(){
		return lastPreviousTouchedDate;
	}

	public boolean isAlredyAssign() {
		return alredyAssign;
	}

	public void setAlredyAssign(boolean alredyAssign) {
		this.alredyAssign = alredyAssign;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean expanded) {
		isExpanded = expanded;
	}
}