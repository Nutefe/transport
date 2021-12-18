package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.UserCompagnieDao;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.UserCompagnies;
import com.bluerizon.transport.entity.UserPK;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.repository.UserCompagniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCompagniesService implements UserCompagnieDao {

    @Autowired
    private UserCompagniesRepository repository;

    @Override
    public UserCompagnies findByUserPK(UserPK userPK) {
        return repository.findByUserPK(userPK);
    }

    @Override
    public List<UserCompagnies> findByDeletedFalse() {
        return repository.findByDeletedFalse();
    }

    @Override
    public List<UserCompagnies> findByDeletedTrue() {
        return repository.findByDeletedTrue();
    }

    @Override
    public List<UserCompagnies> findAllByDeletedFalse(Pageable pageable) {
        return repository.findByDeletedFalse(pageable);
    }

    @Override
    public List<UserCompagnies> findByCompagnie(Compagnies compagnie, Pageable pageable) {
        return repository.findByCompagnie(compagnie, pageable);
    }

    @Override
    public List<UserCompagnies> findByUser(Users user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public List<UserCompagnies> recherche(String search, Pageable pageable) {
        return repository.recherche(search, pageable);
    }

    @Override
    public List<UserCompagnies> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable) {
        return repository.rechercheCompagnie(compagnie, search, pageable);
    }

    @Override
    public List<UserCompagnies> rechercheUser(Users user, String search, Pageable pageable) {
        return repository.rechercheUser(user, search, pageable);
    }

    @Override
    public UserCompagnies save(UserCompagnies userCompagnie) {
        return repository.save(userCompagnie);
    }

    @Override
    public List<UserCompagnies> save(List<UserCompagnies> userCompagnies) {
        return repository.saveAll(userCompagnies);
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
    public Long countByUser(Users user) {
        return repository.countByUser(user);
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
    public Long countRechercheUser(Users user, String search) {
        return repository.countRechercheUser(user, search);
    }

    @Override
    public void delete(UserPK userPK) {
        repository.deleteById(userPK);
    }

}
