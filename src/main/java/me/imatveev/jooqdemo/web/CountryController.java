package me.imatveev.jooqdemo.web;

import lombok.RequiredArgsConstructor;
import me.imatveev.jooqdemo.domain.CountryService;
import me.imatveev.jooqdemo.domain.entity.Country;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    public List<Country> findAll() {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    public Country findById(@PathVariable Long id) {
        return countryService.findById(id);
    }

    @PostMapping
    public Country save(@RequestBody Country country) {
        return countryService.save(country);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        countryService.deleteById(id);
    }

    @PatchMapping
    public Country update(@RequestBody Country country) {
        return countryService.update(country);
    }
}
