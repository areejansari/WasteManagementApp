import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlClickableLabelExample extends JFrame {
    public UrlClickableLabelExample() {
        setTitle("Clickable URL Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create a JEditorPane to display HTML content
        JEditorPane editorPane = new JEditorPane("text/html", "<html><a href='https://www.example.com'>Click here to visit Example.com</a></html>");
        editorPane.setEditable(false);

        // Add a HyperlinkListener to open the URL when clicked
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                openURL(e.getURL().toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(editorPane);
        add(scrollPane);

        setVisible(true);
    }

    // Method to open a URL in the default browser
    private void openURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UrlClickableLabelExample::new);
    }
}
