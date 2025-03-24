package org.example.audiobook.repository;

import org.example.audiobook.entity.UserPrenium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPreniumRepository extends JpaRepository<UserPrenium, UUID> {}
