package com.sales.repository;

import com.sales.domain.SalestypeBillDetl;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalestypeBillDetl entity.
 */
@SuppressWarnings("unused")
public interface SalestypeBillDetlRepository extends JpaRepository<SalestypeBillDetl,Long> {

}
