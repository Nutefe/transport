package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.PaysDao;
import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.response.ResponsePaysPage;
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
public class PaysController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_pays_page}")
    private String url_pays_page;

    @Value("${app.url_pays_search_page}")
    private String url_pays_search_page;

    @Autowired
    private PaysDao paysDao;


    @RequestMapping(value = { "/pays/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Pays selectOne(@PathVariable("id") final Integer id) {
        return this.paysDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/pays_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Pays> selectNoDeleted() {
        return this.paysDao.findByDeletedFalseOrderByIdPaysDesc();
    }
    
    @RequestMapping(value = { "/pays_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Pays> selectDeleted() {
        return this.paysDao.findByDeletedTrueOrderByIdPaysDesc();
    }

    @RequestMapping(value = { "/pays" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Pays save(@Validated @RequestBody final Pays request) {
        return this.paysDao.save(request);
    }

    @RequestMapping(value = { "/pays/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Pays update(@PathVariable("id") final Integer id, @Validated @RequestBody final Pays request) {
        Pays paysInit = this.paysDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        paysInit.setCodePays(request.getCodePays());
        paysInit.setNomPays(request.getNomPays());
        paysInit.setIndicatifPays(request.getIndicatifPays());
        return this.paysDao.save(paysInit);
    }

    @RequestMapping(value ="/pays_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponsePaysPage selectPaysPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Pays> pays = this.paysDao.findAllByDeletedFalse(pageable);

        ResponsePaysPage paysPage = new ResponsePaysPage();

        Long total = this.paysDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            paysPage.setTotal(total);
            paysPage.setPer_page(page_size);
            paysPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            paysPage.setLast_page(lastPage);
            paysPage.setFirst_page_url(url_pays_page+1);
            paysPage.setLast_page_url(url_pays_page+lastPage);
            if (page >= lastPage){

            }else {
                paysPage.setNext_page_url(url_pays_page+(page+1));
            }

            if (page == 1){
                paysPage.setPrev_page_url(null);
                paysPage.setFrom(1L);
                paysPage.setTo(Long.valueOf(page_size));
            } else {
                paysPage.setPrev_page_url(url_pays_page+(page-1));
                paysPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                paysPage.setTo(Long.valueOf(page_size) * page);
            }
            paysPage.setPath(path);
            paysPage.setData(pays);
        }else {
            paysPage.setTotal(0L);
        }

        return paysPage;
    }


    @RequestMapping(value = "/pays_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponsePaysPage searchPaysPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Pays> pays = this.paysDao.recherche(s, pageable);

        ResponsePaysPage paysPage = new ResponsePaysPage();
        Long total = this.paysDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            paysPage.setTotal(total);
            paysPage.setPer_page(page_size);
            paysPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            paysPage.setLast_page(lastPage);
            paysPage.setFirst_page_url(url_pays_search_page+1+"/"+s);
            paysPage.setLast_page_url(url_pays_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                paysPage.setNext_page_url(url_pays_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                paysPage.setPrev_page_url(null);
                paysPage.setFrom(1L);
                paysPage.setTo(Long.valueOf(page_size));
            } else {
                paysPage.setPrev_page_url(url_pays_search_page+(page-1)+"/"+s);
                paysPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                paysPage.setTo(Long.valueOf(page_size) * page);
            }

            paysPage.setPath(path);
            paysPage.setData(pays);

        }else {
            paysPage.setTotal(0L);
        }

        return paysPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
