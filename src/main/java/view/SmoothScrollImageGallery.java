package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SmoothScrollImageGallery {


  private static void smoothScrollToCenter(JScrollPane scrollPane, Rectangle targetBounds) {
    JViewport viewport = scrollPane.getViewport();
    Rectangle viewRect = viewport.getViewRect();
    int viewWidth = viewRect.width;


    int targetX = targetBounds.x - (viewWidth / 2) + (targetBounds.width / 2);

    int startX = viewport.getViewPosition().x;

    int distance = targetX - startX;

    Timer timer = new Timer(5, null);
    timer.addActionListener(
        new ActionListener() {
          private int step = 0;
          private final int totalSteps = 20;
          private final int dx = distance / totalSteps;

          @Override
          public void actionPerformed(ActionEvent e) {
            if (step < totalSteps) {
              viewport.setViewPosition(new Point(startX + dx * step, 0));
              step++;
            } else {
              viewport.setViewPosition(new Point(targetX, 0));
              timer.stop();
            }
          }
        });
    timer.start();
  }
}
