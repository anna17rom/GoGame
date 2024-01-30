import org.example.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.function.Consumer;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

public class BoardPanelTest {

    private GoBoard goBoard;
    private BoardPanel boardPanel;
    private Consumer<GoBoard.Stone> stoneConsumer;

    @BeforeEach
    void setUp() {
        goBoard = new GoBoard();
        stoneConsumer = Mockito.mock(Consumer.class);
        boardPanel = new BoardPanel(goBoard, stoneConsumer);
    }

    @Test
    void testMouseClick() {
        // Tworzenie symulowanego zdarzenia kliknięcia myszy
        int x = 100; // symulowany x kliknięcia
        int y = 100; // symulowany y kliknięcia
        MouseEvent mockEvent = new MouseEvent(boardPanel, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, x, y, 1, false);

        // Symulacja kliknięcia myszy na panelu
        boardPanel.getMouseListeners()[0].mouseClicked(mockEvent);

        // Przechwycenie i weryfikacja, czy odpowiedni kamień został przekazany do stoneConsumer
        ArgumentCaptor<GoBoard.Stone> argument = ArgumentCaptor.forClass(GoBoard.Stone.class);
        verify(stoneConsumer).accept(argument.capture());
        GoBoard.Stone capturedStone = argument.getValue();

        // Sprawdzenie, czy kamień znajduje się w odpowiednim miejscu
        assertEquals(100, capturedStone.getX(), "X-coordinate does not match");
        assertEquals(100, capturedStone.getY(), "Y-coordinate does not match");
        assertEquals(GoBoard.StoneColor.BLACK, capturedStone.getColor(), "Stone color does not match");
    }

    @Test
    void testClearBoard() {
        // Given a board with a stone
        goBoard.addStone(new GoBoard.Stone(5, 5, GoBoard.StoneColor.BLACK));
        assertFalse(goBoard.getStones().isEmpty(), "Board should have stones before clearing");

        // When clearBoard is called
        boardPanel.clearBoard();

        // Then the board should have no stones
        assertTrue(goBoard.getStones().isEmpty(), "Board should be empty after clearing");
    }
    @Test
    void testAddStoneToBoard() {
        // Given a new stone
        GoBoard.Stone newStone = new GoBoard.Stone(3, 3, GoBoard.StoneColor.WHITE);

        // When addStoneToBoard is called
        boardPanel.addStoneToBoard(newStone);
        assertEquals(1, goBoard.getStones().size(), "Board should have one stone");

        GoBoard.Stone addedStone = goBoard.getStones().get(0);
        assertEquals(newStone.getX(), addedStone.getX(), "X-coordinate of the stone should match");
        assertEquals(newStone.getY(), addedStone.getY(), "Y-coordinate of the stone should match");
        assertEquals(newStone.getColor(), addedStone.getColor(),"Color of the stone should match");
    }
    @Test
    void testSetGoBoard() {
        // Given a new GoBoard
        GoBoard newGoBoard = new GoBoard();

        // When setGoBoard is called
        boardPanel.setGoBoard(newGoBoard);

        // Then the GoBoard should be updated
        assertSame(newGoBoard, boardPanel.getGoBoard(), "BoardPanel should use the new GoBoard");
    }

}
