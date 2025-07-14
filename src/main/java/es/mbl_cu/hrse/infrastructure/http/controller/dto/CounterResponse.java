package es.mbl_cu.hrse.infrastructure.http.controller.dto;

public record CounterResponse(String id, SearchCounterResponse search, Long count) {
}
