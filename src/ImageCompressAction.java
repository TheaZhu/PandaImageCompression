import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import tinify.Exception;
import tinify.Record;
import tinify.Source;
import tinify.Tinify;

import java.io.IOException;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ImageCompressAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file == null || !ImageUtils.canCompress(file.getExtension()))
            return;
        if (Tinify.key().equals("")) {
            if (SettingsModel.getInstance().getPandaApiKey().equals("")) {
                new ApiKeySettingDialog(getEventProject(e)).show();
                return;
            } else
                Tinify.setKey(SettingsModel.getInstance().getPandaApiKey());
        }
        int count = 0;
        switch (SettingsModel.getInstance().getActionType()) {
            case SettingsModel.ACTION_TYPE_OVERWRITE:
                try {
                    Source source = Tinify.fromFile(file.getPath());
                    Record record = source.download(file.getPath());
                    count += record.getSavedSize();
                } catch (IOException | Exception ex) {
                    ex.printStackTrace();
                    Notifications.Bus.notify(new Notification(SettingsModel.NOTIFY_GROUP_ID, "Fail", "Compress " +
                            file.getPath() + " failed", NotificationType.INFORMATION));
                }
                break;
            case SettingsModel.ACTION_TYPE_RENAME:
                ImageCompressDialog dialog = new ImageCompressDialog(getEventProject(e), file);
                dialog.show();
                break;
            default:
                break;
        }

        if (count > 0) {
            String notifyContent = "Panda just saved you " + count + "KB.";
            Notifications.Bus.notify(new Notification(SettingsModel.NOTIFY_GROUP_ID, "Success", notifyContent,
                    NotificationType.INFORMATION));
        }
    }
}
