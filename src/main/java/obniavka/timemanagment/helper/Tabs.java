package obniavka.timemanagment.helper;

import java.util.Arrays;
import java.util.Collection;

public enum Tabs {
    REPORTS("/admin/report","fa fa-table fa-2x","REPORT"),
    VACATIONS("/admin/vacations/active","fa fa-sun-o fa-2x", "USER.VACATION"),
    SICKLEAVES("/admin/sickleaves/active","fa fa-medkit fa-2x", "USER.SICKLEAVE"),
    INVOICES("/admin/invoices","fa fa-file fa-2x", "USER.INVOICES"),
    TASKS("/admin/tasks","fa fa-book fa-2x", "USER.TASKS" ),
    PROFILE("/admin/users/profile/","","INFO"),
    ASSIGNMENTS("/admin/assignments", "fa fa-briefcase fa-2x", "USER.ASSIGNMENTS"),
    EXPENSES("/admin/expenses", "fa fa-credit-card fa-2x", "USER.EXPENSES");


    private final String requestUrl;
    private final String icon;
    private final String name;

    private Tabs(String requestUrl, String icon, String name) {
        this.requestUrl = requestUrl;
        this.icon = icon;
        this.name = name;
    }

    public static Collection<Tabs> listMenuTabs() {
        return Arrays.asList(REPORTS,VACATIONS,SICKLEAVES,TASKS,ASSIGNMENTS,INVOICES, EXPENSES);
    }
    public static Collection<Tabs> listProfileTabs() {
        return Arrays.asList(PROFILE);
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }


}
