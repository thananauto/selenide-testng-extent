package com.qa.entity;

import com.qa.pages.LeftMenuComponent;

public enum LeftMenuComponentType {
    ADMIN("Admin"),
    PIM("PIM"),
    LEAVE("Leave");

    private String menuName;

    LeftMenuComponentType(String menuName){
        this.menuName = menuName;
    }

    public String getMenuName(){
        return this.menuName;
    }

}
