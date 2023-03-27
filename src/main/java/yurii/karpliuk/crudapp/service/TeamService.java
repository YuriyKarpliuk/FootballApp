package yurii.karpliuk.crudapp.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Optional<Team> getTeamByName(String name);
    List<String> getAllTeamsNames();
    Optional<Team> getTeamById(Long id);

    Page<Team> getAllTeams(Integer page, Integer size, String sortBy);
    Team updateTeam(Team team, MultipartFile multipartFile);

    Team saveTeam(Team team, MultipartFile multipartFile);

    void deleteTeamById(Long id);

}
