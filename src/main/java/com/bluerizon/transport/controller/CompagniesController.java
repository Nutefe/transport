package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.CompagniesDao;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.response.ResponseCompagniePage;
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
public class CompagniesController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_compagnie_page}")
    private String url_compagnie_page;

    @Value("${app.url_compagnie_search_page}")
    private String url_compagnie_search_page;

    @Autowired
    private CompagniesDao compagniesDao;


    @RequestMapping(value = { "/compagnie/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Compagnies selectOne(@PathVariable("id") final Long id) {
        return this.compagniesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/compagnie_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Compagnies> selectNoDeleted() {
        return this.compagniesDao.findByDeletedFalseOrderByIdCompagnieDesc();
    }
    
    @RequestMapping(value = { "/compagnie_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Compagnies> selectDeleted() {
        return this.compagniesDao.findByDeletedTrueOrderByIdCompagnieDesc();
    }

    @RequestMapping(value = { "/compagnie" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Compagnies save(@Validated @RequestBody final Compagnies request) {
        return this.compagniesDao.save(request);
    }

    @RequestMapping(value = { "/compagnie/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Compagnies update(@PathVariable("id") final Long id, @Validated @RequestBody final Compagnies request) {
        Compagnies compagnieInit = this.compagniesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        compagnieInit.setNomCompagnie(request.getNomCompagnie());
        compagnieInit.setAdresse(request.getAdresse());
        compagnieInit.setEmail(request.getEmail());
        compagnieInit.setTelephone(request.getTelephone());
        return this.compagniesDao.save(compagnieInit);
    }

    @RequestMapping(value ="/compagnie_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseCompagniePage selectCompagniePage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Compagnies> compagnies = this.compagniesDao.findAllByDeletedFalse(pageable);

        ResponseCompagniePage compagniePage = new ResponseCompagniePage();

        Long total = this.compagniesDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            compagniePage.setTotal(total);
            compagniePage.setPer_page(page_size);
            compagniePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            compagniePage.setLast_page(lastPage);
            compagniePage.setFirst_page_url(url_compagnie_page+1);
            compagniePage.setLast_page_url(url_compagnie_page+lastPage);
            if (page >= lastPage){

            }else {
                compagniePage.setNext_page_url(url_compagnie_page+(page+1));
            }

            if (page == 1){
                compagniePage.setPrev_page_url(null);
                compagniePage.setFrom(1L);
                compagniePage.setTo(Long.valueOf(page_size));
            } else {
                compagniePage.setPrev_page_url(url_compagnie_page+(page-1));
                compagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                compagniePage.setTo(Long.valueOf(page_size) * page);
            }
            compagniePage.setPath(path);
            compagniePage.setData(compagnies);
        }else {
            compagniePage.setTotal(0L);
        }

        return compagniePage;
    }


    @RequestMapping(value = "/compagnie_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseCompagniePage searchCompagniePage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Compagnies> compagnies = this.compagniesDao.recherche(s, pageable);

        ResponseCompagniePage compagniePage = new ResponseCompagniePage();
        Long total = this.compagniesDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            compagniePage.setTotal(total);
            compagniePage.setPer_page(page_size);
            compagniePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            compagniePage.setLast_page(lastPage);
            compagniePage.setFirst_page_url(url_compagnie_search_page+1+"/"+s);
            compagniePage.setLast_page_url(url_compagnie_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                compagniePage.setNext_page_url(url_compagnie_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                compagniePage.setPrev_page_url(null);
                compagniePage.setFrom(1L);
                compagniePage.setTo(Long.valueOf(page_size));
            } else {
                compagniePage.setPrev_page_url(url_compagnie_search_page+(page-1)+"/"+s);
                compagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                compagniePage.setTo(Long.valueOf(page_size) * page);
            }

            compagniePage.setPath(path);
            compagniePage.setData(compagnies);

        }else {
            compagniePage.setTotal(0L);
        }

        return compagniePage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
