package es.mbl_cu.hrse.infrastructure.persistence.repository;

import es.mbl_cu.hrse.infrastructure.persistence.entity.SearchEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MongoSearchRepository extends MongoRepository<SearchEntity, String> {

    @Query("{ 'hotelId' : ?0, 'checkIn' : ?1, 'checkOut' : ?2}")
    Optional<SearchEntity> findSimilar(String hotelId, LocalDate checkIn, LocalDate checkOut);

}
