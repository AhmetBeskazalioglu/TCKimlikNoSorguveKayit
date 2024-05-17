// Bu class'ta TC Kimlik No sorgulama ve kaydetme işlemleri yapılmaktadır.
// TC Kimlik No sorgulama işlemi için KPSPublic ve KPSPublicSoap sınıfları kullanılmıştır.
// TC Kimlik No sorgulama işlemi için sorgula() metodu kullanılmıştır.
// TC Kimlik No sorgulama işlemi sonucu kaydetme işlemi için kaydet() metodu kullanılmıştır.
// TC Kimlik No sorgulama işlemi sonucu kaydetme işlemi öncesi sorgulaLocale() metodu ile veritabanında kayıtlı olup olmadığı kontrol edilmiştir.


package com.kraft.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jakarta.jws.WebMethod;
import tr.gov.nvi.tckimlik.ws.KPSPublic;
import tr.gov.nvi.tckimlik.ws.KPSPublicSoap;

public class TcnoSorgulaveKaydet {

	private JFrame frame;
	private JTextField txtKimlikno;
	private JTextField txtad;
	private JTextField txtsoyad;
	private JTextField txtdogumTarihi;
	private JTable table;
	private JTextField txtSonuc;
	private DefaultTableModel tableModel;
	private JTextField txtSonuc1;

	private DatabaseSingletonEnum db;
	private Statement statement;
	private ResultSet resultSet;
	private Connection connection;

