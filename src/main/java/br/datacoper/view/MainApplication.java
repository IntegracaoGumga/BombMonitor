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

import br.datacoper.model.Configuracoes;
import br.datacoper.model.Monitor;

/**
 * @author dread
 *
 *         Execuçao da aplicacao
 * 
 */
public class MainApplication {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final TrayIcon trayIcon;

		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit()
				.getImage(Configuracoes.getInstance().getParam(Configuracoes.PARAM_DIRETORIO_IMAGEM));

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

		PopupMenu popup = new PopupMenu("Menu de Opcoes");

		MenuItem mostramsg = new MenuItem("Configuracoes");
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

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println("Erro, TrayIcon não sera adicionado.");
		}

		new Monitor();
	}
}
