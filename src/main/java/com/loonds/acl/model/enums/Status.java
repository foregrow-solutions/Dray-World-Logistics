package com.loonds.acl.model.enums;

public enum Status {
    DRAFT("Admin Can Manage"), PENDING(""), INPROGRESS(""), COMPLETED("Customer");

    String description;

    Status(String description){this.description=description;}

    public String getDescription(){return description;}

}
