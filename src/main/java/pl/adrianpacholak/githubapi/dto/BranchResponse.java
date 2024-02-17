package pl.adrianpacholak.githubapi.dto;

public record BranchResponse(
        String name,
        String lastCommitSha
) {
}
