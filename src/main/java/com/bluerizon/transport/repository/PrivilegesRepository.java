package com.bluerizon.transport.repository;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Privileges;
import com.bluerizon.transport.entity.TypeBus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegesRepository extends JpaRepository<Privileges, Integer> {

    Privileges findByIdPrivilege(final Integer id);

}
