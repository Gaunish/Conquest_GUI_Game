package team5.risc.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import team5.risc.common.MoveAction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;
import org.mockito.*;

import org.junit.jupiter.api.Test;

// public class ClientTest {
//   @Test
//   public void test_sum() {
//     Client c = new Client();
//   }


//   @Test
//   public void test_client() {}
// }

@ExtendWith(MockitoExtension.class)             
class ClientTest {

    @Mock
    RISCServer mockServer;
    
    @Mock
    TextInput textInput;      

    @Test
    public void testClientGetRegionPhase() throws IOException, ClassNotFoundException  {
        RISCServer mockServer = mock(RISCServer.class);
        TextInput mockTextInput = mock(TextInput.class);
        Mockito.lenient().when(mockServer.readInt()).thenReturn(1);
        Mockito.lenient().when(mockServer.readObject()).
            thenReturn(new ArrayList<String>(Arrays.asList("area0", "area3", "area6")));
        // Mockito.lenient().when(mockServer.readUTF()).thenReturn(32, 4, 5);
        Client client = new Client(mockServer, mockTextInput);
        assertEquals(client.getID(), 1);
        ArrayList<String> regions = client.getRegionPhase();
        assertEquals("area0", regions.get(0));
        assertEquals("area3", regions.get(1));
        assertEquals("area6", regions.get(2));
        // client.run();
    }

    @Test
    public void testClientPlacementPhase() throws IOException, ClassNotFoundException {
        RISCServer mockServer = mock(RISCServer.class);
        Mockito.lenient().when(mockServer.readInt()).thenReturn(1);
        Mockito.lenient().when(mockServer.readObject()).
            thenReturn(new ArrayList<String>(Arrays.asList("area0", "area3", "area6")));
        
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("10");//num of unit for each client
        
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("How many units do you want to place in area0?\n");
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("How many units do you want to place in area3?\n");
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("How many units do you want to place in area6?\n");
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");

        TextInput mockTextInput = mock(TextInput.class);
        Mockito.lenient().when(
            mockTextInput.getPlacement(
                anyString()
            )).
        thenReturn(1, 2, 4);
    
        Client client = new Client(mockServer, mockTextInput);
        client.getRegionPhase();

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        client.placementPhase();
        assertEquals(
            "Successly place unit 1\nSuccessly place unit 2\nSuccessly place unit 4\n", 
            outputStreamCaptor.toString()
        );

    }

    // @Test
    // public void testClientActionPhase() throws IOException, ClassNotFoundException {
    //     RISCServer mockServer = mock(RISCServer.class);
    //     Mockito.lenient().when(mockServer.readInt()).thenReturn(1);
    //     Mockito.lenient().when(mockServer.readObject()).
    //         thenReturn(new ArrayList<String>(Arrays.asList("area0", "area3", "area6")));
        
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("10");//num of unit for each client
        
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("How many units do you want to place in area0?\n");
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("How many units do you want to place in area3?\n");
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("How many units do you want to place in area6?\n");
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");

    //     TextInput mockTextInput = mock(TextInput.class);
    //     Mockito.lenient().when(
    //         mockTextInput.getPlacement(
    //             anyString()
    //         )).
    //     thenReturn(1, 2, 4);
    
    //     Client client = new Client(mockServer, mockTextInput);
    //     client.getRegionPhase();
    //     client.placementPhase();

    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("map info....");
    //     Mockito.lenient().when(mockServer.readUTF()).thenReturn("winner");
    //     // Mockito.lenient().when(mockServer.readUTF()).thenReturn("Not loser");
    //     // Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");
    //     // Mockito.lenient().when(mockTextInput.getAction(anyString())).
    //     //     thenReturn("M", "D");
    //     // Mockito.lenient().when(mockTextInput.getMove(anyInt())).
    //     //     thenReturn(new MoveAction(1, "area3", "area6", 2));

    //     ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    //     client.actionPhase();

    //     System.setOut(new PrintStream(outputStreamCaptor));
    //     assertEquals(
    //         "winner winner", 
    //         outputStreamCaptor.toString()
    //     );

    // }
}
