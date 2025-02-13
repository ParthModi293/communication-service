package org.communication.repository;


import org.communication.entity.SmsSenderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsSenderMasterRepository extends JpaRepository<SmsSenderMaster, Integer> {
    boolean existsBySenderCode(String senderCode);
}