package tinify;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class Record {
    private String sourcePath;
    private int sourceSize;
    private String sourceType;

    private int targetSize;
    private String targetType;
    private int targetWidth;
    private int targetHeight;

    private float ratio;
    private String url;

    public Record(UploadResponse response) {
        sourceSize = response.getInput().getSize();
        sourceType = response.getInput().getType();
        targetSize = response.getOutput().getSize();
        targetType = response.getOutput().getType();
        targetWidth = response.getOutput().getWidth();
        targetHeight = response.getOutput().getHeight();
        ratio = response.getOutput().getRatio();
        url = response.getOutput().getUrl();
    }

    public Record(UploadResponse response, String sourcePath) {
        this(response);
        this.sourcePath = sourcePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public int getSourceSize() {
        return sourceSize;
    }

    public void setSourceSize(int sourceSize) {
        this.sourceSize = sourceSize;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public int getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(int targetSize) {
        this.targetSize = targetSize;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSavedSize() {
        return (sourceSize - targetSize) / 1024;
    }
}
