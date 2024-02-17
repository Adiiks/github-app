package pl.adrianpacholak.githubapi.service;

import pl.adrianpacholak.githubapi.dto.BranchResponse;

import java.util.List;

public interface BranchService {

    List<BranchResponse> getRepositoryBranches(String ownerName, String repoName);
}
