package co.agpaytech.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ResponseCodeEnum {
    SUCCESS(1,"Operation Successful."),
    FAILED(0,"Operation Declined."),
    NOT_ALLOWED(2, "Bad Input Parameters.");

    private final int value;
    private final String description;

    ResponseCodeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
