package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.dtos.OfferDto;
import com.example.springdatabasicdemo.models.Model;
import com.example.springdatabasicdemo.models.Offer;
import com.example.springdatabasicdemo.models.User;
import com.example.springdatabasicdemo.repositories.ModelRepository;
import com.example.springdatabasicdemo.repositories.OfferRepository;
import com.example.springdatabasicdemo.repositories.UserRepository;
import com.example.springdatabasicdemo.services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class OfferServiceImpl implements OfferService<Integer>{
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, ModelRepository modelRepository, ModelMapper modelMapper){
       this.offerRepository=offerRepository;
       this.userRepository=userRepository;
       this.modelRepository=modelRepository;
       this.modelMapper=modelMapper;
    }

    @Override
    public OfferDto add(OfferDto offerDto) {
        Offer offer = modelMapper.map(offerDto, Offer.class);
        offer.setCreated(LocalDate.now());
        offer.setYear(LocalDate.now());
        if (offerDto.getModel() != null && offerDto.getModel().getId() != null) {
            Model model = modelRepository.findById(offerDto.getModel().getId()).orElse(null);
            offer.setModel(model);
        }
        if (offerDto.getUser() != null && offerDto.getUser().getId() != null) {
            User user = userRepository.findById(offerDto.getUser().getId()).orElse(null);
            offer.setUser(user);
        }
        Offer savedOffer = offerRepository.save(offer);
        return modelMapper.map(savedOffer, OfferDto.class);
    }
    @Override
    @Cacheable(value = "offers", key = "#offerDto.id")
    public OfferDto update(OfferDto offerDto) {
        if (offerDto.getId() == null) {
            throw new IllegalArgumentException("Offer ID cannot be null for update");
        }
        Offer offerToUpdate = offerRepository.findById(offerDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + offerDto.getId()));

        modelMapper.map(offerDto, offerToUpdate);
        if (offerDto.getModel() != null && offerDto.getModel().getId() != null) {
            Model model = modelRepository.findById(offerDto.getModel().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid model Id:" + offerDto.getModel().getId()));
            offerToUpdate.setModel(model);
        }
        if (offerDto.getUser() != null && offerDto.getUser().getId() != null) {
            User user = userRepository.findById(offerDto.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + offerDto.getUser().getId()));
            offerToUpdate.setUser(user);
        }
        offerToUpdate.setModified(LocalDate.now());
        Offer updatedOffer = offerRepository.save(offerToUpdate);
        return modelMapper.map(updatedOffer, OfferDto.class);
    }

    @Override
    @Cacheable("offers")
    public List<OfferDto> getAll() {
        return offerRepository.findAll().stream().map((c) -> modelMapper.map(c, OfferDto.class)).collect(Collectors.toList());
    }
    @Override
    @Cacheable(value = "offers", key = "#id")
    public Optional<OfferDto> findOffer(UUID id){
        return offerRepository.findById(id)
                .map(offer -> modelMapper.map(offer, OfferDto.class));
    }

    @Override
    @Cacheable(value = "offers", key = "#id.id")
    public OfferDto delete(UUID id) {
        offerRepository.deleteById(id);
        return null;
    }
}
