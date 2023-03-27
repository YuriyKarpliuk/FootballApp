package yurii.karpliuk.crudapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.dto.request.FootballersSearchRequest;
import yurii.karpliuk.crudapp.dto.request.ManagersSearchRequest;
import yurii.karpliuk.crudapp.exceptions.CouldNotStoreFileException;
import yurii.karpliuk.crudapp.model.Footballer;
import yurii.karpliuk.crudapp.model.Manager;
import yurii.karpliuk.crudapp.model.Team;
import yurii.karpliuk.crudapp.repository.ManagerRepository;
import yurii.karpliuk.crudapp.repository.spec.FootballerSpecification;
import yurii.karpliuk.crudapp.repository.spec.ManagerSpecification;
import yurii.karpliuk.crudapp.service.ManagerService;
import yurii.karpliuk.crudapp.service.OperationValueService;
import yurii.karpliuk.crudapp.service.TeamService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service

public class ManagerServiceImpl implements ManagerService {
    private final Path root = Paths.get("D:/JavaProject/GFL/crudApp/src/main/resources/static/images/");

    private ManagerRepository managerRepository;

    private TeamService teamService;

    private OperationValueService operationValueService;

    public ManagerServiceImpl(ManagerRepository managerRepository, TeamService teamService, OperationValueService operationValueService) {
        this.managerRepository = managerRepository;
        this.teamService = teamService;
        this.operationValueService = operationValueService;
    }

    @Override
    public Optional<Manager> getManagerById(Long id) {
        return this.managerRepository.findById(id);
    }


    @Override
    public Manager saveManager(Manager manager, MultipartFile multipartFile) {
        Manager m = new Manager();
        try {
            Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            m.setImgUrl("/images/" + multipartFile.getOriginalFilename()+ "?timestamp=" + System.currentTimeMillis());
        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }

        m.setFirstName(manager.getFirstName());
        m.setId(manager.getId());
        m.setLastName(manager.getLastName());
        m.setTeam(teamService.getTeamByName(manager.getTeam().getName()).get());
        m.setAge(manager.getAge());
        m.setWeight(manager.getWeight());
        m.setHeight(manager.getHeight());
        return managerRepository.save(m);
    }

    @Override
    public Manager updateManager(Manager manager, MultipartFile multipartFile) {
        Manager m = new Manager();
        try {
            Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            m.setImgUrl("/images/" + multipartFile.getOriginalFilename());

        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }

        m.setFirstName(manager.getFirstName());
        m.setId(manager.getId());
        m.setLastName(manager.getLastName());
        m.setTeam(teamService.getTeamByName(manager.getTeam().getName()).get());
        m.setAge(manager.getAge());
        m.setWeight(manager.getWeight());
        m.setHeight(manager.getHeight());
        return managerRepository.save(m);
    }

    @Override
    public Page<Manager> getAllManagers(Integer page, Integer size, String sortBy) {
        return managerRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)));
    }

    @Override
    public void deleteManagerById(Long id) {
        managerRepository.deleteById(id);
    }

    @Override
    public Page<Manager> getManagersByTeam(Team team, Integer page, Integer size, String sortBy) {
        return managerRepository.findByTeam(team, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)));
    }



    @Override
    public Page<Manager>  searchByLastNameOfManager(String lastName, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return  managerRepository.findAllByLastNameLike("%" + lastName + "%", pageRequest);
    }

    @Override
    public Page<Manager>searchByLastNameAndTeamOfManager(Team team,String lastName,Integer pageNumber, Integer pageSize, String sortBy){
        ManagerSpecification managerSpecification=new ManagerSpecification();
        managerSpecification.setLastName("%" + lastName + "%");
        managerSpecification.setTeam(team);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return  managerRepository.findAll(managerSpecification, pageRequest);
    }

    @Override
    public Page<Manager> filterSearch(ManagersSearchRequest managersSearchRequest, Integer page, Integer size, String sortBy){
        ManagerSpecification managerSpecification=new ManagerSpecification();
        managerSpecification.setAge(managersSearchRequest.getAge());
        managerSpecification.setHeight(managersSearchRequest.getHeight());
        managersSearchRequest.setWeight(managersSearchRequest.getWeight());
        managerSpecification.setAgeOperation(operationValueService.getByName(managersSearchRequest.getAgeOperation().getName()));
        managerSpecification.setWeightOperation(operationValueService.getByName(managersSearchRequest.getWeightOperation().getName()));
        managerSpecification.setHeightOperation(operationValueService.getByName(managersSearchRequest.getHeightOperation().getName()));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        return  managerRepository.findAll(managerSpecification, pageRequest);
    }


}
