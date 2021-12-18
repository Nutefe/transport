package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.BusDao;
import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBus;
import com.bluerizon.transport.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService implements BusDao {

    @Autowired
    private BusRepository repository;

    @Override
    public Bus findByIdBus(Long id) {
        return repository.findByIdBus(id);
    }

    @Override
    public Optional<Bus> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Bus> findByDeletedFalseOrderByIdBusDesc() {
        return repository.findByDeletedFalseOrderByIdBusDesc();
    }

    @Override
    public List<Bus> findByDeletedTrueOrderByIdBusDesc() {
        return repository.findByDeletedTrueOrderByIdBusDesc();
    }

    @Override
    public List<Bus> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdBusDesc(pageable);
    }

    @Override
    public List<Bus> findByCompagnie(Compagnies compagnie, Pageable pageable) {
        return repository.findByCompagnie(compagnie, pageable);
    }

    @Override
    public List<Bus> findByTypeBus(TypeBus typeBus, Pageable pageable) {
        return repository.findByTypeBus(typeBus, pageable);
    }

    @Override
    public List<Bus> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Bus> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable) {
        return repository.rechercheCompagnie(compagnie, search, pageable);
    }

    @Override
    public List<Bus> rechercheTypeBus(TypeBus typeBus, String search, Pageable pageable) {
        return repository.rechercheTypeBus(typeBus, search, pageable);
    }

    @Override
    public Bus save(Bus bus) {
        return repository.save(bus);
    }

    @Override
    public List<Bus> save(List<Bus> bus) {
        return repository.saveAll(bus);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

    @Override
    public Long countByCompagnie(Compagnies compagnie) {
        return repository.countByCompagnie(compagnie);
    }

    @Override
    public Long countByTypeBus(TypeBus typeBus) {
        return repository.countByTypeBus(typeBus);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countRechercheCompagnie(Compagnies compagnie, String search) {
        return repository.countRechercheCompagnie(compagnie, search);
    }

    @Override
    public Long countRechercheTypeBus(TypeBus typeBus, String search) {
        return repository.countRechercheTypeBus(typeBus, search);
    }

    @Override
    public boolean existsByNumero(String numero, Long id) {
        return repository.existsByNumero(numero, id);
    }

    @Override
    public boolean existsByNumero(String numero) {
        return repository.existsByNumero(numero);
    }
}
