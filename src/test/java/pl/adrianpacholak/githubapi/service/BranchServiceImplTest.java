package pl.adrianpacholak.githubapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.adrianpacholak.githubapi.client.GithubClient;
import pl.adrianpacholak.githubapi.converter.BranchConverter;
import pl.adrianpacholak.githubapi.dto.BranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubBranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubCommitResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    BranchServiceImpl branchService;

    @Mock
    GithubClient githubClient;

    BranchConverter branchConverter = new BranchConverter();

    @BeforeEach
    void setUp() {
        branchService = new BranchServiceImpl(githubClient, branchConverter);
    }

    @DisplayName("Get repository branches - return list with one branch")
    @Test
    void getRepositoryBranches() {
        GithubBranchResponse githubBranch = new GithubBranchResponse("main", new GithubCommitResponse("adsad13213asd"));

        when(githubClient.getRepositoryBranches(anyString(), anyString())).thenReturn(List.of(githubBranch));

        List<BranchResponse> repoBranches = branchService.getRepositoryBranches("owner", "repo");

        assertEquals(1, repoBranches.size());
    }
}