package com.barbati.scheduledtask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.*;
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

    @NotNull
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TASK")
    private String description;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String cron;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "request_params", columnDefinition = "JSON")
    private Map<String, Object> requestParams;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "request_body", columnDefinition = "JSON")
    private Map<String, Object> requestBody;

    @NotNull
    @Column(nullable = false)
    private Boolean internal;

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

    public Boolean getInternal()
    {
        return internal;
    }

    public void setInternal(Boolean internal)
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
