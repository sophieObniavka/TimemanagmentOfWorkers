package obniavka.timemanagment.helper;

import java.util.Arrays;
import java.util.Collection;

public enum Tabs {
    REPORTS("/admin/reports","fa fa-table fa-2x","REPORT"),
    VACATIONS("/admin/vacations/active","fa fa-sun-o fa-2x", "USER.VACATION"),
    SICKLEAVES("/admin/sickleaves/active","fa fa-medkit fa-2x", "USER.SICKLEAVE"),
    INVOICES("/admin/invoices","fa fa-file-o fa-2x", "USER.INVOICES");


    private final String requestUrl;
    private final String icon;
    private final String name;
    private boolean active;

    private Tabs(String requestUrl, String icon, String name) {
        this.requestUrl = requestUrl;
        this.icon = icon;
        this.name = name;
    }

    public static Collection<Tabs> listMenuTabs() {
        return Arrays.asList(REPORTS,VACATIONS,SICKLEAVES,INVOICES);
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

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static void setActiveTab(String currentUrl) {
        listMenuTabs().forEach(t -> t.setActive(false));

        for (Tabs tab : listMenuTabs()) {
            if (currentUrl.contains(tab.getRequestUrl())) {
                tab.setActive(true);
                break;
            }
        }
    }


}
