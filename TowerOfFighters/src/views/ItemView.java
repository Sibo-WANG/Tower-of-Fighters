package views;
import models.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class ItemView extends JFrame implements Observer{
	public ItemView() {
	}

	private JTextField itemNameText;
	private Map<String, ItemModel> itemsByMap;

	@Override
	public void update(Observable o, Object arg) {
		JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\item\\itemImage\\Weapon\\Weapon5.jpeg"));
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
//		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setTitle("Items --- by:SiboW");
		frame.setSize(1280, 800);
		frame.getContentPane().setLayout(null);
		try {
			itemsByMap = ((ItemModel)o).listAllItems();
		} catch (UnsupportedEncodingException | FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		JPanel manipulatePanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		manipulatePanel.setBounds(0, 40, 520, 720);
		frame.getContentPane().add(manipulatePanel);
		manipulatePanel.setLayout(null);
		
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(560, 40, 720, 720);
		frame.getContentPane().add(displayPanel);
		displayPanel.setLayout(null);
		
		JLabel lblItemImage = new JLabel();
		lblItemImage.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
//		lblItemImage.setBounds(310, 160, 100, 100);
		lblItemImage.setBounds(325, 160, 66, 66);
		displayPanel.add(lblItemImage);
		
		JLabel lblItemNameToShow = new JLabel();
		lblItemNameToShow.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemNameToShow.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblItemNameToShow.setBounds(276, 280, 160, 21);
		displayPanel.add(lblItemNameToShow);
		
		JLabel lblAffectedTypeToShow = new JLabel();
		lblAffectedTypeToShow.setHorizontalAlignment(SwingConstants.CENTER);
		lblAffectedTypeToShow.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblAffectedTypeToShow.setBounds(276, 330, 160, 21);
		displayPanel.add(lblAffectedTypeToShow);
		
		JLabel lblItemName = new JLabel("Item name:");
		lblItemName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblItemName.setBounds(30, 70, 120, 30);
		manipulatePanel.add(lblItemName);
		
		JLabel lblItemType = new JLabel("Item type:");
		lblItemType.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblItemType.setBounds(30, 120, 120, 30);
		manipulatePanel.add(lblItemType);
		
		itemNameText = new JTextField();
		itemNameText.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		itemNameText.setBounds(150, 70, 120, 30);
		manipulatePanel.add(itemNameText);
		itemNameText.setColumns(10);
		
		JComboBox<String> affectedTypeCbox = new JComboBox<String>();
		affectedTypeCbox.setEnabled(false);
		affectedTypeCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		affectedTypeCbox.setBounds(150, 175, 120, 21);
		manipulatePanel.add(affectedTypeCbox);
		affectedTypeCbox.addItem("select item type");
		
		JComboBox<String> itemTypeCbox = new JComboBox<String>();
		itemTypeCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		itemTypeCbox.setBounds(150, 125, 120, 21);
		manipulatePanel.add(itemTypeCbox);
		itemTypeCbox.addItem("Helmet");
		itemTypeCbox.addItem("Armor");
		itemTypeCbox.addItem("Shield");
		itemTypeCbox.addItem("Ring");
		itemTypeCbox.addItem("Belt");
		itemTypeCbox.addItem("Boot");
		itemTypeCbox.addItem("Weapon");
		itemTypeCbox.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				affectedTypeCbox.removeAllItems();
				affectedTypeCbox.setEnabled(true);
				String selected = itemTypeCbox.getSelectedItem().toString();
				switch (selected) {
				case "Helmet":
					affectedTypeCbox.addItem("Intelligence");
					affectedTypeCbox.addItem("Wisdom");
					affectedTypeCbox.addItem("Armor_class");
					break;
				case "Armor":
					affectedTypeCbox.addItem("Armor_class");
					break;
				case "Shield":
					affectedTypeCbox.addItem("Armor_class");
					break;
				case "Ring":
					affectedTypeCbox.addItem("Armor_class");
					affectedTypeCbox.addItem("Strength");
					affectedTypeCbox.addItem("Constitution");
					affectedTypeCbox.addItem("Wisdom");
					affectedTypeCbox.addItem("Charisma");
					break;
				case "Belt":
					affectedTypeCbox.addItem("Constitution");
					affectedTypeCbox.addItem("Strength");
					break;
				case "Boot":
					affectedTypeCbox.addItem("Armor_class");
					affectedTypeCbox.addItem("Dexterity");
					break;
				case "Weapon":
					affectedTypeCbox.addItem("Attack_bonus");
					affectedTypeCbox.addItem("Damage_bonus");
				}
			}
		});
	
		JComboBox<Integer> affectedValueCbox = new JComboBox<Integer>();
		affectedValueCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		affectedValueCbox.setBounds(150, 225, 55, 21);
		manipulatePanel.add(affectedValueCbox);
		for (int i = 0; i < 10; i++) {
			affectedValueCbox.addItem(i+1);
		}
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnCreate.setBounds(150, 275, 120, 30);
		manipulatePanel.add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<ItemModel> allKeysInMap = new ArrayList<ItemModel>();
				Iterator<Entry<String, ItemModel>> it = itemsByMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, ItemModel> entry = (Map.Entry<String, ItemModel>)it.next();
					allKeysInMap.add(entry.getValue());
				}
				for (ItemModel alreadyExist : allKeysInMap) {
					if(alreadyExist.getItemName().equals(itemNameText.getText())){
						JOptionPane.showMessageDialog(null, "Item name already exist");
						itemNameText.setText("Invalid item name");
					}
				}
				if (itemNameText.getText().equals("Invalid item name")||itemNameText.getText().equals("")||itemNameText.getText().contains(" ")) {
					JOptionPane.showMessageDialog(null, "Invalid item name");
					itemNameText.setText("");
				}
				else{
					if (affectedTypeCbox.getSelectedItem().toString().equals("select item type")) {
						JOptionPane.showMessageDialog(null, "Choose affected type");
					}
					else{
						try {
							ItemModel newItem = new ItemModel(itemNameText.getText(),
															itemTypeCbox.getSelectedItem().toString(),
															affectedTypeCbox.getSelectedItem().toString(),
															Integer.parseInt(affectedValueCbox.getSelectedItem().toString())
															);
							itemsByMap.put(newItem.getItemID(), newItem);
							newItem.saveItem(newItem);
							JOptionPane.showMessageDialog(null, itemNameText.getText()+" create successfully");
							itemNameText.setText("");
							affectedTypeCbox.removeAllItems();
							affectedTypeCbox.addItem("select item type");
							affectedTypeCbox.setEnabled(false);
							ImageIcon itemImage = new ImageIcon("images\\item\\itemImage\\"+newItem.getItemType()+"\\"+newItem.getItemImage());
							lblItemImage.setIcon(itemImage);
							lblItemImage.setText("");
							lblItemNameToShow.setText(newItem.getItemName());
							lblAffectedTypeToShow.setText(newItem.getAffectedType()+" + "+newItem.getAffectedValue()+"");
						} catch (NumberFormatException | UnsupportedEncodingException | FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}						
					}
				}
			}
		});
		
		JLabel lblCreateNewItem = new JLabel("Create New Item");
		lblCreateNewItem.setHorizontalAlignment(SwingConstants.LEFT);
		lblCreateNewItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblCreateNewItem.setBounds(180, 10, 200, 30);
		manipulatePanel.add(lblCreateNewItem);
		
		JLabel lblAffectedType = new JLabel("Affected type:");
		lblAffectedType.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblAffectedType.setBounds(30, 170, 120, 30);
		manipulatePanel.add(lblAffectedType);
		
		JLabel lblAffectedValue = new JLabel("Affected value:");
		lblAffectedValue.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblAffectedValue.setBounds(30, 220, 120, 30);
		manipulatePanel.add(lblAffectedValue);
		
		JLabel labelSeparate = new JLabel("---------------------------------------------------------------");
		labelSeparate.setHorizontalAlignment(SwingConstants.CENTER);
		labelSeparate.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		labelSeparate.setBounds(0, 310, 520, 30);
		manipulatePanel.add(labelSeparate);
		
		JLabel lblEditItem = new JLabel("Edit Item");
		lblEditItem.setHorizontalAlignment(SwingConstants.LEFT);
		lblEditItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		lblEditItem.setBounds(180, 350, 200, 30);
		manipulatePanel.add(lblEditItem);
		
		JLabel labelChooseItem = new JLabel("Choose item:");
		labelChooseItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		labelChooseItem.setBounds(30, 400, 120, 30);
		manipulatePanel.add(labelChooseItem);
		
		JComboBox<String> affectedTypeToEditCbox = new JComboBox<String>();
		affectedTypeToEditCbox.setEnabled(false);
		affectedTypeToEditCbox.addItem("select item");
		affectedTypeToEditCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		affectedTypeToEditCbox.setBounds(150, 455, 120, 21);
		manipulatePanel.add(affectedTypeToEditCbox);
		
		JComboBox<Integer> affectedValueToEditCbox = new JComboBox<Integer>();
		affectedValueToEditCbox.setEnabled(false);
		affectedValueToEditCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		affectedValueToEditCbox.setBounds(150, 505, 120, 21);
		manipulatePanel.add(affectedValueToEditCbox);
		for (int i = 0; i < 10; i++) {
			affectedValueToEditCbox.addItem(i+1);
		}
		
		JComboBox<ItemModel> itemToChooseCbox = new JComboBox<ItemModel>();
		itemToChooseCbox.setEnabled(false);
		itemToChooseCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		itemToChooseCbox.setBounds(150, 405, 120, 21);
		manipulatePanel.add(itemToChooseCbox);
		itemToChooseCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				affectedTypeToEditCbox.removeAllItems();
				affectedTypeToEditCbox.setEnabled(true);
				affectedValueToEditCbox.setEnabled(true);
				ItemModel itemModel = (ItemModel) itemToChooseCbox.getSelectedItem();
				ImageIcon itemImage = new ImageIcon("images\\item\\itemImage\\"+itemModel.getItemType()+"\\"+itemModel.getItemImage());
				lblItemImage.setIcon(itemImage);
				lblItemNameToShow.setText(itemModel.getItemName());
				lblAffectedTypeToShow.setText(itemModel.getAffectedType()+" + "+itemModel.getAffectedValue()+"");
				switch (itemModel.getItemType()) {
				case "Helmet":
				affectedTypeToEditCbox.addItem("Intelligence");
				affectedTypeToEditCbox.addItem("Wisdom");
				affectedTypeToEditCbox.addItem("Armor_class");
				break;
				case "Armor":
				affectedTypeToEditCbox.addItem("Armor_class");
				break;
				case "Shield":
				affectedTypeToEditCbox.addItem("Armor_class");
				break;
				case "Ring":
				affectedTypeToEditCbox.addItem("Armor_class");
				affectedTypeToEditCbox.addItem("Strength");
				affectedTypeToEditCbox.addItem("Constitution");
				affectedTypeToEditCbox.addItem("Wisdom");
				affectedTypeToEditCbox.addItem("Charisma");
				break;
				case "Belt":
				affectedTypeToEditCbox.addItem("Constitution");
				affectedTypeToEditCbox.addItem("Strength");
				break;
				case "Boot":
				affectedTypeToEditCbox.addItem("Armor_class");
				affectedTypeToEditCbox.addItem("Dexterity");
				break;
				case "Weapon":
				affectedTypeToEditCbox.addItem("Attack_bonus");
				affectedTypeToEditCbox.addItem("Damage_bonus");
				break;
			default:
				affectedTypeToEditCbox.addItem("Press reload");
				affectedTypeToEditCbox.setEnabled(false);
				break;
				}
			}
		});	
		
		JButton btnReload = new JButton("Reload");
		btnReload.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnReload.setBounds(284, 400, 120, 30);
		manipulatePanel.add(btnReload);
		
		JButton btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnSave.setBounds(150, 555, 120, 30);
		manipulatePanel.add(btnSave);
		
		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemModel itemModel = new ItemModel();
				for (String itemID : itemsByMap.keySet()) {
					itemModel = itemsByMap.get(itemID);
					for (int i = 0; i < itemToChooseCbox.getItemCount(); i++) {
						if (itemToChooseCbox.getItemAt(i).equals(itemModel)) {
							itemToChooseCbox.removeItem(itemModel);
						}
					} 
					itemToChooseCbox.addItem(itemModel);
					itemToChooseCbox.setEnabled(true);
				}
				btnSave.setEnabled(true);
			}
		});
		

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItemID = null;
				try {
					String selected = itemToChooseCbox.getSelectedItem().toString();
					ItemModel selectedItem = (ItemModel) itemToChooseCbox.getSelectedItem();
					for (String itemID : itemsByMap.keySet()) {
						ItemModel itemModel = itemsByMap.get(itemID);
						if (itemModel.getItemName().equals(selected)) {
							selectedItemID = itemModel.getItemID();
							itemModel.setAffectedType(affectedTypeToEditCbox.getSelectedItem().toString());
							itemModel.setAffectedValue(Integer.parseInt(affectedValueToEditCbox.getSelectedItem().toString()));
							itemsByMap.replace(selectedItemID, itemModel);
							Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
							FileWriter fw = new FileWriter("files\\items.json");
							fw.write(gson.toJson(itemsByMap));
							fw.close();
						}
					}
					
					JOptionPane.showMessageDialog(null, "Save successfully");
					ImageIcon itemImage = new ImageIcon("images\\item\\itemImage\\"+selectedItem.getItemType()+"\\"+selectedItem.getItemImage());
					lblItemImage.setIcon(itemImage);
					lblItemNameToShow.setText(selectedItem.getItemName());
					lblAffectedTypeToShow.setText(affectedTypeToEditCbox.getSelectedItem().toString()+" + "+affectedValueToEditCbox.getSelectedItem().toString());
					btnSave.setEnabled(false);
					itemToChooseCbox.setEnabled(false);
					affectedTypeToEditCbox.setEnabled(false);
					affectedValueToEditCbox.setEnabled(false);
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}				

			}
		});
		
		JLabel lblAffectedTypeToEdit = new JLabel("Affected type:");
		lblAffectedTypeToEdit.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblAffectedTypeToEdit.setBounds(30, 450, 120, 30);
		manipulatePanel.add(lblAffectedTypeToEdit);
		
		JLabel lblAffectedValueToEdit = new JLabel("Affected value:");
		lblAffectedValueToEdit.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblAffectedValueToEdit.setBounds(30, 500, 120, 30);
		manipulatePanel.add(lblAffectedValueToEdit);	

	}
}
