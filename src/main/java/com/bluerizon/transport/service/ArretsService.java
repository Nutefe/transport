package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.ArretsDao;
import com.bluerizon.transport.entity.Arrets;
import com.bluerizon.transport.entity.Villes;
import com.bluerizon.transport.repository.ArretsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArretsService implements ArretsDao {

    @Autowired
    private ArretsRepository repository;

    @Override
    public Arrets findByIdArret(Long id) {
        return repository.findByIdArret(id);
    }

    @Override
    public Optional<Arrets> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Arrets> findByDeletedFalseOrderByIdArretDesc() {
        return repository.findByDeletedFalseOrderByIdArretDesc();
    }

    @Override
    public List<Arrets> findByDeletedTrueOrderByIdArretDesc() {
        return repository.findByDeletedTrueOrderByIdArretDesc();
    }

    @Override
    public List<Arrets> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalseOrderByIdArretDesc(pageable);
    }

    @Override
    public List<Arrets> findByVille(Villes ville, Pageable pageable) {
        return repository.findByVille(ville, pageable);
    }

    @Override
    public List<Arrets> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<Arrets> rechercheVille(Villes ville, String search, Pageable pageable) {
        return repository.rechercheVille(ville, search, pageable);
    }

    @Override
    public Arrets save(Arrets arret) {
        return repository.save(arret);
    }

    @Override
    public List<Arrets> save(List<Arrets> arrets) {
        return repository.saveAll(arrets);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

    @Override
    public Long countByVille(Villes ville) {
        return repository.countByVille(ville);
    }

    @Override
    public Long countRecherche(String search) {
        return repository.countRecherche(search);
    }

    @Override
    public Long countRechercheVille(Villes ville, String search) {
        return repository.countRechercheVille(ville, search);
    }
}
