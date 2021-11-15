package com.bluerizon.transport.dao;

import com.bluerizon.transport.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BilletColisDao {

    BilletColis findByIdBillet(final Long id);

    List<BilletColis> findByDeletedFalseOrderByIdBilletDesc();

    List<BilletColis> findByDeletedTrueOrderByIdBilletDesc();

    List<BilletColis> findAllByDeletedFalse(Pageable pageable);

    List<BilletColis> findByUser(Users user, Pageable pageable);

    List<BilletColis> findByVoyage(Voyages voyage, Pageable pageable);

    List<BilletColis> findByClient(Clients client, Pageable pageable);

    List<BilletColis> recherche(String search, Pageable pageable);

    List<BilletColis> rechercheUser(Users user, String search, Pageable pageable);

    List<BilletColis> rechercheVoyage(Voyages voyage, String search, Pageable pageable);

    List<BilletColis> rechercheClient(Clients client, String search, Pageable pageable);

    BilletColis save(BilletColis billetColi);

    List<BilletColis> save(List<BilletColis> billetColis);

    Long countByDeletedFalse();

    Long countByUser(Users user);

    Long countByVoyage(Voyages voyage);

    Long countByClient(Clients client);

    Long countRecherche(String search);

    Long countRechercheUser(Users user, String search);

    Long countRechercheVoyage(Voyages voyage, String search);

    Long countRechercheClient(Clients client, String search);

}
