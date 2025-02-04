package org.communication.repository;

import org.communication.entity.TemplateMast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateMastRepository extends JpaRepository<TemplateMast, Integer> {

    Page<TemplateMast> findAllByEventId(int eventId,Pageable pageable);

    List<TemplateMast> findAllByEventId(int eventId);

    int countAllByEventId(int eventId);

    @Query(value = "SELECT * FROM template_mast t WHERE t.event_id = :eventId AND (t.template_name LIKE %:searchText% OR t.description LIKE %:searchText%)",nativeQuery = true)
    Page<TemplateMast>  findByEventIdAndTemplateNameLike(@Param("eventId") Integer eventId, @Param("searchText") String searchText, Pageable pageable);

    @Query(value = "SELECT t FROM template_mast t WHERE t.event_id = :eventId AND (t.template_name LIKE %:searchText% OR t.description LIKE %:searchText%)",nativeQuery = true)
    List<TemplateMast> findByEventIdAndTemplateNameLike(@Param("eventId") Integer eventId, @Param("searchText") String searchText);

    boolean existsByTemplateNameAndEventIdAndIdNot(String templateName, int eventId, int id);

    boolean existsByTemplateNameAndEventId(String templateName, int eventId);
}
