package br.com.ifsp.backend.repository;

import br.com.ifsp.backend.model.CountryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryModel, Long> {
}
