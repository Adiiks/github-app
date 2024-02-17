package pl.adrianpacholak.githubapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.adrianpacholak.githubapi.service.RepositoryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RepositoryControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    RepositoryController repositoryController;

    @Mock
    RepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(repositoryController)
                .build();
    }

    @DisplayName("Get list of repositories")
    @Test
    void getRepositories() throws Exception {
        mockMvc.perform(get("/api/repositories")
                .param("username", "jan_kowalski"))
                .andExpect(status().isOk());

        verify(repositoryService).getRepositories(anyString());
    }
}