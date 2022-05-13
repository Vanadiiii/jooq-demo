package me.imatveev.jooqdemo.web;

import me.imatveev.jooqdemo.domain.entity.Country;
import me.imatveev.jooqdemo.repository.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/countries")
public record CountryController(CountryRepository repository) {

    @GetMapping("/{id}")
    public Country findById(@PathVariable Long id) {
        return repository.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't found country with id - " + id));
    }

    @PostMapping
    public Country save(@RequestBody Country country) {
        return repository.insert(country);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return ResponseEntity.status(
                        repository.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND
                )
                .build();
    }

    @PatchMapping
    public Country update(@RequestBody Country country) {
        return repository.update(country);
    }
}
