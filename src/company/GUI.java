package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GUI extends JPanel {
    String[] mapSquares;
    String currentSquare;
    MyFrame change;

    public GUI(MyFrame a) {
        this.setBackground(Color.WHITE);
        change = a;
        this.setLayout(null);
        buttonAdd("Isfan", 10, 0, 80, 40);
        buttonAdd("Giri", 100, 0, 80, 40);
        File[] folder = new File("Map Squares/").listFiles();
        mapSquares = new String[folder.length];
        int counter = 0;
        int start = 0;
        for (File file : folder) {
            mapSquares[counter] = file.getName();
            if(file.getName().equals("41.29.$biome_forest.jpg")){
                start=counter;
            }
            counter++;
        }

        currentSquare = mapSquares[start];
        DragPanel("Map Squares/" + currentSquare, new Point(0, 0));
        imageCorner.setLocation(prevPt);
        repaint();
    }

    public void buttonAdd(String name, int x, int y, int width, int height) {
        JButton button;
        button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.addActionListener(new ClickButton());
        add(button);
    }

    private class ClickButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }

    ImageIcon image;
    int WIDTH;
    int HEIGHT;
    Point imageCorner;
    Point prevPt = new Point(0, 0);

    public void DragPanel(String FileLocation, Point point) {
        image = new ImageIcon(FileLocation);
        WIDTH = image.getIconWidth();
        HEIGHT = image.getIconHeight();
        imageCorner = point;
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    Point imagesPositive;
    Point imagesNegative;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        imagesPositive = new Point(0, 0);
        imagesNegative = new Point(0, 0);

        if (!(imageCorner == null)) {
            image.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());

            int currentY =Integer.parseInt(currentSquare.split("\\.")[0]);
            int currentX =Integer.parseInt(currentSquare.split("\\.")[1]);
            int mapsquareY;
            int mapsquareX;


            for (int x = 0; x < mapSquares.length; x++) {
                mapsquareY = Integer.parseInt(mapSquares[x].split("\\.")[0]);
                mapsquareX = Integer.parseInt(mapSquares[x].split("\\.")[1]);
                if (Math.abs(currentY - mapsquareY) <= 3) {
                    if (Math.abs(currentX - mapsquareX) <= 3) {

                        if (mapsquareX - currentX > imagesPositive.getX()) {
                            imagesPositive.setLocation(mapsquareX - currentX, imagesPositive.getY());
                        }
                        if (mapsquareX - currentX < imagesNegative.getX()) {
                            imagesNegative.setLocation(mapsquareX - currentX, imagesNegative.getY());
                        }
                        if (mapsquareY - currentY > imagesPositive.getY()) {
                            imagesPositive.setLocation(imagesPositive.getX(), mapsquareY - currentY);
                        }
                        if (mapsquareY - currentY < imagesNegative.getY()) {
                            imagesNegative.setLocation(imagesNegative.getX(), mapsquareY - currentY);
                        }

                        int moveY = (500 * ((mapsquareY - currentY)));
                        int moveX = (500 * ((mapsquareX - currentX)));




                        if(mapsquareX/29>=1){
                            moveX= moveX-125;
                        }
                        if(mapsquareY/55>=1){
                            moveY= moveY-213;
                        }


                        (new ImageIcon("Map Squares/" + mapSquares[x])).paintIcon(this, g, (int) imageCorner.getX() + moveX, (int) imageCorner.getY() + moveY);
                    }
                }

            }
        }
    }


    boolean ClickedInImage;

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            ClickedInImage = false;
            if (e.getButton() == MouseEvent.BUTTON1) {
                if ((e.getPoint().getX() <= imageCorner.getX() + WIDTH + (500 * imagesPositive.getX()) && e.getPoint().getX() >= imageCorner.getX() + (500 * imagesNegative.getX())) && (e.getPoint().getY() <= imageCorner.getY() + HEIGHT + (500 * imagesPositive.getY()) && e.getPoint().getY() >= imageCorner.getY() + (500 * imagesNegative.getY()))) {
                    prevPt = e.getPoint();
                    int addX = 0;
                    int addY = 0;
                    int checkX = Integer.parseInt(currentSquare.split("\\.")[1]);
                    int checkY = Integer.parseInt(currentSquare.split("\\.")[0]);

                    if (e.getPoint().getX() - imageCorner.getX() > 1500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 3;
                        addX = 1500;
                    } else if (e.getPoint().getX() - imageCorner.getX() > 1500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 2;
                        addX = 1000;
                    } else if (e.getPoint().getX() - imageCorner.getX() > 500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 1;
                        addX = 500;
                    }
                    if (e.getPoint().getX() - imageCorner.getX() < -1000) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 3;
                        addX = -1500;
                    }else if (e.getPoint().getX() - imageCorner.getX() < -500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 2;
                        addX = -1000;
                    } else if (e.getPoint().getX() - imageCorner.getX() < 0) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 1;
                        addX = -500;
                    }

                    if (e.getPoint().getY() - imageCorner.getY() > 1500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 3;
                        addY = 1500;
                    } else if (e.getPoint().getY() - imageCorner.getY() > 1000) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 2;
                        addY = 1000;
                    } else if (e.getPoint().getY() - imageCorner.getY() > 500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 1;
                        addY = 500;
                    }
                    if (e.getPoint().getY() - imageCorner.getY() < -100) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 3;
                        addY = -1500;
                    }else if (e.getPoint().getY() - imageCorner.getY() < -500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 2;
                        addY = -1000;
                    } else if (e.getPoint().getY() - imageCorner.getY() < 0) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 1;
                        addY = -500;
                    }

                    for (int x = 0; x < mapSquares.length; x++) {

                        if ((mapSquares[x].split("\\.")[0]  + "." + mapSquares[x].split("\\.")[1]).compareTo(checkY + "." + checkX)==0) {
                            currentSquare = mapSquares[x];
                            continue;
                        }
                    }

                    imageCorner.setLocation(imageCorner.getX() + addX, imageCorner.getY() + addY);


                    image = new ImageIcon("Map Squares/" + currentSquare);
                    WIDTH = image.getIconWidth();
                    HEIGHT = image.getIconHeight();

                    ClickedInImage = true;
                } else {
                    ClickedInImage = false;
                }

            }
            //need to change
            if (e.getButton() == MouseEvent.BUTTON3) {
                if ((e.getPoint().getX() <= imageCorner.getX() + WIDTH + (500 * imagesPositive.getX()) && e.getPoint().getX() >= imageCorner.getX() + (500 * imagesNegative.getX())) && (e.getPoint().getY() <= imageCorner.getY() + HEIGHT + (500 * imagesPositive.getY()) && e.getPoint().getY() >= imageCorner.getY() + (500 * imagesNegative.getY()))) {
                    int checkX = Integer.parseInt(currentSquare.split("\\.")[1]);
                    int checkY = Integer.parseInt(currentSquare.split("\\.")[0]);

                    if (e.getPoint().getX() - imageCorner.getX() > 1500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 3;
                    }else if (e.getPoint().getX() - imageCorner.getX() > 1000) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 2;
                    } else if (e.getPoint().getX() - imageCorner.getX() > 500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) + 1;
                    }
                    if (e.getPoint().getX() - imageCorner.getX() < -1000) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 3;
                    }else if (e.getPoint().getX() - imageCorner.getX() < -500) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 2;
                    } else if (e.getPoint().getX() - imageCorner.getX() < 0) {
                        checkX = Integer.parseInt(currentSquare.split("\\.")[1]) - 1;
                    }

                    if (e.getPoint().getY() - imageCorner.getY() > 1500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 3;
                    }else if (e.getPoint().getY() - imageCorner.getY() > 1000) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 2;
                    } else if (e.getPoint().getY() - imageCorner.getY() > 500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) + 1;
                    }
                    if (e.getPoint().getY() - imageCorner.getY() < -1000) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 3;
                    }else if (e.getPoint().getY() - imageCorner.getY() < -500) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 2;
                    } else if (e.getPoint().getY() - imageCorner.getY() < 0) {
                        checkY = Integer.parseInt(currentSquare.split("\\.")[0]) - 1;
                    }
                    String biome="";

                    for (int x = 0; x < mapSquares.length; x++) {
                        if (mapSquares[x].contains(checkY + "." + checkX)) {
                            biome = mapSquares[x].split("\\.")[2];
                            continue;
                        }
                    }
                    if(!biome.equals("NULL")&&!biome.equals("$biome_empty")) {
                        change.toBiome(biome.replace("$biome_",""));
                    }

                }
            }
        }
    }
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            if (ClickedInImage) {
                Point currentPt = e.getPoint();//translate moves object by that much
                imageCorner.translate((int) (currentPt.getX() - prevPt.getX()), (int) (currentPt.getY() - prevPt.getY()));
                prevPt = currentPt;
                repaint();
            }
        }
    }
}