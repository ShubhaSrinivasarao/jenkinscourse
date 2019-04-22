package com.sales.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SalestypeAmount.
 */
@Entity
@Table(name = "salestype_amount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalestypeAmount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sales_type_id")
    private Integer salesTypeId;

    @Column(name = "total_amount", precision=10, scale=2)
    private BigDecimal totalAmount;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @ManyToOne
    private SalestypeBill salestypeBill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalesTypeId() {
        return salesTypeId;
    }

    public SalestypeAmount salesTypeId(Integer salesTypeId) {
        this.salesTypeId = salesTypeId;
        return this;
    }

    public void setSalesTypeId(Integer salesTypeId) {
        this.salesTypeId = salesTypeId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public SalestypeAmount totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public SalestypeAmount saleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
        return this;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public SalestypeBill getSalestypeBill() {
        return salestypeBill;
    }

    public SalestypeAmount salestypeBill(SalestypeBill salestypeBill) {
        this.salestypeBill = salestypeBill;
        return this;
    }

    public void setSalestypeBill(SalestypeBill salestypeBill) {
        this.salestypeBill = salestypeBill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SalestypeAmount salestypeAmount = (SalestypeAmount) o;
        if (salestypeAmount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, salestypeAmount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalestypeAmount{" +
            "id=" + id +
            ", salesTypeId='" + salesTypeId + "'" +
            ", totalAmount='" + totalAmount + "'" +
            ", saleDate='" + saleDate + "'" +
            '}';
    }
}
