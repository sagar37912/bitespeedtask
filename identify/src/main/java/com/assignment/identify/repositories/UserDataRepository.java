package com.assignment.identify.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.identify.entities.UserData;

public interface UserDataRepository extends JpaRepository<UserData , Integer> {
    Optional<UserData> findByEmailOrPhoneNumber(String email, String phoneNumber);
}