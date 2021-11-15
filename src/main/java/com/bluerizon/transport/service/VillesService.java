package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.VillesDao;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.Villes;
import com.bluerizon.transport.repository.VillesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillesService implements VillesDao {

    @Autowired
    private VillesRepository repository;

    @Override
    public Villes findByIdVille(Long id) {
        return repository.findByIdVille(id);
    }

    @Override
    public List<Villes> findByDeletedFalseOrderByIdVilleDesc() {
        return repository.findByDeletedFalseOrderByIdVilleDesc();
    }

    @Override
    public List<Villes> findByDeletedTrueOrderByIdVilleDesc() {
        return repository.findByDeletedTrueOrderByIdVilleDesc();
    }

    @Override
    public List<Villes> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdVilleDesc(pageable);
    }

    @Override
    public List<Villes> findByCompagnie(Compagnies compagnie, Pageable pageable) {
        return repository.findByCompagnie(compagnie, pageable);
    }

    @Override
    public List<Villes> findByPays(Pays pays, Pageable pageable) {
        return repository.findByPays(pays, pageable);
    }

    @Override
    public List<Villes> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Villes> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable) {
        return repository.rechercheCompagnie(compagnie, search, pageable);
    }

    @Override
    public List<Villes> recherchePays(Pays pays, String search, Pageable pageable) {
        return repository.recherchePays(pays, search, pageable);
    }

    @Override
    public Villes save(Villes ville) {
        return repository.save(ville);
    }

    @Override
    public List<Villes> save(List<Villes> villes) {
        return repository.saveAll(villes);
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
    public Long countRecherchePays(Pays pays, String search) {
        return repository.countRecherchePays(pays, search);
    }
}
