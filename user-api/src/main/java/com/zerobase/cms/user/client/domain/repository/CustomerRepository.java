package com.zerobase.cms.user.client.domain.repository;

import com.zerobase.cms.user.client.domain.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);


}
