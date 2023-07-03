package obniavka.timemanagment.helper;

import obniavka.timemanagment.domain.ReportDto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class AmountOfDays {
    public static Integer getWorkingDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
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

    public static LocalTime getDurationOfTime(LocalTime startTime, LocalTime endTime, LocalTime pause){
        Duration workDuration = Duration.between(startTime, endTime).minus(Duration.ofHours(pause.getHour()).plusMinutes(pause.getMinute()));

        if (workDuration.isNegative()) {
            workDuration = workDuration.plus(Duration.ofDays(1));
        }

        long totalMinutes = workDuration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        if (hours > 12) {
            hours -= 12;
        }

        return LocalTime.of((int) hours, (int) minutes);
    }

    public static Double getSalaryPerMonth(List<ReportDto> reportDtos){
        if(reportDtos.isEmpty()){
            return 0.0;
        }
        double totalHours = reportDtos.stream()
                .mapToDouble(report -> report.getAmountOfHours().toSecondOfDay() / 3600.0)
                .sum();

        return Math.round(totalHours * reportDtos.get(0).getUser().getSalaryPerHour() * Math.pow(10, 2)) / Math.pow(10, 2);
    }

    public static int getFractionalPart(Double number){
        double fractionalPart = number % 1;
        return (int) (fractionalPart * 100);
    }
}
