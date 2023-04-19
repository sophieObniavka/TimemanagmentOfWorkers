package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Vacation;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.repository.VacationRepository;
import obniavka.timemanagment.utils.UserMapper;
import obniavka.timemanagment.utils.VacationMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;
AmountOfDays amountOfDays = new AmountOfDays();
    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    VacationMapper vacationMapper = Mappers.getMapper(VacationMapper.class);
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public VacationDto findVacationById(final Long id){
        Optional<Vacation> vacation = vacationRepository.findById(id);
        return vacationMapper.map(vacation.isPresent()?vacation.get():null);
    }

    public VacationDto persistVacationInDB(final VacationDto vacationDto){
        vacationDto.setCreated(LocalDateTime.now());
        Vacation vacation = vacationRepository.save(vacationMapper.map(vacationDto));
        return vacationMapper.map(vacation);
    }


    public Page<VacationDto> selectAllUnconfirmedVacations(Pageable pageable) {
        Page<VacationDto> vacations = vacationRepository.findAllVacationByBeingNotConfirmedYet(pageable).map(vacationMapper::map);

        vacations.forEach(v -> v.getUser().setConvertedImage(Base64.getEncoder().encodeToString(v.getUser().getImageUrl())));
        vacations.forEach(v-> v.setAmountOfDays(amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd())));
        return vacations;
    }

    public Page<VacationDto> selectAllConfirmedVacations(Pageable pageable) {
        Page<VacationDto> vacations = vacationRepository.findAllVacationByBeingConfirmed(pageable).map(vacationMapper::map);

        vacations.forEach(v -> v.getUser().setConvertedImage(Base64.getEncoder().encodeToString(v.getUser().getImageUrl())));
        vacations.forEach(v-> v.setAmountOfDays(amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd())));
        return vacations;
    }

    public Page<VacationDto> findAllVacationsByUser(final UserDto userDto, Pageable pageable){
        Page<VacationDto> vacations = vacationRepository.findAllVacationByUserOrderByCreatedDesc(userMapper.map(userDto), pageable).map(vacationMapper::map);

        vacations.forEach(v-> v.setAmountOfDays(amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd())));
        return vacations;
    }

}
