package es.mbl_cu.hrse.application.usecase;

import es.mbl_cu.hrse.domain.dto.SavedSearchDTO;
import es.mbl_cu.hrse.domain.exception.NotFoundException;
import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.domain.repository.SearchRepository;
import es.mbl_cu.hrse.domain.service.SearchIdBuilder;
import es.mbl_cu.hrse.domain.service.SearchProcessService;
import es.mbl_cu.hrse.domain.service.SearchService;
import es.mbl_cu.hrse.domain.validator.SearchValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchServiceUseCase implements SearchService {

    private final SearchRepository searchRepository;
    private final SearchProcessService searchProcessService;
    private final SearchIdBuilder searchIdBuilder;
    private final SearchValidator searchValidator;

    public SearchServiceUseCase(SearchRepository searchRepository,
                                SearchProcessService searchProcessService,
                                SearchIdBuilder searchIdBuilder,
                                SearchValidator searchValidator) {

        this.searchRepository = searchRepository;
        this.searchProcessService = searchProcessService;
        this.searchIdBuilder = searchIdBuilder;
        this.searchValidator = searchValidator;
    }

    @Transactional
    @Override
    public SavedSearchDTO search(Search search) {
        searchValidator.validate(search);

        var searchId = searchIdBuilder.build();
        var searchDB = searchProcessService.process(search, searchId);

        return new SavedSearchDTO(searchDB.searchId());
    }

    @Transactional(readOnly = true)
    @Override
    public Search getSearchCount(String searchId) {
        return searchRepository.findById(searchId)
                .orElseThrow(NotFoundException::new);
    }

}
