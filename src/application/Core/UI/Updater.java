package application.Core.UI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * 
 * Class for handling future updates (That may or may not happen :P )
 * 
 * @author Spud
 *
 */
public class Updater {

	/**
	 * The URL of the version file.
	 */
	static String url = "https://raw.githubusercontent.com/jeffrypig23/Mayterm/master/src/version.txt";

	/**
	 * Checks to see if the current version is out of date. If it is, then it will
	 * set the returned boolean to true, as there's a new version available.
	 * 
	 * @return Boolean - Whether or not there is a new version available.
	 */
	public static boolean updateAvalible() {
		URL update = null;
		try {
			update = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		}
		Scanner s = null;
		try {
			s = new Scanner(update.openStream());
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		double version = (Double.parseDouble(s.nextLine()));
		s.close();
		double currentVersion = 0;
		try {
			currentVersion = Double.parseDouble(String.valueOf(Resources.toString(
					Updater.class.getClassLoader().getResource("version.txt").toURI().toURL(), Charsets.UTF_8)));
		} catch (NumberFormatException | IOException | URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("GitHub version: " + version);
		System.out.println("Current version: " + currentVersion);

		return (version > currentVersion);

	}

	/**
	 * Displays the prompt window if there's an update available. Clicking
	 * {@code Download now} will open a new window to the GitHub page.
	 */
	public static void showUpdatePrompt() {
		JFrame updatePrompt = new JFrame("Update avalible!");

		updatePrompt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		updatePrompt.setBounds(100, 100, 426, 173);
		updatePrompt.setAlwaysOnTop(true);
		updatePrompt.setResizable(false);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		updatePrompt.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 65, 65, 65, 65, 0 };
		gbl_contentPane.rowHeights = new int[] { 65, 65, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblANewVersion = new JLabel(
				"<html><center>A new update is avalible!<br ><br >Do you want to download it now?</center></html>");
		lblANewVersion.setFont(new Font("Arial", Font.PLAIN, 16));
		GridBagConstraints gbc_lblANewVersion = new GridBagConstraints();
		gbc_lblANewVersion.gridwidth = 4;
		gbc_lblANewVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lblANewVersion.gridx = 0;
		gbc_lblANewVersion.gridy = 0;
		contentPane.add(lblANewVersion, gbc_lblANewVersion);

		JButton btnNewButton = new JButton("Download now");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		JRootPane rootPane = SwingUtilities.getRootPane(updatePrompt);
		rootPane.setDefaultButton(btnNewButton);
		contentPane.add(btnNewButton, gbc_btnNewButton);

		JButton btnMaybeLater = new JButton("Maybe later");
		GridBagConstraints gbc_btnMaybeLater = new GridBagConstraints();
		gbc_btnMaybeLater.insets = new Insets(0, 0, 0, 5);
		gbc_btnMaybeLater.gridx = 2;
		gbc_btnMaybeLater.gridy = 1;
		contentPane.add(btnMaybeLater, gbc_btnMaybeLater);

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(URI.create("https://github.com/jeffrypig23/Mayterm"));
				} catch (IOException e1) {
					return;
				}
			}

		});

		btnMaybeLater.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePrompt.dispose();
			}

		});

		updatePrompt.setVisible(true);

	}

}
