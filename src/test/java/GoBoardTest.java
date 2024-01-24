import org.example.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GoBoardTest {

    private GoBoard goBoard;

    @BeforeEach
    void setUp() {
        goBoard = new GoBoard();
    }

    @Test
    void testAddStone() {
        // Dodajemy kamień do planszy
        GoBoard.Stone stone = new GoBoard.Stone(5, 5, GoBoard.StoneColor.BLACK);
        goBoard.addStone(stone);

        // Pobieramy listę kamieni z planszy
        List<GoBoard.Stone> stones = goBoard.getStones();

        // Sprawdzamy, czy kamień został dodany
        assertTrue(stones.contains(stone), "Kamień powinien być na liście kamieni na planszy.");

        // Sprawdzamy, czy kamień jest w odpowiedniej pozycji
        assertEquals(5, stone.getX(), "X pozycja kamienia jest niepoprawna.");
        assertEquals(5, stone.getY(), "Y pozycja kamienia jest niepoprawna.");
        assertEquals(GoBoard.StoneColor.BLACK, stone.getColor(), "Kolor kamienia jest niepoprawny.");
    }

}

