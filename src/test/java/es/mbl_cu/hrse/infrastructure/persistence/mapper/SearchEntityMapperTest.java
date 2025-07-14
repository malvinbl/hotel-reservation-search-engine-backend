package es.mbl_cu.hrse.infrastructure.persistence.mapper;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.domain.vo.Constant;
import es.mbl_cu.hrse.infrastructure.persistence.entity.SearchEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.*;

class SearchEntityMapperTest {

    private final SearchEntityMapper searchEntityMapper = new SearchEntityMapper();

    @Test
    void should_map_search_to_searchEntity() {
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);
        var ages = List.of(30, 29, 1, 3);

        var search = MotherSearch.buildValid();

        var entity = searchEntityMapper.toSearchEntity(search);

        assertAll(
                () -> assertThat(entity.getHotelId()).isEqualTo(hotelId),
                () -> assertThat(entity.getCheckIn()).isEqualTo(checkIn),
                () -> assertThat(entity.getCheckOut()).isEqualTo(checkOut),
                () -> assertThatList(entity.getAges()).isEqualTo(ages)
        );
    }

    @Test
    void should_map_searchEntity_to_search() {
        var searchId = "searchid1";
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);
        var ages = List.of(30, 29, 1, 3);

        var entity = new SearchEntity();
        entity.setId(searchId);
        entity.setHotelId(hotelId);
        entity.setCheckIn(checkIn);
        entity.setCheckOut(checkOut);
        entity.setAges(ages);
        entity.setCounter(Constant.COUNTER_INCREMENT);

        var search = searchEntityMapper.toSearch(entity);

        assertAll(
                () -> assertThat(search.searchId()).isEqualTo(searchId),
                () -> assertThat(entity.getHotelId()).isEqualTo(hotelId),
                () -> assertThat(entity.getCheckIn()).isEqualTo(checkIn),
                () -> assertThat(entity.getCheckOut()).isEqualTo(checkOut),
                () -> assertThatList(entity.getAges()).isEqualTo(ages),
                () -> assertThat(search.counter()).isEqualTo(Constant.COUNTER_INCREMENT)
        );
    }

}
