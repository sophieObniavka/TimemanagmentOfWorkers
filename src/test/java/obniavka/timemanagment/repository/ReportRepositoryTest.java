package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.Report;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

@DataJpaTest
public class ReportRepositoryTest {

    @Autowired
  private ReportRepository reportRepository;



    private Report report;

    @BeforeEach
    public void initEach(){
        report = reportRepository.save(
                Report.builder()
                        .id(1L)
                        .workDay(LocalDate.of(2023, Month.JANUARY, 18))
                        .begin(LocalTime.of(9, 0))
                        .end(LocalTime.of(19,0))
                        .pause(LocalTime.of(1,0))
                        .description("Was working on project")
                        .build()
        );

        reportRepository.save(
                Report.builder()
                        .id(2L)
                        .workDay(LocalDate.of(2023, Month.APRIL, 20))
                        .begin(LocalTime.of(9, 0))
                        .end(LocalTime.of(18,0))
                        .pause(LocalTime.of(1,0))
                        .description("I was learning")
                        .build()
        );
    }

    @Test
    @DisplayName("create and save report")
    public void testCreateAndSaveReport() {
        System.out.println("Hello");
        Report report1 = Report.builder()
                .id(3L)
                .workDay(LocalDate.of(2023, Month.JANUARY, 2))
                .begin(LocalTime.of(9, 0))
                .end(LocalTime.of(18,0))
                .pause(LocalTime.of(1,15))
                .description("Was working on project")
                .build();

        Report saved = reportRepository.save(report1);
        assertNotNull(saved);

    }

    @DisplayName("Find report by id")
    @Test
    void testFindReportById(){
       Report report = reportRepository.findById( reportRepository.findAll().get(1).getId()).orElse(null);
        assertNotNull(report);
    }

    @Test
    @DisplayName("find all reports")
    void testFindAllReports(){
        assertEquals(2, reportRepository.findAll().size());
        assertFalse(reportRepository.findAll().isEmpty());
    }

    @DisplayName("Delete report")
    @Test
    void testDeleteReport(){
        assertNotNull(report);

        reportRepository.deleteById(report.getId());

        assertFalse(reportRepository.findById(report.getId()).isPresent());
    }

}
