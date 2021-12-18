package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.TarifRequest;
import com.bluerizon.transport.requeste.VoyageRequest;
import com.bluerizon.transport.response.ResponseTarifPage;
import com.bluerizon.transport.response.ResponseTarifPage;
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

import java.util.List;

@RestController
@RequestMapping("/api")
public class TarifsController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_tarif_page}")
    private String url_tarif_page;

    @Value("${app.url_tarif_ligne_page}")
    private String url_tarif_ligne_page;

    @Value("${app.url_tarif_bus_page}")
    private String url_tarif_bus_page;

    @Value("${app.url_tarif_compagnie_page}")
    private String url_tarif_compagnie_page;

    @Value("${app.url_tarif_search_page}")
    private String url_tarif_search_page;

    @Value("${app.url_tarif_ligne_search_page}")
    private String url_tarif_ligne_search_page;

    @Value("${app.url_tarif_bus_search_page}")
    private String url_tarif_bus_search_page;

    @Value("${app.url_tarif_compagnie_search_page}")
    private String url_tarif_compagnie_search_page;

    @Autowired
    private TarifsDao tarifsDao;

    @Autowired
    private LignesDao lignesDao;

    @Autowired
    private BusDao busDao;

    @Autowired
    private CompagniesDao compagniesDao;
    
    @RequestMapping(value = { "/tarif_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Tarifs> selectNoDeleted() {
        return this.tarifsDao.findByDeletedFalse();
    }
    
    @RequestMapping(value = { "/tarif_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Tarifs> selectDeleted() {
        return this.tarifsDao.findByDeletedTrue();
    }

    @RequestMapping(value = { "/tarif_compagnie/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Tarifs> selectAllCompagnie(@PathVariable("id") final Long id) {
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        return this.tarifsDao.findByCompagnie(compagnie);
    }


    @RequestMapping(value = { "/tarif" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Tarifs save(@Validated @RequestBody final TarifRequest request) {
        Bus bus = busDao.findByIdBus(request.getBus().getIdBus());
        Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
        Tarifs tarif = new Tarifs();
        tarif.setTarifPK(new TarifPK(bus, ligne));
        tarif.setPrixKilo(request.getPrixKilo());
        tarif.setPrixPassager(request.getPrixPassager());
        return this.tarifsDao.save(tarif);
    }

    @RequestMapping(value = { "/tarif" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Tarifs update(@Validated @RequestBody final TarifRequest request) {
        Bus bus = busDao.findByIdBus(request.getBus().getIdBus());
        Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
        Tarifs tarifInit = this.tarifsDao.findByTarifPK(new TarifPK(bus, ligne));
        tarifInit.setPrixKilo(request.getPrixKilo());
        tarifInit.setPrixPassager(request.getPrixPassager());
        return this.tarifsDao.save(tarifInit);
    }

    @RequestMapping(value ="/tarif_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseTarifPage selectTarifPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Tarifs> tarifs = this.tarifsDao.findAllByDeletedFalse(pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();

        Long total = this.tarifsDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_page+1);
            tarifPage.setLast_page_url(url_tarif_page+lastPage);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_page+(page+1));
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_page+(page-1));
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }
            tarifPage.setPath(path);
            tarifPage.setData(tarifs);
        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }

    @RequestMapping(value ="/tarif_ligne_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseTarifPage selectTarifLignePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Lignes ligne = lignesDao.findByIdLigne(id);
        List<Tarifs> tarifs = this.tarifsDao.findByLigne(ligne, pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();

        Long total = this.tarifsDao.countByLigne(ligne);
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_ligne_page+id+"/"+1);
            tarifPage.setLast_page_url(url_tarif_ligne_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_ligne_page+id+"/"+(page+1));
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_ligne_page+id+"/"+(page-1));
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }
            tarifPage.setPath(path);
            tarifPage.setData(tarifs);
        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }

    @RequestMapping(value ="/tarif_bus_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseTarifPage selectTarifBusPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Bus bus = busDao.findByIdBus(id);
        List<Tarifs> tarifs = this.tarifsDao.findByBus(bus, pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();

        Long total = this.tarifsDao.countByBus(bus);
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_bus_page+id+"/"+1);
            tarifPage.setLast_page_url(url_tarif_bus_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_bus_page+id+"/"+(page+1));
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_bus_page+id+"/"+(page-1));
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }
            tarifPage.setPath(path);
            tarifPage.setData(tarifs);
        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }

    @RequestMapping(value ="/tarif_compagnie_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseTarifPage selectTarifCompagniePage(@PathVariable(value = "id") Long id,
                                                       @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Tarifs> tarifs = this.tarifsDao.findByCompagnie(compagnie,pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();

        Long total = this.tarifsDao.countByCompagnie(compagnie);
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_compagnie_page+id+"/"+1);
            tarifPage.setLast_page_url(url_tarif_compagnie_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_compagnie_page+id+"/"+(page+1));
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_compagnie_page+id+"/"+(page-1));
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }
            tarifPage.setPath(path);
            tarifPage.setData(tarifs);
        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }

    @RequestMapping(value = "/tarif_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseTarifPage searchTarifPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Tarifs> tarifs = this.tarifsDao.recherche(s, pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();
        Long total = this.tarifsDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_search_page+1+"/"+s);
            tarifPage.setLast_page_url(url_tarif_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_search_page+(page-1)+"/"+s);
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }

            tarifPage.setPath(path);
            tarifPage.setData(tarifs);

        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }

    @RequestMapping(value = "/tarif_ligne_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseTarifPage searchTarifLignePage(@PathVariable(value = "id") Long id,
                                                   @PathVariable(value = "page") int page,
                                                   @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Lignes ligne = lignesDao.findByIdLigne(id);
        List<Tarifs> tarifs = this.tarifsDao.rechercheLigne(ligne, s, pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();
        Long total = this.tarifsDao.contRechercheLigne(ligne, s);
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_ligne_search_page+id+"/"+1+"/"+s);
            tarifPage.setLast_page_url(url_tarif_ligne_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_ligne_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_ligne_search_page+id+"/"+(page-1)+"/"+s);
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }

            tarifPage.setPath(path);
            tarifPage.setData(tarifs);

        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }

    @RequestMapping(value = "/tarif_bus_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseTarifPage searchTarifBusPage(@PathVariable(value = "id") Long id,
                                                   @PathVariable(value = "page") int page,
                                                   @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Bus bus = busDao.findByIdBus(id);
        List<Tarifs> tarifs = this.tarifsDao.rechercheBus(bus, s, pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();
        Long total = this.tarifsDao.countRechercheBus(bus, s);
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_bus_search_page+id+"/"+1+"/"+s);
            tarifPage.setLast_page_url(url_tarif_bus_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_bus_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_bus_search_page+id+"/"+(page-1)+"/"+s);
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }

            tarifPage.setPath(path);
            tarifPage.setData(tarifs);

        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }


    @RequestMapping(value = "/tarif_compagnie_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseTarifPage searchTarifCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Tarifs> tarifs = this.tarifsDao.rechercheCompagnie(compagnie, s, pageable);

        ResponseTarifPage tarifPage = new ResponseTarifPage();
        Long total = this.tarifsDao.countRechercheCompagnie(compagnie, s);
        Long lastPage;

        if (total > 0){
            tarifPage.setTotal(total);
            tarifPage.setPer_page(page_size);
            tarifPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            tarifPage.setLast_page(lastPage);
            tarifPage.setFirst_page_url(url_tarif_compagnie_search_page+id+"/"+1+"/"+s);
            tarifPage.setLast_page_url(url_tarif_compagnie_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                tarifPage.setNext_page_url(url_tarif_compagnie_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                tarifPage.setPrev_page_url(null);
                tarifPage.setFrom(1L);
                tarifPage.setTo(Long.valueOf(page_size));
            } else {
                tarifPage.setPrev_page_url(url_tarif_compagnie_search_page+id+"/"+(page-1)+"/"+s);
                tarifPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                tarifPage.setTo(Long.valueOf(page_size) * page);
            }

            tarifPage.setPath(path);
            tarifPage.setData(tarifs);

        }else {
            tarifPage.setTotal(0L);
        }

        return tarifPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
