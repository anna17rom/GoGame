import org.example.GoBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoBoardRemoveStoneTest {

    private GoBoard goBoard;

    @BeforeEach
    void setUp() {
        goBoard = new GoBoard(19); // Assuming a standard 19x19 Go board
    }

    @Test
    void testRemoveStone() {
        // Add a stone to the board
        int x = 5;
        int y = 5;
        GoBoard.StoneColor color = GoBoard.StoneColor.BLACK;
        goBoard.addStone(x, y, color);

        // Ensure the stone is added
        assertTrue(goBoard.getStones().stream().anyMatch(stone -> stone.getX() == x && stone.getY() == y && stone.getColor() == color),
                "Stone should be on the board before removal.");

        // Now remove the stone
        goBoard.removeStone(x, y);

        // Verify the stone is removed
        assertFalse(goBoard.getStones().stream().anyMatch(stone -> stone.getX() == x && stone.getY() == y),
                "Stone should be removed from the board.");
    }
}
