package tinify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import tinify.exception.ConnectionException;

import java.io.IOException;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class HttpClient {
    private OkHttpClient mClient;
    private String mCredentials;
    private String mUserAgent;

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final String API_ENDPOINT = "https://api.tinify.com";
    private static final String USER_AGENT = "Tinify/"
            + HttpClient.class.getPackage().getImplementationVersion()
            + " Java/" + System.getProperty("java.version")
            + " (" + System.getProperty("java.vendor")
            + ", " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + ")";

    private static final String URL_UPLOAD_IMAGE = "https://api.tinify.com/shrink";

    private static final String RESIZE_METHOD_SCALE = "scale";
    private static final String RESIZE_METHOD_FIT = "fit";
    private static final String RESIZE_METHOD_COVER = "cover";

    public HttpClient(final String key, final String appIdentifier) {
        mClient = new OkHttpClient();
        mCredentials = Credentials.basic("api", key);
        if (appIdentifier == null) {
            mUserAgent = USER_AGENT;
        } else {
            mUserAgent = USER_AGENT + " " + appIdentifier;
        }
    }

    public UploadResponse upload(String fileUrl) throws Exception {
        Options options = new Options().with("source", new Options().with("url", fileUrl));
        try {
            return toUploadResponse(post(URL_UPLOAD_IMAGE, options.toJson()));
        } catch (IOException e) {
            throw new ConnectionException("Error while connecting: " + e.getMessage(), e);
        }
    }

    public UploadResponse upload(byte[] data) throws Exception {
        try {
            return toUploadResponse(post(URL_UPLOAD_IMAGE, data));
        } catch (IOException e) {
            throw new ConnectionException("Error while connecting: " + e.getMessage(), e);
        }
    }

    public byte[] download(Record record) throws Exception {
        try {
            Response response = get(record.getUrl());
            if (response == null)
                return null;
            byte[] data = response.body().bytes();
            response.close();
            return data;
        } catch (IOException e) {
            throw new ConnectionException("Error while connecting: " + e.getMessage(), e);
        }
    }

    public byte[] resize(String url, String method, int width, int height) throws Exception {
        Options options = new Options().with("resize", new Options().with("method", method).with("width", width).with
                ("height", height));
        try {
            Response response = post(url, options.toJson());
            if (response == null)
                return null;
            byte[] result = response.body().bytes();
            response.close();
            return result;
        } catch (IOException e) {
            throw new ConnectionException("Error while connecting: " + e.getMessage(), e);
        }
    }

    public Response get(String url) throws IOException {
        Request request = requestBuilder(url)
                .get()
                .build();

        return mClient.newCall(request).execute();
    }

    public Response post(String url, String data) throws IOException {
        Request request = requestBuilder(url)
                .post(RequestBody.create(JSON, data))
                .build();

        return mClient.newCall(request).execute();
    }

    public Response post(String url, byte[] data) throws IOException {
        Request request = requestBuilder(url)
                .post(RequestBody.create(JSON, data))
                .build();

        return mClient.newCall(request).execute();
    }

    private UploadResponse toUploadResponse(Response response) throws Exception {
        String compressionCount = response.header("Compression-Count");
        if (compressionCount != null && !compressionCount.isEmpty()) {
            Tinify.setCompressionCount(Integer.valueOf(compressionCount));
        }

        if (response.isSuccessful()) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            UploadResponse uploadResponse = gson.fromJson(response.body().charStream(), UploadResponse.class);
            response.close();
            return uploadResponse;
        } else {
            throwException(response);
            response.close();
            return null;
        }
    }

    private void throwException(Response response) throws Exception {
        Exception.Data data;
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            data = gson.fromJson(response.body().charStream(), Exception.Data.class);
            if (data == null) {
                data = new Exception.Data();
                data.setMessage("Error while parsing response: received empty body");
                data.setError("ParseError");
            }
        } catch (com.google.gson.JsonParseException e) {
            data = new Exception.Data();
            data.setMessage("Error while parsing response: " + e.getMessage());
            data.setError("ParseError");
        }

        throw Exception.create(
                data.getMessage(),
                data.getError(),
                response.code());
    }

    private Request.Builder requestBuilder(String endpoint) {
        HttpUrl url;
        if (endpoint.startsWith("https") || endpoint.startsWith("http")) {
            url = HttpUrl.parse(endpoint);
        } else {
            url = HttpUrl.parse(API_ENDPOINT + endpoint);
        }
        return new Request.Builder()
                .url(url)
                .header("authorization", mCredentials)
                .header("content-type", "application/json")
                .header("User-Agent", mUserAgent);
    }
}
