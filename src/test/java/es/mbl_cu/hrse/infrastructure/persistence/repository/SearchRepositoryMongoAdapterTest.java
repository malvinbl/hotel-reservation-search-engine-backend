package es.mbl_cu.hrse.infrastructure.persistence.repository;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.domain.vo.Constant;
import es.mbl_cu.hrse.infrastructure.persistence.entity.SearchEntity;
import es.mbl_cu.hrse.infrastructure.persistence.mapper.SearchEntityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchRepositoryMongoAdapterTest {

    @Mock
    private MongoSearchRepository mongoSearchRepository;
    @Mock
    private SearchEntityMapper searchEntityMapper;

    @InjectMocks
    private SearchRepositoryMongoAdapter searchRepositoryMongoAdapter;

    @Test
    void should_return_search_when_findById_returns_entity() {
        var search = MotherSearch.buildValid();
        var searchId = "searchid1";
        var searchEntity = new SearchEntity();

        when(mongoSearchRepository.findById(searchId)).thenReturn(Optional.of(searchEntity));
        when(searchEntityMapper.toSearch(searchEntity)).thenReturn(search);

        var result = searchRepositoryMongoAdapter.findById(searchId);

        assertThat(result).isPresent().contains(search);
    }

    @Test
    void should_return_empty_when_findById_returns_empty() {
        var searchId = "no-exists";

        when(mongoSearchRepository.findById(searchId)).thenReturn(Optional.empty());

        var result = searchRepositoryMongoAdapter.findById(searchId);

        assertThat(result).isEmpty();
    }

    @Test
    void should_return_similar_search_when_entity_found() {
        var search = MotherSearch.buildValid();
        var searchEntity = new SearchEntity();

        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);

        when(mongoSearchRepository.findSimilar(hotelId, checkIn, checkOut)).thenReturn(Optional.of(searchEntity));
        when(searchEntityMapper.toSearch(searchEntity)).thenReturn(search);

        var result = searchRepositoryMongoAdapter.findSimilar(hotelId, checkIn, checkOut);

        assertThat(result).isPresent().contains(search);
    }

    @Test
    void should_save_with_counter_properly() {
        var search = MotherSearch.buildValid();
        var searchEntity = new SearchEntity();

        when(searchEntityMapper.toSearchEntity(search)).thenReturn(searchEntity);

        searchRepositoryMongoAdapter.saveWithCounter(search, Constant.COUNTER_INCREMENT);

        Assertions.assertAll(
                () -> assertThat(searchEntity.getCounter()).isEqualTo(Constant.COUNTER_INCREMENT),
                () -> verify(mongoSearchRepository).save(searchEntity)
        );
    }

}
