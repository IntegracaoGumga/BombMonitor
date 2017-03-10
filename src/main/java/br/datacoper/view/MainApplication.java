/**
 * 
 */
package br.datacoper.view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import br.datacoper.model.Monitor;

/**
 * @author dread
 *
 *         Main executor class
 * 
 */
public class MainApplication {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final TrayIcon trayIcon;

		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("./src/main/resources/img/icon.png");

		MouseListener mouseListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};

		ActionListener exitListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};

		ActionListener configListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaConfiguracoes tela = new TelaConfiguracoes();
				tela.setVisible(true);
			}
		};

		PopupMenu popup = new PopupMenu("Menu de Opções");

		MenuItem mostramsg = new MenuItem("Configurações");
		MenuItem defaultItem = new MenuItem("Sair");

		mostramsg.addActionListener(configListener);
		defaultItem.addActionListener(exitListener);

		popup.add(mostramsg);
		popup.add(defaultItem);

		trayIcon = new TrayIcon(image, "Monitor de Abastecidas", popup);

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trayIcon.displayMessage("Action Event", "Um Evento foi disparado", TrayIcon.MessageType.INFO);
			}
		};

		trayIcon.setImageAutoSize(true);

		trayIcon.addActionListener(actionListener);
		trayIcon.addMouseListener(mouseListener);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println("Erro, TrayIcon não sera adicionado.");
		}

		new Monitor();
	}
}
