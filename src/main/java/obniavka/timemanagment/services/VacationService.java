package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Vacation;
import obniavka.timemanagment.domain.VacationDto;
import obniavka.timemanagment.repository.VacationRepository;
import obniavka.timemanagment.utils.VacationMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;

    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    VacationMapper vacationMapper = Mappers.getMapper(VacationMapper.class);

    public VacationDto findVacationById(final Long id){
        Optional<Vacation> vacation = vacationRepository.findById(id);
        return vacationMapper.map(vacation.isPresent()?vacation.get():null);
    }

    public VacationDto persistVacationInDB(final VacationDto vacationDto){
        vacationDto.setCreated(LocalDateTime.now());
        Vacation vacation = vacationRepository.save(vacationMapper.map(vacationDto));
        return vacationMapper.map(vacation);
    }

    public void dropVacationFromDb(final Long id){vacationRepository.deleteById(id);}

    public List<VacationDto> fetchAllVacationsFromDb() { return vacationMapper.map(vacationRepository.findAll());}
}
