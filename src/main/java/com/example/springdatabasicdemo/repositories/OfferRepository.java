package com.example.springdatabasicdemo.repositories;

import com.example.springdatabasicdemo.models.Model;
import com.example.springdatabasicdemo.models.Offer;
import com.example.springdatabasicdemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    List<Offer> findByUser(User user);
}
