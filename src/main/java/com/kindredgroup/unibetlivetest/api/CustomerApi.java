package com.kindredgroup.unibetlivetest.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.service.CustomerService;
import com.kindredgroup.unibetlivetest.utils.Urls;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Urls.BASE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerApi {

    private final CustomerService customerService;

    @GetMapping(Urls.CURRENT_CUSTOMER)
    public Customer fetchCustomer() {
        return customerService.findCustomerByPseudo("unibest");
    }


}
