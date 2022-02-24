package com.bluerizon.transport.initializ;

import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.entity.Users;
import com.bluerizon.transport.helper.Helpers;
import com.bluerizon.transport.helper.ReadeJsonFiles;
import com.bluerizon.transport.repository.PaysRepository;
import com.bluerizon.transport.repository.RolesRepository;
import com.bluerizon.transport.repository.UsersRepository;
import com.bluerizon.transport.response.Countries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {

    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;
    private PaysRepository paysRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DbInitializer(UsersRepository usersRepository, RolesRepository rolesRepository,
                         PaysRepository paysRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.paysRepository = paysRepository;
    }

    @Override
    public void run(String... args) throws Exception , ParseException {

        // ---------- cretaion des users------------
        System.out.println(" -- insert Roles");

        Roles role1 = new Roles();
        role1.setIdRole(1);
        role1.setLibelle("SUPER_ADMIN");

        Roles roleSuperAdmin = this.rolesRepository.findByIdRole(1);
        if (roleSuperAdmin == null){
            this.rolesRepository.save(role1);
        }

        Roles role2 = new Roles();
        role2.setIdRole(2);
        role2.setLibelle("ADMIN");

        Roles roleAdmin = this.rolesRepository.findByIdRole(2);
        if (roleAdmin == null){
            this.rolesRepository.save(role2);
        }

        Roles role3 = new Roles();
        role3.setIdRole(3);
        role3.setLibelle("DIRECTEUR");

        Roles roleDirecteur = this.rolesRepository.findByIdRole(3);
        if (roleDirecteur == null){
            this.rolesRepository.save(role3);
        }

        Roles role4 = new Roles();
        role4.setIdRole(4);
        role4.setLibelle("GERANT");

        Roles roleGerant = this.rolesRepository.findByIdRole(4);
        if (roleGerant == null){
            this.rolesRepository.save(role4);
        }

        Roles role5 = new Roles();
        role5.setIdRole(5);
        role5.setLibelle("CAISSIER");

        Roles roleCaissier = this.rolesRepository.findByIdRole(5);
        if (roleCaissier == null){
            this.rolesRepository.save(role5);
        }

        Roles role6 = new Roles();
        role6.setIdRole(6);
        role6.setLibelle("CLIENT");

        Roles roleClient = this.rolesRepository.findByIdRole(6);
        if (roleClient == null){
            this.rolesRepository.save(role6);
        }

        // ---------- cretaion des privileges------------

        System.out.println(" -- insert Super admin");

        Users userSys = new Users();
        userSys.setNom("sysadmin");
        userSys.setUsername("sysadmin");
        userSys.setPrenom("sysadmin");
        userSys.setEmail("sysadmin@gmail.com");
        userSys.setAvatar("avatar.png");
        userSys.setActive(true);
        userSys.setExpirer(Helpers.convertDate("01-01-2100"));
        userSys.setTelephone("90472328");
        userSys.setPassword(passwordEncoder.encode("@sys@#123"));
        userSys.setRole(role1);

        if (!this.usersRepository.findByUsernameAndActiveTrueAndDeletedFalse("sysadmin").isPresent()){
            this.usersRepository.save(userSys);
        }

        System.out.println(" -- insert Countries");

        if (this.paysRepository.countByDeletedFalse() <= 0){
            List<Countries> countries = ReadeJsonFiles.readeCountry();
            if (countries!=null && !countries.isEmpty()){
                List<Pays> paysList = new ArrayList<>();
                for (Countries item :
                        countries) {
                    Pays pays = new Pays(item.getName(), item.getCode(), item.getDial_code());
                    paysList.add(pays);
                }
                this.paysRepository.saveAll(paysList);
            }
        }

        System.out.println(" -- Database has been initialized");

    }

}
