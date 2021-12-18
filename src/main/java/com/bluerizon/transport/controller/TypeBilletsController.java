package com.bluerizon.transport.controller;

import com.bluerizon.transport.dao.TypeBilletsDao;
import com.bluerizon.transport.entity.TypeBillets;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.response.ResponseTypeBilletPage;
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
public class TypeBilletsController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_type_billet_page}")
    private String url_type_billet_page;

    @Value("${app.url_type_billet_search_page}")
    private String url_type_billet_search_page;

    @Autowired
    private TypeBilletsDao typeBilletsDao;


    @RequestMapping(value = { "/type_billet/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public TypeBillets selectOne(@PathVariable("id") final Integer id) {
        return this.typeBilletsDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/type_billet_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<TypeBillets> selectNoDeleted() {
        return this.typeBilletsDao.findByDeletedFalseOrderByIdTypeBilletDesc();
    }
    
    @RequestMapping(value = { "/type_billet_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<TypeBillets> selectDeleted() {
        return this.typeBilletsDao.findByDeletedTrueOrderByIdTypeBilletDesc();
    }

    @RequestMapping(value = { "/type_billet" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public TypeBillets save(@Validated @RequestBody final TypeBillets request) {
        return this.typeBilletsDao.save(request);
    }

    @RequestMapping(value = { "/type_billet/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public TypeBillets update(@PathVariable("id") final Integer id, @Validated @RequestBody final TypeBillets request) {
        TypeBillets typeBilletInit = this.typeBilletsDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        typeBilletInit.setLibelle(request.getLibelle());
        return this.typeBilletsDao.save(typeBilletInit);
    }

    @RequestMapping(value ="/type_billet_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseTypeBilletPage selectTypeBilletPage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<TypeBillets> typeBillets = this.typeBilletsDao.findAllByDeletedFalse(pageable);

        ResponseTypeBilletPage typeBilletPage = new ResponseTypeBilletPage();

        Long total = this.typeBilletsDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            typeBilletPage.setTotal(total);
            typeBilletPage.setPer_page(page_size);
            typeBilletPage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            typeBilletPage.setLast_page(lastPage);
            typeBilletPage.setFirst_page_url(url_type_billet_page+1);
            typeBilletPage.setLast_page_url(url_type_billet_page+lastPage);
            if (page >= lastPage){

            }else {
                typeBilletPage.setNext_page_url(url_type_billet_page+(page+1));
            }

            if (page == 1){
                typeBilletPage.setPrev_page_url(null);
                typeBilletPage.setFrom(1L);
                typeBilletPage.setTo(Long.valueOf(page_size));
            } else {
                typeBilletPage.setPrev_page_url(url_type_billet_page+(page-1));
                typeBilletPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                typeBilletPage.setTo(Long.valueOf(page_size) * page);
            }
            typeBilletPage.setPath(path);
            typeBilletPage.setData(typeBillets);
        }else {
            typeBilletPage.setTotal(0L);
        }

        return typeBilletPage;
    }


    @RequestMapping(value = "/type_billet_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseTypeBilletPage searchTypeBilletPage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<TypeBillets> typeBillets = this.typeBilletsDao.recherche(s, pageable);

        ResponseTypeBilletPage typeBilletPage = new ResponseTypeBilletPage();
        Long total = this.typeBilletsDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            typeBilletPage.setTotal(total);
            typeBilletPage.setPer_page(page_size);
            typeBilletPage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            typeBilletPage.setLast_page(lastPage);
            typeBilletPage.setFirst_page_url(url_type_billet_search_page+1+"/"+s);
            typeBilletPage.setLast_page_url(url_type_billet_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                typeBilletPage.setNext_page_url(url_type_billet_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                typeBilletPage.setPrev_page_url(null);
                typeBilletPage.setFrom(1L);
                typeBilletPage.setTo(Long.valueOf(page_size));
            } else {
                typeBilletPage.setPrev_page_url(url_type_billet_search_page+(page-1)+"/"+s);
                typeBilletPage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                typeBilletPage.setTo(Long.valueOf(page_size) * page);
            }

            typeBilletPage.setPath(path);
            typeBilletPage.setData(typeBillets);

        }else {
            typeBilletPage.setTotal(0L);
        }

        return typeBilletPage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
