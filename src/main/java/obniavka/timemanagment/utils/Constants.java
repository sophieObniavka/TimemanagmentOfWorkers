package obniavka.timemanagment.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    private Constants(){}
    public static final String PAGE = "page";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE_NUMBERS = "pageNumbers";

    public static final String USERS = "users";
    public static final String USER = "user";

    public static final String VACATIONS = "vacations";
    public static final String VACATION = "vacation";

    public static final String TASKS = "tasks";

    public static final String REPORT = "report";
    public static final String REPORTS = "reports";
    public static final String REPORTONCHANGE = "reportOnChange";
    public static final String REPORTNEW = "reportNew";

    public static final String SICKLEAVE = "sickleave";
    public static final String SICKLEAVES = "sickleaves";

    public static final String ASSIGNMENTS = "assignments";
    public static final String CURRENCIES = "currencies";

    public static final List<String> currencies = new ArrayList<>();

    static {
        currencies.add("UAH");
        currencies.add("USD");
        currencies.add("EUR");
        currencies.add("GBP");
        currencies.add("JPY");
        currencies.add("CAD");
    }
}
