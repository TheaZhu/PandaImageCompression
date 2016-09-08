package tinify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Source {
    private Record mRecord;

    private Source(final Record record) {
        mRecord = record;
    }

    static Source fromFile(final String path) throws IOException {
        UploadResponse response = Tinify.httpClient().upload(Files.readAllBytes(Paths.get(path)));
        return new Source(new Record(response, path));
    }

    static Source fromUrl(final String url) {
        UploadResponse response = Tinify.httpClient().upload(url);
        return new Source(new Record(response, url));
    }

    public Record download(String filePath) throws IOException {
        if (mRecord == null) {
            throw new Exception("Did not upload a file or upload file failed.");
        }
        byte[] data = Tinify.httpClient().download(mRecord);
        Files.write(Paths.get(filePath), data);
        return mRecord;
    }
}
