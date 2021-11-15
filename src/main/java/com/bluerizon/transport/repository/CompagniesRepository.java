package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.TypeBillets;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompagniesRepository extends JpaRepository<Compagnies, Long> {

    Compagnies findByIdCompagnie(final Long id);

    List<Compagnies> findByDeletedFalseOrderByIdCompagnieDesc();

    List<Compagnies> findByDeletedTrueOrderByIdCompagnieDesc();

    List<Compagnies> findByDeletedFalseOrderByIdCompagnieDesc(Pageable pageable);

    @Query("SELECT c FROM Compagnies c WHERE (c.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "c.telephone LIKE CONCAT('%',:search,'%') OR " +
            "c.adresse LIKE CONCAT('%',:search,'%')) AND c.deleted = false ORDER BY c.idCompagnie DESC")
    List<Compagnies> recherche(String search, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Compagnies c WHERE c.deleted = false")
    Long countByDeletedFalse();

    @Query("SELECT COUNT(c) FROM Compagnies c WHERE (c.nomCompagnie LIKE CONCAT('%',:search,'%') OR " +
            "c.telephone LIKE CONCAT('%',:search,'%') OR " +
            "c.adresse LIKE CONCAT('%',:search,'%')) AND c.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT CASE WHEN COUNT(email) > 0 THEN true ELSE false END FROM Compagnies c WHERE c.email = :email and c.idCompagnie != :id")
    boolean existsByEmail(@Param("email") final String email, @Param("id") final Long id);

    @Query("SELECT CASE WHEN COUNT(email) > 0 THEN true ELSE false END FROM Compagnies c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") final String email);
}
