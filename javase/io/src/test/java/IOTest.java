import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Author: zouxiang
 * Date: 2020/3/13
 * Description: No Description
 */
public class IOTest {
    public static final String file = "D:\\temp\\1.zip";  // 大约1.17GB

    final Task[] tasks = new Task[]{
            new Task("FileInputStream.read(byte[])") {
                @Override
                public void run() throws IOException {
                    FileInputStream fis = new FileInputStream(file);
                    byte[] buf = new byte[1024 * 4];
                    int len;
                    int total = 0;
                    while ((len = fis.read(buf)) > 0) {
                        total += len;
                    }
                    System.out.println(total);
                    fis.close();
                }
            },
            new Task("FileChannel.read(ByteBuffer)") {
                @Override
                public void run() throws IOException {
                    FileChannel fc = FileChannel.open(Paths.get(file));
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 4);
                    int len;
                    int total = 0;
                    while ((len = fc.read(byteBuffer)) > 0) {
                        total += len;
                        byteBuffer.clear();
                    }
                    System.out.println(total);
                    fc.close();
                }
            },
            new Task("MappedByteBuffer.get()") {
                @Override
                public void run() throws IOException {
                    FileChannel fc = FileChannel.open(Paths.get(file));
                    MappedByteBuffer mb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                    int total = 0;
                    while (mb.hasRemaining()) {
                        mb.get();
                        total++;
                    }
                    System.out.println(total);
                    fc.close();
                }
            }
    };

    @Test
    public void testRead() throws IOException {
        for (Task task : tasks) {
            long start = System.nanoTime();
            System.out.println("开始运行: " + task.getName());
            task.run();
            long end = System.nanoTime();
            System.out.println("总耗时: " + TimeUnit.NANOSECONDS.toMillis(end - start));
            System.out.println();
        }
    }

    static abstract class Task {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        public abstract void run() throws IOException;

        public String getName() {
            return name;
        }
    }
}
