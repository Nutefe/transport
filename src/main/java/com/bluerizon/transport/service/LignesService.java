package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.LignesDao;
import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.repository.LignesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LignesService  implements LignesDao {

    @Autowired
    private LignesRepository repository;

    @Override
    public Lignes findByIdLigne(Long id) {
        return repository.findByIdLigne(id);
    }

    @Override
    public List<Lignes> findByDeletedFalseOrderByIdLigneDesc() {
        return repository.findByDeletedFalseOrderByIdLigneDesc();
    }

    @Override
    public List<Lignes> findByDeletedTrueOrderByIdLigneDesc() {
        return repository.findByDeletedTrueOrderByIdLigneDesc();
    }

    @Override
    public List<Lignes> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdLigneDesc(pageable);
    }

    @Override
    public List<Lignes> findByLigne(Lignes ligne, Pageable pageable) {
        return repository.findByLigne(ligne, pageable);
    }

    @Override
    public List<Lignes> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Lignes> rechercheLigne(Lignes ligne, String search, Pageable pageable) {
        return repository.rechercheLigne(ligne, search, pageable);
    }

    @Override
    public Lignes save(Lignes ligne) {
        return repository.save(ligne);
    }

    @Override
    public List<Lignes> save(List<Lignes> lignes) {
        return repository.saveAll(lignes);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

    @Override
    public Long countByLigne(Lignes ligne) {
        return repository.countByLigne(ligne);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countRechercheLigne(Lignes ligne, String search) {
        return repository.countRechercheLigne(ligne, search);
    }
}
