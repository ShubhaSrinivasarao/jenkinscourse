package com.sales.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SalestypeBill.
 */
@Entity
@Table(name = "salestype_bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalestypeBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "alloted_amount", precision=10, scale=2)
    private BigDecimal allotedAmount;

    @OneToMany(mappedBy = "salestypeBill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SalestypeAmount> salestypeAmounts = new HashSet<>();

    @OneToMany(mappedBy = "salestypeBill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SalestypeBillDetl> salestypeBillDetls = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public SalestypeBill billNo(String billNo) {
        this.billNo = billNo;
        return this;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public BigDecimal getAllotedAmount() {
        return allotedAmount;
    }

    public SalestypeBill allotedAmount(BigDecimal allotedAmount) {
        this.allotedAmount = allotedAmount;
        return this;
    }

    public void setAllotedAmount(BigDecimal allotedAmount) {
        this.allotedAmount = allotedAmount;
    }

    public Set<SalestypeAmount> getSalestypeAmounts() {
        return salestypeAmounts;
    }

    public SalestypeBill salestypeAmounts(Set<SalestypeAmount> salestypeAmounts) {
        this.salestypeAmounts = salestypeAmounts;
        return this;
    }

    public SalestypeBill addSalestypeAmount(SalestypeAmount salestypeAmount) {
        salestypeAmounts.add(salestypeAmount);
        salestypeAmount.setSalestypeBill(this);
        return this;
    }

    public SalestypeBill removeSalestypeAmount(SalestypeAmount salestypeAmount) {
        salestypeAmounts.remove(salestypeAmount);
        salestypeAmount.setSalestypeBill(null);
        return this;
    }

    public void setSalestypeAmounts(Set<SalestypeAmount> salestypeAmounts) {
        this.salestypeAmounts = salestypeAmounts;
    }

    public Set<SalestypeBillDetl> getSalestypeBillDetls() {
        return salestypeBillDetls;
    }

    public SalestypeBill salestypeBillDetls(Set<SalestypeBillDetl> salestypeBillDetls) {
        this.salestypeBillDetls = salestypeBillDetls;
        return this;
    }

    public SalestypeBill addSalestypeBillDetl(SalestypeBillDetl salestypeBillDetl) {
        salestypeBillDetls.add(salestypeBillDetl);
        salestypeBillDetl.setSalestypeBill(this);
        return this;
    }

    public SalestypeBill removeSalestypeBillDetl(SalestypeBillDetl salestypeBillDetl) {
        salestypeBillDetls.remove(salestypeBillDetl);
        salestypeBillDetl.setSalestypeBill(null);
        return this;
    }

    public void setSalestypeBillDetls(Set<SalestypeBillDetl> salestypeBillDetls) {
        this.salestypeBillDetls = salestypeBillDetls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SalestypeBill salestypeBill = (SalestypeBill) o;
        if (salestypeBill.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, salestypeBill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalestypeBill{" +
            "id=" + id +
            ", billNo='" + billNo + "'" +
            ", allotedAmount='" + allotedAmount + "'" +
            '}';
    }
}
