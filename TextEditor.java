//Text-Editor.java file
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.*;
import java.io.*;
import java.io.BufferedReader;

class TextField extends JFrame {

    JTextArea textArea;
    JScrollPane scrollpane;
    JMenuBar menuBar;
    JMenu FileMenu;
    JMenuItem newAction, saveAction, openAction;

    public TextField() {
        super("Text Editor - Team Eureka");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Serif", Font.PLAIN, 15));
        // System.out.println("tab size: "+textArea.getTabSize());
        // textArea.paste();
        // textArea.copy();
        scrollpane = new JScrollPane(textArea);
        getContentPane().add(scrollpane);
        setVisible(true);
        CreateMenu();
    }

    public void CreateMenu() {
        menuBar = new JMenuBar();
        FileMenu = new JMenu("File");
        JMenu ThemeMenu = new JMenu("Prefrences");
        newAction = new JMenuItem("New");
        newAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TextField();
            }
        });
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
        ThemeMenu.add(TabSizeMenu);
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
        menuBar.add(ThemeMenu);
        ThemeMenu.add(TabSizeMenu);
        setJMenuBar(menuBar);
    }

    private class MyRadioButtonMenuItem extends JRadioButtonMenuItem implements ActionListener, ItemListener {
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

public class TextEditor {
    public static void main(String args[]) {
        new TextField();
    }
}
