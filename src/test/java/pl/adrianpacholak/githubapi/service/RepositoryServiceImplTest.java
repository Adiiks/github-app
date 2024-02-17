package pl.adrianpacholak.githubapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.adrianpacholak.githubapi.client.GithubClient;
import pl.adrianpacholak.githubapi.converter.RepositoryConverter;
import pl.adrianpacholak.githubapi.dto.BranchResponse;
import pl.adrianpacholak.githubapi.dto.RepositoryResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubOwnerResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubRepositoryResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceImplTest {

    RepositoryServiceImpl repoService;

    @Mock
    GithubClient githubClient;

    @Mock
    BranchService branchService;

    RepositoryConverter repoConverter = new RepositoryConverter();

    @BeforeEach
    void setUp() {
        repoService = new RepositoryServiceImpl(githubClient, branchService, repoConverter);
    }

    @DisplayName("Get repositories based on username - return one repository")
    @Test
    void getRepositories() {
        GithubRepositoryResponse githubNonForkedRepo = new GithubRepositoryResponse("repo 1",
                new GithubOwnerResponse("owner"), false);

        GithubRepositoryResponse githubForkedRepo = new GithubRepositoryResponse("repo 2",
                new GithubOwnerResponse("owner"), true);

        BranchResponse branchResponse = new BranchResponse("main", "sadsa13213asd");

        when(githubClient.getUserRepositories(anyString())).thenReturn(List.of(githubNonForkedRepo, githubForkedRepo));
        when(branchService.getRepositoryBranches(anyString(), anyString())).thenReturn(List.of(branchResponse));

        List<RepositoryResponse> repos = repoService.getRepositories("username");

        assertEquals(1, repos.size());

        verify(branchService).getRepositoryBranches(anyString(), anyString());
    }
}