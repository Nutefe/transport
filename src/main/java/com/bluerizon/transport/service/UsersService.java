package com.bluerizon.transport.service;

import com.bluerizon.transport.dao.UsersDao;
import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements UsersDao {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users findByIdUser(Long id) {
        return repository.findByIdUser(id);
    }

    @Override
    public Optional<Users> getOneOptional(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Users> findByUsernameAndActive(String username) {
        return repository.findByUsernameAndActiveTrueAndDeletedFalse(username);
    }

    @Override
    public List<Users> selectUser(Long idUser, Pageable pageable) {
        return repository.selectUser(idUser, pageable);
    }

    @Override
    public List<Users> selectUser(Long idUser) {
        return repository.selectUser(idUser);
    }

    @Override
    public List<Users> selectUserSys(Long idUser, Pageable pageable) {
        return repository.selectUserSys(idUser, pageable);
    }

    @Override
    public List<Users> selectUserSys(Long idUser) {
        return repository.selectUserSys(idUser);
    }

    @Override
    public List<Users> selectUserDirecteur(Pageable pageable) {
        return repository.selectUserDirecteur(pageable);
    }

    @Override
    public List<Users> selectUserDirecteur() {
        return repository.selectUserDirecteur();
    }

    @Override
    public List<Users> selectUserDirecteurActif(Pageable pageable) {
        return repository.selectUserDirecteurActif(pageable);
    }

    @Override
    public List<Users> selectUserDirecteurActif() {
        return repository.selectUserDirecteurActif();
    }

    @Override
    public List<Users> selectUserDirecteurDesactif(Pageable pageable) {
        return repository.selectUserDirecteurDesactif(pageable);
    }

    @Override
    public List<Users> selectUserDirecteurDesactif() {
        return repository.selectUserDirecteurDesactif();
    }

    @Override
    public List<Users> rechercheUser(String search, Long idUser, Pageable pageable) {
        return repository.rechercheUser(search, idUser, pageable);
    }

    @Override
    public List<Users> rechercheDirecteur(String search, Pageable pageable) {
        return repository.rechercheDirecteur(search, pageable);
    }

    @Override
    public List<Users> rechercheDirecteurActif(String search, Pageable pageable) {
        return repository.rechercheDirecteurActif(search, pageable);
    }

    @Override
    public List<Users> rechercheDirecteurDesactif(String search, Pageable pageable) {
        return repository.rechercheDirecteurDesactif(search, pageable);
    }

    @Override
    public void active(Long id) {
        repository.active(id);
    }

    @Override
    public void desactive(Long id) {
        repository.desactive(id);
    }

    @Override
    public void updateAvatar(Long id, String avatar) {
        repository.updateAvatar(id, avatar);
    }

    @Override
    public void updateExpire(Long id, Date expirer) {
        repository.updateExpire(id, expirer);
    }

    @Override
    public void updateNbrCompagnie(Long id, Integer nbrCompagnie) {
        repository.updateNbrCompagnie(id, nbrCompagnie);
    }

    @Override
    public void updateUser(Long id, String username, String email, String nom, String prenom, String telephone,
                           String adresse, Date expirer, Integer nbrCompagnie, Roles role) {
        repository.updateUser(id, username, email, nom, prenom, telephone, adresse, expirer, nbrCompagnie, role);
    }

    @Override
    public Users save(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public Long countUser(Long idUser) {
        return repository.countUser(idUser);
    }

    @Override
    public Long countUserSys(Long idUser) {
        return repository.countUserSys(idUser);
    }

    @Override
    public Long contUserDirecteur() {
        return repository.contUserDirecteur();
    }

    @Override
    public Long countUserDirecteurActif() {
        return repository.countUserDirecteurActif();
    }

    @Override
    public Long countUserDirecteurDesactif() {
        return repository.countUserDirecteurDesactif();
    }

    @Override
    public Long countRechercheUser(String search, Long idUser) {
        return repository.countRechercheUser(search, idUser);
    }

    @Override
    public Long countRechercheDirecteur(String search) {
        return repository.countRechercheDirecteur(search);
    }

    @Override
    public Long countRechercheDirecteurActif(String search) {
        return repository.countRechercheDirecteurActif(search);
    }

    @Override
    public Long countRechercheDirecteurDesactif(String search) {
        return repository.countRechercheDirecteurDesactif(search);
    }

    @Override
    public boolean existsByUsername(String username, Long id) {
        return repository.existsByUsername(username, id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email, Long id) {
        return repository.existsByEmail(email, id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
