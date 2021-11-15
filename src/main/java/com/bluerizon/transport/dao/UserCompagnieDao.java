package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCompagnieDao {

    UserCompagnies findByUserPK(final UserPK userPK);

    List<UserCompagnies> findByDeletedFalse();

    List<UserCompagnies> findByDeletedTrue();

    List<UserCompagnies> findAllByDeletedFalse(Pageable pageable);

    List<UserCompagnies> findByCompagnie(Compagnies compagnie, Pageable pageable);

    List<UserCompagnies> findByUser(Users user, Pageable pageable);

    List<UserCompagnies> recherche(String search, Pageable pageable);

    List<UserCompagnies> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    List<UserCompagnies> rechercheUser(Users user, String search, Pageable pageable);

    UserCompagnies save(UserCompagnies userCompagnie);

    List<UserCompagnies> save(List<UserCompagnies> userCompagnies);

    Long countByDeletedFalse();

    Long countByCompagnie(Compagnies compagnie);

    Long countByUser(Users user);

    Long countRecherche(String search);

    Long countRechercheCompagnie(Compagnies compagnie, String search);

    Long countRechercheUser(Users user, String search);

}
