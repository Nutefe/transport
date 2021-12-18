package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.TypeBusDao;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBus;
import com.bluerizon.transport.repository.TypeBusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeBusService implements TypeBusDao {

    @Autowired
    private TypeBusRepository repository;

    @Override
    public TypeBus findByIdTypeBus(Long id) {
        return repository.findByIdTypeBus(id);
    }

    @Override
    public Optional<TypeBus> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<TypeBus> findByDeletedFalseOrderByIdTypeBusDesc() {
        return repository.findByDeletedFalseOrderByIdTypeBusDesc();
    }

    @Override
    public List<TypeBus> findByDeletedTrueOrderByIdTypeBusDesc() {
        return repository.findByDeletedTrueOrderByIdTypeBusDesc();
    }

    @Override
    public List<TypeBus> findByCompagnie(Compagnies compagnie) {
        return repository.findByCompagnie(compagnie);
    }

    @Override
    public List<TypeBus> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdTypeBusDesc(pageable);
    }

    @Override
    public List<TypeBus> findByCompagnie(Pageable pageable, Compagnies compagnie) {
        return repository.findByCompagnie(pageable, compagnie);
    }

    @Override
    public List<TypeBus> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<TypeBus> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable) {
        return repository.rechercheCompagnie(compagnie, search, pageable);
    }

    @Override
    public TypeBus save(TypeBus typeBus) {
        return repository.save(typeBus);
    }

    @Override
    public List<TypeBus> save(List<TypeBus> typeBus) {
        return repository.saveAll(typeBus);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

    @Override
    public Long countRechercheCompagnie(Compagnies compagnie, String search) {
        return repository.countRechercheCompagnie(compagnie, search);
    }

    @Override
    public Long countByCompagnie(Compagnies compagnie) {
        return repository.countByCompagnie(compagnie);
    }
}
