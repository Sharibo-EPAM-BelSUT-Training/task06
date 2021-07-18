package gmail.alexejkrawez.multithreading;

import java.awt.*;

public class Monitor {

    private int bool = 0; // свободен
    private Color color = null;


    synchronized public int isMonitorFreeTask1() {
        if (bool >= 1) {
            try {
                this.wait();
                return 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            bool = 1;
        }
        return 2;
    }

    synchronized public int isMonitorFreeTask2(ThreadSquare threadSquare) {
        if (bool >= 1) {
            if ((threadSquare.getColor()).equals(color)) {
                return bool += 1;
            }

            try {
                this.wait();
                return 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            color = threadSquare.getColor();
            bool = 1;
        }
        return 2;
    }

    synchronized public void monitorIsFreeTask1() {
        bool = 0;
        this.notify();
    }

    synchronized public void monitorIsFreeTask2() {
        if (bool == 1) {
            bool = 0;
            this.notifyAll();
        } else {
            bool -= 1;
        }

    }

}




















