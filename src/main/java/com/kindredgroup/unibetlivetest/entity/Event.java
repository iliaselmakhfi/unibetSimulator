package com.kindredgroup.unibetlivetest.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Table(name = "Event")
@Entity
@Data
@Accessors(chain = true)
public class Event {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "start_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

	@JsonIgnore
	@OneToMany(targetEntity = Market.class, mappedBy = "event", fetch = FetchType.LAZY)
	private List<Market> markets = new ArrayList<>();
}
