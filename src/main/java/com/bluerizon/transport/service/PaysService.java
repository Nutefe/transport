package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.PaysDao;
import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaysService implements PaysDao {

    @Autowired
    private PaysRepository repository;

    @Override
    public Pays findByIdPays(Integer id) {
        return repository.findByIdPays(id);
    }

    @Override
    public Optional<Pays> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Pays> findByDeletedFalseOrderByIdPaysDesc() {
        return repository.findByDeletedFalseOrderByIdPaysDesc();
    }

    @Override
    public List<Pays> findByDeletedTrueOrderByIdPaysDesc() {
        return repository.findByDeletedTrueOrderByIdPaysDesc();
    }

    @Override
    public List<Pays> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdPaysDesc(pageable);
    }

    @Override
    public List<Pays> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public Pays save(Pays pays) {
        return repository.save(pays);
    }

    @Override
    public List<Pays> save(List<Pays> pays) {
        return repository.saveAll(pays);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }
}
