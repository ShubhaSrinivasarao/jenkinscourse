package com.sales.repository;

import com.sales.domain.SalestypeBill;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalestypeBill entity.
 */
@SuppressWarnings("unused")
public interface SalestypeBillRepository extends JpaRepository<SalestypeBill,Long> {

}
