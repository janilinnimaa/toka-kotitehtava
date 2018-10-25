import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.InputEvent;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TekstieditoriGUI extends JFrame {

	private JPanel ikkuna;
	private JEditorPane editoriruutu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TekstieditoriGUI frame = new TekstieditoriGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TekstieditoriGUI() {
		setTitle("Editorus Maximus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 351);
		
		JMenuBar alasvetovalikko = new JMenuBar();
		setJMenuBar(alasvetovalikko);
		
		JMenu tiedostovalikko = new JMenu("Tiedosto");
		alasvetovalikko.add(tiedostovalikko);
		
		JMenuItem valintaAvaa = new JMenuItem("Avaa");
		valintaAvaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.setApproveButtonText("Tästä aukeaa!");
				valintaikkuna.setDialogTitle("Valitseppa tiedosto!");
				valintaikkuna.showOpenDialog(null);
				String rivi = "";
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();
				
				try {
					Scanner lukija = null;
					File tiedosto = new File(uusiTiedosto);
					lukija = new Scanner(tiedosto);
					
					while (lukija.hasNextLine()) {
						rivi += lukija.nextLine() + "\n";
						System.out.println(rivi);
						
					}
					lukija.close();
					
				} catch (FileNotFoundException p) {
					System.out.println("Tiedostoa ei löytynyt!");
				}
				
				editoriruutu.setText(rivi);
				
			}
		});
		valintaAvaa.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		valintaAvaa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		tiedostovalikko.add(valintaAvaa);
		
		JMenuItem valintaTallenna = new JMenuItem("Tallenna");
		valintaTallenna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.showSaveDialog(null);
				
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();
				
				System.out.println("Kirjoitettava tiedosto: " + uusiTiedosto);
				try {
					
					PrintWriter kirjoittaja = new PrintWriter(uusiTiedosto);
					String sisalto = editoriruutu.getText();
					
					kirjoittaja.println(sisalto);
					
					kirjoittaja.flush();
					kirjoittaja.close();
					
				} catch (Exception e1) {
					System.out.println("Tiedoston tallennuksessa tapahtui virhe!");
					e1.printStackTrace();
				}
			}
		});
		valintaTallenna.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		valintaTallenna.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		tiedostovalikko.add(valintaTallenna);
		
		JMenuItem valintaLopeta = new JMenuItem("Lopeta");
		valintaLopeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		valintaLopeta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		valintaLopeta.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		tiedostovalikko.add(valintaLopeta);
		
		// Sulje-komento lopettaa ohjelman ajamisen niin kuin Lopeta-komentokin.
		// Jos editorissa voisi avata useampia tekstitiedostoja samaan aikaan, niin komento toki voisi sulkea niitä
		JMenuItem valintaSulje = new JMenuItem("Sulje");
		valintaSulje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		valintaSulje.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		valintaSulje.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		tiedostovalikko.add(valintaSulje);
		
		JMenu muokkaavalikko = new JMenu("Muokkaa");
		alasvetovalikko.add(muokkaavalikko);
		
		// Etsi-toiminto etsii tekstistä kovakoodatun sanan ("auto") ja highlighttaa sen.
		// Tämä ohjelma tosin löytää vain tekstin ensimmäisen haettavan sanan
		JMenuItem valintaEtsi = new JMenuItem("Etsi");
		valintaEtsi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sisalto = editoriruutu.getText();
				sisalto = sisalto.toLowerCase();
				
				String haettava = "auto";
				int indeksi = sisalto.indexOf(haettava);
				System.out.println("Indeksi: " + indeksi);
				
				editoriruutu.setSelectedTextColor(Color.CYAN);
				
				editoriruutu.setSelectionStart(indeksi);
				editoriruutu.setSelectionEnd(indeksi + haettava.length());
			}
		});
		valintaEtsi.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/expanded.gif")));
		valintaEtsi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		muokkaavalikko.add(valintaEtsi);
		
		// Korvaa-toiminto vaihtaa valitun sanan toiseksi ("pyörä"). 
		// Tämä toimii etsi-toiminnon kanssa, mutta myös käyttäjän maalaaman tekstin kanssa.
		
		JMenuItem valintaKorvaa = new JMenuItem("Korvaa");
		valintaKorvaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sisalto = editoriruutu.getText();
				
				if (!sisalto.isEmpty()) {
					editoriruutu.replaceSelection("pyörä");
				}
			}
		});
		valintaKorvaa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		valintaKorvaa.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Cut-Black.png")));
		muokkaavalikko.add(valintaKorvaa);
		
		JMenu tietojavalikko = new JMenu("Tietoja");
		alasvetovalikko.add(tietojavalikko);
		
		// Message Dialogi, jossa on tekijän nimi, copyright + vuosiluku sekä DeviantArt-profiilin osoite
		JMenuItem mntmTietojaOhjelmasta = new JMenuItem("Tietoja Ohjelmasta");
		mntmTietojaOhjelmasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Jani Linnimaa, \u00a9 2018 \n"
						+ "kuopansetae.deviantart.com", "Info-boksi", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mntmTietojaOhjelmasta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		mntmTietojaOhjelmasta.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		tietojavalikko.add(mntmTietojaOhjelmasta);
		ikkuna = new JPanel();
		ikkuna.setBorder(new EmptyBorder(5, 5, 5, 5));
		ikkuna.setLayout(new BorderLayout(0, 0));
		setContentPane(ikkuna);
		
		JToolBar tyokaluvalikko = new JToolBar();
		ikkuna.add(tyokaluvalikko, BorderLayout.NORTH);
		
		
		// avaaNappiin copypastettu aiempaa koodia. Jos tämän tekisi "oikein" eri osat tulisi varmaankin palastella omiin metodeihinsa, joista niitä
		// kutsuttaisiin tarvittaessa.
		JButton avaaNappi = new JButton("");
		avaaNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.setApproveButtonText("Tästä aukeaa!");
				valintaikkuna.setDialogTitle("Valitseppa tiedosto!");
				valintaikkuna.showOpenDialog(null);
				String rivi = "";
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();
				
				try {
					Scanner lukija = null;
					File tiedosto = new File(uusiTiedosto);
					lukija = new Scanner(tiedosto);
					
					while (lukija.hasNextLine()) {
						rivi += lukija.nextLine() + "\n";
						System.out.println(rivi);
						
					}
					
					lukija.close();
				} catch (FileNotFoundException p) {
					System.out.println("Tiedostoa ei löytynyt!");
				}
				
				editoriruutu.setText(rivi);
				
			}
		});
		avaaNappi.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		tyokaluvalikko.add(avaaNappi);
		
		// Jälleen aikasemmasta osiosta kopioitua koodia.
		JButton tallennaNappi = new JButton("");
		tallennaNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser valintaikkuna = new JFileChooser();
				valintaikkuna.showSaveDialog(null);
				
				String uusiTiedosto = valintaikkuna.getSelectedFile().getAbsolutePath();
				
				System.out.println("Kirjoitettava tiedosto: " + uusiTiedosto);
				try {
					
					PrintWriter kirjoittaja = new PrintWriter(uusiTiedosto);
					String sisalto = editoriruutu.getText();
					
					kirjoittaja.println(sisalto);
					
					kirjoittaja.flush();
					kirjoittaja.close();
					
				} catch (Exception e1) {
					System.out.println("Tiedoston tallennuksessa tapahtui virhe!");
					e1.printStackTrace();
				}
			}
		});
		tallennaNappi.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		tyokaluvalikko.add(tallennaNappi);
		
		JButton korvaaNappi = new JButton("");
		korvaaNappi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sisalto = editoriruutu.getText();
				
				if (!sisalto.isEmpty()) {
					editoriruutu.replaceSelection("pyörä");
				}
			}
		});
		korvaaNappi.setIcon(new ImageIcon(TekstieditoriGUI.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Cut-Black.png")));
		tyokaluvalikko.add(korvaaNappi);
		
		editoriruutu = new JEditorPane();
		ikkuna.add(editoriruutu, BorderLayout.CENTER);
	}

	public JEditorPane getEditoriruutu() {
		return editoriruutu;
	}
}
