import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.jupiter.params.provider.MethodSource;
import taxes.FifteenPCProfit;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.Arrays;
import java.util.stream.Stream;

public class Company_Test {

    private Company company;

    @BeforeEach
    public void creationBeforeEach() {
        company = new Company("ООО Ромашка", null);
    }


    @ParameterizedTest
    @MethodSource("argumentsForShiftMoneyIncomeTest")
    public void shiftMoneyIncomeTest(double[] moneySums, double expected) {

        //act
        for (double i : moneySums) {
            company.shiftMoney(i);
        }

        //assert
        assertThat(company.getIncome(), equalTo(expected));

    }

    public static Stream<Arguments> argumentsForShiftMoneyIncomeTest() {
        return Stream.of(
                Arguments.of(new double[]{144.432, 42354, 156, -80}, 42654.432),
                Arguments.of(new double[]{-144.432, -42354, -156, 80}, 80)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsForShiftMoneyCostsTest")
    public void shiftMoneyCostsTest(double[] moneySums, double expected) {

        //act
        for (double i : moneySums) {
            company.shiftMoney(i);
        }

        //assert
        assertThat(company.getCosts(), equalTo(expected));

    }

    public static Stream<Arguments> argumentsForShiftMoneyCostsTest() {
        return Stream.of(
                Arguments.of(new double[]{144.432, 42354, 156, -80}, 80),
                Arguments.of(new double[]{-144.432, -42354, -156, 80}, 42654.432)
        );
    }


}
