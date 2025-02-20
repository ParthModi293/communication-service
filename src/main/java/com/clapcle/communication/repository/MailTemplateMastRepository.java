package com.clapcle.communication.repository;

import com.clapcle.communication.entity.MailTemplateMast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailTemplateMastRepository extends JpaRepository<MailTemplateMast, Integer> {

    Page<MailTemplateMast> findAllByEventId(int eventId, Pageable pageable);

    List<MailTemplateMast> findAllByEventId(int eventId);

    int countAllByEventId(int eventId);

    @Query(value = "SELECT * FROM template_mast t WHERE t.event_id = :eventId AND (t.template_name LIKE %:searchText% OR t.description LIKE %:searchText%)", nativeQuery = true)
    Page<MailTemplateMast> findByEventIdAndTemplateNameLike(@Param("eventId") Integer eventId, @Param("searchText") String searchText, Pageable pageable);

    @Query(value = "SELECT t FROM template_mast t WHERE t.event_id = :eventId AND (t.template_name LIKE %:searchText% OR t.description LIKE %:searchText%)", nativeQuery = true)
    List<MailTemplateMast> findByEventIdAndTemplateNameLike(@Param("eventId") Integer eventId, @Param("searchText") String searchText);

    boolean existsByTemplateNameAndEventIdAndIdNot(String templateName, int eventId, int id);

    boolean existsByTemplateNameAndEventId(String templateName, int eventId);
}
