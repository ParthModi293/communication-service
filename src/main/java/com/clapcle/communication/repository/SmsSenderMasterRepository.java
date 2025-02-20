package com.clapcle.communication.repository;


import com.clapcle.communication.entity.SmsSenderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsSenderMasterRepository extends JpaRepository<SmsSenderMaster, Integer> {
    boolean existsBySenderCode(String senderCode);
}