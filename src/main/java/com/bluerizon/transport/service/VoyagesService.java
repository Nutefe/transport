package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.VoyagesDao;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.entity.TarifPK;
import com.bluerizon.transport.entity.Voyages;
import com.bluerizon.transport.repository.VoyagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyagesService implements VoyagesDao {

    @Autowired
    private VoyagesRepository repository;

    @Override
    public Voyages findByIdVoyage(Long id) {
        return repository.findByIdVoyage(id);
    }

    @Override
    public List<Voyages> findByDeletedFalse() {
        return repository.findByDeletedFalse();
    }

    @Override
    public List<Voyages> findByDeletedTrue() {
        return repository.findByDeletedTrue();
    }

    @Override
    public List<Voyages> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalse(pageable);
    }

    @Override
    public List<Voyages> findByLigne(Lignes ligne, Pageable pageable) {
        return repository.findByLigne(ligne, pageable);
    }

    @Override
    public List<Voyages> findByCompagnie(Compagnies compagnie, Pageable pageable) {
        return repository.findByCompagnie(compagnie, pageable);
    }

    @Override
    public List<Voyages> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Voyages> rechercheLigne(Lignes ligne, String search, Pageable pageable) {
        return repository.rechercheLigne(ligne, search, pageable);
    }

    @Override
    public List<Voyages> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable) {
        return repository.rechercheCompagnie(compagnie, search, pageable);
    }

    @Override
    public Voyages save(Voyages voyage) {
        return repository.save(voyage);
    }

    @Override
    public List<Voyages> save(List<Voyages> voyages) {
        return repository.saveAll(voyages);
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
    public Long countByCompagnie(Compagnies compagnie) {
        return repository.countByCompagnie(compagnie);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countRechercheLigne(Lignes ligne, String search) {
        return repository.countRechercheLigne(ligne, search);
    }

    @Override
    public Long countRechercheCompagnie(Compagnies compagnie, String search) {
        return repository.countRechercheCompagnie(compagnie, search);
    }
}
