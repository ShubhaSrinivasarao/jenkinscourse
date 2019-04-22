package com.sales.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A SalestypeBillDetl.
 */
@Entity
@Table(name = "salestype_bill_detl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalestypeBillDetl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "return_desc")
    private String returnDesc;

    @Column(name = "return_amount", precision=10, scale=2)
    private BigDecimal returnAmount;

    @ManyToOne
    private SalestypeBill salestypeBill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public SalestypeBillDetl returnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
        return this;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public SalestypeBillDetl returnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
        return this;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    public SalestypeBill getSalestypeBill() {
        return salestypeBill;
    }

    public SalestypeBillDetl salestypeBill(SalestypeBill salestypeBill) {
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
        SalestypeBillDetl salestypeBillDetl = (SalestypeBillDetl) o;
        if (salestypeBillDetl.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, salestypeBillDetl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SalestypeBillDetl{" +
            "id=" + id +
            ", returnDesc='" + returnDesc + "'" +
            ", returnAmount='" + returnAmount + "'" +
            '}';
    }
}
