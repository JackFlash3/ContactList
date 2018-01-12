package com.example.prodigalson7.contactlist.business;

/**
 * Created by ProdigaLsON7 on 11/01/2018.
 */

public class Phone {
    private String mobile;
    private String home;
    private String office;

    public Phone(String mobile, String home, String office) {
        this.mobile = mobile;
        this.home = home;
        this.office = office;
    }

    public String getMobile() {
        return mobile;
    }

    public String getHome() {
        return home;
    }

    public String getOffice() {
        return office;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
