package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.repository.ReportRepository;
import obniavka.timemanagment.utils.ReportMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    ReportMapper mapper = Mappers.getMapper(ReportMapper.class);

    public ReportDto findReportById(final Long id){
        Optional<Report> report = reportRepository.findById(id);
        return mapper.map(report.orElse(null));
    }

    public ReportDto persistReportInDb(final ReportDto reportDto){
        Report report = reportRepository.save(mapper.map(reportDto));
        return mapper.map(report);
    }

    public void dropReportFromDb(final Long id){
        reportRepository.deleteById(id);
    }

    public List<ReportDto> fetchAllReportsFromDb() { return mapper.map(reportRepository.findAll());}
    public List<ReportDto> fetchAllReportsByUserFromDb(Long id) { return mapper.map(reportRepository.findAllByUser(id));}
}
