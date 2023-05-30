package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.repository.ReportRepository;
import obniavka.timemanagment.utils.ReportMapper;
import obniavka.timemanagment.utils.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    ReportMapper mapper = Mappers.getMapper(ReportMapper.class);
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public ReportDto findReportById(final Long id){
        Optional<Report> report = reportRepository.findById(id);
        return mapper.map(report.orElse(null));
    }

    public ReportDto persistReportInDb(final ReportDto reportDto){
        Report report = reportRepository.save(mapper.map(reportDto));
        return mapper.map(report);
    }

    public void dropReportFromDb(final Long id){
        Report report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        User user = report.getUser();
        user.getReports().remove(report);
        reportRepository.deleteById(id);

    }


    public List<ReportDto> fetchAllReportsByDateAndUserFromDb(String date, final UserDto userDto) {
        LocalDate start = LocalDate.of(Integer.parseInt(date.substring(0, 4)),Integer.parseInt(date.substring(5)), 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        List<ReportDto> reports = mapper.map(reportRepository.findByWorkDayIsBetweenAndUserOrderByWorkDayAsc(start, end, userMapper.map(userDto)));
        reports.forEach(r->r.setAmountOfHours(AmountOfDays.getDurationOfTime(r.getBegin(),r.getEnd(),r.getPause())));
        return reports;
    }

    public Page<ReportDto> fetchAllReportsFromDBByDate(String selectedDate,Pageable pageable){
        LocalDate start = LocalDate.of(Integer.parseInt(selectedDate.substring(0, 4)),Integer.parseInt(selectedDate.substring(5)), 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        Page<ReportDto> reports = reportRepository.findByWorkDayIsBetweenOrderByWorkDayAsc(start, end, pageable).map(mapper::map);
        reports.getContent().forEach(r->r.setAmountOfHours(AmountOfDays.getDurationOfTime(r.getBegin(),r.getEnd(),r.getPause())));
        return reports;
    }

}
