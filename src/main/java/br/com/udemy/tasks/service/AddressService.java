package br.com.udemy.tasks.service;

import br.com.udemy.tasks.client.ViaCepClient;
import br.com.udemy.tasks.exception.CepNotFoundException;
import br.com.udemy.tasks.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AddressService {

    private static final Logger LOG = LoggerFactory.getLogger(AddressService.class);

    private final ViaCepClient client;

    public AddressService(ViaCepClient client){
        this.client = client;
    }

    public Mono<Address> getAddress(String zipCode){
        return Mono.just(zipCode)
                .doOnNext(it-> LOG.info("Getting address to Zipcode {}", zipCode))
                .flatMap(client::getAddress)
                .doOnError(it-> Mono.error(CepNotFoundException::new));
    }

}
