import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Common {

    private String filePath="";

    //查找要被导出的文件路径 dir 是在什么路径路径下  fileclassname class文件名 或者 其他文件名
    public String searchFile(File dir, String fileClassPath){
        if(dir.isDirectory()){
            File next[] = dir.listFiles();
            for (int i=0;i<next.length;i++){
                if(next[i].isFile()){
                    String tmpPath = next[i].getPath();
                    tmpPath = tmpPath.replaceAll("\\\\","/");
                    if(tmpPath.contains(fileClassPath)){
                        filePath =  tmpPath;
                    }
                }else{
                    searchFile(next[i],fileClassPath);
                }
            }
        }
        return filePath;
    }

    public Boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[16777216];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
                return true;
            }
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    // 导出文件导桌面 filepath 被导出文件路径   fileName 是被导出文件名
    public Boolean downdesktop(String filePath,String projectName ,String newPath,String fileName){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File desktop=fsv.getHomeDirectory();
        String desktopPath = desktop.getPath();
        desktopPath = desktopPath+"/"+projectName+"/"+newPath;
        File file = new File(desktopPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return copyFile(filePath,desktopPath+"/"+fileName);
    }
}
