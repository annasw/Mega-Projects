package notepad;

/**
 * This is a work-in-progress implementation of the text editor challenge.
 * It has some weird features because I'm in the middle of implementing syntax highlighting.
 * But for now it should be basically functional (i.e. essentially Notepad).
 * Notably, Open/Save/Close work fine.
 * 
 * Partially based on a tutorial from here:
 * http://www.dreamincode.net/forums/topic/66176-creating-a-basic-notepad-application/
 */

//import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {
	private JTextPane textArea = new JTextPane();
	
	private MenuBar menuBar = new MenuBar();
	private Menu file = new Menu();
	private MenuItem openFile = new MenuItem();
	private MenuItem saveFile = new MenuItem();
	private MenuItem close = new MenuItem();
	
	private Menu language = new Menu();
	private MenuItem python = new MenuItem();
	private MenuItem haskell = new MenuItem();
	private MenuItem noLang = new MenuItem();
	
	private String currentLanguage = "noLang";
	
	public Notepad() {
		
		this.setSize(1200, 700);
		this.setTitle("Notepad += 2");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.textArea.setFont(new Font("Courier New", Font.PLAIN, 22));
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(textArea);
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent de) {
				//highlightSyntax();
			}
			
			public void removeUpdate(DocumentEvent de) {
				//highlightSyntax();
			}
			
			public void changedUpdate(DocumentEvent de) {
				//highlightSyntax();
			}
		});
		
		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file);
		this.file.setLabel("File");
		// Open
		this.openFile.setLabel("Open");
		this.openFile.addActionListener(this);
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		this.file.add(this.openFile);
		// Save
		this.saveFile.setLabel("Save");
		this.saveFile.addActionListener(this);
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		this.file.add(this.saveFile);
		//Close
		this.close.setLabel("Close");
		this.close.addActionListener(this);
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_W, false));
		this.file.add(this.close);
		
		this.menuBar.add(this.language);
		this.language.setLabel("Language");
		// Python
		this.noLang.setLabel("No language");
		this.noLang.addActionListener(this);
		this.language.add(this.noLang);
		this.python.setLabel("Python");
		this.python.addActionListener(this);
		this.language.add(this.python);
		this.haskell.setLabel("Haskell");
		this.haskell.addActionListener(this);
		this.language.add(this.haskell);
	}
	
	public void highlightSyntax() {
		String currentText = this.textArea.getText();
		String[] words = currentText.split(" ");
		for (String i : words) {
			appendToPane(this.textArea, i+"!", Color.RED);
		}
		
		//invokeLater();
	}
	
	public void appendToPane(JTextPane pane, String s, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Courier New");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.setCharacterAttributes(aset, false);
		textArea.replaceSelection(s);
	}
	
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == this.close) {
			this.dispose();
		}
		else if (e.getSource() == this.openFile) {
			JFileChooser open = new JFileChooser();
			int option = open.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				this.textArea.setText("");
				try {
					Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
					while (scan.hasNext()) {
						//this.textArea.append(scan.nextLine() + "\n");
						appendToPane(this.textArea, scan.nextLine() + "\n", Color.BLACK);
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		else if (e.getSource() == this.saveFile) {
			JFileChooser save = new JFileChooser();
			int option = save.showSaveDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
					out.write(this.textArea.getText());
					out.close();
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		else if (e.getSource() == this.noLang) {
			currentLanguage = "noLang";
		} else if (e.getSource() == this.python) {
			currentLanguage = "python";
		} else if (e.getSource() == this.haskell) {
			currentLanguage = "haskell";
		}
	}
	
	public static void main(String args[]) {
		Notepad app = new Notepad();
		app.setVisible(true);
	}
}
