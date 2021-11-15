package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UsersDao {

    Users findByIdUser(final Long id);

    Optional<Users> getOneOptional(final Long id);

    Optional<Users> findByUsernameAndActive(final String username);

    List<Users> selectUser(Long idUser, Pageable pageable);

    List<Users> selectUser(Long idUser);

    List<Users> selectUserSys(Long idUser, Pageable pageable);

    List<Users> selectUserSys(Long idUser);

    List<Users> selectUserDirecteur(Pageable pageable);

    List<Users> selectUserDirecteur();

    List<Users> selectUserDirecteurActif(Pageable pageable);

    List<Users> selectUserDirecteurActif();

    List<Users> selectUserDirecteurDesactif(Pageable pageable);

    List<Users> selectUserDirecteurDesactif();

    List<Users> rechercheUser(String search, Long idUser, Pageable pageable);

    List<Users> rechercheDirecteur(String search, Pageable pageable);

    List<Users> rechercheDirecteurActif(String search, Pageable pageable);

    List<Users> rechercheDirecteurDesactif(String search, Pageable pageable);

    void active(Long id);

    void desactive(Long id);

    void updateAvatar(Long id, String avatar);

    void updateExpire(Long id, Date expirer);

    void updateNbrCompagnie(Long id, Integer nbrCompagnie);

    void updateUser(Long id, String username, String email, String nom, String prenom, String telephone,
                    String adresse, Date expirer, Integer nbrCompagnie, Roles role);

    Users save(Users user);

    Long countUser(Long idUser);

    Long countUserSys(Long idUser);

    Long contUserDirecteur();

    Long countUserDirecteurActif();

    Long countUserDirecteurDesactif();

    Long countRechercheUser(String search, Long idUser);

    Long countRechercheDirecteur(String search);

    Long countRechercheDirecteurActif(String search);

    Long countRechercheDirecteurDesactif(String search);

    boolean existsByUsername(final String username, final Long id);

    boolean existsByUsername(final String username);

    boolean existsByEmail(final String email, final Long id);

    boolean existsByEmail( final String email);

}
