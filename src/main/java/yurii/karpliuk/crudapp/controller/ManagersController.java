package yurii.karpliuk.crudapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import yurii.karpliuk.crudapp.dto.request.ManagersSearchRequest;
import yurii.karpliuk.crudapp.enums.FilterOperation;
import yurii.karpliuk.crudapp.model.Manager;
import yurii.karpliuk.crudapp.model.Team;
import yurii.karpliuk.crudapp.service.ManagerService;
import yurii.karpliuk.crudapp.service.TeamService;

import java.util.List;
import java.util.Optional;

@Controller

public class ManagersController {
    private final ManagerService managerService;
    private final TeamService teamService;

    public ManagersController(ManagerService managerService, TeamService teamService) {
        this.managerService = managerService;
        this.teamService = teamService;
    }

    @GetMapping("/managers/new")
    public String createManagerForm(Model model) {
        List<String> teamNames = teamService.getAllTeamsNames();
        model.addAttribute("manager",  new Manager());
        model.addAttribute("teamsNames", teamNames);
        return "add_manager";

    }

    @PostMapping("/managers/created")
    public String saveManager(@ModelAttribute("manager") Manager manager, @RequestParam("image") MultipartFile imageFile) {
        managerService.saveManager(manager,imageFile);
        return "redirect:/managers";
    }

    @GetMapping("/managers/search")
    public String searchByLastNameOfManager(@RequestParam("lastNameLike") String lastNameLike, @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Page<Manager> managerPage = managerService.searchByLastNameOfManager(lastNameLike, page, size, sortBy);
        model.addAttribute("managers", managerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("lastNameLike", lastNameLike);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", managerPage.getTotalPages());
        return "filter_by_last_name_managers";
    }

    @GetMapping("/managers")
    public String managers(@RequestParam(defaultValue = "0") Integer page,@RequestParam(name = "size", defaultValue = "4") Integer size,@RequestParam(defaultValue = "lastName") String sortBy,Model model) {
        Page<Manager> managerPage=  managerService.getAllManagers(page,size,sortBy);
        model.addAttribute("managers", managerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", managerPage.getTotalPages());
        return "managers";
    }

    @GetMapping("/managers/edit/{id}")
    public String editManager(@PathVariable("id") Long id, Model model) {
        Optional<Manager> manager = managerService.getManagerById(id);
        List<String> teamNames = teamService.getAllTeamsNames();
        if (manager.isEmpty()) {
            return "redirect:/managers";
        }
        model.addAttribute("manager", manager.get());
        model.addAttribute("teamsNames", teamNames);
        return "edit_manager";
    }

    @PostMapping("/managers/update/{id}")
    public String updateManager(@PathVariable("id") Long id, @ModelAttribute("manager")Manager manager,@RequestParam("image") MultipartFile imageFile)  {
        Optional<Manager> m = managerService.getManagerById(id);
        if (m.isEmpty()) {
            System.out.println("Manager not found");
            return "redirect:/managers";
        }
        managerService.updateManager(manager,imageFile);
        return "redirect:/managers";
    }

    @GetMapping("/managers/{id}")
    public String deleteManager(@PathVariable Long id) {
        managerService.deleteManagerById(id);
        return "redirect:/managers";
    }

    @GetMapping("/team_managers/{id}")
    public String managersByTeam(@PathVariable("id") Long id,@RequestParam(defaultValue = "0") Integer page,@RequestParam(name = "size", defaultValue = "4") Integer size,@RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isEmpty()) {
            return "redirect:/managers";
        }

        Page<Manager> managerPage=  managerService.getManagersByTeam(team.get(),page,size,sortBy);
        model.addAttribute("managers", managerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", managerPage.getTotalPages());
        model.addAttribute("team", team.get());
        return "managers_by_team";
    }

    @GetMapping("/searchByTeam/managers")
    public String searchByLastNameAndTeamOfManager(@RequestParam("id") Long id, @RequestParam("lastNameLike") String lastNameLike, @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isEmpty()) {
            return "redirect:/managers";
        }

        Page<Manager> managerPage = managerService.searchByLastNameAndTeamOfManager(team.get(), lastNameLike, page, size, sortBy);
        model.addAttribute("managers", managerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("lastNameLike", lastNameLike);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", managerPage.getTotalPages());
        model.addAttribute("team", team.get());
        return "filter_by_last_name_and_team_managers";
    }

    @GetMapping("/filter_search_managers")
    public String filterSearchOfManagers(Model model) {

        model.addAttribute("ageOperations", FilterOperation.values());
        model.addAttribute("weightOperations", FilterOperation.values());
        model.addAttribute("heightOperations", FilterOperation.values());
        model.addAttribute("priceOperations", FilterOperation.values());
        model.addAttribute("managersSearchRequest", new ManagersSearchRequest());

        return "filter_search_managers";
    }

    @GetMapping("/filter_search_managers/results")
    public String showFilteredOfManagers(@ModelAttribute("managersSearchRequest") ManagersSearchRequest managersSearchRequest,
                                            @RequestParam(defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "4") Integer size, @RequestParam(defaultValue = "lastName") String sortBy, Model model) {

        Page<Manager> managerPage = managerService.filterSearch(managersSearchRequest, page, size, sortBy);
        model.addAttribute("managers", managerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", managerPage.getTotalPages());
        model.addAttribute("managersSearchRequest", managersSearchRequest);
        return "filter_search_results_managers";
    }


}