	private long tcKimlikNo;
	private String ad;
	private String soyad;
	private int dogumTarihi;
	private boolean result;
	private int count;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TcnoSorgulaveKaydet window = new TcnoSorgulaveKaydet();
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
	public TcnoSorgulaveKaydet() {

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("TC KİMLİK NO:");
		lblNewLabel.setBounds(61, 52, 100, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("AD:");
		lblNewLabel_1.setBounds(61, 110, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("SOYAD:");
		lblNewLabel_2.setBounds(61, 165, 61, 16);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("DOĞUM TARİHİ:");
		lblNewLabel_3.setBounds(61, 220, 100, 16);
		frame.getContentPane().add(lblNewLabel_3);

		txtKimlikno = new JTextField();
		txtKimlikno.setBounds(173, 47, 197, 26);
		frame.getContentPane().add(txtKimlikno);
		txtKimlikno.setColumns(10);
		txtKimlikno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtKimlikno.getText().length() >= 11) // limit to 11 characters
					e.consume();
			}
		});
		txtKimlikno.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {

					e.consume();
				}
			}
		});

		txtad = new JTextField();
		txtad.setBounds(173, 105, 197, 26);
		frame.getContentPane().add(txtad);
		txtad.setColumns(10);
		// sadece harf girilmesini sağlar. fakat türkçe karakterlerde olacak.üğışçö gibi
		txtad.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z') || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SPACE) 
						|| (c=='ü') || (c=='ğ') || (c=='ı') || (c=='ş') || (c=='ç') 
						|| (c=='ö') || (c=='Ü') || (c=='Ğ') || (c=='İ') || (c=='Ş') 
						|| (c=='Ç') || (c=='Ö') )) {

					e.consume();
				}
			}
		});
		

		txtsoyad = new JTextField();
		txtsoyad.setColumns(10);
		txtsoyad.setBounds(173, 160, 197, 26);
		frame.getContentPane().add(txtsoyad);
		txtsoyad.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z') || (c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SPACE) 
						|| (c=='ü') || (c=='ğ') || (c=='ı') || (c=='ş') || (c=='ç') 
						|| (c=='ö') || (c=='Ü') || (c=='Ğ') || (c=='İ') || (c=='Ş') 
						|| (c=='Ç') || (c=='Ö') )) {

					e.consume();
				}
			}
		});

		txtdogumTarihi = new JTextField();
		txtdogumTarihi.setColumns(10);
		txtdogumTarihi.setBounds(173, 215, 66, 26);
		frame.getContentPane().add(txtdogumTarihi);
		txtdogumTarihi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtdogumTarihi.getText().length() >= 4) // limit to 4 characters
					e.consume();
			}
		});
		txtdogumTarihi.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {

					e.consume();
				}
			}
		});

		JButton btnSorgula = new JButton("SORGULA");
		btnSorgula.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (txtKimlikno.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "TC Kimlik No boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
				} else if (txtad.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Ad boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
				} else if (txtsoyad.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Soyad boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
				} else if (txtdogumTarihi.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Doğum Tarihi boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
				} else {
					tcKimlikNo = Long.parseLong(txtKimlikno.getText());
					ad = txtad.getText();
					soyad = txtsoyad.getText();
					dogumTarihi = Integer.parseInt(txtdogumTarihi.getText());

					result = sorgula(tcKimlikNo, ad, soyad, dogumTarihi);
					txtSonuc.setText(result ? "Kişi bulundu!" : "Kişi bulunamadı!");

				}
			}
		});
		btnSorgula.setBounds(427, 52, 197, 79);
		frame.getContentPane().add(btnSorgula);

		JButton btnkaydet = new JButton("KAYDET");
		btnkaydet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kaydet();
				closeConnection();
			}
		});
		btnkaydet.setBounds(427, 160, 197, 76);
		frame.getContentPane().add(btnkaydet);

		JLabel lblSonuc = new JLabel("SONUÇ:");
		lblSonuc.setBounds(61, 287, 61, 16);
		frame.getContentPane().add(lblSonuc);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 326, 688, 240);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);

		txtSonuc = new JTextField();
		txtSonuc.setBounds(173, 282, 451, 26);
		frame.getContentPane().add(txtSonuc);
		txtSonuc.setColumns(10);

		txtSonuc1 = new JTextField();
		txtSonuc1.setBounds(173, 282, 451, 26);
		frame.getContentPane().add(txtSonuc1);
		txtSonuc1.setColumns(10);
	}

	public boolean sorgula(long tcKimlikNo, String ad, String soyad, int dogumTarihi) {

		count++;
		KPSPublic kps = new KPSPublic();
		KPSPublicSoap kpsSoap = kps.getKPSPublicSoap();
		boolean result = kpsSoap.tcKimlikNoDogrula(tcKimlikNo, ad, soyad, dogumTarihi);
		return result;
	}

	/**
	 * Connects to the database
	 */
	public void connectToDatabase() {
		db = DatabaseSingletonEnum.INSTANCE;
		connection = db.getConnection();
		statement = db.getStatement();
		System.out.println("Bağlantı Açıldı!");
	}

	/**
	 * Closes the connection to the database
	 */
	public void closeConnection() {
		connection = null;
		statement = null;
		resultSet = null;
		System.out.println("Bağlantı Kapatıldı!");
	}

	/**
	 * Shows the hint
	 */
	public void kaydet() {
		if (count == 0) {
			JOptionPane.showMessageDialog(null, "Önce sorgulama yapınız!", "Hata", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (result) {
			if (sorgulaLocale(tcKimlikNo, ad, soyad, dogumTarihi)) {
				JOptionPane.showMessageDialog(null, "Bu kişi zaten kayıtlı!", "Hata", JOptionPane.ERROR_MESSAGE);
				return;
			} else {

				ad = buyukHarf(ad);
				soyad = buyukHarf(soyad);
				String query = "insert into vatandaslar values(" + tcKimlikNo + ",'" + ad + "','" + soyad + "',"
						+ dogumTarihi + ")";
				try {
					statement.executeQuery(query);
				} catch (SQLException e) {
					System.out.println("Hata: kaydet() " + e.getMessage());
				}

				JOptionPane.showMessageDialog(null, "Kişi başarıyla kaydedildi!", "Başarılı",
						JOptionPane.INFORMATION_MESSAGE);
				getir();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Kişi bulunamadığı için kaydedilemez!", "Hata",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean sorgulaLocale(long tcKimlikNo, String ad, String soyad, int dogumTarihi) {

		String query = "select * from vatandaslar where tckimlikno=" + tcKimlikNo + " and ad='" + ad + "' and soyad='"
				+ soyad + "' and dogum_tarihi=" + dogumTarihi + ";";

		try {
			connectToDatabase();
			resultSet = statement.executeQuery(query);
			if (resultSet.next())
				return true;
		} catch (SQLException e) {
			System.out.println("Hata: sorgulaLocale() " + e.getMessage());
		}
		return false;
	}

	/**
     * Shows the table
     */
	public void getir() {

		String query = "Select * from vatandaslar";

		try {
			resultSet = statement.executeQuery(query);

			tableModel = new DefaultTableModel();

			Object[] column = new Object[4];
			column[0] = "Tc Kimlik No";
			column[1] = "Ad";
			column[2] = "Soyad";
			column[3] = "Doğum Tarihi";

			tableModel.setColumnIdentifiers(column);

			while (resultSet.next()) {
				Object[] row = new Object[4];
				row[0] = resultSet.getLong("tckimlikno");
				row[1] = resultSet.getString("ad");
				row[2] = resultSet.getString("soyad");
				row[3] = resultSet.getInt("dogum_tarihi");

				tableModel.addRow(row);
			}

			table.setModel(tableModel);

		} catch (SQLException e) {
			System.out.println("Hata: getir() " + e.getMessage());
		}
	}
	
	/**
	 * İlk harfleri büyük yapar, diğerlerini küçük.
	 */
	public String buyukHarf(String str) {
		String[] kelimeler = str.split(" ");
		String yeniKelime = "";
		for (String kelime : kelimeler) {
			yeniKelime += kelime.substring(0, 1).toUpperCase() + kelime.substring(1).toLowerCase() + " ";
		}
		return yeniKelime.trim();
	}

}
