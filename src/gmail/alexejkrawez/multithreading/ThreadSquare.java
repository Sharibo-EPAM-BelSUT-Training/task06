package gmail.alexejkrawez.multithreading;

import gmail.alexejkrawez.CrossRoad;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ThreadSquare implements Runnable{
    private final boolean horVert;
    private int x, y;
    private int direction = 1;
    JButton button;
    private final Container contentPane;
    private Monitor monitor;

    @Override
    public void run() {
        monitor = CrossRoad.getMonitor();

        while(true) {
            if (Settings.numberTask) {
                getNextPointTask1();
            } else {
                getNextPointTask2();
            }

            move();
        }
    }


    public Color getColor() {
        return button.getBackground();
    }

    public ThreadSquare(boolean horVert) {
        this.horVert = horVert;
        if(horVert) {
            x = 0;
            y = Settings.POINT_CROSS_ROAD.y + (int)(Math.random() * (Settings.DIMENSION_CROSS_ROAD.height - Settings.HEIGHT_SQUARE));
        } else {
            x = Settings.POINT_CROSS_ROAD.x + (int)(Math.random() * (Settings.DIMENSION_CROSS_ROAD.width - Settings.WIDTH_SQUARE));
            y = 0;
        }
        this.contentPane = Settings.contentPane;
        initButton();
    }


    private void getNextPointTask1() {
        if(horVert) {
            x += Settings.STEP_SQUARE * direction;

            /*Спрашивает, свободен ли перекрёсток*/
            if (x >= Settings.POINT_CROSS_ROAD.x - Settings.WIDTH_SQUARE &
                x <= Settings.POINT_CROSS_ROAD.x + Settings.DIMENSION_CROSS_ROAD.width) {

                if (monitor.isMonitorFreeTask1() == 2) {
                    while (x >= Settings.POINT_CROSS_ROAD.x - Settings.WIDTH_SQUARE &
                           x <= Settings.POINT_CROSS_ROAD.x + Settings.DIMENSION_CROSS_ROAD.width) {
                        x += Settings.STEP_SQUARE * direction;
                        move();
                    }

                    monitor.monitorIsFreeTask1();
                }
            }


            if (x >= Settings.FRAME_WIDTH - Settings.WIDTH_SQUARE){
                direction = -1;
            }

            if (x <= 0) {
                direction = 1;
            }

        } else {
            y += Settings.STEP_SQUARE * direction;

            /*Спрашивает, свободен ли перекрёсток*/
            if (y >= Settings.POINT_CROSS_ROAD.y - Settings.HEIGHT_SQUARE &
                y <= Settings.POINT_CROSS_ROAD.y + Settings.DIMENSION_CROSS_ROAD.height) {

                if (monitor.isMonitorFreeTask1() == 2) {
                    while (y >= Settings.POINT_CROSS_ROAD.y - Settings.HEIGHT_SQUARE &
                           y <= Settings.POINT_CROSS_ROAD.y + Settings.DIMENSION_CROSS_ROAD.height) {
                        y += Settings.STEP_SQUARE * direction;
                        move();
                    }

                    monitor.monitorIsFreeTask1();
                }
            }


            if (y >= Settings.FRAME_HEIGHT - Settings.HEIGHT_SQUARE - 24) {
                direction = -1;
            }

            if (y <= 0) {
                direction = 1;
            }
        }

    }


    private void getNextPointTask2() {
        if(horVert) {
            x += Settings.STEP_SQUARE * direction;

            /*Спрашивает, свободен ли перекрёсток*/
            if (x >= Settings.POINT_CROSS_ROAD.x - Settings.WIDTH_SQUARE &
                x <= Settings.POINT_CROSS_ROAD.x + Settings.DIMENSION_CROSS_ROAD.width) {

                if (monitor.isMonitorFreeTask2(this) >= 2) {
                    while (x >= Settings.POINT_CROSS_ROAD.x - Settings.WIDTH_SQUARE &
                           x <= Settings.POINT_CROSS_ROAD.x + Settings.DIMENSION_CROSS_ROAD.width) {
                        x += Settings.STEP_SQUARE * direction;
                        move();
                    }

                    monitor.monitorIsFreeTask2();

                } else {
                    x -= Settings.STEP_SQUARE * direction; // чтобы не ползла вперёд, если ей говорят ждать.
                }
            }


            if(x >= Settings.FRAME_WIDTH - Settings.WIDTH_SQUARE){
                direction = -1;
            }

            if(x <= 0) {
                direction = 1;
            }

        } else {
            y += Settings.STEP_SQUARE * direction;

            /*Спрашивает, свободен ли перекрёсток*/
            if (y >= Settings.POINT_CROSS_ROAD.y - Settings.HEIGHT_SQUARE &
                y <= Settings.POINT_CROSS_ROAD.y + Settings.DIMENSION_CROSS_ROAD.height) {

                if (monitor.isMonitorFreeTask2(this) >= 2) {
                    while (y >= Settings.POINT_CROSS_ROAD.y - Settings.HEIGHT_SQUARE &
                           y <= Settings.POINT_CROSS_ROAD.y + Settings.DIMENSION_CROSS_ROAD.height) {
                        y += Settings.STEP_SQUARE * direction;
                        move();
                    }

                    monitor.monitorIsFreeTask2();

                } else {
                    y -= Settings.STEP_SQUARE * direction; // чтобы не ползла вперёд, если ей говорят ждать.
                }
            }


            if (y >= Settings.FRAME_HEIGHT - Settings.HEIGHT_SQUARE - 24) {
                direction = -1;
            }

            if (y <= 0) {
                direction = 1;
            }
        }

    }

    private void move() {
        button.setBounds(x, y, Settings.WIDTH_SQUARE, Settings.HEIGHT_SQUARE);
        try{
            Thread.sleep(Settings.DELAY_SQUARE);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private void initButton(){
        Random random = new Random();
        Color[] color = {new Color(0,0,0),
                         new Color(255,0,0),
                         new Color(0,255,0),
                         new Color(255,165,122),
                         new Color(255,215,0),
                         new Color(0,0,255),
                         new Color(255,0,255),
                         new Color(190,190,190)};
        button = new JButton("" + (++Settings.counter));
        button.setLocation(x, y);
        button.setSize(Settings.WIDTH_SQUARE, Settings.HEIGHT_SQUARE);
        button.setMargin(new Insets(0,0,0,0));
        button.setForeground(Settings.COLOR_TEXT_SQUARE);
        button.setBackground(color[random.nextInt(color.length)]);
        contentPane.add(button);
    }

}