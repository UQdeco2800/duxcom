package uq.deco2800.duxcom.maps.mapgen.encoder;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by liamdm on 18/08/2016.
 */
public class TextEncodingTest {

    @Test
    public void encodeDecodeTest(){
        String value = "this is a string with, many fdi!$%@#>>FEMW>DS?F?Q#WR !";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value);

        String initial = stringBuilder.toString();
        String encoded = TextEncoder.encode(initial);
        String decoded = TextEncoder.decode(encoded);
        assertEquals(initial, decoded);

        for(int i = 0; i < 10000; ++i){
            stringBuilder.append((char)new Random().nextInt(50) + 100);

             initial = stringBuilder.toString();
             encoded = TextEncoder.encode(initial);
             decoded = TextEncoder.decode(encoded);
            assertEquals(initial, decoded);
        }
    }
}