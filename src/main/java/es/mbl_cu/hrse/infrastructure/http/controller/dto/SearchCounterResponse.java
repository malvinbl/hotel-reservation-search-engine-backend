package es.mbl_cu.hrse.infrastructure.http.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record SearchCounterResponse(
        String hotelId,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,

        List<Integer> ages) {
}
