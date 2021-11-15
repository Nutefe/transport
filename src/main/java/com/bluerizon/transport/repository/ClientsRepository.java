package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Clients;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.Villes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {

    Clients findByIdClient(final Long id);

    List<Clients> findByDeletedFalseOrderByIdClientDesc();

    List<Clients> findByDeletedTrueOrderByIdClientDesc();

    List<Clients> findByDeletedFalseOrderByIdClientDesc(Pageable pageable);

    @Query("SELECT c FROM Clients c WHERE c.deleted = false AND c.compagnie=?1 ORDER BY c.idClient DESC")
    List<Clients> findByCompagnie(Compagnies compagnie, Pageable pageable);

    @Query("SELECT c FROM Clients c WHERE (c.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "c.contact LIKE CONCAT('%',:search,'%') OR " +
            "c.compagnie.nomCompagnie LIKE CONCAT('%',:search,'%')) AND c.deleted = false ORDER BY c.idClient DESC")
    List<Clients> recherche(String search, Pageable pageable);

    @Query("SELECT c FROM Clients c WHERE c.deleted = false AND c.compagnie=:compagnie AND " +
            "(c.nomComplet LIKE CONCAT('%',:search,'%') OR c.contact LIKE CONCAT('%',:search,'%')) ORDER BY c.idClient DESC")
    List<Clients> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Clients c WHERE c.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(c) FROM Clients c WHERE (c.nomComplet LIKE CONCAT('%',:search,'%') OR " +
            "c.contact LIKE CONCAT('%',:search,'%')) AND c.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(c) FROM Clients c WHERE c.deleted = false AND c.compagnie=?1")
    Long countByCompagnie(Compagnies compagnie);

    @Query("SELECT COUNT(c) FROM Clients c WHERE c.deleted = false AND c.compagnie=:compagnie AND" +
            " (c.nomComplet LIKE CONCAT('%',:search,'%') OR c.contact LIKE CONCAT('%',:search,'%'))")
    Long countRechercheCompagnie(Compagnies compagnie, String search);

}
