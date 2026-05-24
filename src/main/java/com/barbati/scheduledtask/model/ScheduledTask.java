package com.barbati.scheduledtask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "scheduled_task")
@SoftDelete(columnName = "deleted", strategy = SoftDeleteType.TIMESTAMP)
public class ScheduledTask
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TASK")
    private String description;

    @Column(nullable = false, length = 100)
    private String cron;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "request_params", columnDefinition = "JSON")
    private Map<String, Object> requestParams;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "request_body", columnDefinition = "JSON")
    private Map<String, Object> requestBody;

    @Column(nullable = false)
    private boolean internal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getTaskId()
    {
        return taskId;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getCron()
    {
        return cron;
    }

    public void setCron(String cron)
    {
        this.cron = cron;
    }

    public Map<String, Object> getRequestParams()
    {
        return requestParams;
    }

    public void setRequestParams(Map<String, Object> requestParams)
    {
        this.requestParams = requestParams;
    }

    public Map<String, Object> getRequestBody()
    {
        return requestBody;
    }

    public void setRequestBody(Map<String, Object> requestBody)
    {
        this.requestBody = requestBody;
    }

    public boolean isInternal()
    {
        return internal;
    }

    public void setInternal(boolean internal)
    {
        this.internal = internal;
    }

    public Instant getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt)
    {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
