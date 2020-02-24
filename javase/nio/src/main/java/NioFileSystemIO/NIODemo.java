package NioFileSystemIO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Author: zouxiang
 * Date: 2020/2/20
 * Description: No Description
 */
public class NIODemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("D:\\temp\\test.txt", "r");
        FileChannel fc = raf.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);
        int len = fc.read(buf);
        if (len > 0) {
            buf.flip();
            while (buf.hasRemaining()){
                byte ch = buf.get();
                System.out.println((char)ch);
            }
        }
    }
}
