package com.barbati.scheduledtask.controller;

import com.barbati.error.model.ApiErrorResponse;
import com.barbati.scheduledtask.exception.ScheduledTaskException;
import com.barbati.scheduledtask.exception.ScheduledTaskUniquenessException;
import com.barbati.scheduledtask.model.ScheduledTask;
import com.barbati.scheduledtask.service.ScheduledTaskService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Objects;

@Path("/api/v1/scheduled-tasks")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduledTaskController
{
    private static final Logger log = Logger.getLogger(ScheduledTaskController.class);

    private final ScheduledTaskService scheduledTaskService;

    public ScheduledTaskController(ScheduledTaskService scheduledTaskService)
    {
        this.scheduledTaskService = scheduledTaskService;
    }

    @GET
    public Response fetch(@QueryParam("taskId") Long taskId)
    {
        try
        {
            List<ScheduledTask> scheduledTasks = taskId == null
                    ? scheduledTaskService.findAll()
                    : scheduledTaskService.findByTaskId(taskId);

            return Response.ok(scheduledTasks).build();
        }
        catch (Exception e)
        {
            log.errorf(
                    "Caught %s while trying to fetch scheduled tasks: %s",
                    e.getClass().getSimpleName(), e.getMessage(), e
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ApiErrorResponse.unexpectedError()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response fetchById(@PathParam("id") Long id)
    {
        try
        {
            ScheduledTask scheduledTask = scheduledTaskService.findById(id);

            return scheduledTask != null
                    ? Response.ok(scheduledTask).build()
                    : Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e)
        {
            log.errorf(
                    "Caught %s while trying to find scheduled task by id %d: %s",
                    e.getClass().getSimpleName(), id, e.getMessage(), e
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ApiErrorResponse.unexpectedError()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, ScheduledTask scheduledTask)
    {
        try
        {
            if (!Objects.equals(id, scheduledTask.getId()))
                return Response.status(Response.Status.BAD_REQUEST).entity(ApiErrorResponse.message("mismatched ids")).build();

            return Response.ok(scheduledTaskService.update(scheduledTask)).build();
        }
        catch (ScheduledTaskUniquenessException e)
        {
            return Response.status(Response.Status.CONFLICT).entity(ApiErrorResponse.message(e.getMessage())).build();
        }
        catch (ScheduledTaskException e)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ApiErrorResponse.message(e.getMessage())).build();
        }
        catch (Exception e)
        {
            log.errorf(
                    "Caught %s while trying to update scheduled task by id %d: %s",
                    e.getClass().getSimpleName(), id, e.getMessage(), e
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ApiErrorResponse.unexpectedError()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid ScheduledTask scheduledTask)
    {
        try
        {
            return Response.status(Response.Status.CREATED).entity(scheduledTaskService.create(scheduledTask)).build();
        }
        catch (ConstraintViolationException e)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ApiErrorResponse.message("request body did not pass validation checks")).build();
        }
        catch (ScheduledTaskUniquenessException e)
        {
            return Response.status(Response.Status.CONFLICT).entity(ApiErrorResponse.message(e.getMessage())).build();
        }
        catch (Exception e)
        {
            log.errorf(
                    "Caught %s while trying to create scheduled task: %s",
                    e.getClass().getSimpleName(), e.getMessage(), e
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ApiErrorResponse.unexpectedError()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id)
    {
        try
        {
            scheduledTaskService.deleteById(id);
            return Response.noContent().build();
        }
        catch (ScheduledTaskException e)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e)
        {
            log.errorf(
                    "Caught %s while trying to delete scheduled task by id %d: %s",
                    e.getClass().getSimpleName(), id, e.getMessage(), e
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ApiErrorResponse.unexpectedError()).build();
        }
    }
}
