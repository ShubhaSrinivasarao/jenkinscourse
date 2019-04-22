package com.sales.repository;

import com.sales.domain.SalestypeAmount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalestypeAmount entity.
 */
@SuppressWarnings("unused")
public interface SalestypeAmountRepository extends JpaRepository<SalestypeAmount,Long> {

}
