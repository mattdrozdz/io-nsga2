package io.nsga2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Mateusz Drożdż on 19.11.15.
 * see: http://www.hindawi.com/journals/tswj/2014/364179/fig5/
 */
public class Hypervolume {

    private static final Point referencePoint = new Point(11.0, 11.0);
    //    for ref. point (11, 11)
    private static final double referenceHvZDT1 = 120.6666666;

    public static double hvrZDT1(List<DoubleSolution> front) {
        return hv(front) / referenceHvZDT1;
    }

    public static double hv(List<DoubleSolution> front) {
        List<Point> points = new ArrayList<>();
        for (DoubleSolution solution : front) {
            points.add(Point.fromSolution(solution));
        }
        return calculateHv(points);
    }

    public static double hvZDT1() {
        return calculateHv(loadParetoFront("ZDT1.pf"));
    }

    private static double calculateHv(List<Point> front) {
        double volume = 0.0;
        Collections.sort(front, (o1, o2) -> Double.compare(o1.getX(), o2.getX()));
        volume += findArea(referencePoint, front.get(0));
        for (int i = 1; i < front.size(); i++) {
            volume += findArea(referencePoint, front.get(i - 1), front.get(i));
        }
        return volume;
    }

    private static double findArea(Point refPoint, Point point) {
        return Math.abs(refPoint.getX() - point.getX()) * Math.abs(refPoint.getY() - point.getY());
    }

    private static double findArea(Point refPoint, Point point1, Point point2) {
        return Math.abs(point1.getY() - point2.getY()) * Math.abs(refPoint.getX() - point2.getX());
    }

    public static List<Point> loadParetoFront(String filename) {
        List<Point> front = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        String splitBy = " ";
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] point = line.split(splitBy);
                front.add(new Point(Double.parseDouble(point[0]), Double.parseDouble(point[1])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return front;
    }

    public static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public static Point fromSolution(DoubleSolution solution) {
            return new Point(solution.getObjective(0), solution.getObjective(1));
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
