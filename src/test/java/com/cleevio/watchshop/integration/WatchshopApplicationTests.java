package com.cleevio.watchshop.integration;

import com.cleevio.watchshop.api.dto.WatchDto;
import com.cleevio.watchshop.persistence.entity.Watch;
import com.cleevio.watchshop.persistence.repository.WatchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.cleevio.watchshop.api.ApiValues.WATCH_CONTEXT_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class WatchshopApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WatchRepository repository;


    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void createWatch_validRequest_returnsCreated() throws Exception {
        mockMvc
                .perform(
                        post(WATCH_CONTEXT_PATH)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testWatchDto()))
                )
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http://localhost" + WATCH_CONTEXT_PATH + "/{spring:[a-z0-9-]*}"));
    }

    @Test
    void updateWatch_validRequest_returnsOk() throws Exception {
        Watch testWatch = repository.save(testWatch());
        WatchDto testWatchDto = testWatchDto();
        testWatchDto.setId(testWatch.getId());
        testWatchDto.setTitle("Changed Tiltle");
        String jsonContent = objectMapper.writeValueAsString(testWatchDto);

        mockMvc
                .perform(
                        put(WATCH_CONTEXT_PATH)
                                .contentType(APPLICATION_JSON)
                                .content(jsonContent)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }

    @Test
    void deleteWatch_validRequest_returnsAccepted() throws Exception {
        Watch testWatch = repository.save(testWatch());

        mockMvc
                .perform(
                        delete(WATCH_CONTEXT_PATH + "/{id}", testWatch.getId())
                )
                .andExpect(status().isAccepted());

        List<Watch> watches = repository.findAll();
        assertEquals(0, watches.size());
    }


    @Test
    void getAllWatch_validRequest_returnsOk() throws Exception {
        Watch testWatch1 = repository.save(testWatch());
        Watch testWatch2 = repository.save(testWatch());
        String jsonContent = objectMapper.writeValueAsString(List.of(testWatch1, testWatch2));

        mockMvc
                .perform(
                        get(WATCH_CONTEXT_PATH)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }

    @Test
    void findWatchByName_validRequest_returnsOk() throws Exception {
        repository.save(testWatch());

        Watch testWatch2 = testWatch();
        testWatch2.setTitle("Different Title");
        testWatch2 = repository.save(testWatch2);
        String jsonContent = objectMapper.writeValueAsString(List.of(testWatch2));

        mockMvc
                .perform(
                        get(WATCH_CONTEXT_PATH).param("title", "Different Title")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }

    @Test
    void getWatchById_validRequest_returnsOk() throws Exception {
        repository.save(testWatch());
        Watch testWatch2 = repository.save(testWatch());
        String jsonContent = objectMapper.writeValueAsString(testWatch2);

        mockMvc
                .perform(
                        get(WATCH_CONTEXT_PATH + "/{id}", testWatch2.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }

    private WatchDto testWatchDto() {
        WatchDto watchDto = new WatchDto();
        watchDto.setTitle("Title");
        watchDto.setDescription("Descriptive description");
        watchDto.setPrice(100);
        watchDto.setFountain("FOUNTAIN".getBytes());
        return watchDto;
    }

    private Watch testWatch() {
        Watch watch = new Watch();
        watch.setTitle("Title");
        watch.setDescription("Descriptive description");
        watch.setPrice(100);
        watch.setFountain("FOUNTAIN".getBytes());
        return watch;
    }


}
