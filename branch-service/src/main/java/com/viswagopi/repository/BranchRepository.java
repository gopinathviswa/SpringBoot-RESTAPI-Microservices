package com.viswagopi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viswagopi.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>{

	Branch findByName(String branch);

}
