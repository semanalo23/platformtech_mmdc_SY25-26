package com.example.finmarkprojectfiner.catalog.repository;

import com.example.finmarkprojectfiner.catalog.model.FinmarkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinmarkOrderRepo extends JpaRepository <FinmarkOrder, Long>{

}
