package com.bluerizon.transport.controller;


import com.bluerizon.transport.dao.*;
import com.bluerizon.transport.entity.*;
import com.bluerizon.transport.exception.NotFoundRequestException;
import com.bluerizon.transport.requeste.UserCompagnieRequest;
import com.bluerizon.transport.response.ResponseUserCompagniePage;
import com.bluerizon.transport.response.ResponseUserCompagniePage;
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
public class UserCompagniesController {

    @Value("${app.base}")
    private String path;

    @Value("${app.page_size}")
    private Integer page_size;

    @Value("${app.url_user_compagnie_page}")
    private String url_user_compagnie_page;
    
    @Value("${app.url_users_compagnie_page}")
    private String url_users_compagnie_page;

    @Value("${app.url_user_compagnies_page}")
    private String url_user_compagnies_page;

    @Value("${app.url_user_compagnie_search_page}")
    private String url_user_compagnie_search_page;

    @Value("${app.url_users_compagnie_search_page}")
    private String url_users_compagnie_search_page;

    @Value("${app.url_user_compagnies_search_page}")
    private String url_user_compagnies_search_page;

    @Autowired
    private UserCompagnieDao userCompagnieDao;

    @Autowired
    private CompagniesDao compagniesDao;

    @Autowired
    private UsersDao usersDao;
    
