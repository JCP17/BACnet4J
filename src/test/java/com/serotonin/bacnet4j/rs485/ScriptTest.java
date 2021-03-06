package com.serotonin.bacnet4j.rs485;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.serotonin.bacnet4j.npdu.mstp.MasterNode;
import com.serotonin.bacnet4j.npdu.mstp.MstpNetwork;
import com.serotonin.bacnet4j.util.TimeSource;
import org.free.bacnet4j.util.ByteQueue;

/**
 * Tests the MS/TP master node using a script from a RS-485 dump of a single master node.
 * 
 * @author Matthew
 */
public class ScriptTest {
    public static void main(String[] args) throws Exception {
        MasterNode master = new MasterNode(getInputStream(), new DumpOut(), (byte) 4, 2);
        new MstpNetwork(master);
        master.setTimeSource(new TimeSource() {
            @Override
            public long currentTimeMillis() {
                return 0;
            }
        });
        master.initialize(false);
        master.run();
    }

    static InputStream getInputStream() {
        ByteQueue queue = new ByteQueue();
        for (String s : script) {
            s = s.substring(1, s.length() - 1);
            String[] parts = s.split(",");
            for (String part : parts)
                queue.push(Integer.parseInt(part, 16));
        }

        return new ByteArrayInputStream(queue.popAll());
    }

    static class DumpOut extends OutputStream {
        boolean first = true;

        @Override
        public void write(int b) throws IOException {
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print(Integer.toString(b, 16));
        }
    }

