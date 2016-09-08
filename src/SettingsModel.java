import com.intellij.ide.util.PropertiesComponent;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class SettingsModel {
    public static final String NOTIFY_GROUP_ID = "PandaImageCompression";

    private static final String PREFIX = "PandaImageCompression";

    private static final String PANDA_API_KEY = "panda_api_key";
    private static final String DEFAULT_PANDA_API_KEY = "Msa18OVr80DCBIGcZKCTgKQt33_CmcK-";

    private static final String ACTION_TYPE = "action_type";
    public static final int ACTION_TYPE_ASK = 0;
    public static final int ACTION_TYPE_OVERWRITE = 1;
    public static final int ACTION_TYPE_RENAME = 2;

    private SettingsModel() {
    }

    public static SettingsModel getInstance() {
        return Singleton.INSTANCE;
    }

    public String getPandaApiKey() {
        return getValue(PANDA_API_KEY, "");
    }

    public void setPandaApiKey(String apiKey) {
        setValue(PANDA_API_KEY, apiKey);
    }

    public int getActionType() {
        return ACTION_TYPE_OVERWRITE;
    }

    private String getValue(String key, String def) {
        return PropertiesComponent.getInstance().getValue(PREFIX + "." + key, def);
    }

    private void setValue(String key, String value) {
        PropertiesComponent.getInstance().setValue(PREFIX + "." + key, value);
    }

    private static class Singleton {
        static final SettingsModel INSTANCE = new SettingsModel();
    }
}
