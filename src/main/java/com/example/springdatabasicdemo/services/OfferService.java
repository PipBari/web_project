package com.example.springdatabasicdemo.services;

import com.example.springdatabasicdemo.dtos.OfferDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferService<ID>{
    OfferDto add(OfferDto offerDto);

    OfferDto update(OfferDto offerDto);

    List<OfferDto> getAll();

    Optional<OfferDto> findOffer(UUID id);

    OfferDto delete(OfferDto id);
}