    static String[] script = { //
    "[55,ff,0,4,8,0,0,14,ff,55,ff,0,4,8,0,0,14,ff,55,ff]",

    //    "[55,ff,6,ff,8,0,15,da,1,20]", //
    //            "[ff,ff,0,ff,10,0,c4,2,4,3a,10,22,1,e0,91]", //
    //            "[3,22,1,15,2b,f9,ff,55,ff,0,4,8,0,0,14]", //

    // Poll for master
    //        "[0]", //
    //            "[0]", //
    //            "[0]", //
    //            "[0]", //
    //            "[55,ff,1,9,8,0,0,7d,ff]", //
    //            "[55]", //
    //            "[ff,1,a,8,0,0,e5,ff]", //
    //            "[55,ff,1,b,8]", //
    //            "[0,0,6c,ff]", //
    //            "[55,ff,1,c,8,0,0,d6,ff]", //
    //            "[55,ff,1,d,8,0,0,5f,ff]", //
    //            "[55]", //
    //            "[ff,1,e,8,0,0,c7,ff]", //
    //            "[55,ff,1,f,8]", //
    //            "[0,0,4e,ff]", //
    //            "[55,ff,1,10,8,0,0,38,ff]", //
    //            "[55,ff,1,11,8,0,0,b1,ff]", //
    //            "[55]", //
    //            "[ff,1,12,8,0,0,29,ff]", //
    //            "[55,ff,1,13,8]", //
    //            "[0,0,a0,ff]", //
    //            "[55,ff,1,14,8,0,0,1a,ff]", //
    //            "[55,ff,1,15,8,0,0,93,ff]", //
    //            "[55,ff]", //
    //            "[1,16,8,0,0,b,ff]", //
    //            "[55,ff,1,17,8,0,0,82,ff]", //
    //            "[55,ff,1,18,8,0,0,7c,ff]", //
    //            "[55,ff]", //
    //            "[1,19,8,0,0,f5,ff]", //
    //            "[55,ff,1,1a,8,0]", //
    //            "[0,6d,ff]", //
    //            "[55,ff,1,1b,8,0,0,e4,ff]", //
    //            "[55,ff,1,1c,8,0,0,5e,ff]", //
    //            "[55,ff]", //
    //            "[1,1d,8,0,0,d7,ff]", //
    //            "[55,ff,1,1e,8,0]", //
    //            "[0,4f,ff]", //
    //            "[55,ff,1,1f,8,0,0,c6,ff]", //
    //            "[55,ff,1,20,8,0,0,a3,ff]", //
    //            "[55,ff]", //
    //            "[1,21,8,0,0,2a,ff]", //
    //            "[55,ff,1,22,8,0]", //
    //            "[0,b2,ff]", //
    //            "[55,ff,1,23,8,0,0,3b,ff]", //
    //            "[55,ff,1,24,8,0,0,81,ff]", //
    //            "[55,ff]", //
    //            "[1,25,8,0,0,8,ff]", //
    //            "[55,ff,1,26,8,0]", //
    //            "[0,90,ff]", //
    //            "[55,ff,1,27,8,0,0,19,ff]", //
    //            "[55,ff,1,28,8,0,0,e7,ff]", //
    //            "[55,ff]", //
    //            "[1,29,8,0,0,6e,ff]", //
    //            "[55,ff,1,2a,8,0]", //
    //            "[0,f6,ff]", //
    //            "[55,ff,1,2b,8,0,0,7f,ff]", //
    //            "[55,ff,1,2c,8,0,0,c5,ff]", //
    //            "[55,ff,1]", //
    //            "[2d,8,0,0,4c,ff]", //
    //            "[55,ff,1,2e,8,0]", //
    //            "[0,d4,ff]", //
    //            "[55,ff,1,2f,8,0,0,5d,ff]", //
    //            "[55,ff,1]", //
    //            "[30,8,0,0,2b,ff]", //
    //            "[55,ff,1,31,8,0,0]", //
    //            "[a2,ff]", //
    //            "[55,ff,1,32,8,0,0,3a,ff]", //
    //            "[55,ff,1,33,8,0,0,b3,ff]", //
    //            "[55,ff,1]", //
    //            "[34,8,0,0,9,ff]", //
    //            "[55,ff,1,35,8,0,0]", //
    //            "[80,ff]", //
    //            "[55,ff,1,36,8,0,0,18,ff]", //
    //            "[55,ff,1,37,8,0,0,91,ff]", //
    //            "[55,ff,1]", //
    //            "[38,8,0,0,6f,ff]", //
    //            "[55,ff,1,39,8,0,0]", //
    //            "[e6,ff]", //
    //            "[55,ff,1,3a,8,0,0,7e,ff]", //
    //            "[55,ff,1,3b,8,0,0,f7,ff]", //
    //            "[55,ff,1]", //
    //            "[3c,8,0,0,4d,ff]", //
    //            "[55,ff,1,3d,8,0,0]", //
    //            "[c4,ff]", //
    //            "[55,ff,1,3e,8,0,0,5c,ff]", //
    //            "[55,ff,1,3f,8,0,0,d5,ff]", //
    //            "[55,ff,1,40,8,0,0,96,ff]", //
    //            "[55,ff,1,41,8,0,0]", //
    //            "[1f,ff]", //
    //            "[55,ff,1,42,8,0,0,87,ff]", //
    //            "[55,ff,1,43,8,0,0,e,ff]", //
    //            "[55,ff,1]", //
    //            "[44,8,0,0,b4,ff]", //
    //            "[55,ff,1,45,8,0,0]", //
    //            "[3d,ff]", //
    //            "[55,ff,1,46,8,0,0,a5,ff]", //
    //            "[55,ff,1,47]", //
    //            "[8,0,0,2c,ff]", //
    //            "[55,ff,1,48,8,0,0,d2]", //
    //            "[ff]", //
    //            "[55,ff,1,49,8,0,0,5b,ff]", //
    //            "[55,ff,1,4a,8,0,0,c3,ff]", //
    //            "[55,ff,1,4b]", //
    //            "[8,0,0,4a,ff]", //
    //            "[55,ff,1,4c,8,0,0,f0]", //
    //            "[ff]", //
    //            "[55,ff,1,4d,8,0,0,79,ff]", //
    //            "[55,ff,1,4e,8,0,0,e1,ff]", //
    //            "[55,ff,1,4f]", //
    //            "[8,0,0,68,ff]", //
    //            "[55,ff,1,50,8,0,0,1e]", //
    //            "[ff]", //
    //            "[55,ff,1,51,8,0,0,97,ff]", //
    //            "[55,ff,1,52,8,0,0,f,ff]", //
    //            "[55,ff,1,53]", //
    //            "[8,0,0,86,ff]", //
    //            "[55,ff,1,54,8,0,0,3c]", //
    //            "[ff]", //
    //            "[55,ff,1,55,8,0,0,b5,ff]", //
    //            "[55,ff,1,56,8,0,0,2d,ff]", //
    //            "[55,ff,1,57]", //
    //            "[8,0,0,a4,ff]", //
    //            "[55,ff,1,58,8,0,0,5a]", //
    //            "[ff]", //
    //            "[55,ff,1,59,8,0,0,d3,ff]", //
    //            "[55]", //
    //            "[ff,1,5a,8,0,0,4b,ff]", //
    //            "[55,ff,1,5b]", //
    //            "[8,0,0,c2,ff]", //
    //            "[55,ff,1,5c,8,0,0,78]", //
    //            "[ff]", //
    //            "[55,ff,1,5d,8,0,0,f1,ff]", //
    //            "[55]", //
    //            "[ff,1,5e,8,0,0,69,ff]", //
    //            "[55,ff,1,5f,8,0,0,e0]", //
    //            "[ff]", //
    //            "[55,ff,1,60,8,0,0,85,ff]", //
    //            "[55]", //
    //            "[ff,1,61,8,0,0,c,ff]", //
    //            "[55,ff,1,62,8]", //
    //            "[0,0,94,ff]", //
    //            "[55,ff,1,63,8,0,0,1d,ff]", //
    //            "[55,ff,1,64,8,0,0,a7,ff]", //
    //            "[55]", //
    //            "[ff,1,65,8,0,0,2e,ff]", //
    //            "[55,ff,1,66,8]", //
    //            "[0,0,b6,ff]", //
    //            "[55,ff,1,67,8,0,0,3f,ff]", //
    //            "[55,ff,1,68,8,0,0,c1,ff]", //
    //            "[55]", //
    //            "[ff,1,69,8,0,0,48,ff]", //
    //            "[55,ff,1,6a,8]", //
    //            "[0,0,d0,ff]", //
    //            "[55,ff,1,6b,8,0,0,59,ff]", //
    //            "[55,ff,1,6c,8,0,0,e3,ff]", //
    //            "[55]", //
    //            "[ff,1,6d,8,0,0,6a,ff]", //
    //            "[55,ff,1,6e,8]", //
    //            "[0,0,f2,ff]", //
    //            "[55,ff,1,6f,8,0,0,7b,ff]", //
    //            "[55,ff,1,70,8,0,0,d,ff]", //
    //            "[55]", //
    //            "[ff,1,71,8,0,0,84,ff]", //
    //            "[55,ff,1,72,8]", //
    //            "[0,0,1c,ff]", //
    //            "[55,ff,1,73,8,0,0,95,ff]", //
    //            "[55,ff,1,74,8,0,0,2f,ff]", //
    //            "[55,ff]", //
    //            "[1,75,8,0,0,a6,ff]", //
    //            "[55,ff,1,76,8]", //
    //            "[0,0,3e,ff]", //
    //            "[55,ff,1,77,8,0,0,b7,ff]", //
    //            "[55,ff]", //
    //            "[1,78,8,0,0,49,ff]", //
    //            "[55,ff,1,79,8,0]", //
    //            "[0,c0,ff]", //
    //            "[55,ff,1,7a,8,0,0,58,ff]", //
    //            "[55,ff,1,7b,8,0,0,d1,ff]", //
    //            "[55,ff]", //
    //            "[1,7c,8,0,0,6b,ff]", //
    //            "[55,ff,1,7d,8,0]", //
    //            "[0,e2,ff]", //
    //            "[55,ff,1,7e,8,0,0,7a,ff]", //
    //            "[55,ff,1,7f,8,0,0,f3,ff]", //
    //            "[55,ff]", //
    //            "[1,0,8,0,0,b0,ff]", //
    //            "[55,ff,1,1,8,0]", //
    //            "[0,39,ff]", //
    //            "[55,ff,1,2,8,0,0,a1,ff]", //
    //            "[55,ff,1,3,8,0,0,28,ff]", //
    //            "[55,ff]", //
    //            "[1,4,8,0,0,92,ff]", //
    //            "[55,ff,1,5,8,0]", //
    //            "[0,1b,ff]", //
    //            "[55,ff,1,6,8,0,0,83,ff]", //
    //            "[55,ff,1,7,8,0,0,a,ff]", //

    // bacnetDataNotExpectingReply
    //            "[55]", //
    //            "[ff,6,ff,8,0,15,da,1,20,ff,ff,0,ff,10,0]", //
    //            "[c4,2,4,3a,10,22,1,e0,91,3,22,1,15,2b,f9]", //

    // Poll for master ...
    //            "[ff,55,ff,1,9,8,0,0,7d,ff,55,ff,1,a,8,0]", //
    //            "[0,e5,ff,55,ff,1,b,8,0]", //
    //            "[0,6c,ff]", //
    //            "[55,ff,1,c,8,0,0,d6,ff]", //
    //            "[55,ff,1,d,8,0,0,5f,ff]", //
    //            "[55,ff,1]", //
    //            "[e,8,0,0,c7,ff]", //
    //            "[55,ff,1,f,8,0]", //
    //            "[0,4e,ff]", //
    //            "[55,ff,1,10,8,0,0,38,ff]", //
    //            "[55,ff,1,11,8,0,0,b1,ff]", //
    //            "[55,ff,1]", //
    //            "[12,8,0,0,29,ff]", //
    //            "[55,ff,1,13,8,0,0]", //
    //            "[a0,ff]", //
    //            "[55,ff,1,14,8,0,0,1a,ff]", //
    //            "[55,ff,1,15,8,0,0,93,ff]", //
    //            "[55,ff,1]", //
    //            "[16,8,0,0,b,ff]", //
    //            "[55,ff,1,17,8,0,0,82,ff]", //
    //            "[55,ff,1,18,8,0,0,7c,ff]", //
    //            "[55,ff,1]", //
    //            "[19,8,0,0,f5,ff]", //
    //            "[55,ff,1,1a,8,0,0]", //
    //            "[6d,ff]", //
    //            "[55,ff,1,1b,8,0,0,e4,ff]", //
    //            "[55,ff,1,1c,8,0,0,5e,ff]", //
    //            "[55,ff,1]", //
    //            "[1d,8,0,0,d7,ff]", //
    //            "[55,ff,1,1e,8,0,0]", //
    //            "[4f,ff]", //
    //            "[55,ff,1,1f,8,0,0,c6,ff]", //
    //            "[55,ff,1,20,8,0,0,a3,ff]", //
    //            "[55,ff,1]", //
    //            "[21,8,0,0,2a,ff]", //
    //            "[55,ff,1,22,8,0,0]", //
    //            "[b2,ff]", //
    //            "[55,ff,1,23,8,0,0,3b,ff]", //
    //            "[55,ff,1,24,8,0,0,81,ff]", //
    //            "[55,ff,1,25]", //
    //            "[8,0,0,8,ff]", //
    //            "[55,ff,1,26,8,0,0]", //
    //            "[90,ff]", //
    //            "[55,ff,1,27,8,0,0,19,ff]", //
    //            "[55,ff,1,28,8,0,0,e7,ff]", //
    //            "[55,ff,1,29]", //
    //            "[8,0,0,6e,ff]", //
    //            "[55,ff,1,2a,8,0,0,f6]", //
    //            "[ff]", //
    //            "[55,ff,1,2b,8,0,0,7f,ff]", //
    //            "[55,ff,1,2c,8,0,0,c5,ff]", //
    //            "[55,ff,1,2d]", //
    //            "[8,0,0,4c,ff]", //
    //            "[55,ff,1,2e,8,0,0,d4]", //
    //            "[ff]", //
    //            "[55,ff,1,2f,8,0,0,5d,ff]", //
    //            "[55,ff,1,30]", //
    //            "[8,0,0,2b,ff]", //
    //            "[55,ff,1,31,8,0,0,a2]", //
    //            "[ff]", //
    //            "[55,ff,1,32,8,0,0,3a,ff]", //
    //            "[55,ff,1,33,8,0,0,b3,ff]", //
    //            "[55,ff,1,34]", //
    //            "[8,0,0,9,ff]", //
    //            "[55,ff,1,35,8,0,0,80]", //
    //            "[ff]", //
    //            "[55,ff,1,36,8,0,0,18,ff]", //
    //            "[55,ff,1,37,8,0,0,91,ff]", //
    //            "[55,ff,1,38]", //
    //            "[8,0,0,6f,ff]", //
    //            "[55,ff,1,39,8,0,0,e6]", //
    //            "[ff]", //
    //            "[55,ff,1,3a,8,0,0,7e,ff]", //
    //            "[55]", //
    //            "[ff,1,3b,8,0,0,f7,ff]", //
    //            "[55,ff,1,3c]", //
    //            "[8,0,0,4d,ff]", //
    //            "[55,ff,1,3d,8,0,0,c4]", //
    //            "[ff]", //
    //            "[55,ff,1,3e,8,0,0,5c,ff]", //
    //            "[55]", //
    //            "[ff,1,3f,8,0,0,d5,ff]", //
    //            "[55,ff,1,40,8]", //
    //            "[0,0,96,ff]", //
    //            "[55,ff,1,41,8,0,0,1f,ff]", //
    //            "[55,ff,1,42,8,0,0,87,ff]", //
    //            "[55]", //
    //            "[ff,1,43,8,0,0,e,ff]", //
    //            "[55,ff,1,44,8]", //
    //            "[0,0,b4,ff]", //
    //            "[55,ff,1,45,8,0,0,3d,ff]", //
    //            "[55,ff,1,46,8,0,0,a5,ff]", //
    //            "[55,ff,1,47,8]", //
    //            "[0,0,2c,ff]", //
    //            "[55,ff,1,48,8,0,0,d2,ff]", //
    //            "[55,ff,1,49,8,0,0,5b,ff]", //
    //            "[55]", //
    //            "[ff,1,4a,8,0,0,c3,ff,55,ff,1,4b,8]", //
    //            "[0,0,4a,ff]", //
    //            "[55,ff,1,4c,8,0,0,f0,ff]", //
    //            "[55,ff,1,4d,8,0,0,79,ff]", //
    //            "[55]", //
    //            "[ff,1,4e,8,0,0,e1,ff]", //
    //            "[55,ff,1,4f,8]", //
    //            "[0,0,68,ff]", //
    //            "[55,ff,1,50,8,0,0,1e,ff]", //
    //            "[55,ff,1,51,8,0,0,97,ff]", //
    //            "[55,ff]", //
    //            "[1,52,8,0,0,f,ff]", //
    //            "[55,ff,1,53,8]", //
    //            "[0,0,86,ff]", //
    //            "[55,ff,1,54,8,0,0,3c,ff]", //
    //            "[55,ff,1,55,8,0,0,b5,ff]", //
    //            "[55,ff]", //
    //            "[1,56,8,0,0,2d,ff]", //
    //            "[55,ff,1,57,8,0]", //
    //            "[0,a4,ff]", //
    //            "[55,ff,1,58,8,0,0,5a,ff]", //
    //            "[55,ff,1,59,8,0,0,d3,ff]", //
    //            "[55,ff]", //
    //            "[1,5a,8,0,0,4b,ff]", //
    //            "[55,ff,1,5b,8,0]", //
    //            "[0,c2,ff]", //
    //            "[55,ff,1,5c,8,0,0,78,ff]", //
    //            "[55,ff,1,5d,8,0,0,f1,ff]", //
    //            "[55,ff]", //
    //            "[1,5e,8,0,0,69,ff]", //
    //            "[55,ff,1,5f,8,0,0,e0,ff]", //
    //            "[55,ff,1,60,8,0,0,85,ff]", //
    //            "[55,ff]", //
    //            "[1,61,8,0,0,c,ff]", //
    //            "[55,ff,1,62,8,0]", //
    //            "[0,94,ff]", //
    //            "[55,ff,1,63,8,0,0,1d,ff]", //
    //            "[55,ff,1,64,8,0,0,a7,ff]", //
    //            "[55,ff]", //
    //            "[1,65,8,0,0,2e,ff]", //
    //            "[55,ff,1,66,8,0]", //
    //            "[0,b6,ff]", //
    //            "[55,ff,1,67,8,0,0,3f,ff]", //
    //            "[55,ff,1,68,8,0,0,c1,ff]", //
    //            "[55,ff]", //
    //            "[1,69,8,0,0,48,ff]", //
    //            "[55,ff,1,6a,8,0]", //
    //            "[0,d0,ff]", //
    //            "[55,ff,1,6b,8,0,0,59,ff]", //
    //            "[55,ff,1,6c,8,0,0,e3,ff]", //
    //            "[55,ff,1]", //
    //            "[6d,8,0,0,6a,ff]", //
    //            "[55,ff,1,6e,8,0]", //
    //            "[0,f2,ff]", //
    //            "[55,ff,1,6f,8,0,0,7b,ff]", //
    //            "[55,ff,1,70,8,0,0,d,ff]", //
    //            "[55,ff,1]", //
    //            "[71,8,0,0,84,ff]", //
    //            "[55,ff,1,72,8,0,0]", //
    //            "[1c,ff]", //
    //            "[55,ff,1,73,8,0,0,95,ff]", //
    //            "[55,ff,1,74,8,0,0,2f,ff]", //
    //            "[55,ff,1]", //
    //            "[75,8,0,0,a6,ff]", //
    //            "[55,ff,1,76,8,0,0]", //
    //            "[3e,ff]", //
    //            "[55,ff,1,77,8,0,0,b7,ff]", //
    //            "[55,ff,1]", //
    //            "[78,8,0,0,49,ff]", //
    //            "[55,ff,1,79,8,0,0]", //
    //            "[c0,ff]", //
    //            "[55,ff,1,7a,8,0,0,58,ff]", //
    //            "[55,ff,1,7b,8,0,0,d1,ff]", //
    //            "[55,ff,1]", //
    //            "[7c,8,0,0,6b,ff]", //
    //            "[55,ff,1,7d,8,0,0]", //
    //            "[e2,ff]", //
    //            "[55,ff,1,7e,8,0,0,7a,ff]", //
    //            "[55,ff,1,7f,8,0,0,f3,ff]", //
    //            "[55,ff,1]", //
    //            "[0,8,0,0,b0,ff]", //
    //            "[55,ff,1,1,8,0,0]", //
    //            "[39,ff]", //
    //            "[55,ff,1,2,8,0,0,a1,ff]", //
    //            "[55,ff,1,3,8,0,0,28,ff]", //
    //            "[55,ff,1,4]", //
    //            "[8,0,0,92,ff]", //
    //            "[55,ff,1,5,8,0,0]", //
    //            "[1b,ff]", //
    //            "[55,ff,1,6,8,0,0,83,ff]", //
    //            "[55,ff,1,7,8,0,0,a,ff]", //
    //            "[55,ff,1,9]", //
    //            "[8,0,0,7d,ff]", //
    //            "[55,ff,1,a,8,0,0,e5]", //
    //            "[ff]", //
    //            "[55,ff,1,b,8,0,0,6c,ff]", //
    //            "[55,ff,1,c,8,0,0,d6,ff]", //
    //            "[55,ff,1,d]", //
    //            "[8,0,0,5f,ff]", //
    //            "[55,ff,1,e,8,0,0,c7]", //
    //            "[ff]", //
    //            "[55,ff,1,f,8,0,0,4e,ff]", //
    //            "[55,ff,1,10]", //
    //            "[8,0,0,38,ff]", //
    //            "[55,ff,1,11,8,0,0,b1]", //
    //            "[ff]", //
    //            "[55,ff,1,12,8,0,0,29,ff]", //
    //            "[55,ff,1,13,8,0,0,a0,ff]", //
    //            "[55,ff,1,14]", //
    //            "[8,0,0,1a,ff]", //
    //            "[55,ff,1,15,8,0,0,93]", //
    //            "[ff]", //
    //            "[55,ff,1,16,8,0,0,b,ff]", //
    //            "[55,ff,1,17,8,0,0,82,ff]", //
    //            "[55,ff,1,18]", //
    //            "[8,0,0,7c,ff]", //
    //            "[55,ff,1,19,8,0,0,f5]", //
    //            "[ff]", //
    //            "[55,ff,1,1a,8,0,0,6d,ff]", //
    //            "[55]", //
    //            "[ff,1,1b,8,0,0,e4,ff]", //
    //            "[55,ff,1,1c,8]", //
    //            "[0,0,5e,ff]", //
    //            "[55,ff,1,1d,8,0,0,d7]", //
    //            "[ff]", //
    //            "[55,ff,1,1e,8,0,0,4f,ff]", //
    //            "[55]", //
    //            "[ff,1,1f,8,0,0,c6,ff]", //
    //            "[55,ff,1,20,8]", //
    //            "[0,0,a3,ff]", //
    //            "[55,ff,1,21,8,0,0,2a,ff]", //
    //            "[55,ff,1,22,8,0,0,b2,ff]", //
    //            "[55]", //
    //            "[ff,1,23,8,0,0,3b,ff]", //
    //            "[55,ff,1,24,8]", //
    //            "[0,0,81,ff]", //
    //            "[55,ff,1,25,8,0,0,8,ff]", //
    //            "[55,ff,1,26,8,0,0,90,ff]", //
    //            "[55]", //
    //            "[ff,1,27,8,0,0,19,ff]", //
    //            "[55,ff,1,28,8,0,0,e7,ff]", //
    //            "[55,ff,1,29,8,0,0,6e,ff]", //
    //            "[55]", //
    //            "[ff,1,2a,8,0,0,f6,ff]", //
    //            "[55,ff,1,2b,8]", //
    //            "[0,0,7f,ff]", //
    //            "[55,ff,1,2c,8,0,0,c5,ff]", //
    //            "[55,ff,1,2d,8,0,0,4c,ff]", //
    //            "[55]", //
    //            "[ff,1,2e,8,0,0,d4,ff]", //
    //            "[55,ff,1,2f,8]", //
    //            "[0,0,5d,ff]", //
    //            "[55,ff,1,30,8,0,0,2b,ff]", //
    //            "[55,ff,1,31,8,0,0,a2,ff]", //
    //            "[55,ff]", //
    //            "[1,32,8,0,0,3a,ff]", //
    //            "[55,ff,1,33,8]", //
    //            "[0,0,b3,ff]", //
    //            "[55,ff,1,34,8,0,0,9,ff]", //
    //            "[55,ff,1,35,8,0,0,80,ff]", //
    //            "[55,ff]", //
    //            "[1,36,8,0,0,18,ff]", //
    //            "[55,ff,1,37,8,0]", //
    //            "[0,91,ff]", //
    //            "[55,ff,1,38,8,0,0,6f,ff]", //
    //            "[55,ff,1,39,8,0,0,e6,ff]", //
    //            "[55,ff]", //
    //            "[1,3a,8,0,0,7e,ff]", //
    //            "[55,ff,1,3b,8,0]", //
    //            "[0,f7,ff]", //
    //            "[55,ff,1,3c,8,0,0,4d,ff]", //
    //            "[55,ff,1,3d,8,0,0,c4,ff]", //
    //            "[55,ff]", //
    //            "[1,3e,8,0,0,5c,ff]", //
    //            "[55,ff,1,3f,8,0]", //
    //            "[0,d5,ff]", //
    //            "[55,ff,1,40,8,0,0,96,ff]", //
    //            "[55,ff]", //
    //            "[1,41,8,0,0,1f,ff]", //
    //            "[55,ff,1,42,8,0]", //
    //            "[0,87,ff]", //
    //            "[55,ff,1,43,8,0,0,e,ff]", //
    //            "[55,ff,1,44,8,0,0,b4,ff]", //
    //            "[55,ff]", //
    //            "[1,45,8,0,0,3d,ff]", //
    //            "[55,ff,1,46,8,0]", //
    //            "[0,a5,ff]", //
    //            "[55,ff,1,47,8,0,0,2c,ff]", //
    //            "[55,ff,1,48,8,0,0,d2,ff]", //
    //            "[55,ff,1]", //
    //            "[49,8,0,0,5b,ff]", //
    //            "[55,ff,1,4a,8,0]", //
    //            "[0,c3,ff]", //
    //            "[55,ff,1,4b,8,0,0,4a,ff]", //
    //            "[55,ff,1,4c,8,0,0,f0,ff]", //
    //            "[55,ff,1]", //
    //            "[4d,8,0,0,79,ff]", //
    //            "[55,ff,1,4e,8,0,0]", //
    //            "[e1,ff]", //
    //            "[55,ff,1,4f,8,0,0,68,ff]", //
    //            "[55,ff,1,50,8,0,0,1e,ff]", //
    //            "[55,ff,1]", //
    //            "[51,8,0,0,97,ff]", //
    //            "[55,ff,1,52,8,0,0]", //
    //            "[f,ff]", //
    //            "[55,ff,1,53,8,0,0,86,ff]", //
    //            "[55,ff,1,54,8,0,0,3c,ff]", //
    //            "[55,ff,1]", //
    //            "[55,8,0,0,b5,ff]", //
    //            "[55,ff,1,56,8,0,0]", //
    //            "[2d,ff]", //
    //            "[55,ff,1,57,8,0,0,a4,ff]", //
    //            "[55,ff,1]", //
    //            "[58,8,0,0,5a,ff]", //
    //            "[55,ff,1,59,8,0,0]", //
    //            "[d3,ff]", //
    //            "[55,ff,1,5a,8,0,0,4b,ff]", //
    //            "[55,ff,1,5b,8,0,0,c2,ff]", //
    //            "[55,ff,1]", //
    //            "[5c,8,0,0,78,ff]", //
    //            "[55,ff,1,5d,8,0,0]", //
    //            "[f1,ff]", //
    //            "[55,ff,1,5e,8,0,0,69,ff]", //
    //            "[55,ff,1,5f,8,0,0,e0,ff]", //
    //            "[55,ff,1]", //
    //            "[60,8,0,0,85,ff]", //
    //            "[55,ff,1,61,8,0,0]", //
    //            "[c,ff]", //
    //            "[55,ff,1,62,8,0,0,94,ff]", //
    //            "[55,ff,1,63,8,0,0,1d,ff]", //
    //            "[55,ff,1,64]", //
    //            "[8,0,0,a7,ff]", //
    //            "[55,ff,1,65,8,0,0]", //
    //            "[2e,ff]", //
    //            "[55,ff,1,66,8,0,0,b6,ff]", //
    //            "[55,ff,1,67,8,0,0,3f,ff]", //
    //            "[55,ff,1,68]", //
    //            "[8,0,0,c1,ff]", //
    //            "[55,ff,1,69,8,0,0,48]", //
    //            "[ff]", //
    //            "[55,ff,1,6a,8,0,0,d0,ff]", //
    //            "[55,ff,1,6b,8,0,0,59,ff]", //
    //            "[55,ff,1,6c]", //
    //            "[8,0,0,e3,ff]", //
    //            "[55,ff,1,6d,8,0,0,6a]", //
    //            "[ff]", //
    //            "[55,ff,1,6e,8,0,0,f2,ff]", //
    //            "[55,ff,1,6f,8,0,0,7b,ff]", //
    //            "[55,ff,1,70,8,0,0,d]", //
    //            "[ff]", //
    //            "[55,ff,1,71,8,0,0,84,ff]", //
    //            "[55,ff,1,72,8,0,0,1c,ff]", //
    //            "[55,ff,1,73]", //
    //            "[8,0,0,95,ff]", //
    //            "[55,ff,1,74,8,0,0,2f]", //
    //            "[ff]", //
    //            "[55,ff,1,75,8,0,0,a6,ff]", //
    //            "[55]", //
    //            "[ff,1,76,8,0,0,3e,ff]", //
    //            "[55,ff,1,77]", //
    //            "[8,0,0,b7,ff]", //
    //            "[55,ff,1,78,8,0,0,49]", //
    //            "[ff]", //
    //            "[55,ff,1,79,8,0,0,c0,ff]", //
    //            "[55]", //
    //            "[ff,1,7a,8,0,0,58,ff]", //
    //            "[55,ff,1,7b,8]", //
    //            "[0,0,d1,ff]", //
    //            "[55,ff,1,7c,8,0,0,6b]", //
    //            "[ff]", //
    //            "[55,ff,1,7d,8,0,0,e2,ff]", //
    //            "[55]", //
    //            "[ff,1,7e,8,0,0,7a,ff]", //
    //            "[55,ff,1,7f,8]", //
    //            "[0,0,f3,ff]", //
    //            "[55,ff,1,0,8,0,0,b0,ff]", //
    //            "[55,ff,1,1,8,0,0,39,ff]", //
    //            "[55]", //
    //            "[ff,1,2,8,0,0,a1,ff]", //
    //            "[55,ff,1,3,8]", //
    //            "[0,0,28,ff]", //
    //            "[55,ff,1,4,8,0,0,92,ff]", //
    //            "[55,ff,1,5,8,0,0,1b,ff]", //
    //            "[55]", //
    //            "[ff,1,6,8,0,0,83,ff]", //
    //            "[55,ff,1,7,8]", //
    //            "[0,0,a,ff]", //
    //            "[55,ff,1,9,8,0,0,7d,ff]", //
    //            "[55]", //
    //            "[ff,1,a,8,0,0,e5,ff]", //
    //            "[55,ff,1,b,8]", //
    //            "[0,0,6c,ff]", //
    //            "[55,ff,1,c,8,0,0,d6,ff]", //
    //            "[55,ff,1,d,8,0,0,5f,ff]", //
    //            "[55]", //
    //            "[ff,1,e,8,0,0,c7,ff]", //
    //            "[55,ff,1,f,8]", //
    //            "[0,0,4e,ff]", //
    //            "[55,ff,1,10,8,0,0,38,ff]", //
    //            "[55,ff,1,11,8,0,0,b1,ff]", //
    //            "[55,ff]", //
    //            "[1,12,8,0,0,29,ff]", //
    //            "[55,ff,1,13,8]", //
    //            "[0,0,a0,ff]", //
    //            "[55,ff,1,14,8,0,0,1a,ff]", //
    //            "[55,ff,1,15,8,0,0,93,ff]", //
    //            "[55,ff]", //
    //            "[1,16,8,0,0,b,ff]", //
    //            "[55,ff,1,17,8,0]", //
    //            "[0,82,ff]", //
    //            "[55,ff,1,18,8,0,0,7c,ff]", //
    //            "[55,ff,1,19,8,0,0,f5,ff]", //
    //            "[55,ff]", //
    //            "[1,1a,8,0,0,6d,ff]", //
    //            "[55,ff,1,1b,8,0]", //
    //            "[0,e4,ff]", //
    //            "[55,ff,1,1c,8,0,0,5e,ff]", //
    //            "[55,ff,1,1d,8,0,0,d7,ff]", //
    //            "[55,ff]", //
    //            "[1,1e,8,0,0,4f,ff]", //
    //            "[55,ff,1,1f,8,0]", //
    //            "[0,c6,ff]", //
    //            "[55,ff,1,20,8,0,0,a3,ff]", //
    //            "[55,ff,1,21,8,0,0,2a,ff]", //
    //            "[55,ff]", //
    //            "[1,22,8,0,0,b2,ff]", //
    //            "[55,ff,1,23,8,0]", //
    //            "[0,3b,ff]", //
    //            "[55,ff,1,24,8,0,0,81,ff]", //
    //            "[55,ff]", //
    //            "[1,25,8,0,0,8,ff]", //
    //            "[55,ff,1,26,8,0]", //
    //            "[0,90,ff]", //
    //            "[55,ff,1,27,8,0,0,19,ff]", //
    //            "[55,ff,1,28,8,0,0,e7,ff]", //
    //            "[55,ff,1]", //
    //            "[29,8,0,0,6e,ff]", //
    //            "[55,ff,1,2a,8,0]", //
    //            "[0,f6,ff]", //
    //            "[55,ff,1,2b,8,0,0,7f,ff]", //
    //            "[55,ff,1,2c,8,0,0,c5,ff]", //
    //            "[55,ff,1]", //
    //            "[2d,8,0,0,4c,ff]", //
    //            "[55,ff,1,2e,8,0,0]", //
    //            "[d4,ff]", //
    //            "[55,ff,1,2f,8,0,0,5d,ff]", //
    //            "[55,ff,1,30,8,0,0,2b,ff]", //
    //            "[55,ff,1]", //
    //            "[31,8,0,0,a2,ff]", //
    //            "[55,ff,1,32,8,0,0]", //
    //            "[3a,ff]", //
    //            "[55,ff,1,33,8,0,0,b3,ff]", //
    //            "[55,ff,1,34,8,0,0,9,ff]", //
    //            "[55,ff,1]", //
    //            "[35,8,0,0,80,ff]", //
    //            "[55,ff,1,36,8,0,0]", //
    //            "[18,ff]", //
    //            "[55,ff,1,37,8,0,0,91,ff]", //
    //            "[55,ff,1,38,8,0,0,6f,ff]", //
    //            "[55,ff,1]", //
    //            "[39,8,0,0,e6,ff]", //
    //            "[55,ff,1,3a,8,0,0]", //
    //            "[7e,ff]", //
    //            "[55,ff,1,3b,8,0,0,f7,ff]", //
    //            "[55,ff,1]", //
    //            "[3c,8,0,0,4d,ff]", //
    //            "[55,ff,1,3d,8,0,0]", //
    //            "[c4,ff]", //
    //            "[55,ff,1,3e,8,0,0,5c,ff]", //
    //            "[55,ff,1,3f,8,0,0,d5,ff]", //
    //            "[55,ff,1]", //
    //            "[40,8,0,0,96,ff]", //
    //            "[55,ff,1,41,8,0,0]", //
    //            "[1f,ff]", //
    //            "[55,ff,1,42,8,0,0,87,ff]", //
    //            "[55,ff,1,43,8,0,0,e,ff]", //
    //            "[55,ff,1,44]", //
    //            "[8,0,0,b4,ff]", //
    //            "[55,ff,1,45,8,0,0]", //
    //            "[3d,ff]", //
    //            "[55,ff,1,46,8,0,0,a5,ff]", //
    //            "[55,ff,1,47,8,0,0,2c,ff]", //
    //            "[55,ff,1,48]", //
    //            "[8,0,0,d2,ff]", //
    //            "[55,ff,1,49,8,0,0,5b]", //
    //            "[ff]", //
    //            "[55,ff,1,4a,8,0,0,c3,ff]", //
    //            "[55,ff,1,4b,8,0,0,4a,ff]", //
    //            "[55,ff,1,4c]", //
    //            "[8,0,0,f0,ff]", //
    //            "[55,ff,1,4d,8,0,0,79]", //
    //            "[ff]", //
    //            "[55,ff,1,4e,8,0,0,e1,ff]", //
    //            "[55,ff,1,4f,8,0,0,68,ff]", //
    //            "[55,ff,1,50]", //
    //            "[8,0,0,1e,ff]", //
    //            "[55,ff,1,51,8,0,0,97]", //
    //            "[ff]", //
    //            "[55,ff,1,52,8,0,0,f,ff]", //
    //            "[55,ff,1,53,8,0,0,86,ff]", //
    //            "[55,ff,1,54,8,0,0,3c]", //
    //            "[ff]", //
    //            "[55,ff,1,55,8,0,0,b5,ff]", //
    //            "[55,ff,1,56,8,0,0,2d,ff]", //
    //            "[55,ff,1,57]", //
    //            "[8,0,0,a4,ff]", //
    //            "[55,ff,1,58,8,0,0,5a]", //
    //            "[ff]", //
    //            "[55,ff,1,59,8,0,0,d3,ff]", //
    //            "[55]", //
    //            "[ff,1,5a,8,0,0,4b,ff]", //
    //            "[55,ff,1,5b]", //
    //            "[8,0,0,c2,ff]", //
    //            "[55,ff,1,5c,8,0,0,78]", //
    //            "[ff]", //
    //            "[55,ff,1,5d,8,0,0,f1,ff]", //
    //            "[55]", //
    //            "[ff,1,5e,8,0,0,69,ff]", //
    //            "[55,ff,1,5f,8]", //
    //            "[0,0,e0,ff]", //
    //            "[55,ff,1,60,8,0,0,85,ff]", //
    //            "[55,ff,1,61,8,0,0,c,ff]", //
    //            "[55]", //
    //            "[ff,1,62,8,0,0,94,ff]", //
    //            "[55,ff,1,63,8]", //
    //            "[0,0,1d,ff]", //
    //            "[55,ff,1,64,8,0,0,a7,ff]", //
    //            "[55,ff,1,65,8,0,0,2e,ff]", //
    //            "[55]", //
    //            "[ff,1,66,8,0,0,b6,ff]", //
    //            "[55,ff,1,67,8]", //
    //            "[0,0,3f,ff]", //
    //            "[55,ff,1,68,8,0,0,c1,ff]", //
    //            "[55,ff,1,69,8,0,0,48,ff]", //
    //            "[55]", //
    //            "[ff,1,6a,8,0,0,d0,ff]", //
    //            "[55,ff,1,6b,8]", //
    //            "[0,0,59,ff]", //
    //            "[55,ff,1,6c,8,0,0,e3,ff]", //
    //            "[55]", //
    //            "[ff,1,6d,8,0,0,6a,ff]", //
    //            "[55,ff,1,6e,8]", //
    //            "[0,0,f2,ff]", //
    //            "[55,ff,1,6f,8,0,0,7b,ff]", //
    //            "[55,ff,1,70,8,0,0,d,ff]", //
    //            "[55,ff]", //
    //            "[1,71,8,0,0,84,ff]", //
    //            "[55,ff,1,72,8]", //
    //            "[0,0,1c,ff]", //
    //            "[55,ff,1,73,8,0,0,95,ff]", //
    //            "[55,ff,1,74,8,0,0,2f,ff]", //
    //            "[55,ff]", //
    //            "[1,75,8,0,0,a6,ff]", //
    //            "[55,ff,1,76,8,0]", //
    //            "[0,3e,ff]", //
    //            "[55,ff,1,77,8,0,0,b7,ff]", //
    //            "[55,ff,1,78,8,0,0,49,ff]", //
    //            "[55,ff]", //
    //            "[1,79,8,0,0,c0,ff]", //
    //            "[55,ff,1,7a,8,0]", //
    //            "[0,58,ff]", //
    //            "[55,ff,1,7b,8,0,0,d1,ff]", //
    //            "[55,ff,1,7c,8,0,0,6b,ff]", //
    //            "[55,ff]", //
    //            "[1,7d,8,0,0,e2,ff]", //
    //            "[55,ff,1,7e,8,0]", //
    //            "[0,7a,ff]", //
    //            "[55,ff,1,7f,8,0,0,f3,ff]", //
    //            "[55,ff,1,0,8,0,0,b0,ff]", //
    //            "[55,ff]", //
    //            "[1,1,8,0,0,39,ff]", //
    //            "[55,ff,1,2,8,0]", //
    //            "[0,a1,ff]", //
    //            "[55,ff,1,3,8,0,0,28,ff]", //
    //            "[55,ff]", //
    //            "[1,4,8,0,0,92,ff]", //
    //            "[55,ff,1,5,8,0]", //
    //            "[0,1b,ff]", //
    //            "[55,ff,1,6,8,0,0,83,ff]", //
    //            "[55,ff,1,7,8,0,0,a,ff]", //
    //            "[55,ff]", //
    //            "[1,9,8,0,0,7d,ff]", //
    //            "[55,ff,1,a,8,0]", //
    //            "[0,e5,ff]", //
    //            "[55,ff,1,b,8,0,0,6c,ff]", //
    //            "[55,ff,1,c,8,0,0,d6,ff]", //
    //            "[55,ff,1]", //
    //            "[d,8,0,0,5f,ff]", //
    //            "[55,ff,1,e,8,0]", //
    //            "[0,c7,ff]", //
    //            "[55,ff,1,f,8,0,0,4e,ff]", //
    //            "[55,ff,1,10,8,0,0,38,ff]", //
    //            "[55,ff,1]", //
    //            "[11,8,0,0,b1,ff]", //
    //            "[55,ff,1,12,8,0,0]", //
    //            "[29,ff]", //
    //            "[55,ff,1,13,8,0,0,a0,ff]", //
    //            "[55,ff,1,14,8,0,0,1a,ff]", //
    //            "[55,ff,1]", //
    //            "[15,8,0,0,93,ff]", //
    //            "[55,ff,1,16,8,0,0]", //
    //            "[b,ff]", //
    //            "[55,ff,1,17,8,0,0,82,ff]", //
    //            "[55,ff,1,18,8,0,0,7c,ff]", //
    //            "[55,ff,1]", //
    //            "[19,8,0,0,f5,ff]", //
    //            "[55,ff,1,1a,8,0,0]", //
    //            "[6d,ff]", //
    //            "[55,ff,1,1b,8,0,0,e4,ff]", //
    //            "[55,ff,1,1c,8,0,0,5e,ff]", //
    //            "[55,ff,1]", //
    //            "[1d,8,0,0,d7,ff]", //
    //            "[55,ff,1,1e,8,0,0]", //
    //            "[4f,ff]", //
    //            "[55,ff,1,1f,8,0,0,c6,ff]", //
    //            "[55,ff,1,20,8,0,0,a3,ff]", //
    //            "[55,ff,1]", //
    //            "[21,8,0,0,2a,ff]", //
    //            "[55,ff,1,22,8,0,0]", //
    //            "[b2,ff]", //
    //            "[55,ff,1,23,8,0,0,3b,ff]", //
    //            "[55,ff,1,24,8,0,0,81,ff]", //
    //            "[0]", //
    //            "[0]", //
    //            "[0]", //
    //            "[0]", //
    //            "[f8,0,0]", //
    //            "[fe,0,80]", //
    //            "[ff,fe]", //
    //            "[ff]", //
    //            "[ff]", //
    //            "[ff]", //
    //            "[ff]", //
    //            "[ff]", //
    };
}
