package obniavka.timemanagment.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AmountOfDays {
    public Integer getWorkingDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        int businessDays = 0;
        LocalDate d = startDate;
        while (d.isBefore(endDate)) {
            DayOfWeek dw = d.getDayOfWeek();
            if (dw != DayOfWeek.SATURDAY && dw != DayOfWeek.SUNDAY) {
                businessDays++;
            }
            d = d.plusDays(1);
        }
        return businessDays;
    }
}
