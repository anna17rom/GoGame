import org.example.GoBoard;
import org.example.Intersection;
import org.example.StoneChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {

    private GoBoard board;
    private Intersection intersection;

    @BeforeEach
    void setUp() {
        board = new GoBoard();
        board.setIntersections();  // Ta metoda powinna inicjalizować planszę i przecięcia
        // Załóżmy, że chcemy sprawdzić przecięcie na współrzędnych (3, 3)
        intersection = board.getIntersection(150, 150); // Zakładając, że gridSize to 50
    }

    @Test
    void testEmptyNeighborsInIntersection() {
        assertNotNull(intersection, "Intersection should not be null");

        // Sprawdzamy, czy sąsiadujące przecięcia są puste na początku gry
        assertTrue(intersection.getEmptyNeighbors().size() > 0, "Should have empty neighbors");

        // Dodatkowo, możesz przetestować konkretne warunki, na przykład
        // czy liczba pustych sąsiadów jest równa 4, co jest typowe dla większości przecięć na planszy Go
        assertEquals(4, intersection.getEmptyNeighbors().size(), "Should have 4 empty neighbors");
    }
    @Test
    void testIsEmpty() {
        assertTrue(intersection.isEmpty(), "New intersection should be empty");
    }

    @Test
    void testSetStoneChain() {
        StoneChain stoneChain = new StoneChain(intersection, null); // Załóżmy, że mamy nowy łańcuch kamieni
        intersection.setStoneChain(stoneChain);

        assertEquals(stoneChain, intersection.getStoneChain(), "StoneChain should be set correctly");
    }

}
