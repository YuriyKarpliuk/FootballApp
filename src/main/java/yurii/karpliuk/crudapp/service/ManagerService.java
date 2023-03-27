package yurii.karpliuk.crudapp.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.dto.request.ManagersSearchRequest;
import yurii.karpliuk.crudapp.model.Manager;
import yurii.karpliuk.crudapp.model.Team;

import java.util.Optional;

public interface ManagerService {

     Page<Manager>searchByLastNameAndTeamOfManager(Team team,String lastName,Integer pageNumber, Integer pageSize, String sortBy);


    Page<Manager> filterSearch(ManagersSearchRequest managersSearchRequest, Integer page, Integer size, String sortBy);

    Page<Manager>  searchByLastNameOfManager(String lastName, Integer pageNumber, Integer pageSize, String sortBy) ;

    Page<Manager> getAllManagers(Integer page, Integer size, String sortBy);
    Page<Manager> getManagersByTeam(Team team, Integer page, Integer size, String sortBy);

    Optional<Manager> getManagerById(Long id);

    Manager updateManager(Manager manager, MultipartFile multipartFile);

    Manager saveManager(Manager manager, MultipartFile multipartFile);

    void deleteManagerById(Long id);
}
