package es.mbl_cu.hrse.domain.repository;

public interface CounterIncrementorMongoOperations {

    void incCounterBySearchId(String searchId, Long value);

}
