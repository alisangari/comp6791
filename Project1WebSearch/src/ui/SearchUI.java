package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		textField.setColumns(10);

		JList<String> list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel<String> listModel = new DefaultListModel<>();
		JTextArea textArea = new JTextArea();
		textArea.setColumns(10);
		textArea.setRows(1);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setSize(500, 50);
		
		list.setSize(300, 50);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					String fileName = list.getSelectedValue().toString();
					textArea.setText(DisplayArticle.display(fileName));
				}
			}
		});

		JButton btnNewButton = new JButton(" Search ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					results = Search.search(textField.getText());
					listModel.removeAllElements();
					for (int i = 0; i < results.size(); i++) {
						
						listModel.addElement(results.get(i).trim());
						
					}
//					list.clearSelection();
					list.setModel(listModel);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton);

//		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(list, BorderLayout.WEST);

//		panel_1.add(list);

		frame.getContentPane().add(textArea, BorderLayout.EAST);
	}

}
