package es.mbl_cu.hrse.application.service;

import es.mbl_cu.hrse.domain.service.SearchIdBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SearchIdBuilderImpl implements SearchIdBuilder {

    @Override
    public String build() {
        return UUID.randomUUID().toString();
    }

}
