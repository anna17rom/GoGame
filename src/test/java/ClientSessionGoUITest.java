import org.example.*;

import org.example.GoBoard.Stone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.concurrent.CountDownLatch;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientSessionGoUITest {

    private ClientSession clientSession;
    private GoUI mockGoUI;
    private BlockingQueue<GoBoard.Stone> stonesQueue;
    private Consumer<ClientRequest> mockRequestConsumer;
    private CountDownLatch latch;


    @BeforeEach
    void setUp() {
        latch = new CountDownLatch(1);
        MockitoAnnotations.initMocks(this);
        stonesQueue = new LinkedBlockingQueue<>();
        mockGoUI = Mockito.mock(GoUI.class);
        mockRequestConsumer = Mockito.mock(Consumer.class);
        clientSession = new ClientSession(mockRequestConsumer);
        clientSession.setUi(mockGoUI);

    }

    @Test
    void testClientReceivesUpdatedBoardAndUpdatesUI() {
        // Przygotowanie odpowiedzi serwera
        GoBoard goBoard = new GoBoard();
        goBoard.addStone(new GoBoard.Stone(3, 3, GoBoard.StoneColor.BLACK));
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setBoard(goBoard);
        serverResponse.setType(ServerResponse.Type.MOVE_REQUEST);

        // Symulacja odbioru odpowiedzi przez klienta
        clientSession.handleResponse(serverResponse);

        latch.countDown();

        // Weryfikacja, czy UI zostało zaktualizowane nową planszą
        ArgumentCaptor<GoBoard> boardCaptor = ArgumentCaptor.forClass(GoBoard.class);
        verify(mockGoUI).renderBoard(boardCaptor.capture());
        GoBoard capturedBoard = boardCaptor.getValue();

        assertEquals(goBoard, capturedBoard, "Plansza w UI powinna zostać zaktualizowana");
    }
}

