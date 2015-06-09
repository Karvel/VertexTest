/*
 Elanna Grossman
 June 9, 2015
 Java applet that listens to mouse and keyboard input to draw and connect points in a window.
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;
import java.util.ArrayList;
import java.applet.*;

public class VertexTestApplet extends JPanel
{
   private Point p1 = new Point(0, 0);
   private Point p2 = new Point(0, 0);
   private int x = 0;
   private int y = 0;
   private boolean mouseDrag = false;
   private boolean drawLine = false;
   private boolean draggingVert = false;
   final static int RADIUS = 20;
   ArrayList<Point> pointsList = new ArrayList<Point>();
   ArrayList<Point> edgesList = new ArrayList<Point>();
   
   public VertexTestApplet()
   {
      this.addMouseListener(new MouseAdapter()
                               {
         public void mouseClicked(MouseEvent e)
         {
            if (e.getButton() == MouseEvent.BUTTON1)//left click behavior
            {
               if (!inCircDiam(e.getPoint()))
               {
                  pointsList.add(e.getPoint());
                  repaint();
               }//end if
            }//end if
            else if (e.getButton() == MouseEvent.BUTTON3)//right click behavior
            {
               removeVertex(e.getPoint());
               repaint();
            }//end else if
         }//end mouseClicked
      });//end addMouseListener
      
      this.addMouseMotionListener(new MouseAdapter()
                                     {
         public void mouseDragged(MouseEvent e)
         {
            if (e.isControlDown())//control click and drag behavior
            {
               draggingVert = false;
               if (inCircDiam(e.getPoint()))
               {
                  mouseDrag = true;
                  if (!draggingVert)
                  {
                     removeVertex(e.getPoint());
                  }//end if
                  x = e.getX();
                  y = e.getY();
                  pointsList.add(e.getPoint());
                  repaint();
                  draggingVert = true;
               }//end if
            }//end if
            else //drag behavior
            {
               if (inCircRadius(e.getPoint()))
               {
                  drawLine = true;
                  p1.x = e.getPoint().x;
                  p1.y = e.getPoint().y;                  
               }//end if
               x = e.getX();
               y = e.getY();
               p2.x = x;
               p2.y = y;
               
               if (!inCircRadius(p2))
               {
                  edgesList.add(p1);
                  edgesList.add(p2);
                  repaint();
                  //drawLine = false;
                  //drawLine2 = true;
               }//end if
            }//end else
         }//end mouseDragged
      });//addMouseMotionListener
   }//end VertexTestApplet
   
   public boolean inCircDiam(Point point)
   {
      boolean inCircDiam = false;
      for (int i = 0; i < pointsList.size(); i++)
      {
         double distance = pointDistance(((Point)pointsList.get(i)).x, ((Point)pointsList.get(i)).y, point.x, point.y);  
         if (distance <= (RADIUS * 2))
         {
            inCircDiam = true;
         }//end if
      }//end for
      return inCircDiam;
   }//end inCircDiam
   
   public boolean inCircRadius(Point point)
   {
      boolean inCircRadius = false;
      for (int i = 0; i < pointsList.size(); i++)
      {
         double distance = pointDistance(((Point)pointsList.get(i)).x, ((Point)pointsList.get(i)).y, point.x, point.y);  
         if (distance <= RADIUS)
         {
            inCircRadius = true;
         }//end if
      }//end for
      return inCircRadius;
   }//end inCircRadius
   
   //Calculate difference between two points
   private double pointDistance(double x1, double y1, double x2, double y2)
   {
      return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
   }//end pointDistance
   
   private void removeVertex(Point point)
   {
      for (int i = 0; i < pointsList.size(); i++)
      {
         double distance = pointDistance(((Point)pointsList.get(i)).x, ((Point)pointsList.get(i)).y, point.x, point.y);  
         if (distance <= RADIUS)
         {
            pointsList.remove(i);
            break;
         }//end if
      }//end for
   }//end removeVertex
   
   @Override
   protected void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      for (int i = 0; i < pointsList.size(); i++)
      {
         g.fillOval(((Point) pointsList.get(i)).x - RADIUS, ((Point) pointsList.get(i)).y - RADIUS, 2 * RADIUS, 2 * RADIUS);
      }//end for 
      
      if (mouseDrag == true && pointsList.size() > 0)
      {
         g.fillOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
      }//end if
      
      if (drawLine == true && pointsList.size() > 0)
      {
         for (int ii = 0; ii < edgesList.size(); ii += 2)
         {
            if (edgesList.size() < 0)
            {
               g.drawLine(((Point) edgesList.get(ii)).x, ((Point) edgesList.get(ii)).y, ((Point) edgesList.get(ii + 1)).x, ((Point) edgesList.get(ii + 1)).y);
            }//end if
         }//end for
      }//end if
   }//end paintComponent
}//end VertexTestApplet