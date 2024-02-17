package pl.adrianpacholak.githubapi.dto;

import java.util.List;

public record RepositoryResponse(
        String name,
        String ownerLogin,
        List<BranchResponse> branches
) {
}
