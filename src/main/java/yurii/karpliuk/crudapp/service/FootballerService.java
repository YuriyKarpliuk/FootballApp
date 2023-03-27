package yurii.karpliuk.crudapp.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.dto.request.FootballersSearchRequest;
import yurii.karpliuk.crudapp.model.Footballer;
import yurii.karpliuk.crudapp.model.OperationValue;
import yurii.karpliuk.crudapp.model.Position;
import yurii.karpliuk.crudapp.model.Team;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FootballerService {
    Page<Footballer> filterSearch(FootballersSearchRequest footballersSearchRequest, Integer page, Integer size, String sortBy);


     Page<Footballer>searchByFirstNameAndPositionOfFootballer(Position position,String firstName,Integer pageNumber, Integer pageSize, String sortBy);
    Page<Footballer>searchByFirstNameAndTeamOfFootballer(Team team,String firstName,Integer pageNumber, Integer pageSize, String sortBy);

    Page<Footballer> getAllFootballers(Integer page,Integer size,String sortBy);
    Page<Footballer> getFootballersByTeam(Team team, Integer page, Integer size, String sortBy);

    Page<Footballer> getFootballersByPosition(Position position, Integer page, Integer size, String sortBy);
    Optional<Footballer> getFootballerById(Long id);

    Footballer updateFootballer(Footballer footballer, MultipartFile multipartFile);

    Footballer saveFootballer(Footballer footballer, MultipartFile multipartFile);

    void deleteFootballerById(Long id);

    Page<Footballer> searchByFirstNameOfFootballer(String firstName, Integer pageNumber, Integer pageSize, String sortBy);

}
