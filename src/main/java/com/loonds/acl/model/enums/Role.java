package com.loonds.acl.model.enums;

public enum Role {
    ADMIN("Admin Can Manage"), CEO("Owner of Company"),
    AGENT("Employee");

    String description;

    Role(String description){this.description=description;}

    public String getDescription(){return description;}


}
