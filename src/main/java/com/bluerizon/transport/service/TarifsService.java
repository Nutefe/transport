package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.TarifsDao;
import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.entity.TarifPK;
import com.bluerizon.transport.entity.Tarifs;
import com.bluerizon.transport.repository.TarifsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifsService implements TarifsDao {

    @Autowired
    private TarifsRepository repository;

    @Override
    public Tarifs findByTarifPK(TarifPK tarifPK) {
        return repository.findByTarifPK(tarifPK);
    }

    @Override
    public List<Tarifs> findByDeletedFalse() {
        return repository.findByDeletedFalse();
    }

    @Override
    public List<Tarifs> findByDeletedTrue() {
        return repository.findByDeletedTrue();
    }

    @Override
    public List<Tarifs> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalse(pageable);
    }

    @Override
    public List<Tarifs> findByLigne(Lignes ligne, Pageable pageable) {
        return repository.findByLigne(ligne, pageable);
    }

    @Override
    public List<Tarifs> findByBus(Bus bus, Pageable pageable) {
        return repository.findByBus(bus, pageable);
    }

    @Override
    public List<Tarifs> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Tarifs> rechercheLigne(Lignes ligne, String search, Pageable pageable) {
        return repository.rechercheLigne(ligne, search, pageable);
    }

    @Override
    public List<Tarifs> rechercheBus(Bus bus, String search, Pageable pageable) {
        return repository.rechercheBus(bus, search, pageable);
    }

    @Override
    public Tarifs save(Tarifs tarif) {
        return repository.save(tarif);
    }

    @Override
    public List<Tarifs> save(List<Tarifs> tarifs) {
        return repository.saveAll(tarifs);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long contRechercheLigne(Lignes ligne, String search) {
        return repository.contRechercheLigne(ligne, search);
    }

    @Override
    public Long countRechercheBus(Bus bus, String search) {
        return repository.countRechercheBus(bus, search);
    }

    @Override
    public Long countByLigne(Lignes ligne) {
        return repository.countByLigne(ligne);
    }

    @Override
    public Long countByBus(Bus bus) {
        return repository.countByBus(bus);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }
}
