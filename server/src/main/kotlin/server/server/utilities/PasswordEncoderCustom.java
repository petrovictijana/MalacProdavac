package server.server.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import server.server.dtos.HashedPassword;

import java.security.SecureRandom;

@Component
public class PasswordEncoderCustom {

    public static HashedPassword passwordEncoder(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String salt = generateSalt(10);

        return new HashedPassword(passwordEncoder.encode(password + salt), salt);
    }

    public static String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return bytesToHex(salt); // Konvertuje bajtove u heksadecimalni string
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
