
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.ui.Messages;

import java.io.*;

public class exportClassAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        VirtualFile[] vfs = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        String path = project.getBasePath(); //项目路径
        String projectName = project.getName(); //项目名
        String home = "WebRoot";  //根目录
//        String pathHome = path+"/"+home;  //拼接根目录  webroot
        Common common = new Common();
        for (VirtualFile vf :vfs){
            if(vf.isDirectory()){
                Messages.showMessageDialog(project,"您选择了文件夹，请选择文件！",
                        "警告",Messages.getWarningIcon());
            }else{
                //需要被查找文件路径
                String clickFile = vf.getPath();
                String moduleName =clickFile.replace(path,"").split("/")[1];
                String pathHome = path+"/"+moduleName+"/"+home;  //拼接根目录  webroot
                String exportClass = vf.getPath().replace(pathHome,"").replace(path+"/"+moduleName+"/src","").replace(".java",".class");
                //查找出来的文件路径
                String exportClassPath =  common.searchFile(new File(pathHome),exportClass);
                //要导出的路径
                String newPath = exportClassPath.replace(pathHome,"");
                String fileName = newPath.substring(newPath.lastIndexOf("/")+1);
                newPath = newPath.substring(0,newPath.lastIndexOf("/")+1);
                //导出到桌面
                boolean flag = common.downdesktop(exportClassPath,projectName+"/"+moduleName,newPath,fileName);
                if(flag == false){
                    Messages.showMessageDialog(project,"这个文件没能导出成功:'"+vf.getName()+"'",
                            "很遗憾出错了！",Messages.getErrorIcon());
                }
            }

        }
        Messages.showMessageDialog(project,"导出执行完毕!",
                "通知",Messages.getInformationIcon());

    }


}
