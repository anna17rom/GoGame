import org.example.*;

import org.example.ServerResponse;
import org.example.GoBoard;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class ServerResponseTest {

    @Test
    //Poprawność ustawienia i pobierania numeru gracza.
    public void testPlayerNoSetterGetter() {
        ServerResponse response = new ServerResponse();
        int testPlayerNo = 1;
        response.setPlayerNo(testPlayerNo);
        assertEquals(testPlayerNo, response.getPlayerNo());
    }

    @Test
    //Poprawność ustawienia i pobierania obiektu GoBoard.
    public void testBoardSetterGetter() {
        ServerResponse response = new ServerResponse();
        GoBoard testBoard = new GoBoard();
        response.setBoard(testBoard);
        assertSame(testBoard, response.getBoard());
    }

    @Test
    //Poprawność ustawienia i pobierania typu odpowiedzi serwera.
    public void testTypeSetterGetter() {
        ServerResponse response = new ServerResponse();
        ServerResponse.Type testType = ServerResponse.Type.GAME_STARTED;
        response.setType(testType);
        assertEquals(testType, response.getType());
    }

    @Test
    //Poprawność ustawienia i pobierania wyników graczy.
    public void testPlayerScoresSetterGetter() {
        ServerResponse response = new ServerResponse();
        Map<Integer, Integer> testScores = new HashMap<>();
        testScores.put(1, 10);
        testScores.put(2, 20);
        response.setPlayerScores(testScores);
        assertEquals(testScores, response.getPlayerScores());
    }
}


