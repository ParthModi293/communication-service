package com.clapcle.communication.repository;

import com.clapcle.communication.entity.MailTemplateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MailTemplateDetailRepository extends JpaRepository<MailTemplateDetail, Integer> {
    boolean existsByTemplateMastId(int templateMastId);

    MailTemplateDetail findFirstByTemplateMastIdOrderByCreatedAtDesc(int templateMastId);

    @Query(value = """
            SELECT 
                td.subject AS subject, 
                td.body AS body, 
                td.version AS version, 
                tm.priority AS priority,
                td.from_email_id AS fromEmailId, 
                ec.password AS password, 
                ec.port AS port, 
                ec.host AS host,
                 ec.max_limit AS maxLimit
            FROM 
                public.template_details td
            INNER JOIN 
            public.template_mast tm ON td.template_mast_id = tm.id
            INNER JOIN 
                public.email_configuration ec ON td.from_email_id = ec.user_name
            WHERE 
                tm.id = :templateId 
                AND is_active = 'Y'
            ORDER BY 
                td.created_at DESC 
            LIMIT 1
            """, nativeQuery = true)
    Map<String, Object> getMailDetails(@Param("templateId") int templateId, @Param("db") String db);

    List<MailTemplateDetail> findAllByTemplateMastIdOrderByCreatedAtDesc(int templateMastId);
}