package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.ClientsDao;
import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService implements ClientsDao {

    @Autowired
    private ClientsRepository repository;

    @Override
    public Clients findByIdClient(Long id) {
        return repository.findByIdClient(id);
    }

    @Override
    public Optional<Clients> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Clients findByContact(String contact) {
        return repository.findByContact(contact);
    }

    @Override
    public List<Clients> findByDeletedFalseOrderByIdClientDesc() {
        return repository.findByDeletedFalseOrderByIdClientDesc();
    }

    @Override
    public List<Clients> findByDeletedTrueOrderByIdClientDesc() {
        return repository.findByDeletedTrueOrderByIdClientDesc();
    }

    @Override
    public List<Clients> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdClientDesc(pageable);
    }

    @Override
    public List<Clients> findByCompagnie(Compagnies compagnie, Pageable pageable) {
        return repository.findByCompagnie(compagnie, pageable);
    }

    @Override
    public List<Clients> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Clients> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable) {
        return repository.rechercheCompagnie(compagnie, search, pageable);
    }

    @Override
    public Clients save(Clients client) {
        return repository.save(client);
    }

    @Override
    public List<Clients> save(List<Clients> clients) {
        return repository.saveAll(clients);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countByCompagnie(Compagnies compagnie) {
        return repository.countByCompagnie(compagnie);
    }

    @Override
    public Long countRechercheCompagnie(Compagnies compagnie, String search) {
        return repository.countRechercheCompagnie(compagnie, search);
    }
}
