package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

public class Circle extends JComponent {
	public int x, y, diameter;
	public Color circleColor;

	public Circle(int x, int y, int diameter, Color circleColor) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.circleColor = circleColor;
		setPreferredSize(new Dimension(100, 100));
	}
	public void setCircleColor(Color circleColor) {
		this.circleColor = circleColor;
		repaint();
	}
	public void setDiameter(int diameter) {
        this.diameter = diameter;
        setPreferredSize(new Dimension(100, 90));
        revalidate();
        repaint();
    }
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(circleColor);
		g.fillOval(x, y, diameter, diameter);
	}

	
}