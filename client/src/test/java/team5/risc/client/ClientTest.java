package team5.risc.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
    MockServer mockServer;                                  

    @Test
    public void testClientString()  {
        MockServer mockServer = mock(MockServer.class);
        Mockito.lenient().when(mockServer.isAvailable()).thenReturn(true);
    }
}
