package es.mbl_cu.hrse.infrastructure.persistence.mapper;

import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.infrastructure.persistence.entity.SearchEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchEntityMapper {

    public SearchEntity toSearchEntity(Search search) {
        return new SearchEntity(search.searchId(), search.hotelId(), search.checkIn(), search.checkOut(), search.ages());
    }

    public Search toSearch(SearchEntity searchEntity) {
        return new Search(
                searchEntity.getId(),
                searchEntity.getHotelId(),
                searchEntity.getCheckIn(),
                searchEntity.getCheckOut(),
                searchEntity.getAges(),
                searchEntity.getCounter()
        );
    }

}
