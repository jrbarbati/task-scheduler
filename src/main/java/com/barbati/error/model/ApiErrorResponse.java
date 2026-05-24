package com.barbati.error.model;

public class ApiErrorResponse
{
    private final String message;

    public ApiErrorResponse()
    {
        this(null);
    }

    private ApiErrorResponse(String message)
    {
        this.message = message;
    }

    public static ApiErrorResponse message(String message)
    {
        return new ApiErrorResponse(message);
    }

    public static ApiErrorResponse unexpectedError()
    {
        return new ApiErrorResponse("unexpected error");
    }

    public String getMessage()
    {
        return message;
    }
}
