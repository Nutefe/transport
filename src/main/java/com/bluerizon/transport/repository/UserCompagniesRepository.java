package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCompagniesRepository extends JpaRepository<UserCompagnies, UserPK> {

    UserCompagnies findByUserPK(final UserPK userPK);

    List<UserCompagnies> findByDeletedFalse();

    List<UserCompagnies> findByDeletedTrue();

    List<UserCompagnies> findByDeletedFalse(Pageable pageable);

    @Query("SELECT u FROM UserCompagnies u WHERE u.deleted = false AND u.userPK.compagnie=?1")
    List<UserCompagnies> findByCompagnie(Compagnies compagnie, Pageable pageable);

    @Query("SELECT u FROM UserCompagnies u WHERE u.deleted = false AND u.userPK.user=?1")
    List<UserCompagnies> findByUser(Users user, Pageable pageable);

    @Query("SELECT u FROM UserCompagnies u WHERE " +
            "(u.userPK.user.username LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.nom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.prenom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.adresse LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.telephone LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.telephone LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND u.deleted = false")
    List<UserCompagnies> recherche(String search, Pageable pageable);

    @Query("SELECT u FROM UserCompagnies u WHERE " +
            "(u.userPK.user.username LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.nom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.prenom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.adresse LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.telephone LIKE CONCAT('%',:search,'%')) AND (u.deleted = false AND u.userPK.compagnie =:compagnie)")
    List<UserCompagnies> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    @Query("SELECT u FROM UserCompagnies u WHERE " +
            "(u.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.telephone LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND (u.deleted = false AND u.userPK.user =:user)")
    List<UserCompagnies> rechercheUser(Users user, String search, Pageable pageable);

    @Query("SELECT COUNT(u) FROM UserCompagnies u WHERE u.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(u) FROM UserCompagnies u WHERE u.deleted = false AND u.userPK.compagnie=?1")
    Long countByCompagnie(Compagnies compagnie);

    @Query("SELECT COUNT(u) FROM UserCompagnies u WHERE u.deleted = false AND u.userPK.user=?1")
    Long countByUser(Users user);

    @Query("SELECT COUNT(u) FROM UserCompagnies u WHERE " +
            "(u.userPK.user.username LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.nom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.prenom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.adresse LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.telephone LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.telephone LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND u.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(u) FROM UserCompagnies u WHERE " +
            "(u.userPK.user.username LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.nom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.prenom LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.adresse LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.user.telephone LIKE CONCAT('%',:search,'%')) AND (u.deleted = false AND u.userPK.compagnie =:compagnie)")
    Long countRechercheCompagnie(Compagnies compagnie, String search);

    @Query("SELECT COUNT(u) FROM UserCompagnies u WHERE " +
            "(u.userPK.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.telephone LIKE CONCAT('%',:search,'%') OR " +
            "u.userPK.compagnie.adresse LIKE CONCAT('%',:search,'%')) AND (u.deleted = false AND u.userPK.user =:user)")
    Long countRechercheUser(Users user, String search);

}
