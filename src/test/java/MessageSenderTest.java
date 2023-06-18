import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

public class MessageSenderTest {

    private MessageSenderImpl messanger;
    private GeoServiceImpl geoService;
    private LocalizationServiceImpl localizationService;

    /*
    Исользовать Mockito в данном случае имеет смысл для того, чтобы с помощью метода verify убедиться в корректной
    логике работы метода send класса MessageSenderImpl, поскольку возвращает данный класс напрямую объект, генерируемый
    интегрируемым классом - а их работа проверяется в других тестах
     */

    @BeforeEach
    public void messangerCreation() {
        geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.anyString()))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Mockito.any(Country.class)))
                .thenReturn("Добро пожаловать");

        messanger = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
   public  void testingMessageSenderExistingIP() {
        //arrange

        Map<String,String> headers = new HashMap<>();
        headers.put(IP_ADDRESS_HEADER, "192.0.020.02");

        //act
        messanger.send(headers);

        //assert
        Mockito.verify(localizationService, Mockito.times(2)).locale(Mockito.any(Country.class));

    }

    /*
    В классе не предусмотрен случай передачи в качестве значения с ключом IP_ADRESS_HEADER объекта null
    Согласно документации класса String метод valueOf преобразует значение null в строку "null", поэтому оператор
    str != null не вернет значения true, необходимо заменить на equals
     */

    @Test
    public void testingMessageSenderNullIP() {
        //arrange
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);

        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        messanger = new MessageSenderImpl(geoService, localizationService);
        Map<String,String> headers = new HashMap<>();
        headers.put(IP_ADDRESS_HEADER, null);

        //act
        messanger.send(headers);

        //assert
        Mockito.verify(localizationService, Mockito.times(1)).locale(Mockito.any(Country.class));

    }

    @Test
    public void testingMessageSenderEmptyIP() {
        //arrange
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);

        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        messanger = new MessageSenderImpl(geoService, localizationService);
        Map<String,String> headers = new HashMap<>();
        headers.put(IP_ADDRESS_HEADER, "");

        //act
        messanger.send(headers);

        //assert
        Mockito.verify(localizationService, Mockito.times(1)).locale(Mockito.any(Country.class));

    }

}
