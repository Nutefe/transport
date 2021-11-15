package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.BilletColisDao;
import com.bluerizon.transport.entity.BilletColis;
import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.entity.Voyages;
import com.bluerizon.transport.repository.BilletColisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BilletColisService implements BilletColisDao {

    @Autowired
    private BilletColisRepository repository;

    @Override
    public BilletColis findByIdBillet(Long id) {
        return repository.findByIdBillet(id);
    }

    @Override
    public List<BilletColis> findByDeletedFalseOrderByIdBilletDesc() {
        return repository.findByDeletedFalseOrderByIdBilletDesc();
    }

    @Override
    public List<BilletColis> findByDeletedTrueOrderByIdBilletDesc() {
        return repository.findByDeletedTrueOrderByIdBilletDesc();
    }

    @Override
    public List<BilletColis> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdBilletDesc(pageable);
    }

    @Override
    public List<BilletColis> findByUser(Users user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public List<BilletColis> findByVoyage(Voyages voyage, Pageable pageable) {
        return repository.findByVoyage(voyage, pageable);
    }

    @Override
    public List<BilletColis> findByClient(Clients client, Pageable pageable) {
        return repository.findByClient(client, pageable);
    }

    @Override
    public List<BilletColis> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<BilletColis> rechercheUser(Users user, String search, Pageable pageable) {
        return repository.rechercheUser(user, search, pageable);
    }

    @Override
    public List<BilletColis> rechercheVoyage(Voyages voyage, String search, Pageable pageable) {
        return repository.rechercheVoyage(voyage, search, pageable);
    }

    @Override
    public List<BilletColis> rechercheClient(Clients client, String search, Pageable pageable) {
        return repository.rechercheClient(client, search, pageable);
    }

    @Override
    public BilletColis save(BilletColis billetColi) {
        return repository.save(billetColi);
    }

    @Override
    public List<BilletColis> save(List<BilletColis> billetColis) {
        return repository.saveAll(billetColis);
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
