package es.mbl_cu.hrse;

import es.mbl_cu.hrse.domain.model.Search;

import java.time.LocalDate;
import java.util.List;

public class MotherSearch {

    public static Search buildValid() {
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);
        var ages = List.of(30, 29, 1, 3);

        return Search.of(hotelId, checkIn, checkOut, ages);
    }

    public static Search buildInvalidByRequiredField() {
        return new Search(null, null, null, null, null, null);
    }

    public static Search buildInvalidByDateRange() {
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 26);
        var checkOut = LocalDate.of(2025, 6, 25);
        var ages = List.of(30, 29, 1, 3);

        return Search.of(hotelId, checkIn, checkOut, ages);
    }

    public static Search buildInvalidByAges() {
        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);
        var ages = List.of(0, 0);

        return Search.of(hotelId, checkIn, checkOut, ages);
    }

}
