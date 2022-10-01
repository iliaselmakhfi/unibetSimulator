package com.kindredgroup.unibetlivetest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kindredgroup.unibetlivetest.types.SelectionResult;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "selection")
@Entity
@Data
@Accessors(chain = true)
public class Selection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "current_odd")
	private BigDecimal currentOdd;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private SelectionState state;

	@Column(name = "result")
	@Enumerated(EnumType.STRING)
	private SelectionResult result;

	@JsonIgnore
	@OneToMany(targetEntity = Bet.class, mappedBy = "selection", fetch = FetchType.EAGER)
	private List<Bet> bets = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "market_id")
	@JsonIgnore
	private Market market;

}
