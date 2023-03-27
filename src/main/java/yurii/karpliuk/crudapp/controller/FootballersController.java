package yurii.karpliuk.crudapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.dto.request.FootballersSearchRequest;
import yurii.karpliuk.crudapp.enums.FilterOperation;
import yurii.karpliuk.crudapp.enums.PlayerPosition;
import yurii.karpliuk.crudapp.model.Footballer;
import yurii.karpliuk.crudapp.model.Position;
import yurii.karpliuk.crudapp.model.Team;
import yurii.karpliuk.crudapp.service.FootballerService;
import yurii.karpliuk.crudapp.service.OperationValueService;
import yurii.karpliuk.crudapp.service.PositionService;
import yurii.karpliuk.crudapp.service.TeamService;


import java.util.List;
import java.util.Optional;

@Controller
public class FootballersController {
    private final FootballerService footballerService;
    private final TeamService teamService;
    private final PositionService positionService;

    private final OperationValueService operationValueService;


    public FootballersController(FootballerService footballerService, TeamService teamService, PositionService positionService, OperationValueService operationValueService) {
        this.footballerService = footballerService;
        this.teamService = teamService;
        this.positionService = positionService;
        this.operationValueService = operationValueService;
    }

    @GetMapping("/footballers/new")
    public String createFootballerForm(Model model) {
        List<String> teamNames = teamService.getAllTeamsNames();
        model.addAttribute("footballer", new Footballer());
        model.addAttribute("teamsNames", teamNames);
        model.addAttribute("positions", PlayerPosition.values());
        return "add_footballer";

    }

    @PostMapping("/footballers/created")
    public String saveFootballer(@ModelAttribute("footballer") Footballer footballer, @RequestParam("image") MultipartFile imageFile) {
        footballerService.saveFootballer(footballer, imageFile);
        return "redirect:/footballers";
    }


    @GetMapping("/footballers")
    public String footballers(@RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Page<Footballer> footballerPage = footballerService.getAllFootballers(page, size, sortBy);
        model.addAttribute("footballers", footballerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", footballerPage.getTotalPages());
        return "footballers";
    }

    @GetMapping("/footballers/edit/{id}")
    public String editFootballer(@PathVariable("id") Long id, Model model) {
        Optional<Footballer> footballer = footballerService.getFootballerById(id);
        List<String> teamNames = teamService.getAllTeamsNames();
        if (footballer.isEmpty()) {
            return "redirect:/footballers";
        }
        model.addAttribute("footballer", footballer.get());
        model.addAttribute("teamsNames", teamNames);
        model.addAttribute("positions", PlayerPosition.values());
        return "edit_footballer";
    }

    @PostMapping("/footballers/update/{id}")
    public String updateFootballer(@PathVariable("id") Long id, @ModelAttribute("footballer") Footballer footballer, @RequestParam("image") MultipartFile imageFile) {
        Optional<Footballer> f = footballerService.getFootballerById(id);
        if (f.isEmpty()) {
            System.out.println("Footballer not found");
            return "redirect:/footballers";
        }
        footballerService.updateFootballer(footballer, imageFile);
        return "redirect:/footballers";
    }

    @GetMapping("/footballers/{id}")
    public String deleteFootballer(@PathVariable Long id) {
        footballerService.deleteFootballerById(id);
        return "redirect:/footballers";
    }

    @GetMapping("/team_footballers/{id}")
    public String footballersByTeam(@PathVariable("id") Long id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isEmpty()) {
            return "redirect:/footballers";
        }

        Page<Footballer> footballerPage = footballerService.getFootballersByTeam(team.get(), page, size, sortBy);
        model.addAttribute("footballers", footballerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", footballerPage.getTotalPages());
        model.addAttribute("team", team.get());
        return "footballers_by_team";
    }

    @GetMapping("/searchByTeam")
    public String searchByFirstNameAndTeamOfFootballer(@RequestParam("id") Long id, @RequestParam("firstNameLike") String firstNameLike, @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isEmpty()) {
            return "redirect:/footballers";
        }

        Page<Footballer> footballerPage = footballerService.searchByFirstNameAndTeamOfFootballer(team.get(), firstNameLike, page, size, sortBy);
        model.addAttribute("footballers", footballerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("firstNameLike", firstNameLike);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", footballerPage.getTotalPages());
        model.addAttribute("team", team.get());
        return "filter_by_first_name_and_team_footballer";
    }


    @GetMapping("/footballers/search")
    public String searchByFirstNameOfFootballer(@RequestParam("firstNameLike") String firstNameLike, @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Page<Footballer> footballerPage = footballerService.searchByFirstNameOfFootballer(firstNameLike, page, size, sortBy);
        model.addAttribute("footballers", footballerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("firstNameLike", firstNameLike);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", footballerPage.getTotalPages());
        return "filter_by_first_name_footballers";
    }


    @GetMapping("/search")
    public String searchByFirstNameAndPositionOfFootballer(@RequestParam("positionName") String positionName, @RequestParam("firstNameLike") String firstNameLike, @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Optional<Position> position = Optional.ofNullable(positionService.getByName(PlayerPosition.valueOf(positionName)));
        if (position.isEmpty()) {
            return "redirect:/footballers";
        }
        Page<Footballer> footballerPage = footballerService.searchByFirstNameAndPositionOfFootballer(position.get(), firstNameLike, page, size, sortBy);
        model.addAttribute("footballers", footballerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("firstNameLike", firstNameLike);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", footballerPage.getTotalPages());
        model.addAttribute("positionName", position.get());
        return "filter_by_first_name_and_position_footballer";
    }

    @GetMapping("/position_footballers")
    public String footballersByPosition(@RequestParam("positionName") String positionName, @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Optional<Position> position = Optional.ofNullable(positionService.getByName(PlayerPosition.valueOf(positionName)));
        if (position.isEmpty()) {
            return "redirect:/footballers";
        }

        Page<Footballer> footballerPage = footballerService.getFootballersByPosition(position.get(), page, size, sortBy);
        model.addAttribute("footballers", footballerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", footballerPage.getTotalPages());
        model.addAttribute("positionName", position.get());

        return "footballers_by_position";
    }

    @GetMapping("/filter_search")
    public String filterSearchOfFootballers(Model model) {

        model.addAttribute("ageOperations", FilterOperation.values());
        model.addAttribute("weightOperations", FilterOperation.values());
        model.addAttribute("heightOperations", FilterOperation.values());
        model.addAttribute("priceOperations", FilterOperation.values());
        model.addAttribute("footballersSearchRequest", new FootballersSearchRequest());

        return "filter_search_footballers";
    }

    @GetMapping("/filter_search/results")
    public String showFilteredOfFootballers(@ModelAttribute("footballersSearchRequest") FootballersSearchRequest footballersSearchRequest,
                                            @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {

        Page<Footballer> footballerPage = footballerService.filterSearch(footballersSearchRequest, page, size, sortBy);
        model.addAttribute("footballers", footballerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", footballerPage.getTotalPages());
        model.addAttribute("footballersSearchRequest", footballersSearchRequest);
        return "filter_search_results";
    }


}
