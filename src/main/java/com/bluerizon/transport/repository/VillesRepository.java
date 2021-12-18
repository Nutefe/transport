package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.TypeBus;
import com.bluerizon.transport.entity.Villes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VillesRepository extends JpaRepository<Villes, Long> {

    Villes findByIdVille(final Long id);

    List<Villes> findByDeletedFalseOrderByIdVilleDesc();

    List<Villes> findByDeletedTrueOrderByIdVilleDesc();

    List<Villes> findByDeletedFalseOrderByIdVilleDesc(Pageable pageable);

    @Query("SELECT v FROM Villes v WHERE v.deleted = false AND v.compagnie=?1 ORDER BY v.idVille DESC")
    List<Villes> findByCompagnie(Compagnies compagnie, Pageable pageable);

    @Query("SELECT v FROM Villes v WHERE v.deleted = false AND v.compagnie=?1 ORDER BY v.idVille DESC")
    List<Villes> findByCompagnie(Compagnies compagnie);

    @Query("SELECT v FROM Villes v WHERE v.deleted = false AND v.pays=?1 ORDER BY v.idVille DESC")
    List<Villes> findByPays(Pays pays, Pageable pageable);

    @Query("SELECT v FROM Villes v WHERE v.deleted = false AND v.pays=?1 ORDER BY v.idVille DESC")
    List<Villes> findByPays(Pays pays);

    @Query("SELECT v FROM Villes v WHERE v.nomVille LIKE CONCAT('%',:search,'%') AND v.deleted = false")
    List<Villes> recherche(String search, Pageable pageable);

    @Query("SELECT v FROM Villes v WHERE v.deleted = false AND v.compagnie=:compagnie AND v.nomVille LIKE CONCAT('%',:search,'%') ORDER BY v.idVille DESC")
    List<Villes> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    @Query("SELECT v FROM Villes v WHERE v.deleted = false AND v.pays=:pays AND v.nomVille LIKE CONCAT('%',:search,'%') ORDER BY v.idVille DESC")
    List<Villes> recherchePays(Pays pays, String search, Pageable pageable);

    @Query("SELECT COUNT(v) FROM Villes v WHERE v.nomVille LIKE CONCAT('%',:search,'%') AND v.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(v) FROM Villes v WHERE v.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(v) FROM Villes v WHERE v.deleted = false AND v.compagnie=:compagnie AND v.nomVille LIKE CONCAT('%',:search,'%')")
    Long countRechercheCompagnie(Compagnies compagnie, String search);

    @Query("SELECT COUNT(v) FROM Villes v WHERE v.deleted = false AND v.pays=:pays AND v.nomVille LIKE CONCAT('%',:search,'%')")
    Long countRecherchePays(Pays pays, String search);

    @Query("SELECT COUNT(v) FROM Villes v WHERE v.deleted = false AND v.pays=?1 ORDER BY v.idVille DESC")
    Long countByPays(Pays pays);

    @Query("SELECT COUNT(v) FROM Villes v WHERE v.deleted = false AND v.compagnie=?1 ORDER BY v.idVille DESC")
    Long countByCompagnie(Compagnies compagnie);

}
