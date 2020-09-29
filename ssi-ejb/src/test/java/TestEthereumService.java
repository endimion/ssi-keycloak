
import gr.uaegean.services.EthereumService;
import gr.uaegean.services.impl.EthereumServiceImpl;
import gr.uaegean.utils.ByteConverters;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nikos
 */
public class TestEthereumService {

    @Test
    public void testGetRevocationList() throws InterruptedException, ExecutionException {
        EthereumService ethServ = new EthereumServiceImpl();
        List<byte[]> o = ethServ.getRevocationContract().getAllCases().sendAsync().get();
        assertEquals(ByteConverters.bytes32ToString(o.get(0)), "123");
        assertEquals(ethServ.getAllRevockedCredentials().size(), 1);

        assertEquals(ethServ.getRevocationContract().isRevoked(ByteConverters.stringToBytes32("123").getValue()).sendAsync().get(), true);

        assertEquals(ethServ.checkRevocationStatus("123"), true);
        assertEquals(ethServ.checkRevocationStatus("1234"), false);

    }

    @Test
    public void testUuidCovertions() {
        String uuid = "d6e358d6-0077-43bf-be01-a85fde6c8f02";
        ByteConverters.stringToBytes32(uuid);
    }

}
