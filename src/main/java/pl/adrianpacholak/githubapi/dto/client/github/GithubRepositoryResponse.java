package pl.adrianpacholak.githubapi.dto.client.github;

public record GithubRepositoryResponse(
        String name,
        GithubOwnerResponse owner,
        boolean fork
) {
}
