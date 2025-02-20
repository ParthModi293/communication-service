package com.clapcle.communication.repository;

import com.clapcle.communication.entity.MailEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailEventRepository extends JpaRepository<MailEvent, Integer> {
}