package pl.adrianpacholak.githubapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrianpacholak.githubapi.client.GithubClient;
import pl.adrianpacholak.githubapi.converter.RepositoryConverter;
import pl.adrianpacholak.githubapi.dto.BranchResponse;
import pl.adrianpacholak.githubapi.dto.RepositoryResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubBranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubRepositoryResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

    private final GithubClient githubClient;
    private final BranchService branchService;
    private final RepositoryConverter repositoryConverter;

    @Override
    public List<RepositoryResponse> getRepositories(String username) {
        List<GithubRepositoryResponse> githubRepos = githubClient.getUserRepositories(username);

        List<RepositoryResponse> repos = new ArrayList<>(githubRepos.size());
        githubRepos.forEach(githubRepo -> {
            if (!githubRepo.fork()) {
               List<BranchResponse> repoBranches = branchService.getRepositoryBranches(githubRepo.owner().login(),
                       githubRepo.name());

               RepositoryResponse repo = repositoryConverter.githubRepositoryResponseToRepositoryResponse(githubRepo);
               repo.branches().addAll(repoBranches);

               repos.add(repo);
            }
        });

        return repos;
    }
}
