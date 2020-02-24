import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class MappedIO {
    private static int numOfInts = 400000000;
    private static int numOfUbuffInts = 200000;

    private abstract static class Tester {
        private String name;
        public Tester(String name){
            this.name = name;
        }
        public void runTest(){
            System.out.print(name + ": ");
            try{
                long start = System.nanoTime();
                test();
                double duration = System.nanoTime() - start;
                System.out.format("%.2f\n", duration/1.0e9);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        public abstract void test() throws IOException;
    }

    private static Tester[] tests = {
         /*   new Tester("Stream Write") {
                @Override
                public void test() throws IOException {
                    DataOutputStream dos = new DataOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(new File("temp.tmp"))
                            )
                    );
                    for (int i=0; i<numOfInts; i++){
                        dos.writeInt(i);
                    }
                    dos.close();
                }
            },
            new Tester("Mapped Write") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile("temp.tmp", "rw")
                            .getChannel();
                    IntBuffer ib = fc.map(
                            FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                    for (int i=0; i<numOfInts; i++){
                        ib.put(i);
                    }
                    ib = null;
                    fc.close();
                    fc = null;
                }
            },*/
            new Tester("Stream Read") {
                @Override
                public void test() throws IOException {
                    DataInputStream dis = new DataInputStream(
                            new BufferedInputStream(
                                    new FileInputStream("temp.tmp"))
                    );
                    for (int i=0; i<numOfInts; i++){
                        dis.readInt();
                    }
                    dis.close();
                }
            },
            new Tester("NIO Read") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new FileInputStream(
                            new File("temp.tmp")).getChannel();
                    ByteBuffer buf = ByteBuffer.allocate(128);
                    int total = 0;
                    while (true){
                        int len = fc.read(buf);
                        buf.clear();
                        total += len;
//                        System.out.println(total);
                        if (total >= numOfInts*4){
                            break;
                        }
                    }
                }
            },
            new Tester("Mapped Read") {
                @Override
                public void test() throws IOException {
                    FileChannel fc = new FileInputStream(
                            new File("temp.tmp")).getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
                    while (ib.hasRemaining())
                        ib.get();
                    fc.close();
                }
            }
    };

    public static void main(String[] args) {
        for (Tester tester : tests){
            tester.runTest();
        }
    }
}
