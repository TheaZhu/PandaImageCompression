import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ImageUtils {

    public static boolean compress(File sourceFile, String targetPath) {
        return true;
    }

    public static boolean canCompress(@Nullable String extension) {
        return extension != null && (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg"));
    }
}
