package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    ReportMapper MAPPER = Mappers.getMapper(ReportMapper.class);
    ReportDto map(final Report report);

    Report map(final ReportDto reportDto);

    List<ReportDto> map(final List<Report> reports);

    @Mapping(target = "reports", ignore = true)
    UserDto map(final User user);

    @Mapping(target = "reports", ignore = true)
    User map(UserDto userDto);
}
