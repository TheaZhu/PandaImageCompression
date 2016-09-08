import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ImageCompressDialog extends DialogWrapper {
    public static final String TITLE = "Image Compression";

    private final Project mProject;
    private final VirtualFile mFile;

    private JPanel mCenterPanel;


    ImageCompressDialog(@Nullable Project project, VirtualFile file) {
        super(project);

        mProject = project;
        mFile = file;
    }

    @Override
    protected void doOKAction() {
        String sourcePath = mFile.getCanonicalPath();
        String targetPath = mProject.getBasePath() + "/resources/berlin_compressed.png";
        System.out.println("source path: " + sourcePath);
        super.doOKAction();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mCenterPanel;
    }
}
