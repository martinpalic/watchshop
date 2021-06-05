package com.cleevio.watchshop.service;

import com.cleevio.watchshop.api.dto.WatchDto;
import com.cleevio.watchshop.persistence.entity.Watch;
import com.cleevio.watchshop.persistence.repository.WatchRepository;
import com.cleevio.watchshop.service.mapping.WatchMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchServiceTest {

    @Mock
    private WatchRepository repository;
    @Mock
    private WatchMapper mapper;
    @InjectMocks
    private WatchService service;


    @Test
    void save() {
        WatchDto watchDto = testWatchDto();
        Watch watch = testWatch();
        Watch watchWithId = testWatch();
        watchWithId.setId(UUID.randomUUID());

        when(mapper.toWatch(watchDto)).thenReturn(watch);
        when(repository.save(watch)).thenReturn(watchWithId);

        UUID uuid = service.save(watchDto);

        verify(mapper, times(1)).toWatch(any());
        verify(repository, times(1)).save(any());

        assertEquals(watchWithId.getId(), uuid);
    }

    @Test
    void findByTitle() {
        WatchDto watchDto = testWatchDto();
        Watch watch = testWatch();

        List<Watch> watchList = List.of(watch);
        List<WatchDto> expectedDtos = List.of(watchDto);

        when(repository.findByTitle("Title")).thenReturn(watchList);
        when(mapper.toDtos(watchList)).thenReturn(expectedDtos);

        List<WatchDto> dtos = service.findByTitle("Title");

        verify(mapper, times(1)).toDtos(any());
        verify(repository, times(1)).findByTitle(any());

        assertEquals(1, dtos.size());
        assertEquals(expectedDtos.get(0), dtos.get(0));
    }

    @Test
    void getById() {
        WatchDto watchDto = testWatchDto();
        Watch watch = testWatch();
        UUID uuid = UUID.randomUUID();

        when(repository.findById(uuid)).thenReturn(Optional.of(watch));
        when(mapper.toDto(watch)).thenReturn(watchDto);

        Optional<WatchDto> dtoOptional = service.getById(uuid);

        verify(mapper, times(1)).toDto(any());
        verify(repository, times(1)).findById(any());

        assertTrue(dtoOptional.isPresent());
        assertEquals(dtoOptional.get(), watchDto);
    }

    @Test
    void getAll() {
        WatchDto watchDto = testWatchDto();
        Watch watch = testWatch();

        List<Watch> watchList = List.of(watch);
        List<WatchDto> expectedDtos = List.of(watchDto);

        when(repository.findAll()).thenReturn(watchList);
        when(mapper.toDtos(watchList)).thenReturn(expectedDtos);

        List<WatchDto> dtos = service.getAll();

        verify(mapper, times(1)).toDtos(any());
        verify(repository, times(1)).findAll();

        assertEquals(1, dtos.size());
        assertEquals(expectedDtos.get(0), dtos.get(0));
    }

    @Test
    void update() {
        UUID uuid = UUID.randomUUID();
        WatchDto watchDto = testWatchDto();
        watchDto.setId(uuid);
        WatchDto changedWatchDto = testWatchDto();
        changedWatchDto.setId(uuid);
        changedWatchDto.setDescription("New Description");

        Watch watch = testWatch();
        watch.setId(uuid);

        when(repository.findById(uuid)).thenReturn(Optional.of(watch));
        when(repository.save(watch)).thenReturn(watch);

        Optional<WatchDto> dtoOptional = service.update(changedWatchDto);

        verify(mapper, times(1)).updateFromDto(changedWatchDto, watch);
        verify(repository, times(1)).findById(uuid);
        verify(repository, times(1)).save(watch);

        assertTrue(dtoOptional.isPresent());
        assertEquals(dtoOptional.get(), changedWatchDto);
    }

    @Test
    void delete() {
        UUID uuid = UUID.randomUUID();

        service.delete(uuid);

        verify(repository, times(1)).deleteById(uuid);
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