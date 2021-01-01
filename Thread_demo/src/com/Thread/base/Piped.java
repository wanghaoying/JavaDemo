package com.Thread.base;

import javax.print.attribute.standard.PresentationDirection;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {

    public static void main(String[] args) throws IOException {
        PipedWriter in = new PipedWriter();
        PipedReader out = new PipedReader();

        in.connect(out);

        Thread printThread = new Thread(new PrintThread(out),"printThread");
        printThread.start();

        int receive = 0;

        try{
            while((receive = System.in.read()) != -1){
                in.write(receive);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            in.close();
            out.close();
        }




    }
}

class PrintThread extends Thread{
    private PipedReader out;

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public PrintThread(PipedReader out) {
        this.out = out;
    }

    @Override
    public void run(){
        int receive = 0;
        try{
            while((receive = out.read()) != -1){
                System.out.print((char) receive);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
