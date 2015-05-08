package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by vladimir on 08.05.15.
 */
public class View {
    private JTextArea textArea;
    private File lastFile = null;
    private JFrame frame;

    public void build() {
        frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 400, 300);

        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openFileItem = new JMenuItem("Open file");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save as...");

        fileMenu.add(openFileItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);

        bar.add(fileMenu);

        textArea = new JTextArea();
        //Перенос строки в JTextArea
        textArea.setLineWrap(true);

        openFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int returnValue = chooser.showOpenDialog(frame);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();

                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(new FileInputStream(file))
                        );

                        String readingLine;
                        while ((readingLine = reader.readLine()) != null) {
                            textArea.setText(textArea.getText() + readingLine + "\n");
                        }

                        reader.close();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    lastFile = file;
                }
            }
        });


        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastFile == null) {
                    saveAsAction();
                } else {
                    try {
                        PrintWriter writer = new PrintWriter(lastFile);

                        writer.println(textArea.getText());

                        writer.close();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsAction();
            }
        });

        frame.add(BorderLayout.CENTER, textArea);
        frame.setJMenuBar(bar);
        frame.setVisible(true);
    }

    private void saveAsAction() {
        JFileChooser chooser = new JFileChooser();
        int returnValue = chooser.showSaveDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try {
                PrintWriter writer = new PrintWriter(file);

                writer.println(textArea.getText());

                writer.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }
}
