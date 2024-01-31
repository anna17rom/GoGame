import org.example.DB.GoDb;
import org.example.GoBoard;
import org.example.Intersection;
import org.example.Player;
import org.example.ServerSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.example.ServerSession.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerSessionTest {

    private ServerSession session;
    private Player player;
    private GoBoard board;
    private Intersection intersection;

    @BeforeEach
    public void setUp() {
        // Mock the dependencies
        player = Mockito.mock(Player.class);
        board = new GoBoard(19); // Assuming a standard 19x19 Go board
        board.setIntersections(); // Initialize intersections
        session = new ServerSession(player, Mockito.mock(Player.class), Mockito.mock(GoDb.class));

        // Set up an intersection
        intersection = board.getIntersectionFromArray(10, 10); // Choose an arbitrary intersection
    }

    @Test
    public void testIsValidWithEmptyIntersection() {
        GoBoard.Stone stone = new GoBoard.Stone(intersection.getX(), intersection.getY(), GoBoard.StoneColor.BLACK);
        assertTrue(session.isValid(intersection, player, board), "Move should be valid on an empty intersection.");
    }
}
