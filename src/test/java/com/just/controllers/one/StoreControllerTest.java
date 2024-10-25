package com.just.controllers.one;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.just.controllers.StoreController;
import com.just.entities.one.Store;
import com.just.services.one.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc autowired

    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreController storeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();  // Manually initialize MockMvc
    }

    @Test
    public void whenCreateStoreMapperLessReturnSuccess() throws Exception {
        // Prepare mock data
        Store store = new Store();  // Assuming Store is a valid entity object

        // Mock the behavior of storeService
        when(storeService.saveStore(any(Store.class))).thenReturn(store);

        // Convert the store object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String storeJson = objectMapper.writeValueAsString(store);

        // Perform POST request and validate the status
        mockMvc.perform(post("/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storeJson))  // Pass the JSON as content
                .andExpect(status().is(200));  // Expect HTTP 201 Created
    }
}
