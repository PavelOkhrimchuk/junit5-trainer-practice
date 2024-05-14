package ohrim.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalDateFormatterTest {

    @Test
    void format() {
        String date = "2021-11-28"; //given
        var actualResult = LocalDateFormatter.format(date); //when

        assertThat(actualResult).isEqualTo(LocalDate.of(2021,11,28)); //then
    }

    @Test
    void shouldThrowExceptionIfDateInvalid() {
        String date = "2021-11-28 12:25";
        assertThrows(DateTimeParseException.class, () -> LocalDateFormatter.format(date));

    }

    static Stream<Arguments> getValidationArguments() {
        return Stream.of(
                Arguments.of("2020-11-28", true),
                Arguments.of("01-01-2001", false),
                Arguments.of("01-02-2003 12:28", false),
                Arguments.of(null,false)
        );
    }

    @ParameterizedTest
    @MethodSource("getValidationArguments")
    void testIsValidWithDates(String date, boolean expectResult) {
        var actualResult = LocalDateFormatter.isValid(date);
        assertEquals(expectResult, actualResult);

    }

}