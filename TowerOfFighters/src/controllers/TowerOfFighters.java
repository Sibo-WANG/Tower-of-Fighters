package controllers;
import models.MapModel;
import models.CharacterModel;
import models.ItemModel;
import views.MapView;
import views.ItemView;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import java.awt.Toolkit;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class TowerOfFighters extends JFrame{

	MapModel mapModel;
	MapView mapView;
	ItemModel itemModel;
	ItemView itemView;
	CharacterModel characterModel;
	
	public TowerOfFighters(){
		
		JLayeredPane layeredPanel = new JLayeredPane();
		layeredPanel.setForeground(Color.BLACK);
		layeredPanel.setLayout(null);
				
		JPanel panel_background = new JPanel();
		panel_background.setBounds(0, 20, 1280, 750);
		layeredPanel.add(panel_background, JLayeredPane.DEFAULT_LAYER);
		
		JLabel background = new JLabel(new ImageIcon("images\\main\\main.jpg"));
		background.setBounds(new Rectangle(0, 0, 1000, 800));
		panel_background.add(background);	
		getContentPane().add(layeredPanel, BorderLayout.CENTER);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(255, 240, 200, 50);
		layeredPanel.add(btnNewGame, JLayeredPane.MODAL_LAYER);
		btnNewGame.setForeground(Color.WHITE);
		btnNewGame.setBackground(Color.BLACK);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewGame.setFont(new Font("Simplified Arabic", Font.PLAIN, 30));
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(255, 300, 200, 50);
		layeredPanel.add(btnLoad, JLayeredPane.MODAL_LAYER);
		btnLoad.setForeground(Color.WHITE);
		btnLoad.setBackground(Color.BLACK);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLoad.setFont(new Font("Simplified Arabic", Font.PLAIN, 30));
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(255, 360, 200, 50);
		layeredPanel.add(btnExit, JLayeredPane.MODAL_LAYER);
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.BLACK);
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});	
		btnExit.setFont(new Font("Simplified Arabic", Font.PLAIN, 30));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		menuBar.setBounds(0, 0, 1274, 23);
		layeredPanel.add(menuBar, JLayeredPane.MODAL_LAYER);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		mnFile.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnFile);
		
		JMenu mnCharacter = new JMenu("Character");
		mnCharacter.setForeground(Color.BLACK);
		mnCharacter.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnFile.add(mnCharacter);
		
		JMenuItem mntmCharacter = new JMenuItem("Create character");
		mnCharacter.add(mntmCharacter);
		mntmCharacter.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmCharacter.setForeground(Color.BLACK);
		
		JMenu mnItem = new JMenu("Item");
		mnItem.setForeground(Color.BLACK);
		mnItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnFile.add(mnItem);
		
		JMenuItem mntmItem = new JMenuItem("Create item");
		mntmItem.setForeground(Color.BLACK);
		mnItem.add(mntmItem);
		mntmItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				itemModel = new ItemModel();
				itemView = new ItemView();
				itemModel.addObserver(itemView);
				itemModel.setItemView();
			}
		});
		
		JMenu mnMap = new JMenu("Map");
		mnMap.setForeground(Color.BLACK);
		mnMap.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnFile.add(mnMap);
		
		JMenuItem mntmMap = new JMenuItem("Create map");
		mntmMap.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmMap.setForeground(Color.BLACK);
		mnMap.add(mntmMap);
		mntmMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapModel = new MapModel();
				mapView = new MapView();
				mapModel.addObserver(mapView);
			}
		});
		
//		JMenuItem mntmEditMap = new JMenuItem("Edit map");
//		mntmEditMap.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
//		mntmEditMap.setForeground(Color.BLACK);
//		mnMap.add(mntmEditMap);
//		mntmEditMap.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				mapModel = new MapModel();
//				mapView = new MapView();
//				mapModel.addObserver(mapView);
//			}
//		});
		
		JMenu mnCampaign = new JMenu("Campaign");
		mnCampaign.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnCampaign.setForeground(Color.BLACK);
		mnFile.add(mnCampaign);
		
		JMenuItem mntmCampaign = new JMenuItem("Create campaign");
		mntmCampaign.setForeground(Color.BLACK);
		mnCampaign.add(mntmCampaign);
		mntmCampaign.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
				
		JMenu mnAbout = new JMenu("About");
		mnAbout.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		menuBar.add(mnAbout);
				
		JMenuItem mntmAuthor = new JMenuItem("Author");
		mntmAuthor.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mntmAuthor.setForeground(Color.BLACK);
		mnAbout.add(mntmAuthor);
			
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\map\\hero.jpg"));
		setResizable(false);
		setLocationByPlatform(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Tower of Fighters --- by:SiboW");
		setSize(1280, 800);
		
	}
	
	public static void main(String[] args) throws IOException {
		new TowerOfFighters();
////		System.out.println("Hello world");
//		CharacterModel c1 = new CharacterModel("Yittt", "Friend", 15, null);
//
//		
//		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//		Map<String, CharacterModel> map1 = c1.listAllCharacters();
//		map1.put(c1.getCharacterID(), c1);
//		
//		
////		map1.put(c1.getCharacterID(), c1);
//		String str = gson.toJson(map1);
////		System.out.println(str);
//		File file = new File("files\\characters.json");
//		FileWriter fw = new FileWriter(file);
//		fw.write(str);
//		fw.close();		
	}

}
