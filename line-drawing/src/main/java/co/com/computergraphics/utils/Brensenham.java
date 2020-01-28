package co.com.computergraphics.utils;


import java.util.ArrayList;
import java.util.List;

public class Brensenham {
    // This algorithm was taken from: https://es.wikipedia.org/wiki/Algoritmo_de_Bresenham
    public List<Point> getPoints(Point p1, Point p2) {
        int x1 = p1.getX(), y1 = p1.getY(), x2 = p2.getX(), y2 = p2.getY();
        List<Point> points = new ArrayList<>();
        int dy = y2 - y1;
        int dx = x2 - x1;
        int incyi, incxi, incxr, incyr;
        if (dy >= 0) {
            incyi = 1;
        } else {
            dy = -dy;
            incyi = -1;
        }
        if (dx >= 0) {
            incxi = 1;
        } else {
            dx = -dx;
            incxi = -1;
        }
        if (dx >= dy) {
            incyr = 0;
            incxr = incxi;
        } else {
            incxr = 0;
            incyr = incyi;
            dx ^= dy;
            dy ^= dx;
            dx ^= dy;
        }
        int x = x1;
        int y = y1;
        int avr = 2 * dy;
        int av = avr - dx;
        int avi = av - dx;
        do {
            points.add(new Point(x, y));
            if (av >= 0) {
                x += incxi;
                y += incyi;
                av += avi;
            } else {
                x += incxr;
                y += incyr;
                av += avr;
            }
        } while (x != x2);
        return points;
    }
}
