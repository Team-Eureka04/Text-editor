package com.texteditor;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import java.util.Vector;

public class Theme {

    public int FontSize;
    public String FontColor;
    public String BackgroundColor;
    public String CurrentLineHighlightColor;
    public Vector<String> listThemes = new Vector<String>();

    public Theme(String ThemeName) {
        ExtractThemes(ThemeName);
        ExtractThemeNames();

    }

    private void ExtractThemeNames() {
        try {
            File dir = new File("Themes");

            File contents[] = dir.listFiles();
            for (File file : contents) {
                listThemes.add(file.getName().replaceFirst("[.][^.]+$", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ExtractThemes(String ThemeName) {
        try {
            File file = new File("Themes/" + ThemeName + ".xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("Font");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                System.out.println("\nNode Name " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    FontColor = eElement.getElementsByTagName("Color").item(0).getTextContent();
                    FontSize = Integer.parseInt(eElement.getElementsByTagName("Size").item(0).getTextContent());
                }
            }
            nodes = doc.getElementsByTagName("Editor");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    BackgroundColor = eElement.getElementsByTagName("Background").item(0).getTextContent();
                    CurrentLineHighlightColor = eElement.getElementsByTagName("CurrentLineHighlight").item(0)
                            .getTextContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
