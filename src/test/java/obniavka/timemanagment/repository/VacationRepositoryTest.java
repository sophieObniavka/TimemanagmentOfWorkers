package obniavka.timemanagment.repository;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.data.Vacation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

@DataJpaTest
public class VacationRepositoryTest {

    @Autowired
    private VacationRepository vacationRepository;

    private Vacation vacation;
    @BeforeEach
    public void initEach() {
       vacation = vacationRepository.save(Vacation.builder()
                .id(4L)
                .confirmed(null)
                .begin(LocalDate.of(2023, Month.FEBRUARY, 12))
                .end(LocalDate.of(2023, Month.MARCH, 12))
                .build());

       vacationRepository.save(Vacation.builder().id(3L).build());
    }

    @DisplayName("Create vacation")
    @Test
    void testCreateVacation(){
        Vacation vacation = new Vacation();
        vacation.setId(1L);
        vacation.setBegin(LocalDate.of(2023, Month.JANUARY, 12));
        vacation.setEnd(LocalDate.of(2023, Month.FEBRUARY, 22));
        vacation.setConfirmed(null);

        Vacation res = vacationRepository.save(vacation);
        assertNotNull(res);
        assertNotNull(vacation.getId());
        assertNotNull(vacation.getBegin());
        assertNotNull(vacation.getEnd());
        assertNull(vacation.getConfirmed());
    }

    @DisplayName("Find vacation by id")
    @Test
    void testFindVacationById(){
        Vacation vac = vacationRepository.findById( vacationRepository.findAll().get(1).getId()).orElse(null);
        vacationRepository.findAll().forEach(x-> System.out.println(x.getId()));
        assertNotNull(vac);
    }

    @Test
    @DisplayName("find all vacations")
    void testFindAllVacations(){
        assertFalse(vacationRepository.findAll().isEmpty());
    }

    @DisplayName("Delete vacation")
    @Test
    void testDeleteVacation(){


        System.out.println("All ");
        vacationRepository.findAll().forEach(x -> System.out.println(x.getId()));
        Vacation vac1 = vacationRepository.findById(4L).orElse(null);
        assertNotNull(vac1);

        vacationRepository.deleteById(4L);

        assertFalse(vacationRepository.findById(4L).isPresent());
    }
}
