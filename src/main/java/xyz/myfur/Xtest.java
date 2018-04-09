package xyz.myfur;

public class Xtest extends Thread{
    public int i;

    public Xtest(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                this.wait();
                System.out.println("Я отработал"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
