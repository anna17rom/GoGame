import org.example.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class RemotePlayerTest {

    @Test
    public void testMode() throws IOException {
        // Symulowanie danych wejściowych
        String data = "{\"type\":\"START_2P_GAME\", \"size\": 19}";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes());
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        // Utworzenie instancji ClientIO z prawdziwym DataInputStream
        GoServerV2.ClientIO clientIO = new GoServerV2.ClientIO(dataInputStream, null);

        RemotePlayer player = new RemotePlayer(clientIO);
        assertEquals(ServerResponse.Mode.MULTIPLAYER, player.mode());
        assertEquals(19, player.size);
    }
}
