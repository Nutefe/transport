package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.BilletsDao;
import com.bluerizon.transport.entity.Billets;
import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.entity.Voyages;
import com.bluerizon.transport.repository.BilletsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BilletsService implements BilletsDao {

    @Autowired
    private BilletsRepository repository;

    @Override
    public Billets findByIdBillet(Long id) {
        return repository.findByIdBillet(id);
    }

    @Override
    public List<Billets> findByDeletedFalseOrderByIdBilletDesc() {
        return repository.findByDeletedFalseOrderByIdBilletDesc();
    }

    @Override
    public List<Billets> findByDeletedTrueOrderByIdBilletDesc() {
        return repository.findByDeletedTrueOrderByIdBilletDesc();
    }

    @Override
    public List<Billets> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdBilletDesc(pageable);
    }

    @Override
    public List<Billets> findByUser(Users user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public List<Billets> findByVoyage(Voyages voyage, Pageable pageable) {
        return repository.findByVoyage(voyage, pageable);
    }

    @Override
    public List<Billets> findByClient(Clients client, Pageable pageable) {
        return repository.findByClient(client, pageable);
    }

    @Override
    public List<Billets> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Billets> rechercheUser(Users user, String search, Pageable pageable) {
        return repository.rechercheUser(user, search, pageable);
    }

    @Override
    public List<Billets> rechercheVoyage(Voyages voyage, String search, Pageable pageable) {
        return repository.rechercheVoyage(voyage, search, pageable);
    }

    @Override
    public List<Billets> rechercheClient(Clients client, String search, Pageable pageable) {
        return repository.rechercheClient(client, search, pageable);
    }

    @Override
    public Billets save(Billets billet) {
        return repository.save(billet);
    }

    @Override
    public List<Billets> save(List<Billets> billets) {
        return repository.saveAll(billets);
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
