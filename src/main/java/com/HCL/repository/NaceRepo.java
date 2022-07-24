package com.HCL.repository;


import com.HCL.entity.NACE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaceRepo extends JpaRepository<NACE, Long> {


    NACE findByOrderId(Long orderId);


}
