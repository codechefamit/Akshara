import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Akshara extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SpellCheck sc;
	private JLabel lblImage;
	private JTextField textField;
	private JLabel lblGetPronunciation;
	private JButton btnPlay;
	private PlayAction pa;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Akshara frame = new Akshara();
					frame.setVisible(true);
					frame.setSize(350,300);
					frame.setTitle("Akshara by Amit Kothiyal");
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Akshara() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pa=new PlayAction();
		sc=new SpellCheck();
		
		JLabel lblAkshara = new JLabel("Akshara");
		lblAkshara.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblAkshara.setBounds(120, 11, 120, 55);
		contentPane.add(lblAkshara);
		
		JLabel lblWriteAWord = new JLabel("Write a word :");
		lblWriteAWord.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblWriteAWord.setBounds(10, 80, 140, 30);
		contentPane.add(lblWriteAWord);
		
		lblImage = new JLabel("");
		lblImage.setBounds(234, 111, 30, 30);
		contentPane.add(lblImage);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				char c=event.getKeyChar();
				if(c==KeyEvent.VK_ENTER && btnPlay.isEnabled()){
					pa.actionPerformed(null);
				}
			}
		});
		textField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent event) {
				change();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
					change();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				change();
			}
			
			void change(){
				if(textField.getText().equals("")){
					lblImage.setIcon(null);
					btnPlay.setEnabled(false);
				}
				else{
				String txt=textField.getText();
				if(sc.check(txt)){
					lblImage.setIcon(new ImageIcon(getClass().getResource("res/right.png")));
					btnPlay.setEnabled(true);
				}
				else{
					lblImage.setIcon(new ImageIcon(getClass().getResource("res/wrong.png")));
					btnPlay.setEnabled(false);
				}
				}
				
			}
			
		});
		textField.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField.setBounds(10, 111, 214, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblGetPronunciation = new JLabel("Get pronunciation :");
		lblGetPronunciation.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblGetPronunciation.setBounds(10, 152, 175, 30);
		contentPane.add(lblGetPronunciation);
		
		btnPlay = new JButton("Say");
		btnPlay.addActionListener(pa);
		btnPlay.setEnabled(false);
		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnPlay.setBounds(120, 193, 90, 50);
		contentPane.add(btnPlay);
		
	}
	
	class PlayAction implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String txt=textField.getText();
			if(!(new File("mp3/"+txt+".mp3").exists())){
			URL website;
			try {
				website = new URL("https://ssl.gstatic.com/dictionary/static/sounds/de/0/"+txt+".mp3");
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				@SuppressWarnings("resource")
				FileOutputStream fos = new FileOutputStream("mp3/"+txt+".mp3");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error in file download.");
				btnPlay.setEnabled(false);
			};
			}
			if(new File("mp3/"+txt+".mp3").exists()){
				 FileInputStream fis;
					try {
						fis = new FileInputStream("mp3\\"+txt+".mp3");
					    Player playMP3 = new Player(fis);
					    playMP3.play();
					} catch (FileNotFoundException | JavaLayerException e) {
						JOptionPane.showMessageDialog(null, "Error in playing pronunciation.");
						btnPlay.setEnabled(false);
					}
			}
			
		}
		
	}
}
