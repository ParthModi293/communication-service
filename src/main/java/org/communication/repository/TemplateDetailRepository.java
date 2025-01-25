package org.communication.repository;

import org.communication.dto.TemplateDto;
import org.communication.entity.TemplateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TemplateDetailRepository extends JpaRepository<TemplateDetail, Integer> {
    boolean existsByTemplateMastId(int templateMastId);

    TemplateDetail findFirstByTemplateMastIdOrderByCreatedAtDesc(int templateMastId);

    @Query(value = """
            SELECT 
                td.subject AS subject, 
                td.body AS body, 
                td.version AS version, 
                tm.priority AS priority,
                td.from_email_id AS fromEmailId, 
                ec.password AS password, 
                ec.port AS port, 
                ec.host AS host
            FROM 
                :db.template_details td
            INNER JOIN 
                :db.template_mast tm ON td.template_mast_id = tm.id
            INNER JOIN 
                :db.email_configuration ec ON td.from_email_id = ec.user_name
            WHERE 
                tm.template_name = :templateName 
                AND is_active = 'Y'
            ORDER BY 
                td.created_at DESC 
            LIMIT 1
            """, nativeQuery = true)
    Map<String, Object> getMailDetails(@Param("templateName") String templateName, @Param("db") String db);
}