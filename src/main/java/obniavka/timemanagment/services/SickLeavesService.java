package obniavka.timemanagment.services;

import obniavka.timemanagment.data.SickLeave;
import obniavka.timemanagment.domain.SickLeaveDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.repository.SickLeaveRepository;
import obniavka.timemanagment.utils.SickLeaveMapper;
import obniavka.timemanagment.utils.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class SickLeavesService {
    private final SickLeaveRepository sickLeaveRepository;
    AmountOfDays amountOfDays = new AmountOfDays();

    public SickLeavesService(SickLeaveRepository sickLeaveRepository) {
        this.sickLeaveRepository = sickLeaveRepository;
    }

    SickLeaveMapper sickLeaveMapper = Mappers.getMapper(SickLeaveMapper.class);
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public SickLeaveDto findSickLeaveById(final Long id){
        Optional<SickLeave> sickLeave = sickLeaveRepository.findById(id);
        return sickLeaveMapper.map(sickLeave.orElse(null));
    }

    public SickLeaveDto persistSickLeaveInDB(final SickLeaveDto sickLeaveDto){
        sickLeaveDto.setCreated(LocalDateTime.now());
        SickLeave sickLeave = sickLeaveRepository.save(sickLeaveMapper.map(sickLeaveDto));
        return sickLeaveMapper.map(sickLeave);
    }

    public Page<SickLeaveDto> selectAllUncofirmedSickLeaves(Pageable pageable){
        Page<SickLeaveDto> sickLeaves = sickLeaveRepository.findAllSickLeaveByBeingNotConfirmedYet(pageable).map(sickLeaveMapper::map);

        sickLeaves.forEach(s -> s.getUser().setConvertedImage(Base64.getEncoder().encodeToString(s.getUser().getImageUrl())));
        sickLeaves.forEach(v-> v.setAmountOfDays(amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd())));

        return sickLeaves;
    }

    public Page<SickLeaveDto> selectAllCofirmedSickLeaves(Pageable pageable){
        Page<SickLeaveDto> sickLeaves = sickLeaveRepository.findAllSickLeaveByBeingConfirmed(pageable).map(sickLeaveMapper::map);

        sickLeaves.forEach(s -> s.getUser().setConvertedImage(Base64.getEncoder().encodeToString(s.getUser().getImageUrl())));
        sickLeaves.forEach(v-> v.setAmountOfDays(amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd())));

        return sickLeaves;
    }
    public Page<SickLeaveDto> findAllSickLLeavesByUser(final UserDto userDto, Pageable pageable){
        Page<SickLeaveDto> sickLeaves = sickLeaveRepository.findAllSickLeaveByUserOrderByCreated(userMapper.map(userDto), pageable).map(sickLeaveMapper::map);

        sickLeaves.forEach(v-> v.setAmountOfDays(amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd())));
        return sickLeaves;
    }
}
