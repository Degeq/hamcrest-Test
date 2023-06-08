import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import taxes.FifteenPCProfit;

import java.util.Arrays;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class FifteenPCProfit_Test {

    private Company company;

    @BeforeEach
    public void creation() {
        company = new Company("ООО Ромашка", new FifteenPCProfit());
    }


    @Test
    public void havingProperty() {
        assertThat(company, hasProperty("taxSystem"));
    }

    @ParameterizedTest
    @MethodSource("argumentsFor15PCP")
    public void payTaxes15PCProfit(double[] budget, Matcher expected) {

        //act
        Arrays.stream(budget).forEach((x) -> company.shiftMoney(x));

       //assert
        assertThat(company.payTaxes(), expected);


    }

    public static Stream<Arguments> argumentsFor15PCP() {
        return Stream.of(
                Arguments.of(new double[]{144.432, 42354, 156, -80}, equalTo(6386.1648)),
                Arguments.of(new double[]{-144.432, -42354, -156, 80}, equalTo(0))
        );
    }
}
