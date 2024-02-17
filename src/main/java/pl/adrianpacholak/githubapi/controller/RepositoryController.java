package pl.adrianpacholak.githubapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.adrianpacholak.githubapi.dto.RepositoryResponse;
import pl.adrianpacholak.githubapi.service.RepositoryService;

import java.util.List;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class RepositoryController {

    private final RepositoryService repositoryService;

    @GetMapping
    public List<RepositoryResponse> getRepositories(@RequestParam String username) {
        return repositoryService.getRepositories(username);
    }
}
