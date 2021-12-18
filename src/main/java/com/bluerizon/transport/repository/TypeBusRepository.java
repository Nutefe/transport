package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBus;
import com.bluerizon.transport.entity.UserCompagnies;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeBusRepository extends JpaRepository<TypeBus, Long> {

    TypeBus findByIdTypeBus(final Long id);

    List<TypeBus> findByDeletedFalseOrderByIdTypeBusDesc();

    List<TypeBus> findByDeletedTrueOrderByIdTypeBusDesc();

    List<TypeBus> findByDeletedFalseOrderByIdTypeBusDesc(Pageable pageable);

    @Query("SELECT t FROM TypeBus t WHERE t.deleted = false AND t.userCompagnie.userPK.compagnie=?1 ORDER BY t.idTypeBus DESC")
    List<TypeBus> findByCompagnie(Pageable pageable, Compagnies compagnie);

    @Query("SELECT t FROM TypeBus t WHERE t.deleted = false AND t.userCompagnie.userPK.compagnie=?1 ORDER BY t.idTypeBus DESC")
    List<TypeBus> findByCompagnie(Compagnies compagnie);

    @Query("SELECT t FROM TypeBus t WHERE t.libelle LIKE CONCAT('%',:search,'%') AND t.deleted = false ORDER BY t.idTypeBus DESC")
    List<TypeBus> recherche(String search, Pageable pageable);

    @Query("SELECT t FROM TypeBus t WHERE t.deleted = false AND t.userCompagnie.userPK.compagnie=:compagnie AND t.libelle LIKE CONCAT('%',:search,'%') ORDER BY t.idTypeBus DESC")
    List<TypeBus> rechercheCompagnie(Compagnies compagnie, String search, Pageable pageable);

    @Query("SELECT COUNT(t) FROM TypeBus t WHERE t.libelle LIKE CONCAT('%',:search,'%') AND t.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(t) FROM TypeBus t WHERE t.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(t) FROM TypeBus t WHERE t.deleted = false AND t.userCompagnie.userPK.compagnie=:compagnie AND t.libelle LIKE CONCAT('%',:search,'%')")
    Long countRechercheCompagnie(Compagnies compagnie, String search);

    @Query("SELECT COUNT(t) FROM TypeBus t WHERE t.deleted = false AND t.userCompagnie.userPK.compagnie=?1 ORDER BY t.idTypeBus DESC")
    Long countByCompagnie(Compagnies compagnie);

}
