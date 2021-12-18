package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.BilletPassagersDao;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.repository.BilletPassagersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BilletPassagersService implements BilletPassagersDao {

    @Autowired
    private BilletPassagersRepository repository;

    @Override
    public BilletPassagers findByIdBillet(Long id) {
        return repository.findByIdBillet(id);
    }

    @Override
    public Optional<BilletPassagers> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<BilletPassagers> findByDeletedFalseOrderByIdBilletDesc() {
        return repository.findByDeletedFalseOrderByIdBilletDesc();
    }

    @Override
    public List<BilletPassagers> findByDeletedTrueOrderByIdBilletDesc() {
        return repository.findByDeletedTrueOrderByIdBilletDesc();
    }

    @Override
    public List<BilletPassagers> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdBilletDesc(pageable);
    }

    @Override
    public List<BilletPassagers> findByUser(Users user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public List<BilletPassagers> findByVoyage(Voyages voyage, Pageable pageable) {
        return repository.findByVoyage(voyage, pageable);
    }

    @Override
    public List<BilletPassagers> findByClient(Clients client, Pageable pageable) {
        return repository.findByClient(client, pageable);
    }

    @Override
    public List<BilletPassagers> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<BilletPassagers> rechercheUser(Users user, String search, Pageable pageable) {
        return repository.rechercheUser(user, search, pageable);
    }

    @Override
    public List<BilletPassagers> rechercheVoyage(Voyages voyage, String search, Pageable pageable) {
        return repository.rechercheVoyage(voyage, search, pageable);
    }

    @Override
    public List<BilletPassagers> rechercheClient(Clients client, String search, Pageable pageable) {
        return repository.rechercheClient(client, search, pageable);
    }

    @Override
    public BilletPassagers save(BilletPassagers billetPassager) {
        return repository.save(billetPassager);
    }

    @Override
    public List<BilletPassagers> save(List<BilletPassagers> billetPassagers) {
        return repository.saveAll(billetPassagers);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

    @Override
    public Long countByUser(Users user) {
        return repository.countByUser(user);
    }

    @Override
    public Long countByVoyage(Voyages voyage) {
        return repository.countByVoyage(voyage);
    }

    @Override
    public Long countByClient(Clients client) {
        return repository.countByClient(client);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countRechercheUser(Users user, String search) {
        return repository.countRechercheUser(user, search);
    }

    @Override
    public Long countRechercheVoyage(Voyages voyage, String search) {
        return repository.countRechercheVoyage(voyage, search);
    }

    @Override
    public Long countRechercheClient(Clients client, String search) {
        return repository.countRechercheClient(client, search);
    }
}
