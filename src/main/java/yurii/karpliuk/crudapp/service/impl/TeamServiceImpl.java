package yurii.karpliuk.crudapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.exceptions.CouldNotStoreFileException;
import yurii.karpliuk.crudapp.model.Manager;
import yurii.karpliuk.crudapp.model.Team;
import yurii.karpliuk.crudapp.repository.TeamRepository;
import yurii.karpliuk.crudapp.service.TeamService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class TeamServiceImpl implements TeamService {
    private final Path root = Paths.get("D:/JavaProject/GFL/crudApp/src/main/resources/static/images/");

    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Optional<Team> getTeamByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<String> getAllTeamsNames() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(Team::getName).collect(Collectors.toList());
    }
    @Override
    public Optional<Team> getTeamById(Long id){
        return teamRepository.findById(id);
    }




    @Override
    public Team saveTeam(Team team, MultipartFile multipartFile) {
        Team t = new Team();
        try {
            Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            t.setImgUrl("/images/" + multipartFile.getOriginalFilename() + "?timestamp=" + System.currentTimeMillis());
        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }

        t.setCity(team.getCity());
        t.setCountry(team.getCountry());
        t.setName(team.getName());
        return teamRepository.save(t);
    }

    @Override
    public Team updateTeam(Team team, MultipartFile multipartFile) {
        Optional<Team> t = teamRepository.findById(team.getId());
        try {
            Path path = this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            t.get().setImgUrl("/images/" + multipartFile.getOriginalFilename());

        } catch (IOException e) {
            throw new CouldNotStoreFileException("Could not store the file");
        }

        t.get().setCity(team.getCity());
        t.get().setCountry(team.getCountry());
        t.get().setName(team.getName());
        return teamRepository.save(t.get());
    }

    @Override
    public Page<Team> getAllTeams(Integer page, Integer size, String sortBy) {
        return teamRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy)));
    }

    @Override
    public void deleteTeamById(Long id) {
        teamRepository.deleteById(id);
    }

}
