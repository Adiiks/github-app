package pl.adrianpacholak.githubapi.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import pl.adrianpacholak.githubapi.dto.client.github.GithubBranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubRepositoryResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubClientImpl implements GithubClient {

    public static final String GITHUB_BASE_URL = "https://api.github.com";
    public static final String GITHUB_ACCEPT_HEADER = "application/vnd.github+json";
    public static final int ITEMS_PER_PAGE = 100;
    public static final int START_PAGE_NUMBER = 1;

    private final RestClient restClient;

    public GithubClientImpl(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(GITHUB_BASE_URL)
                .defaultHeader("accept", GITHUB_ACCEPT_HEADER)
                .build();
    }

    @Override
    public List<GithubRepositoryResponse> getUserRepositories(String username) {
        int currentPage = START_PAGE_NUMBER;
        boolean isNextPage = true;
        List<GithubRepositoryResponse> githubRepos = new ArrayList<>();

        while (isNextPage) {
            ResponseEntity<List<GithubRepositoryResponse>> response = restClient.get()
                    .uri("/users/{username}/repos?per_page={ITEMS_PER_PAGE}&page={currentPage}",
                            username, ITEMS_PER_PAGE, currentPage)
                    .retrieve()
                    .onStatus(status -> status.value() == 404, ((request, response1) -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not exists.");
                    }))
                    .toEntity(new ParameterizedTypeReference<>() {});

            githubRepos.addAll(response.getBody());

            if (isNextPageAvailable(response.getHeaders())) {
                currentPage++;
            }
            else {
                isNextPage = false;
            }
        }

        return githubRepos;
    }

    @Override
    public List<GithubBranchResponse> getRepositoryBranches(String ownerName, String repoName) {
        int currentPage = START_PAGE_NUMBER;
        boolean isNextPage = true;
        List<GithubBranchResponse> repoBranches = new ArrayList<>();

        while (isNextPage) {
            ResponseEntity<List<GithubBranchResponse>> response = restClient.get()
                    .uri("/repos/{ownerName}/{repoName}/branches?per_page={ITEMS_PER_PAGE}&page={currentPage}",
                            ownerName, repoName, ITEMS_PER_PAGE, currentPage)
                    .retrieve()
                    .onStatus(status -> status.value() == 404, ((request, response1) -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner or repository not exists.");
                    }))
                    .toEntity(new ParameterizedTypeReference<>() {});

            repoBranches.addAll(response.getBody());

            if (isNextPageAvailable(response.getHeaders())) {
                currentPage++;
            }
            else {
                isNextPage = false;
            }
        }

        return repoBranches;
    }

    private boolean isNextPageAvailable(HttpHeaders headers) {
        List<String> linksHeader = headers.get("link");

        if (linksHeader == null || linksHeader.isEmpty()) return false;

        String pagesLinks = linksHeader.getFirst();

        return pagesLinks.contains("rel=\"next\"");
    }
}
