import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    @Disabled("Отключен, при необходимости его можно запустить вручную.")
    public void checkHippodromeExecutionTimeDuration() {
        long start = System.currentTimeMillis();
        try {
            Main.hippodromeExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        long result = finish - start;
        //////////////
        assertTrue(result <= 22000);
    }

}