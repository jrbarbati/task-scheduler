package com.barbati.scheduledtask.exception;

public class ScheduledTaskException extends Exception
{
    public ScheduledTaskException(String message)
    {
        this(message, null);
    }

    public ScheduledTaskException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
