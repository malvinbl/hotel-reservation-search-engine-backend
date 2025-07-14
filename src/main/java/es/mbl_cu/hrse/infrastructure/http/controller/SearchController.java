package es.mbl_cu.hrse.infrastructure.http.controller;

import es.mbl_cu.hrse.domain.service.SearchService;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.CounterResponse;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchRequest;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchResponse;
import es.mbl_cu.hrse.infrastructure.http.controller.mapper.SearchMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Search Controller")
@RestController
public class SearchController {

    private final SearchMapper searchMapper;
    private final SearchService searchService;

    public SearchController(SearchMapper searchMapper, SearchService searchService) {
        this.searchMapper = searchMapper;
        this.searchService = searchService;
    }

    @Operation(summary = "Search by filters and save the search")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/search")
    public ResponseEntity<SearchResponse> search(
        @org.springframework.web.bind.annotation.RequestBody SearchRequest searchRequest) {

        var search = searchMapper.toSearch(searchRequest);
        var saveSearchDTO = searchService.search(search);

        return ResponseEntity.ok(searchMapper.toSearchResponse(saveSearchDTO));
    }

    @Operation(summary = "Get number of searches")
    @GetMapping(value = "/count")
    public ResponseEntity<CounterResponse> getSearchCount(
        @RequestParam String searchId) {

        var search = searchService.getSearchCount(searchId);
        return ResponseEntity.ok(searchMapper.toCounterResponse(search));
    }

}
