import org.example.RemotePlayer;
import org.example.GoServerV2.ClientIO;
import org.example.GoBoard;
import org.example.GoBoard.Stone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.DataInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RemotePlayerTest {

    @Mock
    private ClientIO mockIO;
    @Mock
    private DataInputStream mockInputStream;

    private RemotePlayer player;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Assume ClientIO has public fields 'reader' and 'writer' for this example
        mockIO.reader = mockInputStream;
        player = new RemotePlayer(mockIO);
    }

    @Test
    public void testNextMove() throws IOException {
        Stone expectedStone = new Stone(1, 1, GoBoard.StoneColor.BLACK);
        String stoneJson = "{\"x\":1, \"y\":1, \"color\":\"BLACK\"}";

        when(mockInputStream.readUTF()).thenReturn(stoneJson);

        Stone actualStone = player.nextMove(new GoBoard());

        verify(mockInputStream, times(1)).readUTF();
        assertEquals(expectedStone, actualStone);
    }


}
