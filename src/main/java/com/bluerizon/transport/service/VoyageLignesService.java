package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.VoyageLignesDao;
import com.bluerizon.transport.dao.VoyagesDao;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.repository.VoyageLignesRepository;
import com.bluerizon.transport.repository.VoyagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoyageLignesService implements VoyageLignesDao {

    @Autowired
    private VoyageLignesRepository repository;


    @Override
    public VoyageLignes findByVoyagePK(VoyagePK voyagePK) {
        return repository.findByVoyagePK(voyagePK);
    }

    @Override
    public List<VoyageLignes> findByDeletedFalse() {
        return repository.findByDeletedFalse();
    }

    @Override
    public List<VoyageLignes> findByDeletedTrue() {
        return repository.findByDeletedTrue();
    }

    @Override
    public List<VoyageLignes> findByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalse(pageable);
    }

    @Override
    public List<VoyageLignes> findByVoyage(Voyages voyage) {
        return repository.findByVoyage(voyage);
    }

    @Override
    public List<VoyageLignes> findByVoyage(Voyages voyage, Pageable pageable) {
        return repository.findByVoyage(voyage, pageable);
    }

    @Override
    public List<VoyageLignes> findByBus(Bus bus, Pageable pageable) {
        return repository.findByBus(bus, pageable);
    }

    @Override
    public VoyageLignes save(VoyageLignes voyageLigne) {
        return repository.save(voyageLigne);
    }

    @Override
    public List<VoyageLignes> save(List<VoyageLignes> voyageLignes) {
        return repository.saveAll(voyageLignes);
    }

    @Override
    public void delete(VoyagePK voyagePK) {
        repository.deleteById(voyagePK);
    }

    @Override
    public void delete(List<VoyageLignes> voyageLignes) {
        repository.deleteAll(voyageLignes);
    }
}
