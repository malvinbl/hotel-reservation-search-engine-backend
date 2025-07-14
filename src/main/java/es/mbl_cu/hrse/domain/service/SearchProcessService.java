package es.mbl_cu.hrse.domain.service;

import es.mbl_cu.hrse.domain.model.Search;

public interface SearchProcessService {

    Search process(Search search, String searchId);

}
