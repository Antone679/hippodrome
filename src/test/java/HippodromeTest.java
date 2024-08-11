import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    @Test
    public void checkCorrectConstructorsWorkOnList() {
        IllegalArgumentException nullException = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(null));

        assertEquals("Horses cannot be null.", nullException.getMessage());
        ////////
        IllegalArgumentException blankException = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(new ArrayList<>()));

        assertEquals("Horses cannot be empty.", blankException.getMessage());
    }

    @Test
    public void getHorsesReturnsSameList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse " + i, 10.0));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        /////////////
        assertArrayEquals(horses.toArray(), hippodrome.getHorses().toArray());
    }

    @Test
    public void methodMoveCorrectWorkWithHorsesList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mockedHorse = mock(Horse.class);
            horses.add(mockedHorse);
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        ///////
        for (Horse mockedHorse : horses){
            verify(mockedHorse).move();
        }
    }
    @Test
    public void methodGetWinnerReturnsCorrectHorse(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse " + i, 10.0));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        Horse streamWinner = horses.stream().max(Comparator.comparingDouble(Horse::getDistance)).get();
        /////////
        assertEquals(streamWinner, hippodrome.getWinner());
    }
}