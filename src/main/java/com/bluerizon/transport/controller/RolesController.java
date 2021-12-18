package com.bluerizon.transport.controller;

import com.bluerizon.transport.dao.RolesDao;
import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.response.ResponseRolePage;
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
public class RolesController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_role_page}")
    private String url_role_page;

    @Value("${app.url_role_search_page}")
    private String url_role_search_page;

    @Autowired
    private RolesDao rolesDao;


    @RequestMapping(value = { "/role/{id}" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public Roles selectOne(@PathVariable("id") final Integer id) {
        return this.rolesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
    }
    
    @RequestMapping(value = { "/role_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Roles> selectNoDeleted() {
        return this.rolesDao.findByDeletedFalseOrderByIdRoleDesc();
    }
    
    @RequestMapping(value = { "/role_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<Roles> selectDeleted() {
        return this.rolesDao.findByDeletedTrueOrderByIdRoleDesc();
    }

    @RequestMapping(value = { "/role" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public Roles save(@Validated @RequestBody final Roles request) {
        return this.rolesDao.save(request);
    }

    @RequestMapping(value = { "/role/{id}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public Roles update(@PathVariable("id") final Integer id, @Validated @RequestBody final Roles request) {
        Roles roleInit = this.rolesDao.findById(id).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+id+" n'existe pas!"));
        roleInit.setLibelle(request.getLibelle());
        return this.rolesDao.save(roleInit);
    }

    @RequestMapping(value ="/role_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseRolePage selectRolePage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<Roles> roles = this.rolesDao.findAllByDeletedFalse(pageable);

        ResponseRolePage rolePage = new ResponseRolePage();

        Long total = this.rolesDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            rolePage.setTotal(total);
            rolePage.setPer_page(page_size);
            rolePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            rolePage.setLast_page(lastPage);
            rolePage.setFirst_page_url(url_role_page+1);
            rolePage.setLast_page_url(url_role_page+lastPage);
            if (page >= lastPage){

            }else {
                rolePage.setNext_page_url(url_role_page+(page+1));
            }

            if (page == 1){
                rolePage.setPrev_page_url(null);
                rolePage.setFrom(1L);
                rolePage.setTo(Long.valueOf(page_size));
            } else {
                rolePage.setPrev_page_url(url_role_page+(page-1));
                rolePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                rolePage.setTo(Long.valueOf(page_size) * page);
            }
            rolePage.setPath(path);
            rolePage.setData(roles);
        }else {
            rolePage.setTotal(0L);
        }

        return rolePage;
    }


    @RequestMapping(value = "/role_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseRolePage searchrolePage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<Roles> roles = this.rolesDao.recherche(s, pageable);

        ResponseRolePage rolePage = new ResponseRolePage();
        Long total = this.rolesDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            rolePage.setTotal(total);
            rolePage.setPer_page(page_size);
            rolePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            rolePage.setLast_page(lastPage);
            rolePage.setFirst_page_url(url_role_search_page+1+"/"+s);
            rolePage.setLast_page_url(url_role_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                rolePage.setNext_page_url(url_role_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                rolePage.setPrev_page_url(null);
                rolePage.setFrom(1L);
                rolePage.setTo(Long.valueOf(page_size));
            } else {
                rolePage.setPrev_page_url(url_role_search_page+(page-1)+"/"+s);
                rolePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                rolePage.setTo(Long.valueOf(page_size) * page);
            }

            rolePage.setPath(path);
            rolePage.setData(roles);

        }else {
            rolePage.setTotal(0L);
        }

        return rolePage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
