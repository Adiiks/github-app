package pl.adrianpacholak.githubapi.client;

import pl.adrianpacholak.githubapi.dto.client.github.GithubBranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubRepositoryResponse;

import java.util.List;

public interface GithubClient {

    List<GithubRepositoryResponse> getUserRepositories(String username);

    List<GithubBranchResponse> getRepositoryBranches(String ownerName, String repoName);
}
