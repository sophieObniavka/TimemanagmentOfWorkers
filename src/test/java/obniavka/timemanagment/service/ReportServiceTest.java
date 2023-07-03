package obniavka.timemanagment.service;

import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.repository.ReportRepository;
import obniavka.timemanagment.services.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportRepository reportRepository;

    private Report report;
    private ReportDto reportDto;

    @BeforeEach
    void setUp() {
        report = Report.builder()
                .id(1L)
                .workDay(LocalDate.of(2023, Month.JANUARY, 12))
                .build();
        reportDto = ReportDto.builder()
                .id(1L)
                .workDay(LocalDate.of(2023, Month.JANUARY, 12))
                .build();
    }
    @Test
    @DisplayName("Finds report by id")
    void findsProjectById() {
        when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));
        var result = reportService.findReportById(1L);
        verify(reportRepository).findById(1L);
        assertEquals(reportDto, result);
    }
}
