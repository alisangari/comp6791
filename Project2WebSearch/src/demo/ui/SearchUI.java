package demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import services.stopwords.StopWordDictionary;
import utility.Strings;
import demo.DisplayArticle;
import demo.Search;

public class SearchUI {

	private JFrame frame;
	private JTextField textField;
	private ArrayList<String> results;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchUI window = new SearchUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(50);
		JScrollPane scrollPane = new JScrollPane();
		JList<String> list = new JList<>();
		list.setSize(500, 50);
		scrollPane.setViewportView(list);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel<String> listModel = new DefaultListModel<>();
		JTextArea textArea = new JTextArea();
		textArea.setColumns(10);
		textArea.setRows(1);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setColumns(50);
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setViewportView(textArea);
		scrollPane2.setSize(500, 50);

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					String temp = list.getSelectedValue().toString();
					temp = temp.replaceAll("[(]", "");
					temp = temp.replaceAll("[)]", "");
//					String fileName = GetArticle.get(temp, type);
					String tStr = textField.getText();
					tStr = Strings.normalize(tStr, true, false, false);
					String[] q = tStr.split(" ");
					q = StopWordDictionary.getInstance().removeStopWords(q);

					// String title = DisplayArticle.displayTitle(fileName);
					String text = DisplayArticle.display(temp, 1);
					// textArea.setText(title+": "+text);
					textArea.setText(text);
					Highlighter highlighter = textArea.getHighlighter();
					HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
							Color.pink);
					for (String str : q) {

						Pattern p = Pattern.compile("\\b" + str.toLowerCase()+"\\b.");  // pattern is defined here
						Matcher m = p.matcher(text.toLowerCase());
						while (m.find()) {
							try {
								highlighter.addHighlight(m.start(), m.start()+str.length(), painter);
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});

		JButton btnNewButton = new JButton(" Search ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listModel.removeAllElements();
				list.setModel(listModel);
				try {
					results = Search.search(textField.getText(), 1);
					listModel.removeAllElements();
					for (int i = 0; i < results.size(); i++) {

						listModel.addElement(results.get(i).toString());

					}
					list.setModel(listModel);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);

		frame.getContentPane().add(scrollPane, BorderLayout.WEST);

		frame.getContentPane().add(scrollPane2, BorderLayout.EAST);
	}

}
