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
                .build();
        reportDto = ReportDto.builder()
                .id(1L)
                .build();

    }


//    @Test
//    public void testDropReportFromDb() {
//        reportService.dropReportFromDb(reportDto.getId());
//
//        verify(reportRepository, times(1)).deleteById(reportDto.getId());
//    }

    @Test
    @DisplayName("Finds report by id")
    void findsProjectById() {
        when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));

        var result = reportService.findReportById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
