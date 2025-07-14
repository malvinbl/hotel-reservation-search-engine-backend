package es.mbl_cu.hrse.domain.service;

import es.mbl_cu.hrse.domain.dto.SavedSearchDTO;
import es.mbl_cu.hrse.domain.model.Search;

public interface SearchService {

    SavedSearchDTO search(Search search);
    Search getSearchCount(String searchId);

}
