import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class MappedWrite {
    public static void main(String[] args) throws IOException {
        final int numOfInts = 100000000;
        RandomAccessFile file = new RandomAccessFile(
                new File("MappedWrite.tmp"), "rw");
        FileChannel fc = file.getChannel();
        long pos = 0;
        int size = 4*1024*1024;
        IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, pos, size).asIntBuffer();
        for (int i=0; i<numOfInts; i++){
            if (ib.hasRemaining() == false){
                pos += size;
                ib = fc.map(FileChannel.MapMode.READ_WRITE, pos, size).asIntBuffer();
            }
            ib.put(i);
        }
        file.close();
    }
}
