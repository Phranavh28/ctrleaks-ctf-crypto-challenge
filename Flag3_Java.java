import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class AESCTRDecryptor {

    private static final String ciphertextHex = "20c215a87443928d16e1993373955f2128a7633fabbe1874cb1f73cbd2d71306e11485486e1980f48e39b215e5ae7a66";
    private static final byte[] iv = "unimaginableness".getBytes(); // 16-byte IV
    private static final char[] allowedDigits = {'0', '1', '3', '4', '7', '9'};
    private static final int THREAD_COUNT = 6;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (char startDigit : allowedDigits) {
            executor.execute(new KeySearcher(startDigit));
        }
        executor.shutdown();
    }

    static class KeySearcher implements Runnable {
        private final char startDigit;

        KeySearcher(char startDigit) {
            this.startDigit = startDigit;
        }

        @Override
        public void run() {
            generateKeysAndTest("", 0);
        }

        private void generateKeysAndTest(String prefix, int depth) {
            if (depth == 16) {
                if (prefix.charAt(0) != startDigit) return;
                try {
                    byte[] key = new byte[16];
                    for (int i = 0; i < 16; i++) {
                        key[i] = (byte) prefix.charAt(i);
                    }
                    byte[] decrypted = decryptAESCTR(hexStringToByteArray(ciphertextHex), key, iv);
                    String result = new String(decrypted);
                    if (Pattern.matches("[a-z]+_[a-z]+_[a-z]+", result)) {
                        System.out.println("Key: " + prefix + " | Plaintext: " + result);
                    }
                } catch (Exception e) {
                    // Ignore malformed attempts
                }
                return;
            }
            for (char c : allowedDigits) {
                generateKeysAndTest(prefix + c, depth + 1);
            }
        }

        private byte[] decryptAESCTR(byte[] ciphertext, byte[] key, byte[] iv) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(ciphertext);
        }

        private byte[] hexStringToByteArray(String s) {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                    + Character.digit(s.charAt(i + 1), 16));
            }
            return data;
        }
    }
}
