package es.mbl_cu.hrse.domain.validator;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.application.validator.SearchValidatorImpl;
import es.mbl_cu.hrse.domain.exception.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchValidatorImplTest {

    @InjectMocks
    private SearchValidatorImpl searchValidatorImpl;

    @Test
    void should_validate_success() {
        var search = MotherSearch.buildValid();

        Assertions.assertThatCode(() -> searchValidatorImpl.validate(search))
                .doesNotThrowAnyException();
    }

    @Test
    void should_throw_exception_if_hotelId_is_null() {
        var search = MotherSearch.buildInvalidByRequiredField();

        Assertions.assertThatThrownBy(() -> searchValidatorImpl.validate(search))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The field: [HotelId] is required");
    }

    @Test
    void should_throw_exception_if_checkIn_is_after_checkOut() {
        var search = MotherSearch.buildInvalidByDateRange();

        Assertions.assertThatThrownBy(() -> searchValidatorImpl.validate(search))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range");
    }

    @Test
    void should_throw_exception_if_ages_is_not_valid() {
        var search = MotherSearch.buildInvalidByAges();

        Assertions.assertThatThrownBy(() -> searchValidatorImpl.validate(search))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid ages");
    }

}