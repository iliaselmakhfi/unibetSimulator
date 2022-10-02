package com.kindredgroup.unibetlivetest.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Table(name = "customer")
@Entity
@Data
@Accessors(chain = true)
public class Customer {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "balance")
    private BigDecimal balance;

	@JsonIgnore
	@OneToMany(targetEntity = Bet.class, mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Bet> bets = new ArrayList<>();

}
