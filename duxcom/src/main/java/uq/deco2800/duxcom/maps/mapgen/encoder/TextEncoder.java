package uq.deco2800.duxcom.maps.mapgen.encoder;

import uq.deco2800.duxcom.annotation.UtilityConstructor;

import java.util.Base64;

/**
 * Used to encode text in a format that won't
 * mess with known delineators. At the moment this
 * is just a wrapper for Base64 encode but may
 * perform more features in the future.
 *
 * Created by liamdm on 18/08/2016.
 */
public class TextEncoder {

    /**
     * Decodes the given encoded string
     * @param encoded
     * @return the decoded string
     */
    public static String decode(String encoded){
        return new String(Base64.getDecoder().decode(encoded));
    }

    /**
     * Encodes the given raw string
     * @param raw
     * @return the encoded string
     */
    public static String encode(String raw){
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }


    @UtilityConstructor
    private TextEncoder(){}
}
