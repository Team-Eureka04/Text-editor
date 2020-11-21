import javax.swing.text.BadLocationException;
import com.texteditor.TextField;

public class TextEditor {
    public static void main(String args[]) {
        try {
            new TextField();
        } catch (BadLocationException e) {
            System.out.println("Error bad location " + e);
        }
    }
}
