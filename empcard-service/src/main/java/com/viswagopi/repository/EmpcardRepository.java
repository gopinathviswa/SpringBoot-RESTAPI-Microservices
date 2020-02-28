package com.viswagopi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viswagopi.model.Empcard;

@Repository
public interface EmpcardRepository extends JpaRepository<Empcard, Long>{
	List<Empcard> findByNumberNotInOrderByNumberAsc(Collection<String> cards);
}
