package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.entity.Customer;

public interface CustomerService {
	
	/**
	 * Récupére le Customer à partir de son pseudo
	 * @param pseudo du Customer
	 * @return Customer
	 */
	Customer findCustomerByPseudo(String pseudo);
}
