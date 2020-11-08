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


class TextField extends JFrame{

    JTextArea textArea;
    JScrollPane scrollpane;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem newAction,saveAction,openAction;
    public TextField(){
        super("untitled");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Serif",Font.ITALIC,15));
        scrollpane = new JScrollPane(textArea);
        getContentPane().add(scrollpane);
        setVisible(true);
        CreateMenu();
    }
    public void CreateMenu(){
            menuBar = new JMenuBar();
            menu = new JMenu("FILE");
            newAction = new JMenuItem("New");
            newAction.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                       new TextField();
                }
            });
            saveAction = new JMenuItem("Save");  
            saveAction.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify new File");
                    fileChooser.setSelectedFile(new File("file1"));
                    int userSelection = fileChooser.showSaveDialog(fileChooser);
                    if(userSelection == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        BufferedWriter  writer = null;
                        try{
                            writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()+""));
                            writer.write(textArea.getText());
                            writer.close();
                        }catch(IOException a){
                            System.out.println("Something went Wrong");
                        }
                    }
                }
            });
            openAction = new JMenuItem("Open");
            openAction.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e ){
                    JFileChooser fileChooser = new JFileChooser();
                    int ans = fileChooser.showOpenDialog(fileChooser);
                    if(ans == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        String filename = fileChooser.getSelectedFile().getName();
                        System.out.println(filename);
                        try {
                            FileReader fr = new FileReader(file);
                            BufferedReader br = new BufferedReader(fr);
                            String s=""; int c=0;
                                  while((c=br.read())!=-1)
                                          s+=(char)c; 
                            textArea.append(s);
                            br.close();
                            fr.close();
                        }catch(IOException b){
                           System.out.println("Something went wrong\n");
                        }
                    }
                }
            });
            menuBar.add(menu);
            menu.add(newAction);
            menu.add(saveAction);
            menu.add(openAction);
            setJMenuBar(menuBar);
    }      
}

public class TextEditor{
    public static void main(String args[]){
        new TextField();
    }
}