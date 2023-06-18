
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.*;
import ru.netology.geo.*;

import java.util.stream.Stream;


public class GeoServiceImplTest {

    private GeoServiceImpl geoService = new GeoServiceImpl();

    @ParameterizedTest
    @MethodSource("argsForLocateByIp")
    public void LocateByIp(String ip, Location expected) {
        //act
        System.out.println(ip);
        Location actual = geoService.byIp(ip);

        //assert
        Assertions.assertEquals(expected.getCountry(), actual.getCountry());
        Assertions.assertEquals(expected.getBuiling(), actual.getBuiling());
        Assertions.assertEquals(expected.getCity(), actual.getCity());
        Assertions.assertEquals(expected.getStreet(), actual.getStreet());

    }

    public static Stream<Arguments> argsForLocateByIp() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue",
                                32)),
                Arguments.of("172.32.42.22", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.109.149", new Location("New York", Country.USA, null,
                                0))

        );
    }

    @Test
    public void testingThrowErrorInCoordinate() {
        //arrange
        double latitude = 12.1;
        double longitude = 12.1;

        //act
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(latitude, longitude), "Not implemented");
    }


}
