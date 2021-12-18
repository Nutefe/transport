package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.TypeBilletsDao;
import com.bluerizon.transport.entity.TypeBillets;
import com.bluerizon.transport.repository.TypeBilletsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeBilletsService implements TypeBilletsDao {

    @Autowired
    private TypeBilletsRepository repository;

    @Override
    public TypeBillets findByIdTypeBillet(Integer id) {
        return repository.findByIdTypeBillet(id);
    }

    @Override
    public Optional<TypeBillets> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<TypeBillets> findByDeletedFalseOrderByIdTypeBilletDesc() {
        return repository.findByDeletedFalseOrderByIdTypeBilletDesc();
    }

    @Override
    public List<TypeBillets> findByDeletedTrueOrderByIdTypeBilletDesc() {
        return repository.findByDeletedTrueOrderByIdTypeBilletDesc();
    }

    @Override
    public List<TypeBillets> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdTypeBilletDesc(pageable);
    }

    @Override
    public List<TypeBillets> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public TypeBillets save(TypeBillets typeBillet) {
        return repository.save(typeBillet);
    }

    @Override
    public List<TypeBillets> save(List<TypeBillets> typeBillets) {
        return repository.saveAll(typeBillets);
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
