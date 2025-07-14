package es.mbl_cu.hrse.infrastructure.http.controller.mapper;

import es.mbl_cu.hrse.domain.dto.SavedSearchDTO;
import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.CounterResponse;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchCounterResponse;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchRequest;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchResponse;
import org.springframework.stereotype.Component;

@Component
public class SearchMapper {

    public Search toSearch(SearchRequest searchRequest) {
        return Search.of(searchRequest.hotelId(), searchRequest.checkIn(), searchRequest.checkOut(), searchRequest.ages());
    }

    public SearchResponse toSearchResponse(SavedSearchDTO savedSearchDTO) {
        return new SearchResponse(savedSearchDTO.searchId());
    }

    public CounterResponse toCounterResponse(Search search) {
        var searchCounterResponse = new SearchCounterResponse(search.hotelId(), search.checkIn(), search.checkOut(), search.ages());
        return new CounterResponse(search.searchId(), searchCounterResponse, search.counter());
    }

}
