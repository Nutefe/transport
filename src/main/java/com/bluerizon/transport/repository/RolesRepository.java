package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.entity.TypeBillets;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Roles findByIdRole(final Integer id);

    List<Roles> findByDeletedFalseOrderByIdRoleDesc();

    List<Roles> findByDeletedTrueOrderByIdRoleDesc();

    List<Roles> findByDeletedFalseOrderByIdRoleDesc(Pageable pageable);

    @Query("SELECT r FROM Roles r WHERE r.libelle LIKE CONCAT('%',:search,'%') AND r.deleted = false")
    List<Roles> recherche(String search, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Roles r WHERE r.libelle LIKE CONCAT('%',:search,'%') AND r.deleted = false")
    Long countRecherche(String search);

    @Query("SELECT COUNT(r) FROM Roles r WHERE r.deleted = false")
    Long countByDeletedFalse();

}
