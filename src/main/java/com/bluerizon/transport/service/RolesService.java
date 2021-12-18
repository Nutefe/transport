package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.RolesDao;
import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService implements RolesDao {

    @Autowired
    private RolesRepository repository;

    @Override
    public Roles findByIdRole(Integer id) {
        return repository.findByIdRole(id);
    }

    @Override
    public Optional<Roles> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Roles> findByDeletedFalseOrderByIdRoleDesc() {
        return repository.findByDeletedFalseOrderByIdRoleDesc();
    }

    @Override
    public List<Roles> findByDeletedTrueOrderByIdRoleDesc() {
        return repository.findByDeletedTrueOrderByIdRoleDesc();
    }

    @Override
    public List<Roles> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdRoleDesc(pageable);
    }

    @Override
    public List<Roles> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public Roles save(Roles role) {
        return repository.save(role);
    }

    @Override
    public List<Roles> save(List<Roles> roles) {
        return repository.saveAll(roles);
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
