package es.mbl_cu.hrse.infrastructure.http.controller.mapper;

import es.mbl_cu.hrse.domain.dto.SavedSearchDTO;
import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.domain.vo.Constant;
import es.mbl_cu.hrse.infrastructure.http.controller.dto.SearchRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SearchMapperTest {

    private final SearchMapper searchMapper = new SearchMapper();

    @Test
    void should_map_searchRequest_to_search() {
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);

        var request = new SearchRequest(hotelId, checkIn, checkOut, List.of(30));

        var response = searchMapper.toSearch(request);

        assertAll(
                () -> assertThat(response.hotelId()).isEqualTo(hotelId),
                () -> assertThat(response.checkIn()).isEqualTo(checkIn),
                () -> assertThat(response.checkOut()).isEqualTo(checkOut),
                () -> assertThat(response.ages()).containsExactly(30)
        );
    }

    @Test
    void should_map_savedSearchDTO_to_searchResponse() {
        var searchId = "searchid1";
        var savedSearchDTO = new SavedSearchDTO(searchId);

        var response = searchMapper.toSearchResponse(savedSearchDTO);

        assertThat(response.searchId()).isEqualTo(searchId);
    }

    @Test
    void should_map_search_to_counterResponse() {
        var searchId = "searchid1";
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);

        var search = new Search(
                searchId,
                hotelId,
                checkIn,
                checkOut,
                List.of(18, 35),
                Constant.COUNTER_INCREMENT
        );

        var response = searchMapper.toCounterResponse(search);

        assertAll(
                () -> assertThat(response.id()).isEqualTo(searchId),
                () -> assertThat(response.search().hotelId()).isEqualTo(hotelId),
                () -> assertThat(response.search().checkIn()).isEqualTo(checkIn),
                () -> assertThat(response.search().checkOut()).isEqualTo(checkOut),
                () -> assertThat(response.count()).isEqualTo(Constant.COUNTER_INCREMENT)
        );
    }

}
