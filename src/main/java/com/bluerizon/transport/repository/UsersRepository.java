package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByIdUser(final Long id);

    Optional<Users> findByUsernameAndActiveTrueAndDeletedFalse(final String username);

    @Query("SELECT u FROM Users u WHERE u.role.idRole != 1 AND u.deleted = false AND u.idUser != :idUser")
    List<Users> selectUser(Long idUser, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.role.idRole != 1 AND u.deleted = false AND u.idUser != :idUser")
    List<Users> selectUser(Long idUser);

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 1 AND u.deleted = false AND u.idUser != :idUser")
    List<Users> selectUserSys(Long idUser, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 1 AND u.deleted = false AND u.idUser != :idUser")
    List<Users> selectUserSys(Long idUser);

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 1 AND u.deleted = false")
    List<Users> selectUserSys();

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false")
    List<Users> selectUserDirecteur(Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false")
    List<Users> selectUserDirecteur();

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false AND u.active = true")
    List<Users> selectUserDirecteurActif(Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false AND u.active = true")
    List<Users> selectUserDirecteurActif();

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false AND u.active = false")
    List<Users> selectUserDirecteurDesactif(Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false AND u.active = false")
    List<Users> selectUserDirecteurDesactif();

    @Query("SELECT u FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole != 1 AND u.deleted = false AND u.idUser != :idUser)")
    List<Users> rechercheUser(String search, Long idUser, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole = 2 AND u.deleted = false)")
    List<Users> rechercheDirecteur(String search, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole = 2 AND u.deleted = false AND u.active = true)")
    List<Users> rechercheDirecteurActif(String search, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole = 2 AND u.deleted = false AND u.active = false)")
    List<Users> rechercheDirecteurDesactif(String search, Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.active = true where u.idUser = :id")
    void active(@Param(value = "id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.active = false where u.idUser = :id")
    void desactive(@Param(value = "id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.avatar = :avatar where u.idUser = :id")
    void updateAvatar(@Param(value = "id") Long id, @Param(value = "avatar") String avatar);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.expirer = :expirer where u.idUser = :id")
    void updateExpire(@Param(value = "id") Long id, @Param(value = "expirer") Date expirer);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.nbrCompagnie = :nbrCompagnie where u.idUser = :id")
    void updateNbrCompagnie(@Param(value = "id") Long id, @Param(value = "nbrCompagnie") Integer nbrCompagnie);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users u set u.username = :username, u.email = :email, u.nom = :nom, u.prenom = :prenom, " +
            "u.telephone = :telephone,  u.adresse = :adresse, " +
            "u.expirer = :expirer, u.nbrCompagnie = :nbrCompagnie, u.role = :role where u.idUser = :id")
    void updateUser(@Param(value = "id") Long id, @Param(value = "username") String username,
                    @Param(value = "email") String email, @Param(value = "nom") String nom,
                    @Param(value = "prenom") String prenom, @Param(value = "telephone") String telephone,
                    @Param(value = "adresse") String adresse,@Param(value = "expirer") Date expirer,
                    @Param(value = "nbrCompagnie") Integer nbrCompagnie, @Param(value = "role") Roles role);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role.idRole != 1 AND u.deleted = false AND u.idUser != :idUser")
    Long countUser(Long idUser);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role.idRole = 1 AND u.deleted = false AND u.idUser != :idUser")
    Long countUserSys(Long idUser);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false")
    Long contUserDirecteur();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false AND u.active = true")
    Long countUserDirecteurActif();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role.idRole = 2 AND u.deleted = false AND u.active = false")
    Long countUserDirecteurDesactif();

    @Query("SELECT COUNT(u) FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole != 1 AND u.deleted = false AND u.idUser != :idUser)")
    Long countRechercheUser(String search, Long idUser);

    @Query("SELECT COUNT(u) FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole = 2 AND u.deleted = false)")
    Long countRechercheDirecteur(String search);

    @Query("SELECT COUNT(u) FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole = 2 AND u.deleted = false AND u.active = true)")
    Long countRechercheDirecteurActif(String search);

    @Query("SELECT COUNT(u) FROM Users u WHERE (u.username LIKE CONCAT('%',:search,'%') OR " +
            "u.nom LIKE CONCAT('%',:search,'%') OR u.prenom LIKE CONCAT('%',:search,'%')) AND "+
            "(u.role.idRole = 2 AND u.deleted = false AND u.active = false)")
    Long countRechercheDirecteurDesactif(String search);

    @Query("SELECT CASE WHEN COUNT(username) > 0 THEN true ELSE false END FROM Users u WHERE u.username = :username and u.idUser != :id")
    boolean existsByUsername(@Param("username") final String username, @Param("id") final Long id);

    @Query("SELECT CASE WHEN COUNT(username) > 0 THEN true ELSE false END FROM Users u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") final String username);

    @Query("SELECT CASE WHEN COUNT(email) > 0 THEN true ELSE false END FROM Users u WHERE u.email = :email and u.idUser != :id")
    boolean existsByEmail(@Param("email") final String email, @Param("id") final Long id);

    @Query("SELECT CASE WHEN COUNT(email) > 0 THEN true ELSE false END FROM Users u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") final String email);

}
