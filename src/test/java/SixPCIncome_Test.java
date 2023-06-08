import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import taxes.FifteenPCProfit;
import taxes.SixPCIncome;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.stream.Stream;

public class SixPCIncome_Test {
    private static Company company;

    @BeforeEach
    public void creation() {
        company = new Company("ООО Ромашка", new SixPCIncome());
    }

    @ParameterizedTest
    @MethodSource("argumentsFor6PCI")
    public void payTaxes6PCIncome(double[] budget, Matcher expected) {

        //act
        Arrays.stream(budget).forEach((x) -> company.shiftMoney(x));

        //assert
        assertThat(company.payTaxes(), expected);


    }

    public static Stream<Arguments> argumentsFor6PCI() {
        return Stream.of(
                Arguments.of(new double[] {144.432, 42354, 156, -80}, equalTo(2559.26592)),
                Arguments.of(new double[]{-144.432, -42354, -156, 80}, equalTo(4.8))
        );
    }
}
