package es.mbl_cu.hrse.application.validator;

import es.mbl_cu.hrse.domain.exception.BadRequestException;
import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.domain.validator.SearchValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SearchValidatorImpl implements SearchValidator {

    @Override
    public void validate(Search search) {
        validateRequired(search);
        validateRange(search.checkIn(), search.checkOut());
        validateAges(search.ages());
    }

    private void validateRequired(Search search) {
        validateRequired(search.hotelId(), "HotelId");
        validateRequired(search.checkIn(), "CheckIn");
        validateRequired(search.checkOut(), "CheckOut");
    }

    private <T> void validateRequired(T field, String fieldName) {
        if (field == null) {
            throw new BadRequestException(getValidateRequiredLogMessage(fieldName));
        }
    }

    private void validateRange(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isAfter(checkOut)) {
            throw new BadRequestException(getValidateRangeLogMessage(checkIn, checkOut));
        }
    }

    private void validateAges(List<Integer> edades) {
        if (edades == null || edades.isEmpty()) {
            return;
        }

        var allMatch = edades.stream()
                .allMatch(ege -> ege > 0);

        if (!allMatch) {
            throw new BadRequestException("Invalid ages");
        }
    }

    private String getValidateRangeLogMessage(LocalDate checkIn, LocalDate checkOut) {
        var sb = new StringBuilder();
        sb.append("Invalid date range checkIn: [")
                .append(checkIn)
                .append("] checkOut: [")
                .append(checkOut)
                .append("].");
        return sb.toString();
    }

    private String getValidateRequiredLogMessage(String fieldName) {
        var sb = new StringBuilder();
        sb.append("The field: [")
                .append(fieldName)
                .append("] is required");
        return sb.toString();
    }

}
