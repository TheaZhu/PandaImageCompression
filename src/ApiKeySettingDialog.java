import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiKeySettingDialog extends DialogWrapper {
    private static final String TITLE = "API Key Setting";

    private static final String URL_GET_API_KEY = "https://tinypng.com/developers";

    private final Project mProject;

    private JPanel mCenterPanel;
    private JTextField mApiKey;
    private JLabel mGetKeyLabel;

    ApiKeySettingDialog(@Nullable Project project) {
        super(project);

        mProject = project;

        setTitle(TITLE);
        initLabelLink(mGetKeyLabel, URL_GET_API_KEY);

        init();
    }

    @Override
    protected void doOKAction() {
        SettingsModel.getInstance().setPandaApiKey(mApiKey.getText());
        super.doOKAction();
    }

    private void initLabelLink(JLabel label, final String url) {
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(url);
                            desktop.browse(uri);
                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mCenterPanel;
    }
}
