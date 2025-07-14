package es.mbl_cu.hrse.application;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.application.usecase.SearchServiceUseCase;
import es.mbl_cu.hrse.domain.dto.SavedSearchDTO;
import es.mbl_cu.hrse.domain.exception.NotFoundException;
import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.domain.repository.SearchRepository;
import es.mbl_cu.hrse.domain.service.SearchIdBuilder;
import es.mbl_cu.hrse.domain.service.SearchProcessService;
import es.mbl_cu.hrse.domain.validator.SearchValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SearchServiceUseCaseTest {

    @Mock
    private SearchRepository searchRepository;
    @Mock
    private SearchProcessService searchProcessService;
    @Mock
    private SearchIdBuilder searchIdBuilder;
    @Mock
    private SearchValidator searchValidator;

    @InjectMocks
    private SearchServiceUseCase searchServiceUseCase;

    @Test
    void should_search_by_filters_and_save_search() {
        var search = MotherSearch.buildValid();
        var searchId = "searchid1";
        var searchDB = Search.of(search, searchId);

        var expectedSearchResponse = new SavedSearchDTO(searchId);

        Mockito.when(searchIdBuilder.build()).thenReturn(searchId);
        Mockito.when(searchProcessService.process(search, searchId)).thenReturn(searchDB);

        var actualSearchResponse = searchServiceUseCase.search(search);

        Assertions.assertThat(actualSearchResponse).usingRecursiveComparison().isEqualTo(expectedSearchResponse);
    }

    @Test
    void should_get_counterResponse_given_a_searchId() {
        var search = MotherSearch.buildValid();
        var searchId = "searchid1";
        var searchDB = Search.of(search, searchId);

        Mockito.when(searchRepository.findById(searchId)).thenReturn(Optional.of(searchDB));

        var actualCounterResponse = searchServiceUseCase.getSearchCount(searchId);

        Assertions.assertThat(actualCounterResponse).usingRecursiveComparison().isEqualTo(searchDB);
    }

    @Test
    void should_throw_exception_when_search_not_found() {
        var searchId = "searchid1";

        Mockito.when(searchRepository.findById(searchId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> searchServiceUseCase.getSearchCount(searchId))
                .isInstanceOf(NotFoundException.class);
    }

}