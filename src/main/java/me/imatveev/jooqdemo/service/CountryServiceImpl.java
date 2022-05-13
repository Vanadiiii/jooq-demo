package me.imatveev.jooqdemo.service;

import lombok.RequiredArgsConstructor;
import me.imatveev.jooqdemo.domain.CountryRepository;
import me.imatveev.jooqdemo.domain.CountryService;
import me.imatveev.jooqdemo.domain.entity.Country;
import me.imatveev.jooqdemo.domain.exception.CountryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;

    @Override
    public List<Country> findAll() {
        return repository.findAll();
    }

    @Override
    public Country findById(Long id) throws CountryNotFoundException {
        return repository.find(id)
                .orElseThrow(() -> new CountryNotFoundException("Can't found country with id - " + id));
    }

    @Override
    public Country save(Country country) {
        return repository.insert(country);
    }

    @Override
    public void deleteById(Long id) throws CountryNotFoundException {
        if (!repository.delete(id)) {
            throw new CountryNotFoundException("Can't found country with id - " + id);
        }
    }

    @Override
    public Country update(Country country) {
        return repository.update(country);
    }
}
