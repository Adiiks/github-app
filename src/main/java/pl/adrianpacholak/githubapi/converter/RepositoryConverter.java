package pl.adrianpacholak.githubapi.converter;

import org.springframework.stereotype.Component;
import pl.adrianpacholak.githubapi.dto.RepositoryResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubRepositoryResponse;

import java.util.ArrayList;

@Component
public class RepositoryConverter {

    public RepositoryResponse githubRepositoryResponseToRepositoryResponse(GithubRepositoryResponse repo) {
        return new RepositoryResponse(
                repo.name(),
                repo.owner().login(),
                new ArrayList<>()
        );
    }
}
