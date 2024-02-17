package pl.adrianpacholak.githubapi.converter;

import org.springframework.stereotype.Component;
import pl.adrianpacholak.githubapi.dto.BranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubBranchResponse;

@Component
public class BranchConverter {

    public BranchResponse githubBranchResponseToBranchResponse(GithubBranchResponse githubBranchResponse) {
        return new BranchResponse(
                githubBranchResponse.name(),
                githubBranchResponse.commit().sha()
        );
    }
}
