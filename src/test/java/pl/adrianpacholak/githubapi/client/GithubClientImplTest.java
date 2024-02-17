package pl.adrianpacholak.githubapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.adrianpacholak.githubapi.dto.client.github.GithubBranchResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubCommitResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubOwnerResponse;
import pl.adrianpacholak.githubapi.dto.client.github.GithubRepositoryResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest({GithubClientImpl.class})
class GithubClientImplTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    GithubClientImpl githubClient;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Get user's repositories based on his username - returning 2 repos from two pages")
    @Test
    void getUserRepositories() throws Exception {
        String username = "username";
        String serverUrlFirstPage = UriComponentsBuilder.fromUriString(GithubClientImpl.GITHUB_BASE_URL)
                .path("/users/" + username + "/repos")
                .queryParam("per_page", GithubClientImpl.ITEMS_PER_PAGE)
                .queryParam("page", 1)
                .build()
                .toUriString();

        GithubRepositoryResponse githubRepo = new GithubRepositoryResponse("repo", new GithubOwnerResponse("owner"), false);

        server.expect(requestTo(serverUrlFirstPage))
                .andRespond(withSuccess()
                        .body(objectMapper.writeValueAsString(List.of(githubRepo)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("link", "rel=\"next\""));

        String serverUrlSecondPage = serverUrlFirstPage.replace("&page=1", "&page=2");
        server.expect(requestTo(serverUrlSecondPage))
                .andRespond(withSuccess()
                        .body(objectMapper.writeValueAsString(List.of(githubRepo)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("link", "rel=\"first\""));

        List<GithubRepositoryResponse> userRepos = githubClient.getUserRepositories(username);

        assertEquals(2, userRepos.size());
    }

    @DisplayName("Get user's repositories based on his username - throw ResponseStatusException")
    @Test
    void getUserRepositoriesUsernameNotExists() throws Exception {
        String username = "username";
        String serverUrlFirstPage = UriComponentsBuilder.fromUriString(GithubClientImpl.GITHUB_BASE_URL)
                .path("/users/" + username + "/repos")
                .queryParam("per_page", GithubClientImpl.ITEMS_PER_PAGE)
                .queryParam("page", 1)
                .build()
                .toUriString();

        server.expect(requestTo(serverUrlFirstPage))
                .andRespond(withResourceNotFound());

        assertThrows(ResponseStatusException.class, () -> githubClient.getUserRepositories(username));
    }

    @DisplayName("Get repository branches - returning 2 branches from two pages")
    @Test
    void getRepositoryBranches() throws Exception {
        String ownerName = "owner";
        String repoName = "repo";

        String serverUrlFirstPage = UriComponentsBuilder.fromUriString(GithubClientImpl.GITHUB_BASE_URL)
                .path("/repos/" + ownerName + "/" + repoName + "/branches")
                .queryParam("per_page", GithubClientImpl.ITEMS_PER_PAGE)
                .queryParam("page", 1)
                .build()
                .toUriString();

        GithubBranchResponse githubBranch = new GithubBranchResponse("main", new GithubCommitResponse("asd1132adsa22"));

        server.expect(requestTo(serverUrlFirstPage))
                .andRespond(withSuccess()
                        .body(objectMapper.writeValueAsString(List.of(githubBranch)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("link", "rel=\"next\""));

        String serverUrlSecondPage = serverUrlFirstPage.replace("&page=1", "&page=2");
        server.expect(requestTo(serverUrlSecondPage))
                .andRespond(withSuccess()
                        .body(objectMapper.writeValueAsString(List.of(githubBranch)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("link", "rel=\"first\""));

        List<GithubBranchResponse> repoBranches = githubClient.getRepositoryBranches(ownerName, repoName);

        assertEquals(2, repoBranches.size());
    }

    @DisplayName("Get repository branches - throws ResponseStatusException")
    @Test
    void getRepositoryBranchesRepoNotExists() throws Exception {
        String ownerName = "owner";
        String repoName = "repo";

        String serverUrlFirstPage = UriComponentsBuilder.fromUriString(GithubClientImpl.GITHUB_BASE_URL)
                .path("/repos/" + ownerName + "/" + repoName + "/branches")
                .queryParam("per_page", GithubClientImpl.ITEMS_PER_PAGE)
                .queryParam("page", 1)
                .build()
                .toUriString();

        server.expect(requestTo(serverUrlFirstPage))
                .andRespond(withResourceNotFound());

        assertThrows(ResponseStatusException.class, () -> githubClient.getRepositoryBranches(ownerName, repoName));
    }
}