    @RequestMapping(value = { "/user_compagnie_no_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<UserCompagnies> selectNoDeleted() {
        return this.userCompagnieDao.findByDeletedFalse();
    }
    
    @RequestMapping(value = { "/user_compagnie_deleted" }, method = { RequestMethod.GET })
    @ResponseStatus(HttpStatus.OK)
    public List<UserCompagnies> selectDeleted() {
        return this.userCompagnieDao.findByDeletedTrue();
    }

    @RequestMapping(value = { "/user_compagnie" }, method = { RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public UserCompagnies save(@Validated @RequestBody final UserCompagnieRequest request) {

        Users userInit = new Users();
        userInit.setNom(request.getNom());
        userInit.setPrenom(request.getPrenom());
        userInit.setEmail(request.getEmail());
        userInit.setUsername(request.getUsername());
        userInit.setPassword(request.getPassword());
        userInit.setTelephone(request.getTelephone());
        userInit.setAdresse(request.getAdresse());
        userInit.setRole(request.getRole());
        userInit.setExpirer(request.getExpirer());
        userInit.setNbrCompagnie(request.getNbrCompagnie());
        userInit.setActive(request.isActive());
        Users userSave = this.usersDao.save(userInit);

        Compagnies compagnie = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());

        UserCompagnies userCompagnie= new UserCompagnies();
        userCompagnie.setUserPK(new UserPK(userSave, compagnie));

        return this.userCompagnieDao.save(userCompagnie);
    }

    @RequestMapping(value = { "/user_compagnie/{idUser}/{idCompagnie}" }, method = { RequestMethod.PUT })
    @ResponseStatus(HttpStatus.OK)
    public UserCompagnies update(@PathVariable("idUser") final Long idUser, @PathVariable("idCompagnie") final Long idCompagnie,
                                 @Validated @RequestBody final UserCompagnieRequest request) {

        Users userInit = this.usersDao.getOneOptional(idUser).orElseThrow(() -> new NotFoundRequestException("Objet dont l'id "+idUser+" n'existe pas!"));
        userInit.setNom(request.getNom());
        userInit.setPrenom(request.getPrenom());
        userInit.setEmail(request.getEmail());
        userInit.setUsername(request.getUsername());
        userInit.setPassword(request.getPassword());
        userInit.setTelephone(request.getTelephone());
        userInit.setAdresse(request.getAdresse());
        userInit.setRole(request.getRole());
        userInit.setExpirer(request.getExpirer());
        userInit.setNbrCompagnie(request.getNbrCompagnie());
        userInit.setActive(request.isActive());

        this.usersDao.updateUser(userInit.getIdUser(), userInit.getUsername(), userInit.getEmail(), userInit.getNom(),
                userInit.getPrenom(), userInit.getTelephone(), userInit.getAdresse(), userInit.getExpirer(),
                userInit.getNbrCompagnie(), userInit.getRole());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(idCompagnie);
        Compagnies compagnieChange = compagniesDao.findByIdCompagnie(request.getCompagnie().getIdCompagnie());

        userCompagnieDao.delete(new UserPK(userInit, compagnie));

        UserCompagnies userCompagnie= new UserCompagnies();
        userCompagnie.setUserPK(new UserPK(userInit, compagnieChange));

        return this.userCompagnieDao.save(userCompagnie);
    }

    @RequestMapping(value ="/user_compagnie_page/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserCompagniePage selectUserCompagniePage(@PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        List<UserCompagnies> userCompagnies = this.userCompagnieDao.findAllByDeletedFalse(pageable);

        ResponseUserCompagniePage userCompagniePage = new ResponseUserCompagniePage();

        Long total = this.userCompagnieDao.countByDeletedFalse();
        Long lastPage;

        if (total > 0){
            userCompagniePage.setTotal(total);
            userCompagniePage.setPer_page(page_size);
            userCompagniePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userCompagniePage.setLast_page(lastPage);
            userCompagniePage.setFirst_page_url(url_user_compagnie_page+1);
            userCompagniePage.setLast_page_url(url_user_compagnie_page+lastPage);
            if (page >= lastPage){

            }else {
                userCompagniePage.setNext_page_url(url_user_compagnie_page+(page+1));
            }

            if (page == 1){
                userCompagniePage.setPrev_page_url(null);
                userCompagniePage.setFrom(1L);
                userCompagniePage.setTo(Long.valueOf(page_size));
            } else {
                userCompagniePage.setPrev_page_url(url_user_compagnie_page+(page-1));
                userCompagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userCompagniePage.setTo(Long.valueOf(page_size) * page);
            }
            userCompagniePage.setPath(path);
            userCompagniePage.setData(userCompagnies);
        }else {
            userCompagniePage.setTotal(0L);
        }

        return userCompagniePage;
    }
    
    @RequestMapping(value ="/users_compagnie_page/{idCompagnie}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserCompagniePage selectUsersCompagniePage(@PathVariable(value = "idCompagnie") Long idCompagnie,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Compagnies compagnie = compagniesDao.findByIdCompagnie(idCompagnie);
        List<UserCompagnies> userCompagnies = this.userCompagnieDao.findByCompagnie(compagnie,pageable);

        ResponseUserCompagniePage userCompagniePage = new ResponseUserCompagniePage();

        Long total = this.userCompagnieDao.countByCompagnie(compagnie);
        Long lastPage;

        if (total > 0){
            userCompagniePage.setTotal(total);
            userCompagniePage.setPer_page(page_size);
            userCompagniePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userCompagniePage.setLast_page(lastPage);
            userCompagniePage.setFirst_page_url(url_users_compagnie_page+idCompagnie+"/"+1);
            userCompagniePage.setLast_page_url(url_users_compagnie_page+idCompagnie+"/"+lastPage);
            if (page >= lastPage){

            }else {
                userCompagniePage.setNext_page_url(url_users_compagnie_page+idCompagnie+"/"+(page+1));
            }

            if (page == 1){
                userCompagniePage.setPrev_page_url(null);
                userCompagniePage.setFrom(1L);
                userCompagniePage.setTo(Long.valueOf(page_size));
            } else {
                userCompagniePage.setPrev_page_url(url_users_compagnie_page+idCompagnie+"/"+(page-1));
                userCompagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userCompagniePage.setTo(Long.valueOf(page_size) * page);
            }
            userCompagniePage.setPath(path);
            userCompagniePage.setData(userCompagnies);
        }else {
            userCompagniePage.setTotal(0L);
        }

        return userCompagniePage;
    }

    @RequestMapping(value ="/user_compagnies_page/{idUser}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseUserCompagniePage selectUserCompagniesPage(@PathVariable(value = "idUser") Long idUser,
                                                            @PathVariable(value = "page") int page) {

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());

        Users user = usersDao.findByIdUser(idUser);
        List<UserCompagnies> userCompagnies = this.userCompagnieDao.findByUser(user, pageable);

        ResponseUserCompagniePage userCompagniePage = new ResponseUserCompagniePage();

        Long total = this.userCompagnieDao.countByUser(user);
        Long lastPage;

        if (total > 0){
            userCompagniePage.setTotal(total);
            userCompagniePage.setPer_page(page_size);
            userCompagniePage.setCurrent_page(page);
            if (total % page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;

            }
            userCompagniePage.setLast_page(lastPage);
            userCompagniePage.setFirst_page_url(url_user_compagnies_page+idUser+"/"+1);
            userCompagniePage.setLast_page_url(url_user_compagnies_page+idUser+"/"+lastPage);
            if (page >= lastPage){

            }else {
                userCompagniePage.setNext_page_url(url_user_compagnies_page+idUser+"/"+(page+1));
            }

            if (page == 1){
                userCompagniePage.setPrev_page_url(null);
                userCompagniePage.setFrom(1L);
                userCompagniePage.setTo(Long.valueOf(page_size));
            } else {
                userCompagniePage.setPrev_page_url(url_user_compagnies_page+idUser+"/"+(page-1));
                userCompagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userCompagniePage.setTo(Long.valueOf(page_size) * page);
            }
            userCompagniePage.setPath(path);
            userCompagniePage.setData(userCompagnies);
        }else {
            userCompagniePage.setTotal(0L);
        }

        return userCompagniePage;
    }


    @RequestMapping(value = "/user_compagnie_search_page/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserCompagniePage searchUserCompagniePage(@PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        List<UserCompagnies> userCompagnies = this.userCompagnieDao.recherche(s, pageable);

        ResponseUserCompagniePage userCompagniePage = new ResponseUserCompagniePage();
        Long total = this.userCompagnieDao.countRecherche(s);
        Long lastPage;

        if (total > 0){
            userCompagniePage.setTotal(total);
            userCompagniePage.setPer_page(page_size);
            userCompagniePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            userCompagniePage.setLast_page(lastPage);
            userCompagniePage.setFirst_page_url(url_user_compagnie_search_page+1+"/"+s);
            userCompagniePage.setLast_page_url(url_user_compagnie_search_page+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                userCompagniePage.setNext_page_url(url_user_compagnie_search_page+(page+1)+"/"+s);
            }

            if (page == 1){
                userCompagniePage.setPrev_page_url(null);
                userCompagniePage.setFrom(1L);
                userCompagniePage.setTo(Long.valueOf(page_size));
            } else {
                userCompagniePage.setPrev_page_url(url_user_compagnie_search_page+(page-1)+"/"+s);
                userCompagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userCompagniePage.setTo(Long.valueOf(page_size) * page);
            }

            userCompagniePage.setPath(path);
            userCompagniePage.setData(userCompagnies);

        }else {
            userCompagniePage.setTotal(0L);
        }

        return userCompagniePage;
    }

    @RequestMapping(value = "/users_compagnie_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserCompagniePage searchUsersCompagniePage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Compagnies compagnie = compagniesDao.findByIdCompagnie(id);
        List<UserCompagnies> userCompagnies = this.userCompagnieDao.rechercheCompagnie(compagnie, s, pageable);

        ResponseUserCompagniePage userCompagniePage = new ResponseUserCompagniePage();
        Long total = this.userCompagnieDao.countRechercheCompagnie(compagnie, s);
        Long lastPage;

        if (total > 0){
            userCompagniePage.setTotal(total);
            userCompagniePage.setPer_page(page_size);
            userCompagniePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            userCompagniePage.setLast_page(lastPage);
            userCompagniePage.setFirst_page_url(url_users_compagnie_search_page+id+"/"+1+"/"+s);
            userCompagniePage.setLast_page_url(url_users_compagnie_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                userCompagniePage.setNext_page_url(url_users_compagnie_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                userCompagniePage.setPrev_page_url(null);
                userCompagniePage.setFrom(1L);
                userCompagniePage.setTo(Long.valueOf(page_size));
            } else {
                userCompagniePage.setPrev_page_url(url_users_compagnie_search_page+id+"/"+(page-1)+"/"+s);
                userCompagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userCompagniePage.setTo(Long.valueOf(page_size) * page);
            }

            userCompagniePage.setPath(path);
            userCompagniePage.setData(userCompagnies);

        }else {
            userCompagniePage.setTotal(0L);
        }

        return userCompagniePage;
    }

    @RequestMapping(value = "/user_compagnies_search_page/{id}/{page}/{s}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserCompagniePage searchUserCompagniesPage(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "page") int page,
                                                            @PathVariable(value = "s") String s){

        Pageable pageable = PageRequest.of(page - 1, page_size, sortByCreatedDesc());
        Users user = usersDao.findByIdUser(id);
        List<UserCompagnies> userCompagnies = this.userCompagnieDao.rechercheUser(user, s, pageable);

        ResponseUserCompagniePage userCompagniePage = new ResponseUserCompagniePage();
        Long total = this.userCompagnieDao.countRechercheUser(user, s);
        Long lastPage;

        if (total > 0){
            userCompagniePage.setTotal(total);
            userCompagniePage.setPer_page(page_size);
            userCompagniePage.setCurrent_page(page);

            if (total %page_size == 0){
                lastPage = total/page_size;
            } else {
                lastPage = (total/page_size)+1;
            }
            userCompagniePage.setLast_page(lastPage);
            userCompagniePage.setFirst_page_url(url_user_compagnies_search_page+id+"/"+1+"/"+s);
            userCompagniePage.setLast_page_url(url_user_compagnies_search_page+id+"/"+lastPage+"/"+s);
            if (page >= lastPage){

            }else {
                userCompagniePage.setNext_page_url(url_user_compagnies_search_page+id+"/"+(page+1)+"/"+s);
            }

            if (page == 1){
                userCompagniePage.setPrev_page_url(null);
                userCompagniePage.setFrom(1L);
                userCompagniePage.setTo(Long.valueOf(page_size));
            } else {
                userCompagniePage.setPrev_page_url(url_user_compagnies_search_page+id+"/"+(page-1)+"/"+s);
                userCompagniePage.setFrom(1L + (Long.valueOf(page_size)*(page -1)));
                userCompagniePage.setTo(Long.valueOf(page_size) * page);
            }

            userCompagniePage.setPath(path);
            userCompagniePage.setData(userCompagnies);

        }else {
            userCompagniePage.setTotal(0L);
        }

        return userCompagniePage;
    }

    private Sort sortByCreatedDesc(){
        return Sort.by( Sort.Direction.DESC, "createdAt");
    }


}
