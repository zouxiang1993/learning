package NioFileSystemIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Author: zouxiang
 * Date: 2020/2/20
 * Description: No Description
 */
public class BIODemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(new File("D:\\temp\\test.txt"));
        byte[] buf = new byte[1024];
        int len = fis.read(buf);
        for (int i=0; i<len; i++){
            System.out.println((char)buf[i]);
        }
    }
}
