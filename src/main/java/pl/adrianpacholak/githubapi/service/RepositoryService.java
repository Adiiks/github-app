package pl.adrianpacholak.githubapi.service;

import pl.adrianpacholak.githubapi.dto.RepositoryResponse;

import java.util.List;

public interface RepositoryService {

    List<RepositoryResponse> getRepositories(String username);
}
