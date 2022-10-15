import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class moneyTest {

    private static final Logger log = LoggerFactory.getLogger(ormTest.class);

    @Test
    public void test(){
        Money money = Money.of(20,"PLN");
        log.info(money.toString());
    }
}
