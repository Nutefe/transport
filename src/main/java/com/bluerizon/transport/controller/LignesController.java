package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.ArretsDao;
import com.bluerizon.transport.dao.LignesDao;
import com.bluerizon.transport.entity.Arrets;
import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.response.ResponseLignePage;
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
public class LignesController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_ligne_page}")
    private String url_ligne_page;

    @Value("${app.url_lignes_page}")
    private String url_lignes_page;

    @Value("${app.url_ligne_search_page}")
    private String url_ligne_search_page;

    @Value("${app.url_lignes_search_page}")
    private String url_lignes_search_page;

    @Autowired
    private LignesDao lignesDao;
    
    @Autowired
    private ArretsDao arretsDao;


    @RequestMapping(value = { "/ligne/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Lignes selectOne(@PathVariable("id") final Long id) {
        return this.lignesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/ligne_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Lignes> selectNoDeleted() {
        return this.lignesDao.findByDeletedFalseOrderByIdLigneDesc();
    }
    
    @RequestMapping(value = { "/ligne_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Lignes> selectDeleted() {
        return this.lignesDao.findByDeletedTrueOrderByIdLigneDesc();
    }

    @RequestMapping(value = { "/ligne" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Lignes save(@Validated @RequestBody final Lignes request) {
        Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
        Arrets arrive = arretsDao.findByIdArret(request.getArriver().getIdArret());
        Arrets depart = arretsDao.findByIdArret(request.getDepart().getIdArret());
        request.setLigne(ligne);
        request.setArriver(arrive);
        request.setDepart(depart);
        return this.lignesDao.save(request);
    }

    @RequestMapping(value = { "/ligne/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Lignes update(@PathVariable("id") final Long id, @Validated @RequestBody final Lignes request) {
        Lignes ligneInit = this.lignesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        Lignes ligne = lignesDao.findByIdLigne(request.getLigne().getIdLigne());
        Arrets arrive = arretsDao.findByIdArret(request.getArriver().getIdArret());
        Arrets depart = arretsDao.findByIdArret(request.getDepart().getIdArret());
        ligneInit.setLigne(ligne);
        ligneInit.setArriver(arrive);
        ligneInit.setDepart(depart);
        return this.lignesDao.save(ligneInit);
    }

    @RequestMapping(value ="/ligne_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseLignePage selectLignePage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Lignes> lignes = this.lignesDao.findAllByDeletedFalse(pageable);

        ResponseLignePage lignePage = new ResponseLignePage();

        Long total = this.lignesDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            lignePage.setTotal(total);
            lignePage.setPer_page(page_size);
            lignePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            lignePage.setLast_page(lastPage);
            lignePage.setFirst_page_url(url_ligne_page+1);
            lignePage.setLast_page_url(url_ligne_page+lastPage);
            if (page >= lastPage){

            }else {
                lignePage.setNext_page_url(url_ligne_page+(page+1));
            }

            if (page == 1){
                lignePage.setPrev_page_url(null);
                lignePage.setFrom(1L);
                lignePage.setTo(Long.valueOf(page_size));
            } else {
                lignePage.setPrev_page_url(url_ligne_page+(page-1));
                lignePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                lignePage.setTo(Long.valueOf(page_size) * page);
            }
            lignePage.setPath(path);
            lignePage.setData(lignes);
        }else {
            lignePage.setTotal(0L);
        }

        return lignePage;
    }

    @RequestMapping(value ="/lignes_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseLignePage selectLignesPage(@PathVariable(value = "id") Long id,
                                                        @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Lignes ligne = lignesDao.findByIdLigne(id);
        List<Lignes> lignes = this.lignesDao.findByLigne(ligne, pageable);

        ResponseLignePage lignePage = new ResponseLignePage();

        Long total = this.lignesDao.countByLigne(ligne);
        Long lastPage;

        if (total > 0){
            lignePage.setTotal(total);
            lignePage.setPer_page(page_size);
            lignePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            lignePage.setLast_page(lastPage);
            lignePage.setFirst_page_url(url_lignes_page+id+"/"+1);
            lignePage.setLast_page_url(url_lignes_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                lignePage.setNext_page_url(url_lignes_page+id+"/"+(page+1));
            }

            if (page == 1){
                lignePage.setPrev_page_url(null);
                lignePage.setFrom(1L);
                lignePage.setTo(Long.valueOf(page_size));
            } else {
                lignePage.setPrev_page_url(url_lignes_page+id+"/"+(page-1));
                lignePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                lignePage.setTo(Long.valueOf(page_size) * page);
            }
            lignePage.setPath(path);
            lignePage.setData(lignes);
        }else {
            lignePage.setTotal(0L);
        }

        return lignePage;
    }


    @RequestMapping(value = "/ligne_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseLignePage searchLignePage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Lignes> lignes = this.lignesDao.recherche(s, pageable);

        ResponseLignePage lignePage = new ResponseLignePage();
        Long total = this.lignesDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            lignePage.setTotal(total);
            lignePage.setPer_page(page_size);
            lignePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            lignePage.setLast_page(lastPage);
            lignePage.setFirst_page_url(url_ligne_search_page+1+"/"+s);
            lignePage.setLast_page_url(url_ligne_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                lignePage.setNext_page_url(url_ligne_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                lignePage.setPrev_page_url(null);
                lignePage.setFrom(1L);
                lignePage.setTo(Long.valueOf(page_size));
            } else {
                lignePage.setPrev_page_url(url_ligne_search_page+(page-1)+"/"+s);
                lignePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                lignePage.setTo(Long.valueOf(page_size) * page);
            }

            lignePage.setPath(path);
            lignePage.setData(lignes);

        }else {
            lignePage.setTotal(0L);
        }

        return lignePage;
    }

    @RequestMapping(value = "/lignes_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseLignePage searchLignesPage(@PathVariable(value = "id") Long id,
                                                        @PathVariable(value = "page") int page,
                                                        @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Lignes ligne = lignesDao.findByIdLigne(id);
        List<Lignes> lignes = this.lignesDao.rechercheLigne(ligne, s, pageable);

        ResponseLignePage clientPage = new ResponseLignePage();
        Long total = this.lignesDao.countRechercheLigne(ligne, s);
        Long lastPage;

        if (total > 0){
            clientPage.setTotal(total);
            clientPage.setPer_page(page_size);
            clientPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            clientPage.setLast_page(lastPage);
            clientPage.setFirst_page_url(url_lignes_search_page+id+"/"+1+"/"+s);
            clientPage.setLast_page_url(url_lignes_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                clientPage.setNext_page_url(url_lignes_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                clientPage.setPrev_page_url(null);
                clientPage.setFrom(1L);
                clientPage.setTo(Long.valueOf(page_size));
            } else {
                clientPage.setPrev_page_url(url_lignes_search_page+id+"/"+(page-1)+"/"+s);
                clientPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                clientPage.setTo(Long.valueOf(page_size) * page);
            }

            clientPage.setPath(path);
            clientPage.setData(lignes);

        }else {
            clientPage.setTotal(0L);
        }

        return clientPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
