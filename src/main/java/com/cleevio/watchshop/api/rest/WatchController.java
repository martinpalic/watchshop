package com.cleevio.watchshop.api.rest;

import com.cleevio.watchshop.api.dto.WatchDto;
import com.cleevio.watchshop.service.WatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cleevio.watchshop.api.ApiValues.WATCH_CONTEXT_PATH;

@Controller
@RequestMapping(WATCH_CONTEXT_PATH)
@RequiredArgsConstructor
public class WatchController {

    private final WatchService watchService;

    @GetMapping
    public ResponseEntity<List<WatchDto>> getAll() {
        List<WatchDto> watches = watchService.getAll();
        return ResponseEntity.ok(watches);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WatchDto> getById(@Valid @PathVariable UUID id) {
        Optional<WatchDto> watch = watchService.getById(id);
        return ResponseEntity.of(watch);
    }

    @GetMapping
    public ResponseEntity<List<WatchDto>> findByTitle(@NotBlank @RequestParam String title) {
        List<WatchDto> watches = watchService.findByTitle(title);
        return ResponseEntity.ok(watches);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody WatchDto watchDto) {
        UUID uuid = watchService.save(watchDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uuid)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<WatchDto> update(@Valid @RequestBody WatchDto watchDto) {
        Optional<WatchDto> updated = watchService.update(watchDto);
        return ResponseEntity.of(updated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@Valid @PathVariable UUID id) {
        watchService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
