package com.lifedev.medreminder.custom;

/**
 * Created by alano on 4/30/16.
 */
public class CustomDialogRow {

    private String label;

    private Integer icon;

    private String activitytarget;

    public CustomDialogRow(String label, Integer icon, String target){
        this.label = label;
        this.icon = icon;
        this.activitytarget = target;
    }

    public String getLabel() { return label; }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getActivitytarget() {
        return activitytarget;
    }

    public void setActivitytarget(String activitytarget) {
        this.activitytarget = activitytarget;
    }
}
