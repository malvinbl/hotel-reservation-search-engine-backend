package es.mbl_cu.hrse.domain.repository;

import es.mbl_cu.hrse.domain.model.Search;

import java.time.LocalDate;
import java.util.Optional;

public interface SearchRepository {

    Optional<Search> findById(String searchId);
    Optional<Search> findSimilar(String hotelId, LocalDate checkIn, LocalDate checkOut);
    void saveWithCounter(Search search, Long counter);

}
