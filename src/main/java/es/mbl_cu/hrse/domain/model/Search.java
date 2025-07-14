package es.mbl_cu.hrse.domain.model;

import java.time.LocalDate;
import java.util.List;

public record Search(String searchId, String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages, Long counter) {

    public static Search of(String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {
        return new Search(null, hotelId, checkIn, checkOut, ages, null);
    }

    public static Search of(Search search, String searchId) {
        return new Search(searchId, search.hotelId(), search.checkIn(), search.checkOut(), search.ages(), null);
    }

}
