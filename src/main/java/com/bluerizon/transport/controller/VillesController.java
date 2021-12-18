package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.TypeBusRequest;
import com.bluerizon.transport.response.ResponseVillePage;
import com.bluerizon.transport.response.ResponseVillePage;
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
public class VillesController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_ville_page}")
    private String url_ville_page;
    
    @Value("${app.url_ville_compagnie_page}")
    private String url_ville_compagnie_page;

    @Value("${app.url_ville_pays_page}")
    private String url_ville_pays_page;

    @Value("${app.url_ville_search_page}")
    private String url_ville_search_page;

    @Value("${app.url_ville_compagnie_search_page}")
    private String url_ville_compagnie_search_page;

    @Value("${app.url_ville_pays_search_page}")
    private String url_ville_pays_search_page;

    @Autowired
    private VillesDao villesDao;

    @Autowired
    private CompagniesDao compagniesDao;

    @Autowired
    private PaysDao paysDao;


    @RequestMapping(value = { "/ville/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Villes selectOne(@PathVariable("id") final Long id) {
        return this.villesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/ville_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Villes> selectNoDeleted() {
        return this.villesDao.findByDeletedFalseOrderByIdVilleDesc();
    }
    
    @RequestMapping(value = { "/ville_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Villes> selectDeleted() {
        return this.villesDao.findByDeletedTrueOrderByIdVilleDesc();
    }


    @RequestMapping(value = { "/ville_compagnie/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Villes> SelectAllByCompagnie(@PathVariable("id") final Long id) {
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        return this.villesDao.findByCompagnie(compagnie);
    }

    @RequestMapping(value = { "/ville_pays/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Villes> SelectAllByCompagnie(@PathVariable("id") final Integer id) {
        Pays pays = paysDao.findByIdPays(id);
        return this.villesDao.findByPays(pays);
    }

    @RequestMapping(value = { "/ville" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Villes save(@Validated @RequestBody final Villes request) {
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        Pays pays = paysDao.findByIdPays(request.getPays().getIdPays());
        request.setCompagnie(compagnie);
        request.setPays(pays);
        return this.villesDao.save(request);
    }

    @RequestMapping(value = { "/ville/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Villes update(@PathVariable("id") final Long id, @Validated @RequestBody final Villes request) {
        Villes villeInit = this.villesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());
        Pays pays = paysDao.findByIdPays(request.getPays().getIdPays());
        villeInit.setCompagnie(compagnie);
        villeInit.setPays(pays);
        villeInit.setNomVille(request.getNomVille());
        return this.villesDao.save(villeInit);
    }

    @RequestMapping(value ="/ville_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVillePage selectVillePage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Villes> villes = this.villesDao.findAllByDeletedFalse(pageable);

        ResponseVillePage villePage = new ResponseVillePage();

        Long total = this.villesDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            villePage.setTotal(total);
            villePage.setPer_page(page_size);
            villePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            villePage.setLast_page(lastPage);
            villePage.setFirst_page_url(url_ville_page+1);
            villePage.setLast_page_url(url_ville_page+lastPage);
            if (page >= lastPage){

            }else {
                villePage.setNext_page_url(url_ville_page+(page+1));
            }

            if (page == 1){
                villePage.setPrev_page_url(null);
                villePage.setFrom(1L);
                villePage.setTo(Long.valueOf(page_size));
            } else {
                villePage.setPrev_page_url(url_ville_page+(page-1));
                villePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                villePage.setTo(Long.valueOf(page_size) * page);
            }
            villePage.setPath(path);
            villePage.setData(villes);
        }else {
            villePage.setTotal(0L);
        }

        return villePage;
    }
    
    @RequestMapping(value ="/ville_compagnie_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVillePage selectVilleCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Villes> villes = this.villesDao.findByCompagnie(compagnie,pageable);

        ResponseVillePage villePage = new ResponseVillePage();

        Long total = this.villesDao.countByCompagnie(compagnie);
        Long lastPage;

        if (total > 0){
            villePage.setTotal(total);
            villePage.setPer_page(page_size);
            villePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            villePage.setLast_page(lastPage);
            villePage.setFirst_page_url(url_ville_compagnie_page+id+"/"+1);
            villePage.setLast_page_url(url_ville_compagnie_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                villePage.setNext_page_url(url_ville_compagnie_page+id+"/"+(page+1));
            }

            if (page == 1){
                villePage.setPrev_page_url(null);
                villePage.setFrom(1L);
                villePage.setTo(Long.valueOf(page_size));
            } else {
                villePage.setPrev_page_url(url_ville_compagnie_page+id+"/"+(page-1));
                villePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                villePage.setTo(Long.valueOf(page_size) * page);
            }
            villePage.setPath(path);
            villePage.setData(villes);
        }else {
            villePage.setTotal(0L);
        }

        return villePage;
    }

    @RequestMapping(value ="/ville_pays_page/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVillePage selectVillePaysPage(@PathVariable(value = "id") Integer id,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Pays pays = paysDao.findByIdPays(id);
        List<Villes> villes = this.villesDao.findByPays(pays, pageable);

        ResponseVillePage villePage = new ResponseVillePage();

        Long total = this.villesDao.countByPays(pays);
        Long lastPage;

        if (total > 0){
            villePage.setTotal(total);
            villePage.setPer_page(page_size);
            villePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            villePage.setLast_page(lastPage);
            villePage.setFirst_page_url(url_ville_pays_page+id+"/"+1);
            villePage.setLast_page_url(url_ville_pays_page+id+"/"+lastPage);
            if (page >= lastPage){

            }else {
                villePage.setNext_page_url(url_ville_pays_page+id+"/"+(page+1));
            }

            if (page == 1){
                villePage.setPrev_page_url(null);
                villePage.setFrom(1L);
                villePage.setTo(Long.valueOf(page_size));
            } else {
                villePage.setPrev_page_url(url_ville_pays_page+id+"/"+(page-1));
                villePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                villePage.setTo(Long.valueOf(page_size) * page);
            }
            villePage.setPath(path);
            villePage.setData(villes);
        }else {
            villePage.setTotal(0L);
        }

        return villePage;
    }


    @RequestMapping(value = "/ville_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVillePage searchvillePage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Villes> villes = this.villesDao.recherche(s, pageable);

        ResponseVillePage villePage = new ResponseVillePage();
        Long total = this.villesDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            villePage.setTotal(total);
            villePage.setPer_page(page_size);
            villePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            villePage.setLast_page(lastPage);
            villePage.setFirst_page_url(url_ville_search_page+1+"/"+s);
            villePage.setLast_page_url(url_ville_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                villePage.setNext_page_url(url_ville_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                villePage.setPrev_page_url(null);
                villePage.setFrom(1L);
                villePage.setTo(Long.valueOf(page_size));
            } else {
                villePage.setPrev_page_url(url_ville_search_page+(page-1)+"/"+s);
                villePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                villePage.setTo(Long.valueOf(page_size) * page);
            }

            villePage.setPath(path);
            villePage.setData(villes);

        }else {
            villePage.setTotal(0L);
        }

        return villePage;
    }

    @RequestMapping(value = "/ville_compagnie_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVillePage searchVilleCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<Villes> villes = this.villesDao.rechercheCompagnie(compagnie, s, pageable);

        ResponseVillePage villePage = new ResponseVillePage();
        Long total = this.villesDao.countRechercheCompagnie(compagnie, s);
        Long lastPage;

        if (total > 0){
            villePage.setTotal(total);
            villePage.setPer_page(page_size);
            villePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            villePage.setLast_page(lastPage);
            villePage.setFirst_page_url(url_ville_compagnie_search_page+id+"/"+1+"/"+s);
            villePage.setLast_page_url(url_ville_compagnie_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                villePage.setNext_page_url(url_ville_compagnie_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                villePage.setPrev_page_url(null);
                villePage.setFrom(1L);
                villePage.setTo(Long.valueOf(page_size));
            } else {
                villePage.setPrev_page_url(url_ville_compagnie_search_page+id+"/"+(page-1)+"/"+s);
                villePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                villePage.setTo(Long.valueOf(page_size) * page);
            }

            villePage.setPath(path);
            villePage.setData(villes);

        }else {
            villePage.setTotal(0L);
        }

        return villePage;
    }

    @RequestMapping(value = "/ville_pays_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseVillePage searchVillePaysPage(@PathVariable(value = "id") Integer id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Pays pays = paysDao.findByIdPays(id);
        List<Villes> villes = this.villesDao.recherchePays(pays, s, pageable);

        ResponseVillePage villePage = new ResponseVillePage();
        Long total = this.villesDao.countRecherchePays(pays, s);
        Long lastPage;

        if (total > 0){
            villePage.setTotal(total);
            villePage.setPer_page(page_size);
            villePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            villePage.setLast_page(lastPage);
            villePage.setFirst_page_url(url_ville_pays_search_page+id+"/"+1+"/"+s);
            villePage.setLast_page_url(url_ville_pays_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                villePage.setNext_page_url(url_ville_pays_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                villePage.setPrev_page_url(null);
                villePage.setFrom(1L);
                villePage.setTo(Long.valueOf(page_size));
            } else {
                villePage.setPrev_page_url(url_ville_pays_search_page+id+"/"+(page-1)+"/"+s);
                villePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                villePage.setTo(Long.valueOf(page_size) * page);
            }

            villePage.setPath(path);
            villePage.setData(villes);

        }else {
            villePage.setTotal(0L);
        }

        return villePage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
