package xyz.myfur;

public class WaitTest {
    public static void main(String... a) throws InterruptedException {
        Xtest[] tests = {new Xtest(0),new Xtest(1)};
        tests[0].start();
        Thread.sleep(100);
        tests[1].start();
        //Thread.sleep(100);
        synchronized (tests[0]) {
            tests[0].notify();
        }synchronized (tests[1]) {
            tests[1].notify();
        }

    }

}
