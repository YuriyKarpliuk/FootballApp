package yurii.karpliuk.crudapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.crudapp.model.Manager;
import yurii.karpliuk.crudapp.model.Team;
import yurii.karpliuk.crudapp.service.TeamService;

import java.util.List;
import java.util.Optional;

@Controller

public class TeamsController {
    private final TeamService teamService;

    public TeamsController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams/new")
    public String createTeamForm(Model model) {
        model.addAttribute("team",  new Team());
        return "add_team";

    }

    @PostMapping("/teams/created")
    public String saveTeam(@ModelAttribute("team") Team team,@RequestParam("image") MultipartFile imageFile) {
        teamService.saveTeam(team,imageFile);
        return "redirect:/teams";
    }

    @GetMapping("/teams")
    public String teams(@RequestParam(defaultValue = "0") Integer page,@RequestParam(name = "size", defaultValue = "4") Integer size,@RequestParam(defaultValue = "name") String sortBy,Model model) {
        Page<Team> teamPage=  teamService.getAllTeams(page,size,sortBy);
        model.addAttribute("teams", teamPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("totalPages", teamPage.getTotalPages());
        return "teams";
    }

    @GetMapping("/teams/edit/{id}")
    public String editTeam(@PathVariable("id") Long id, Model model) {
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isEmpty()) {
            return "redirect:/teams";
        }
        model.addAttribute("team", team.get());
        return "edit_team";
    }

    @PostMapping("/teams/update/{id}")
    public String updateTeam(@PathVariable("id") Long id, @ModelAttribute("team")Team team,@RequestParam("image") MultipartFile imageFile)  {
        Optional<Team> t = teamService.getTeamById(id);
        if (t.isEmpty()) {
            System.out.println("Team not found");
            return "redirect:/teams";
        }
        teamService.updateTeam(team,imageFile);
        return "redirect:/teams";
    }

    @GetMapping("/teams/{id}")
    public String deleteTeam(@PathVariable Long id) {
        teamService.deleteTeamById(id);
        return "redirect:/teams";
    }
}
