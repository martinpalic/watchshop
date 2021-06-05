package com.cleevio.watchshop.api.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import java.util.UUID;

@Data
public class WatchDto {

    private UUID id;
    @NotBlank(message = "Watch tile can NOT be left Blank")
    private String title;
    @PositiveOrZero(message = "Watch price can NOT be Negative")
    private Integer price;
    @NotBlank(message = "Watch description can NOT be left Blank")
    private String description;
    @NotEmpty(message = "Watch fountain can NOT be left Empty")
    private byte[] fountain;
}
