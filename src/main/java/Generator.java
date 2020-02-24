import java.util.ArrayList;

public class Generator {

    final static int NUMBER_OF_THREADS = 1;
    final static ArrayList<Thread> THREAD_POOL = new ArrayList<Thread>();

    public static void main(String[] args){
        for(int i = 0 ; i < NUMBER_OF_THREADS ; i++){

            Thread currentThread = new GeneratorThread();
            THREAD_POOL.add(currentThread);
            currentThread.start();

        }
    }

}