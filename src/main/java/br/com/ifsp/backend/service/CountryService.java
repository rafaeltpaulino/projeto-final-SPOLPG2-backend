package br.com.ifsp.backend.service;

import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country findById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum país encontado com o ID: " + id));
    }
}
