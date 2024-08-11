
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

public class HorseTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "   ", "\t\t", " \n "})
    public void testConstructorThrowsCorrectExceptionOnFirstParameter(String name) {
        IllegalArgumentException nullException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 10.0, 100.0);
        });
        assertEquals("Name cannot be null.", nullException.getMessage());
//////////
        IllegalArgumentException blankException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, 10.0, 100.0);
        });
        assertEquals("Name cannot be blank.", blankException.getMessage());
    }

    @Test
    public void testConstructorThrowsCorrectExceptionOnSecondParameter() {
        IllegalArgumentException negativeException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Plotva", -1.0, 100.0);
        });
        assertEquals("Speed cannot be negative.", negativeException.getMessage());
    }

    @Test
    public void testConstructorThrowsCorrectExceptionOnThirdParameter() {
        IllegalArgumentException negativeException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Plotva", 10.0, -100.0);
        });
        assertEquals("Distance cannot be negative.", negativeException.getMessage());
    }

    @Test
    public void getNameReturnsFirstArgumentOfConstructor() {
        String name = "Plotva";
        Horse horse = new Horse(name, 10.0, 100.0);

        assertEquals("Plotva", horse.getName());
    }

    @Test
    public void getSpeedReturnsSecondArgumentOfConstructor() {
        double speed = 10.0;
        Horse horse = new Horse("Plotva", speed, 100.0);

        assertEquals(10.0, horse.getSpeed());
    }

    @Test
    public void getSpeedReturnsThirdArgumentOfConstructorOrNull() {
        double distance = 100.0;
        Horse horse = new Horse("Plotva", 10.0, distance);

        assertEquals(100.0, horse.getDistance());
        ////////
        Horse horseTwoParams = new Horse("Plotva", 10.0);
        assertEquals(0.0, horseTwoParams.getDistance());
    }

    @Test
    public void checkMoveCallsStaticGetRandomDoubleWithSpecificParameters() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Plotva", 10.0, 100.0);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    static Stream<Object[]> parametersForMoveTest() {
        return Stream.of(
                new Object[]{10.0, 0.2, 2.0}, // speed, RandomDouble, expectedDistance
                new Object[]{20.0, 0.5, 10.0},
                new Object[]{30.0, 0.9, 27.0}
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForMoveTest")
    public void checkMoveAssignsCorrectDistance(double speed, double randomDouble, double expectedDistance) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);
            Horse horse = new Horse("Plotva", speed, 0.0);
            horse.move();
            /////
            assertEquals(expectedDistance, horse.getDistance(), 0.01);
        }
    }
}