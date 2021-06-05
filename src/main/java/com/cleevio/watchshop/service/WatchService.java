package com.cleevio.watchshop.service;

import com.cleevio.watchshop.api.dto.WatchDto;
import com.cleevio.watchshop.persistence.entity.Watch;
import com.cleevio.watchshop.service.mapping.WatchMapper;
import com.cleevio.watchshop.persistence.repository.WatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WatchService {

    private final WatchRepository repository;
    private final WatchMapper mapper;

    public UUID save(WatchDto watchDto) {
        Watch watch = mapper.toWatch(watchDto);
        Watch saved = repository.save(watch);
        return saved.getId();
    }

    public List<WatchDto> findByTitle(String title) {
        List<Watch> watches = repository.findByTitle(title);
        return mapper.toDtos(watches);
    }

    public Optional<WatchDto> getById(UUID id) {
        Optional<Watch> watch = repository.findById(id);
        return watch.map(mapper::toDto);
    }

    public List<WatchDto> getAll() {
        List<Watch> watches = repository.findAll();
        return mapper.toDtos(watches);
    }

    public Optional<WatchDto> update(WatchDto watchDto) {
        Optional<Watch> watchOptional = repository.findById(watchDto.getId());
        return watchOptional.map(updateWatchEntityFunction(watchDto));
    }

    private Function<Watch, WatchDto> updateWatchEntityFunction(WatchDto watchDto) {
        return watch -> {
            mapper.updateFromDto(watchDto, watch);
            repository.save(watch);
            return watchDto;
        };
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
