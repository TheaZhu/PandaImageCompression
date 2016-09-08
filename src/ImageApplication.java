import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;
import tinify.Tinify;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ImageApplication implements ApplicationComponent {
    public ImageApplication() {
    }

    @Override
    public void initComponent() {
        Tinify.setKey(SettingsModel.getInstance().getPandaApiKey());
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "ImageApplication";
    }
}
