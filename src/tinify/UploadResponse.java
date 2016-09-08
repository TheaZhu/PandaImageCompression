package tinify;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UploadResponse {
    private Input input;
    private Output output;

    public static class Input {
        private int size;
        private String type;

        public int getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }

    public static class Output {
        private int size;
        private String type;
        private int width;
        private int height;
        private float ratio;
        private String url;

        public int getSize() {
            return size;
        }

        public String getType() {
            return type;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public float getRatio() {
            return ratio;
        }

        public String getUrl() {
            return url;
        }
    }

    public Input getInput() {
        return input;
    }

    public Output getOutput() {
        return output;
    }
}
