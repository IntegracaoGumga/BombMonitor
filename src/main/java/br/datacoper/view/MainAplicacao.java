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

import org.apache.log4j.Logger;

import br.datacoper.model.Configuracoes;
import br.datacoper.model.Monitor;

/**
 * @author dread
 *
 *         Execuçao da aplicacao
 * 
 */
public class MainAplicacao {

	static Logger logger = Logger.getLogger("br.datacoper.view.MainAplicacao");

	/**
	 * @param args
	 */
	public static void main(final String[] args) {


		if (!Configuracoes.getInstancia().getValido()) {
			logger.info("Parametros do arquivo invalidos! Abrindo tela para configuracoes.");
			TelaConfiguracoes tela = new TelaConfiguracoes();
			tela.setVisible(true);
		}

		final TrayIcon trayIcon;

		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit()
				.getImage(Configuracoes.getInstancia().DIRETORIOIMAGENS.concat("icon.png"));

		ActionListener exitListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Opcao SAIR acionada.");
				System.exit(0);
			}
		};

		ActionListener configListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Opcao CONFIGURACOES acionada.");
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
		trayIcon.setImageAutoSize(true);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			logger.error("Erro ao adicionar o TrayIcon");
		}

		new Monitor();
	}
}
