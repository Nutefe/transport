package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Arrets;
import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Villes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArretsRepository extends JpaRepository<Arrets, Long> {

    Arrets findByIdArret(final Long id);

    List<Arrets> findByDeletedFalseOrderByIdArretDesc();

    List<Arrets> findByDeletedTrueOrderByIdArretDesc();

    List<Arrets> findByDeletedFalseOrderByIdArretDesc(Pageable pageable);

    @Query("SELECT a FROM Arrets a WHERE a.deleted = false AND a.ville=?1 ORDER BY a.idArret DESC")
    List<Arrets> findByVille(Villes ville, Pageable pageable);

    @Query("SELECT a FROM Arrets a WHERE (a.libelle LIKE CONCAT('%',:search,'%') OR " +
            "a.adresse LIKE CONCAT('%',:search,'%') OR " +
            "a.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "a.ville.pays.nomPays LIKE CONCAT('%',:search,'%') OR " +
            "a.ville.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%')) AND a.deleted = false ORDER BY a.idArret DESC")
    List<Arrets> recherche(String search, Pageable pageable);

    @Query("SELECT a FROM Arrets a WHERE a.deleted = false AND a.ville=:ville AND " +
            "(a.libelle LIKE CONCAT('%',:search,'%') OR a.adresse LIKE CONCAT('%',:search,'%')) ORDER BY a.idArret DESC")
    List<Arrets> rechercheVille(Villes ville, String search, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Arrets a WHERE a.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(a) FROM Arrets a WHERE a.deleted = false AND a.ville=?1 ORDER BY a.idArret DESC")
    Long countByVille(Villes ville);

    @Query("SELECT COUNT(a) FROM Arrets a WHERE (a.libelle LIKE CONCAT('%',:search,'%') OR " +
            "a.adresse LIKE CONCAT('%',:search,'%') OR " +
            "a.ville.nomVille LIKE CONCAT('%',:search,'%') OR " +
            "a.ville.pays.nomPays LIKE CONCAT('%',:search,'%') OR " +
            "a.ville.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%')) AND a.deleted = false ORDER BY a.idArret DESC")
    Long countRecherche(String search);

    @Query("SELECT COUNT(a) FROM Arrets a WHERE a.deleted = false AND a.ville=:ville AND " +
            "(a.libelle LIKE CONCAT('%',:search,'%') OR a.adresse LIKE CONCAT('%',:search,'%')) ORDER BY a.idArret DESC")
    Long countRechercheVille(Villes ville, String search);

}
