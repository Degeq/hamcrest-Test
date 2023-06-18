import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {

    private LocalizationServiceImpl localizationServiceImpl = new LocalizationServiceImpl();

    @ParameterizedTest
    @MethodSource("argsForLocaleReturnTest")
    public void localeReturnTest(Country country, String expected) {
        //act
        String origin = localizationServiceImpl.locale(country);

        //assert
        Assertions.assertEquals(expected, origin);

    }

    public static Stream<Arguments> argsForLocaleReturnTest() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}
