package pl.adrianpacholak.githubapi.dto.client.github;

public record GithubBranchResponse(
        String name,
        GithubCommitResponse commit
) {
}
