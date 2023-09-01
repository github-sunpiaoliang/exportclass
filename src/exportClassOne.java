import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;

public class exportClassOne extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e)  {
        Project project = (Project)e.getData(PlatformDataKeys.PROJECT);
        VirtualFile[] vfs = (VirtualFile[])e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        String path = project.getBasePath();
        String projectName = project.getName();
        String home = "WebRoot";
        String pathHome = path + "/" + home;
        Common common = new Common();
        for (VirtualFile vf : vfs) {
            if (vf.isDirectory())
            {
                Messages.showMessageDialog(project, "您选择了文件夹,请选择文件", "警告",
                        Messages.getWarningIcon());
            }
            else
            {
                String exportClass = vf.getPath().replace(pathHome, "").replace(path + "/src", "").replace(".java", ".class");

                String exportClassPath = common.searchFile(new File(pathHome), exportClass);

                String newPath = exportClassPath.replace(pathHome, "");
                String fileName = newPath.substring(newPath.lastIndexOf("/") + 1);
                newPath = newPath.substring(0, newPath.lastIndexOf("/") + 1);

                boolean flag = common.downdesktop(exportClassPath, projectName, newPath, fileName).booleanValue();
                if (!flag) {
                    Messages.showMessageDialog(project, "这个文件没能导出成功:'" + vf.getName() + "'", "很遗憾出错了",
                            Messages.getErrorIcon());
                }
            }
        }
        Messages.showMessageDialog(project, "导出执行完毕!", "通知",
                Messages.getInformationIcon());
    }
}
