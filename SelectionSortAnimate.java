import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SelectionSortAnimate extends JPanel {

    private static final int NUM_OF_ITEMS = 200;
    private static final int DIM_W = 1200;
    private static final int DIM_H = 800;
    private static final int HORIZON = 1080;
    private static final int VERT_INC = 0;
    private static final int HOR_INC = DIM_W / NUM_OF_ITEMS;

    private JButton startButton;
    private Timer timer = null;
    private JButton resetButton;

    Integer[] list;
    int currentIndex = NUM_OF_ITEMS - 1;

    public SelectionSortAnimate() {
        list = initList();

        timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    sortOnlyOneItem();
                }
                repaint();
            }
        });
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list = initList();
                currentIndex = NUM_OF_ITEMS - 1;
                repaint();
                startButton.setEnabled(true);
            }
        });
        add(startButton);
        add(resetButton);
    }

    public boolean isSortingDone() {
        return currentIndex == 0;
    }

    public Integer[] initList() {
        Integer[] nums = new Integer[NUM_OF_ITEMS];
        for (int i = 1; i <= nums.length; i++) {
            nums[i - 1] = i;
        }
        Collections.shuffle(Arrays.asList(nums));
        return nums;
    }

    public void drawItem(Graphics g, int item, int index) {
    int maxItem = Collections.max(Arrays.asList(list));
    int height = item * (HORIZON - VERT_INC) / maxItem;
    int y = HORIZON - height;
    int x = index * HOR_INC;
    g.fillRect(x, y, HOR_INC, height);
}



    public void sortOnlyOneItem() {
        int currentMax = list[0];
        int currentMaxIndex = 0;

        for (int j = 1; j <= currentIndex; j++) {
            if (list[j] > currentMax) {
                currentMax = list[j];
                currentMaxIndex = j;
            }
        }

        // Swap the largest element with the last unsorted element
        if (currentMaxIndex != currentIndex) {
            int temp = list[currentIndex];
            list[currentIndex] = list[currentMaxIndex];
            list[currentMaxIndex] = temp;
        }
        currentIndex--;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < NUM_OF_ITEMS; i++) {
            drawItem(g, list[i], i);
        }
    }
    public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            JFrame frame = new JFrame("Selection Sort Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SelectionSortAnimate panel = new SelectionSortAnimate();
            frame.add(panel);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full screen
            frame.setVisible(true);
        }
    });
}


}