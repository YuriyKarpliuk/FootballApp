package yurii.karpliuk.crudapp.service.impl;

import javafx.geometry.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.dto.request.FootballersSearchRequest;
import yurii.karpliuk.crudapp.exceptions.CouldNotStoreFileException;
import yurii.karpliuk.crudapp.model.Footballer;
import yurii.karpliuk.crudapp.model.Position;
import yurii.karpliuk.crudapp.model.Team;
import yurii.karpliuk.crudapp.repository.FootballerRepository;
import yurii.karpliuk.crudapp.repository.spec.FootballerSpecification;
import yurii.karpliuk.crudapp.service.FootballerService;
import yurii.karpliuk.crudapp.service.OperationValueService;
import yurii.karpliuk.crudapp.service.PositionService;
import yurii.karpliuk.crudapp.service.TeamService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FootballerServiceImpl implements FootballerService {
    private final Path root = Paths.get("D:/JavaProject/GFL/crudApp/src/main/resources/static/images/");

    private FootballerRepository footballerRepository;

    private TeamService teamService;

    private PositionService positionService;

    private OperationValueService operationValueService;

    public FootballerServiceImpl(FootballerRepository footballerRepository, TeamService teamService, PositionService positionService, OperationValueService operationValueService) {
        this.footballerRepository = footballerRepository;
        this.teamService = teamService;
        this.positionService = positionService;
        this.operationValueService = operationValueService;
    }

    @Override
    public Optional<Footballer> getFootballerById(Long id) {
        return this.footballerRepository.findById(id);
    }


    @Override
    public Page<Footballer> searchByFirstNameOfFootballer(String firstName, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return  footballerRepository.findAllByFirstNameLike("%" + firstName + "%", pageRequest);
    }

    @Override
    public Page<Footballer>searchByFirstNameAndPositionOfFootballer(Position position,String firstName,Integer pageNumber, Integer pageSize, String sortBy){
        FootballerSpecification footballerSpecification=new FootballerSpecification();
        footballerSpecification.setFirstName("%" + firstName + "%");
        footballerSpecification.setPosition(position);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return  footballerRepository.findAll(footballerSpecification, pageRequest);
    }
    @Override
    public Page<Footballer>searchByFirstNameAndTeamOfFootballer(Team team,String firstName,Integer pageNumber, Integer pageSize, String sortBy){
        FootballerSpecification footballerSpecification=new FootballerSpecification();
        footballerSpecification.setFirstName("%" + firstName + "%");
        footballerSpecification.setTeam(team);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return  footballerRepository.findAll(footballerSpecification, pageRequest);
    }


    @Override
    public Footballer saveFootballer(Footballer footballer, MultipartFile multipartFile) {
        Footballer f = new Footballer();
        try {
            Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            f.setImgUrl("/images/" + multipartFile.getOriginalFilename()+ "?timestamp=" + System.currentTimeMillis());
        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }

        f.setFirstName(footballer.getFirstName());
        f.setId(footballer.getId());
        f.setLastName(footballer.getLastName());
        f.setTeam(teamService.getTeamByName(footballer.getTeam().getName()).get());
        f.setAge(footballer.getAge());
        f.setWeight(footballer.getWeight());
        f.setHeight(footballer.getHeight());
        f.setPriceValue(footballer.getPriceValue());
        f.setPosition(positionService.getByName(footballer.getPosition().getName()));
        return footballerRepository.save(f);
    }

    @Override
    public Footballer updateFootballer(Footballer footballer, MultipartFile multipartFile) {
        Footballer f = new Footballer();
        try {
            Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            f.setImgUrl("/images/" + multipartFile.getOriginalFilename());

        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }

        f.setFirstName(footballer.getFirstName());
        f.setId(footballer.getId());
        f.setLastName(footballer.getLastName());
        f.setTeam(teamService.getTeamByName(footballer.getTeam().getName()).get());
        f.setAge(footballer.getAge());
        f.setWeight(footballer.getWeight());
        f.setHeight(footballer.getHeight());
        f.setPriceValue(footballer.getPriceValue());
        f.setPosition(positionService.getByName(footballer.getPosition().getName()));
        return footballerRepository.save(f);
    }

    @Override
    public Page<Footballer> getAllFootballers(Integer page, Integer size, String sortBy) {
        return footballerRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)));
    }

    @Override
    public void deleteFootballerById(Long id) {
        footballerRepository.deleteById(id);
    }

    @Override
    public Page<Footballer> getFootballersByTeam(Team team, Integer page, Integer size, String sortBy) {
        return footballerRepository.findByTeam(team, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)));
    }

    @Override
    public Page<Footballer> getFootballersByPosition(Position position, Integer page, Integer size, String sortBy) {
        return footballerRepository.findByPosition(position, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)));
    }

    @Override
    public Page<Footballer> filterSearch(FootballersSearchRequest footballersSearchRequest,Integer page, Integer size, String sortBy){
        FootballerSpecification footballerSpecification=new FootballerSpecification();
        footballerSpecification.setAge(footballersSearchRequest.getAge());
        footballerSpecification.setHeight(footballersSearchRequest.getHeight());
        footballerSpecification.setWeight(footballersSearchRequest.getWeight());
        footballerSpecification.setPriceValue(BigDecimal.valueOf(footballersSearchRequest.getPrice()));
        footballerSpecification.setAgeOperation(operationValueService.getByName(footballersSearchRequest.getAgeOperation().getName()));
        footballerSpecification.setWeightOperation(operationValueService.getByName(footballersSearchRequest.getWeightOperation().getName()));
        footballerSpecification.setHeightOperation(operationValueService.getByName(footballersSearchRequest.getHeightOperation().getName()));
        footballerSpecification.setPriceOperation(operationValueService.getByName(footballersSearchRequest.getPriceOperation().getName()));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        return  footballerRepository.findAll(footballerSpecification, pageRequest);
    }


}
