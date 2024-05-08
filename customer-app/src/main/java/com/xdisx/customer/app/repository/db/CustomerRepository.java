package com.xdisx.customer.app.repository.db;

import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, BigInteger>, JpaSpecificationExecutor<CustomerEntity> {}
