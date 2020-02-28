package com.viswagopi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viswagopi.model.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Long>{

	Town findByName(String id);

}
