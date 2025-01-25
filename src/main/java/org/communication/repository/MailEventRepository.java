package org.communication.repository;

import org.communication.entity.MailEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailEventRepository extends JpaRepository<MailEvent, Integer> {
}