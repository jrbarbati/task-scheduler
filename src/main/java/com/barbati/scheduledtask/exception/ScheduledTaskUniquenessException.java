package com.barbati.scheduledtask.exception;

public class ScheduledTaskUniquenessException extends ScheduledTaskException
{
    public ScheduledTaskUniquenessException(String message)
    {
        this(message, null);
    }

    public ScheduledTaskUniquenessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
