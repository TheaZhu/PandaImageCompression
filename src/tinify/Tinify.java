package tinify;

import tinify.exception.AccountException;
import tinify.exception.ClientException;

import java.io.IOException;

public class Tinify {
    private static String key;
    private static String appIdentifier;
    private static int compressionCount = 0;
    private static HttpClient sClient;

    public static HttpClient httpClient() {
        if (key == null) {
            throw new AccountException("Provide an API key with Tinify.setKey(...)");
        }
        if (sClient != null) {
            return sClient;
        } else {
            synchronized (Tinify.class) {
                if (sClient == null) {
                    sClient = new HttpClient(key(), appIdentifier());
                }
            }
            return sClient;
        }
    }

    public static void setKey(final String key) {
        Tinify.key = key;
        sClient = null;
    }

    public static void setAppIdentifier(final String identifier) {
        Tinify.appIdentifier = identifier;
        sClient = null;
    }

    public static Source fromFile(final String path) throws IOException {
        return Source.fromFile(path);
    }

    public static Source fromUrl(final String url) {
        return Source.fromUrl(url);
    }

    public static boolean validate() {
        try {
            httpClient().upload(new byte[]{});
        } catch (AccountException ex) {
            if (ex.status == 429) return true;
            throw ex;
        } catch (ClientException ex) {
            return true;
        }
        return false;
    }

    public static String key() {
        return key;
    }

    public static String appIdentifier() {
        return appIdentifier;
    }

    public static void setCompressionCount(final int count) {
        compressionCount = count;
    }

    public static int compressionCount() {
        return compressionCount;
    }
}
