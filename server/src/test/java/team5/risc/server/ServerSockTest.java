package team5.risc.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import team5.risc.common.AttackAction;

import org.mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
import java.net.Socket;
import java.net.SocketException;

@ExtendWith(MockitoExtension.class)  
public class ServerSockTest {
  @Mock
  RISCClient mockClient1;

  @Mock
  RISCClient mockClient2;

  @Test
  public void test_server() throws SocketException, ClassNotFoundException, IOException {
    /*
    Server server = new Server(1234, 2);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    
    RISCClient mockClient1 = mock(RISCClient.class);
    assertEquals("Player 0 connected!\n", out.toString());
    out.reset();

    RISCClient mockClient2 = mock(RISCClient.class);
    assertEquals("Player 1 connected!\n", out.toString());
    out.reset();

    assertEquals("All players are connected, let's start the game!\n", out.toString());
    out.reset();

    //Placement phase
    Mockito.lenient().when(mockClient1.readInt()).thenReturn(100);
    Mockito.lenient().when(mockClient1.readInt()).thenReturn(1);
    Mockito.lenient().when(mockClient1.readInt()).thenReturn(1);
    Mockito.lenient().when(mockClient1.readInt()).thenReturn(1);
    Mockito.lenient().when(mockClient2.readInt()).thenReturn(15);
    Mockito.lenient().when(mockClient2.readInt()).thenReturn(15);
    Mockito.lenient().when(mockClient2.readInt()).thenReturn(15);

    assertEquals("Placement Phase has finished\n", out.toString());
    out.reset();

    //Action Phase
    Mockito.lenient().when(mockClient1.readUTF()).thenReturn("Done");
    Mockito.lenient().when(mockClient2.readUTF()).thenReturn("Attack");
    Mockito.lenient().when(mockClient2.readObject()).thenReturn(new AttackAction(0, "area1", "area0", 13));
    Mockito.lenient().when(mockClient2.readUTF()).thenReturn("Attack");
    Mockito.lenient().when(mockClient2.readObject()).thenReturn(new AttackAction(0, "area3", "area2", 13));
    Mockito.lenient().when(mockClient2.readUTF()).thenReturn("Attack");
    Mockito.lenient().when(mockClient2.readObject()).thenReturn(new AttackAction(0, "area5", "area4", 13));
    Mockito.lenient().when(mockClient2.readUTF()).thenReturn("Done");

    assertEquals("Game has ended, Thanks for Playing!\n", out.toString());
    out.reset();

    mockClient1.close();
    mockClient2.close();
    */
  }
  
}
