package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.CompagniesDao;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.repository.CompagniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompagniesService implements CompagniesDao {

    @Autowired
    private CompagniesRepository repository;

    @Override
    public Compagnies findByIdCompagnie(Long id) {
        return repository.findByIdCompagnie(id);
    }

    @Override
    public Optional<Compagnies> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Compagnies> findByDeletedFalseOrderByIdCompagnieDesc() {
        return repository.findByDeletedFalseOrderByIdCompagnieDesc();
    }

    @Override
    public List<Compagnies> findByDeletedTrueOrderByIdCompagnieDesc() {
        return repository.findByDeletedTrueOrderByIdCompagnieDesc();
    }

    @Override
    public List<Compagnies> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdCompagnieDesc(pageable);
    }

    @Override
    public List<Compagnies> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public Compagnies save(Compagnies compagnie) {
        return repository.save(compagnie);
    }

    @Override
    public List<Compagnies> save(List<Compagnies> compagnies) {
        return repository.saveAll(compagnies);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public boolean existsByEmail(String email, Long id) {
        return repository.existsByEmail(email, id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
