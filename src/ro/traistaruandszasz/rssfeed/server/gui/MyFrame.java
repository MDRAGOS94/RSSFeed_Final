package ro.traistaruandszasz.rssfeed.server.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {
    private JLabel lblConectionCount;
    private JTextArea textArea;

    public MyFrame() {
	this.lblConectionCount = new JLabel();
	this.textArea = new JTextArea(20, 20);

	setSize(380, 450);
	JPanel panel = new JPanel(null);

	JLabel label1 = new JLabel();
	label1.setText("Server Online");
	label1.setForeground(new Color(50, 160, 0));
	label1.setFont(new Font("TimesRoman", 2, 20));
	panel.add(label1);
	panel.getComponent(0).setBounds(100, 10, 200, 20);

	lblConectionCount.setText("Active connections number: 0");
	lblConectionCount.setForeground(new Color(0, 0, 0));
	lblConectionCount.setFont(new Font("TimesRoman", 2, 16));
	panel.add(lblConectionCount);
	panel.getComponent(1).setBounds(10, 40, 200, 20);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setViewportView(textArea);
	scrollPane.setBounds(0, 0, 600, 600);
	textArea.setEditable(false);
	scrollPane
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	panel.add(scrollPane);
	panel.getComponent(2).setBounds(10, 70, 350, 330);

	add(panel);
	setVisible(true);
    }

    public void updateClientsCount(int count) {
	lblConectionCount.setText("Active connections number: " + count);
    }

    public void updateMessages(String msg) {
	textArea.append("\n" + msg);
    }

}
