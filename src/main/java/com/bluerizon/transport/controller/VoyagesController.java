package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.VoyageRequest;
import com.bluerizon.transport.response.ResponseVoyage;
import com.bluerizon.transport.response.ResponseVoyagePage;
import com.bluerizon.transport.response.ResponseVoyagePage;
import com.bluerizon.transport.security.CurrentUser;
import com.bluerizon.transport.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class VoyagesController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_voyage_page}")
    private String url_voyage_page;
    
    @Value("${app.url_voyage_compagnie_page}")
    private String url_voyage_compagnie_page;

    @Value("${app.url_voyage_ligne_page}")
    private String url_voyage_ligne_page;

    @Value("${app.url_voyage_search_page}")
    private String url_voyage_search_page;

    @Value("${app.url_voyage_compagnie_search_page}")
    private String url_voyage_compagnie_search_page;

    @Value("${app.url_voyage_ligne_search_page}")
    private String url_voyage_ligne_search_page;

    @Autowired
    private VoyagesDao voyagesDao;

    @Autowired
    private CompagniesDao compagniesDao;

    @Autowired
    private LignesDao lignesDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private UserCompagnieDao userCompagnieDao;

    @Autowired
    private BusDao busDao;

    @Autowired
    private VoyageLignesDao voyageLignesDao;


    @RequestMapping(value = { "/voyage/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Voyages selectOne(@PathVariable("id") final Long id) {
        return this.voyagesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/voyage_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Voyages> selectNoDeleted() {
        return this.voyagesDao.findByDeletedFalse();
    }
    
    @RequestMapping(value = { "/voyage_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Voyages> selectDeleted() {
        return this.voyagesDao.findByDeletedTrue();
    }


    @RequestMapping(value = { "/voyage" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public ResponseVoyage save(@Validated @RequestBody final VoyageRequest request, @CurrentUser UserPrincipal currentUser) {
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        Users user = usersDao.findByIdUser(currentUser.getId());
        Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
        UserCompagnies userCompagnies = userCompagnieDao.findByUserPK(new UserPK(user, compagnie));
        Voyages voyage = new Voyages();
        voyage.setUserCompagnie(userCompagnies);
        voyage.setLigne(ligne);
        voyage.setDateDepart(request.getDateDepart());
        voyage.setDateArriver(request.getDateArriver());
        Voyages voyageSave = this.voyagesDao.save(voyage);
        List<Bus> list = new ArrayList<>();
        if (voyageSave!=null){
            List<VoyageLignes> voyageLignes = new ArrayList<>();
            for (Bus item :
                    request.getBus()) {
                Bus bus = busDao.findByIdBus(item.getIdBus());
                list.add(bus);
                VoyageLignes voyageLigne = new VoyageLignes();
                voyageLigne.setVoyagePK(new VoyagePK(voyageSave, bus));
                voyageLignes.add(voyageLigne);
            }
            voyageLignesDao.save(voyageLignes);
        }
        return new ResponseVoyage(voyageSave, list);
    }

    @RequestMapping(value = { "/voyage/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public ResponseVoyage update(@PathVariable("id") final Long id, @Validated @RequestBody final VoyageRequest request, @CurrentUser UserPrincipal currentUser) {
        Voyages voyageInit = this.voyagesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        Users user = usersDao.findByIdUser(currentUser.getId());
        Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
        UserCompagnies userCompagnies = userCompagnieDao.findByUserPK(new UserPK(user, compagnie));
        voyageInit.setUserCompagnie(userCompagnies);
        voyageInit.setLigne(ligne);
        voyageInit.setDateDepart(request.getDateDepart());
        voyageInit.setDateArriver(request.getDateArriver());
        Voyages voyageSave = this.voyagesDao.save(voyageInit);
        List<Bus> list = new ArrayList<>();
        if (voyageSave!=null){
            List<VoyageLignes> lignes = voyageLignesDao.findByVoyage(voyageSave);
            voyageLignesDao.delete(lignes);
            List<VoyageLignes> voyageLignes = new ArrayList<>();
            for (Bus item :
                    request.getBus()) {
                Bus bus = busDao.findByIdBus(item.getIdBus());
                list.add(bus);
                VoyageLignes voyageLigne = new VoyageLignes();
                voyageLigne.setVoyagePK(new VoyagePK(voyageSave, bus));
                voyageLignes.add(voyageLigne);
            }
            voyageLignesDao.save(voyageLignes);
        }
        return new ResponseVoyage(voyageSave, list);
    }

    @RequestMapping(value ="/voyage_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVoyagePage selectVoyagePage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Voyages> voyages = this.voyagesDao.findAllByDeletedFalse(pageable);
        List<ResponseVoyage> responseVoyages = new ArrayList<>();
        for (Voyages item :
                voyages) {
            List<VoyageLignes> voyageLignes = voyageLignesDao.findByVoyage(item);
            List<Bus> bus = new ArrayList<>();
            for (VoyageLignes vitem:
                 voyageLignes) {
                bus.add(vitem.getVoyagePK().getBus());
            }
            responseVoyages.add(new ResponseVoyage(item, bus));
        }

        ResponseVoyagePage voyagePage = new ResponseVoyagePage();

        Long total = this.voyagesDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            voyagePage.setTotal(total);
            voyagePage.setPer_page(page_size);
            voyagePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            voyagePage.setLast_page(lastPage);
            voyagePage.setFirst_page_url(url_voyage_page+1);
            voyagePage.setLast_page_url(url_voyage_page+lastPage);
            if (page >= lastPage){

            }else {
                voyagePage.setNext_page_url(url_voyage_page+(page+1));
            }

            if (page == 1){
                voyagePage.setPrev_page_url(null);
                voyagePage.setFrom(1L);
                voyagePage.setTo(Long.valueOf(page_size));
            } else {
                voyagePage.setPrev_page_url(url_voyage_page+(page-1));
                voyagePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                voyagePage.setTo(Long.valueOf(page_size) * page);
            }
            voyagePage.setPath(path);
            voyagePage.setData(responseVoyages);
        }else {
            voyagePage.setTotal(0L);
        }

        return voyagePage;
    }
    
    @RequestMapping(value ="/voyage_compagnie_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVoyagePage selectVoyageCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Voyages> voyages = this.voyagesDao.findByCompagnie(compagnie,pageable);
        List<ResponseVoyage> responseVoyages = new ArrayList<>();
        for (Voyages item :
                voyages) {
            List<VoyageLignes> voyageLignes = voyageLignesDao.findByVoyage(item);
            List<Bus> bus = new ArrayList<>();
            for (VoyageLignes vitem:
                    voyageLignes) {
                bus.add(vitem.getVoyagePK().getBus());
            }
            responseVoyages.add(new ResponseVoyage(item, bus));
        }

        ResponseVoyagePage voyagePage = new ResponseVoyagePage();

        Long total = this.voyagesDao.countByCompagnie(compagnie);
        Long lastPage;

        if (total > 0){
            voyagePage.setTotal(total);
            voyagePage.setPer_page(page_size);
            voyagePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            voyagePage.setLast_page(lastPage);
            voyagePage.setFirst_page_url(url_voyage_compagnie_page+id+"/"+1);
            voyagePage.setLast_page_url(url_voyage_compagnie_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                voyagePage.setNext_page_url(url_voyage_compagnie_page+id+"/"+(page+1));
            }

            if (page == 1){
                voyagePage.setPrev_page_url(null);
                voyagePage.setFrom(1L);
                voyagePage.setTo(Long.valueOf(page_size));
            } else {
                voyagePage.setPrev_page_url(url_voyage_compagnie_page+id+"/"+(page-1));
                voyagePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                voyagePage.setTo(Long.valueOf(page_size) * page);
            }
            voyagePage.setPath(path);
            voyagePage.setData(responseVoyages);
        }else {
            voyagePage.setTotal(0L);
        }

        return voyagePage;
    }

    @RequestMapping(value ="/voyage_ligne_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVoyagePage selectVoyageLignePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Lignes ligne = lignesDao.findByIdLigne(id);
        List<Voyages> voyages = this.voyagesDao.findByLigne(ligne, pageable);
        List<ResponseVoyage> responseVoyages = new ArrayList<>();
        for (Voyages item :
                voyages) {
            List<VoyageLignes> voyageLignes = voyageLignesDao.findByVoyage(item);
            List<Bus> bus = new ArrayList<>();
            for (VoyageLignes vitem:
                    voyageLignes) {
                bus.add(vitem.getVoyagePK().getBus());
            }
            responseVoyages.add(new ResponseVoyage(item, bus));
        }

        ResponseVoyagePage voyagePage = new ResponseVoyagePage();

        Long total = this.voyagesDao.countByLigne(ligne);
        Long lastPage;

        if (total > 0){
            voyagePage.setTotal(total);
            voyagePage.setPer_page(page_size);
            voyagePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            voyagePage.setLast_page(lastPage);
            voyagePage.setFirst_page_url(url_voyage_ligne_page+id+"/"+1);
            voyagePage.setLast_page_url(url_voyage_ligne_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                voyagePage.setNext_page_url(url_voyage_ligne_page+id+"/"+(page+1));
            }

            if (page == 1){
                voyagePage.setPrev_page_url(null);
                voyagePage.setFrom(1L);
                voyagePage.setTo(Long.valueOf(page_size));
            } else {
                voyagePage.setPrev_page_url(url_voyage_ligne_page+id+"/"+(page-1));
                voyagePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                voyagePage.setTo(Long.valueOf(page_size) * page);
            }
            voyagePage.setPath(path);
            voyagePage.setData(responseVoyages);
        }else {
            voyagePage.setTotal(0L);
        }

        return voyagePage;
    }


    @RequestMapping(value = "/voyage_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVoyagePage searchVoyagePage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Voyages> voyages = this.voyagesDao.recherche(s, pageable);
        List<ResponseVoyage> responseVoyages = new ArrayList<>();
        for (Voyages item :
                voyages) {
            List<VoyageLignes> voyageLignes = voyageLignesDao.findByVoyage(item);
            List<Bus> bus = new ArrayList<>();
            for (VoyageLignes vitem:
                    voyageLignes) {
                bus.add(vitem.getVoyagePK().getBus());
            }
            responseVoyages.add(new ResponseVoyage(item, bus));
        }

        ResponseVoyagePage voyagePage = new ResponseVoyagePage();
        Long total = this.voyagesDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            voyagePage.setTotal(total);
            voyagePage.setPer_page(page_size);
            voyagePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            voyagePage.setLast_page(lastPage);
            voyagePage.setFirst_page_url(url_voyage_search_page+1+"/"+s);
            voyagePage.setLast_page_url(url_voyage_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                voyagePage.setNext_page_url(url_voyage_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                voyagePage.setPrev_page_url(null);
                voyagePage.setFrom(1L);
                voyagePage.setTo(Long.valueOf(page_size));
            } else {
                voyagePage.setPrev_page_url(url_voyage_search_page+(page-1)+"/"+s);
                voyagePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                voyagePage.setTo(Long.valueOf(page_size) * page);
            }

            voyagePage.setPath(path);
            voyagePage.setData(responseVoyages);

        }else {
            voyagePage.setTotal(0L);
        }

        return voyagePage;
    }

    @RequestMapping(value = "/voyage_compagnie_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVoyagePage searchVoyageCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Voyages> voyages = this.voyagesDao.rechercheCompagnie(compagnie, s, pageable);
        List<ResponseVoyage> responseVoyages = new ArrayList<>();
        for (Voyages item :
                voyages) {
            List<VoyageLignes> voyageLignes = voyageLignesDao.findByVoyage(item);
            List<Bus> bus = new ArrayList<>();
            for (VoyageLignes vitem:
                    voyageLignes) {
                bus.add(vitem.getVoyagePK().getBus());
            }
            responseVoyages.add(new ResponseVoyage(item, bus));
        }

        ResponseVoyagePage voyagePage = new ResponseVoyagePage();
        Long total = this.voyagesDao.countRechercheCompagnie(compagnie, s);
        Long lastPage;

        if (total > 0){
            voyagePage.setTotal(total);
            voyagePage.setPer_page(page_size);
            voyagePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            voyagePage.setLast_page(lastPage);
            voyagePage.setFirst_page_url(url_voyage_compagnie_search_page+id+"/"+1+"/"+s);
            voyagePage.setLast_page_url(url_voyage_compagnie_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                voyagePage.setNext_page_url(url_voyage_compagnie_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                voyagePage.setPrev_page_url(null);
                voyagePage.setFrom(1L);
                voyagePage.setTo(Long.valueOf(page_size));
            } else {
                voyagePage.setPrev_page_url(url_voyage_compagnie_search_page+id+"/"+(page-1)+"/"+s);
                voyagePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                voyagePage.setTo(Long.valueOf(page_size) * page);
            }

            voyagePage.setPath(path);
            voyagePage.setData(responseVoyages);

        }else {
            voyagePage.setTotal(0L);
        }

        return voyagePage;
    }

    @RequestMapping(value = "/voyage_ligne_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVoyagePage searchVoyageLignePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Lignes ligne = lignesDao.findByIdLigne(id);
        List<Voyages> voyages = this.voyagesDao.rechercheLigne(ligne, s, pageable);
        List<ResponseVoyage> responseVoyages = new ArrayList<>();
        for (Voyages item :
                voyages) {
            List<VoyageLignes> voyageLignes = voyageLignesDao.findByVoyage(item);
            List<Bus> bus = new ArrayList<>();
            for (VoyageLignes vitem:
                    voyageLignes) {
                bus.add(vitem.getVoyagePK().getBus());
            }
            responseVoyages.add(new ResponseVoyage(item, bus));
        }

        ResponseVoyagePage voyagePage = new ResponseVoyagePage();
        Long total = this.voyagesDao.countRechercheLigne(ligne, s);
        Long lastPage;

        if (total > 0){
            voyagePage.setTotal(total);
            voyagePage.setPer_page(page_size);
            voyagePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            voyagePage.setLast_page(lastPage);
            voyagePage.setFirst_page_url(url_voyage_ligne_search_page+id+"/"+1+"/"+s);
            voyagePage.setLast_page_url(url_voyage_ligne_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                voyagePage.setNext_page_url(url_voyage_ligne_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                voyagePage.setPrev_page_url(null);
                voyagePage.setFrom(1L);
                voyagePage.setTo(Long.valueOf(page_size));
            } else {
                voyagePage.setPrev_page_url(url_voyage_ligne_search_page+id+"/"+(page-1)+"/"+s);
                voyagePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                voyagePage.setTo(Long.valueOf(page_size) * page);
            }

            voyagePage.setPath(path);
            voyagePage.setData(responseVoyages);

        }else {
            voyagePage.setTotal(0L);
        }

        return voyagePage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
