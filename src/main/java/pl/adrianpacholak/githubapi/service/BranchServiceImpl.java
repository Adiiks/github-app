package pl.adrianpacholak.githubapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrianpacholak.githubapi.client.GithubClient;
import pl.adrianpacholak.githubapi.converter.BranchConverter;
import pl.adrianpacholak.githubapi.dto.BranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubBranchResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final GithubClient githubClient;
    private final BranchConverter branchConverter;

    @Override
    public List<BranchResponse> getRepositoryBranches(String ownerName, String repoName) {
        List<GithubBranchResponse> githubRepoBranches = githubClient.getRepositoryBranches(ownerName, repoName);

        return githubRepoBranches.stream()
                .map(branchConverter::githubBranchResponseToBranchResponse)
                .toList();
    }
}
