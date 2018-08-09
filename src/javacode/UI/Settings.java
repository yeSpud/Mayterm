package javacode.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import javacode.Debugger;
import javacode.Database.Database;

public class Settings {
	
	private static JPanel contentPane;
	private static JFrame frame = new JFrame();
	
	public static JLabel algorithmWarning = new JLabel("<html><b>Player must be stopped (not paused<br>or playing) in order to select a<br>visualizer algorithm</b></html>");
	
	public static ButtonGroup visualizerChoices = new ButtonGroup();
	public static JRadioButton DefaultVis = new JRadioButton("JavaFX built in (Default)"), fftVis = new JRadioButton("FFT (More accurate, also experimental)");;
	
	public static void checkSettings() {
		if (Database.getSettings().toString().equals("[]")) {
			Debugger.d(Settings.class, "Settings was invalid! Updating");
			Database.editSettings(0);
		} else {
			Debugger.d(Settings.class, "Settings valid");
			return;
		}
	}
	
	public static void setup() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		frame.setResizable(false);
		frame.setTitle("Settings");
		frame.setBounds(100, 100, 305, 184);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 19, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("Visualizer algorithm:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 1;
		contentPane.add(horizontalStrut, gbc_horizontalStrut);
		
		visualizerChoices.add(DefaultVis);
		DefaultVis.setFont(new Font("Arial", Font.PLAIN, 12));
		DefaultVis.setEnabled(false);
		GridBagConstraints gbc_rdbtnDefault = new GridBagConstraints();
		gbc_rdbtnDefault.anchor = GridBagConstraints.WEST;
		gbc_rdbtnDefault.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnDefault.gridx = 0;
		gbc_rdbtnDefault.gridy = 2;
		contentPane.add(DefaultVis, gbc_rdbtnDefault);
		
		visualizerChoices.add(fftVis);
		fftVis.setFont(new Font("Arial", Font.PLAIN, 12));
		fftVis.setEnabled(false);
		GridBagConstraints gbc_rdbtnFftmoreAccurate = new GridBagConstraints();
		gbc_rdbtnFftmoreAccurate.anchor = GridBagConstraints.WEST;
		gbc_rdbtnFftmoreAccurate.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFftmoreAccurate.gridx = 0;
		gbc_rdbtnFftmoreAccurate.gridy = 3;
		contentPane.add(fftVis, gbc_rdbtnFftmoreAccurate);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 4;
		contentPane.add(verticalStrut_1, gbc_verticalStrut_1);
		
		algorithmWarning.setForeground(Color.RED);
		algorithmWarning.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNoTrackMust = new GridBagConstraints();
		gbc_lblNoTrackMust.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoTrackMust.gridx = 0;
		gbc_lblNoTrackMust.gridy = 5;
		contentPane.add(algorithmWarning, gbc_lblNoTrackMust);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 6;
		contentPane.add(verticalStrut, gbc_verticalStrut);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 8;
		contentPane.add(btnOk, gbc_btnOk);
		
		DefaultVis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Database.editSettings(0);
			}
		});
		
		fftVis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Database.editSettings(1);
			}
		});
		
		frame.pack();
	}
	
	public static void showSettings() {
		frame.setVisible(true);
	}
	
	public static void enableButtons(boolean enable) {
		DefaultVis.setEnabled(enable);
		fftVis.setEnabled(enable);
		algorithmWarning.setVisible(!enable);
	}
	
	public static void setDefaultEnabled(int value) {
		if (value == 1) {
			fftVis.setSelected(true);
			DefaultVis.setSelected(false);
		} else {
			fftVis.setSelected(false);
			DefaultVis.setSelected(true);
		}
	}
	
}
