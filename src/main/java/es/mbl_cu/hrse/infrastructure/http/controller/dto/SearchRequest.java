package es.mbl_cu.hrse.infrastructure.http.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record SearchRequest(
        String hotelId,

        @Schema(type = "string", pattern = "dd/MM/yyyy", example = "25/06/2025")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @Schema(type = "string", pattern = "dd/MM/yyyy", example = "25/06/2025")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        List<Integer> ages) {
}
