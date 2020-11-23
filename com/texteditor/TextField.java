package com.texteditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.*;
import java.io.*;
import java.io.BufferedReader;
import java.util.Vector;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;
import javax.swing.text.Element;
// import com.texteditor.Theme;

import javax.swing.event.*;

public class TextField extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    RSyntaxTextArea textArea;
    JScrollPane scrollpane;
    JMenuBar menuBar;
    JMenu FileMenu;
    Document doc;
    JMenuItem newAction, saveAction, openAction, ThemeNameMenu;
    Theme theme;

    public TextField() throws BadLocationException {
        super("untitled");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        theme = new Theme("color");
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        // textArea.setCurrentLineHighlightColor(Color.decode(theme.CurrentLineHighlightColor));

        // textArea.setBackground(Color.decode(theme.BackgroundColor));
        // textArea.setForeground(Color.decode(theme.FontColor));
        // textArea.setCaretColor(Color.BLACK);
        textArea.setAutoIndentEnabled(true);
        // textArea.setCurrentLineHighlightColor(Color.decode("#262626"));
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Serif", Font.PLAIN, theme.FontSize));
        // System.out.println("tab size: "+textArea.getTabSize());
        // textArea.paste();
        // textArea.copy();
        RTextScrollPane sp = new RTextScrollPane(textArea);
        // getContentPane().add(scrollpane);
        getContentPane().add(sp);
        setVisible(true);
        try {
            CreateMenu();
        } catch (BadLocationException exp) {
            exp.printStackTrace();
        }

    }

    public void CreateMenu() throws BadLocationException {
        menuBar = new JMenuBar();
        FileMenu = new JMenu("File");
        JMenu prefMenu = new JMenu("Prefrences");
        JMenu ThemeMenu = new JMenu("Theme");
        newAction = new JMenuItem("New");
        newAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new TextField();
                } catch (BadLocationException excp) {
                    excp.printStackTrace();
                }
            }
        });

        for (String themeName : theme.listThemes) {
            System.out.println(themeName);
            ThemeNameMenu = new JMenuItem(themeName);
            ThemeMenu.add(ThemeNameMenu);

            ThemeNameMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ChangeTheme(e.getActionCommand());

                }

            });
        }

        saveAction = new JMenuItem("Save");
        saveAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify new File");
                fileChooser.setSelectedFile(new File("untitled"));
                int userSelection = fileChooser.showSaveDialog(fileChooser);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    BufferedWriter writer = null;
                    try {
                        setTitle(file.getName());
                        writer = new BufferedWriter(new FileWriter(file.getAbsolutePath() + ""));
                        writer.write(textArea.getText());
                        writer.close();
                    } catch (IOException a) {
                        System.out.println("Something went Wrong");
                    }
                }
            }
        });

        openAction = new JMenuItem("Open");
        openAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int ans = fileChooser.showOpenDialog(fileChooser);
                if (ans == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String filename = fileChooser.getSelectedFile().getName();
                    System.out.println(filename);
                    setTitle(filename);
                    try {
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        String s = "";
                        int c = 0;
                        while ((c = br.read()) != -1)
                            s += (char) c;
                        textArea.append(s);
                        br.close();
                        fr.close();
                    } catch (IOException b) {
                        System.out.println("Something went wrong\n");
                    }
                }
            }
        });

        JMenu TabSizeMenu = new JMenu("Tab Size");
        prefMenu.add(TabSizeMenu);
        prefMenu.add(ThemeMenu);
        ButtonGroup group = new ButtonGroup();
        MyRadioButtonMenuItem radioMenuItem = new MyRadioButtonMenuItem("2");
        TabSizeMenu.add(radioMenuItem);
        group.add(radioMenuItem);
        radioMenuItem = new MyRadioButtonMenuItem("4");
        TabSizeMenu.add(radioMenuItem);
        group.add(radioMenuItem);
        radioMenuItem = new MyRadioButtonMenuItem("8");
        radioMenuItem.setSelected(true);
        TabSizeMenu.add(radioMenuItem);
        group.add(radioMenuItem);

        menuBar.add(FileMenu);
        FileMenu.add(newAction);
        FileMenu.add(saveAction);
        FileMenu.add(openAction);
        menuBar.add(prefMenu);
        prefMenu.add(TabSizeMenu);
        setJMenuBar(menuBar);
    }

    void ChangeTheme(String ThemeName) {
        theme = new Theme(ThemeName);
        System.out.println("Theme Change Called " + ThemeName);
        System.out.println("Theme Background " + theme.BackgroundColor);
        textArea.setCurrentLineHighlightColor(Color.decode(theme.CurrentLineHighlightColor));
        textArea.setBackground(Color.decode(theme.BackgroundColor));
        textArea.setForeground(Color.decode(theme.FontColor));
        textArea.setFont(new Font("Serif", Font.PLAIN, theme.FontSize));

    }

    private class MyRadioButtonMenuItem extends JRadioButtonMenuItem implements ActionListener, ItemListener {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public MyRadioButtonMenuItem(String text) {
            super(text);
            addActionListener(this);
            addItemListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Item clicked: " + e.getActionCommand());
            textArea.setTabSize(Integer.parseInt(e.getActionCommand()));
        }

        public void itemStateChanged(ItemEvent e) {
            System.out.println(
                    "State changed: " + e.getStateChange() + " on " + ((MyRadioButtonMenuItem) e.getItem()).getText());
        }
    }
}