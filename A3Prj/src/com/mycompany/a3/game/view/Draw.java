package com.mycompany.a3.game.view;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.util.Color;

/*
 * Reusable functions generate various shapes 
 */

/**
 * 
 * @param location	The center point of the gameObject
 * @param containerOrigin	The top-left point of the container
 * @param size	The size of the gameObject. Size of the shape representing the gameObject is relative to its size
 * @param color	The color of the shape representing the gameObject
 */

public class Draw
{
    private static class Vertices
    {
        int leftX, middleX, rightX, bottomY, topY;
    }

    public static void unfilledCircle(Graphics g, Point location, Point containerOrigin, int size, Color color)
    {
        int x = Math.round(containerOrigin.getX() + location.getX() - size);
        int y = Math.round(containerOrigin.getY() + location.getY() - size);
        g.setColor(color.toInt());
        g.drawArc(x, y, size * 2, size * 2, 0, 360);
    }

    public static void unfilledTriangle(Graphics g, Point location, Point containerOrigin, int size, Color color)
    {
        Vertices v = computeTriangleVertices(location, containerOrigin, size);
        g.setColor(color.toInt());
        g.drawPolygon(new int[]{v.leftX, v.middleX, v.rightX}, new int[]{v.bottomY, v.topY, v.bottomY}, 3);
    }

    public static void filledTriangle(Graphics g, Point location, Point containerOrigin, int size, Color color)
    {
        Vertices v = computeTriangleVertices(location, containerOrigin, size);
        g.setColor(color.toInt());
        g.fillTriangle(v.leftX, v.bottomY, v.middleX, v.topY, v.rightX, v.bottomY);
    }

    public static void text(String str, Graphics g, Point location, Point containerOrigin, float xAlign, float yAlign,
                            Color color)
    {
        int x = Math.round(containerOrigin.getX() + location.getX() + xAlign);
        int y = Math.round(containerOrigin.getY() + location.getY() + yAlign);
        g.setColor(color.toInt());
        g.drawString(str, x, y);
    }

    private static Vertices computeTriangleVertices(Point location, Point containerOrigin, float size)
    {
        float root3 = 1.732050808f;
        float locationX = location.getX() + containerOrigin.getX();
        float locationY = location.getY() + containerOrigin.getY();
        Vertices v = new Vertices();
        v.leftX = Math.round(locationX - size * root3 / 2);
        v.middleX = Math.round(locationX);
        v.rightX = Math.round(locationX + size * root3 / 2);
        v.bottomY = Math.round(locationY - size / 2);
        v.topY = Math.round(locationY + size);
        return v;
    }
}
