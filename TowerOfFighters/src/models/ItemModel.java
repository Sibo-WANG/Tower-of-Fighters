package models;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ItemModel extends Observable{
//--------------------------------------------------Attributes--------------------------------------------------	
	/**
	 * unique item ID
	 */
	private String itemID;
	/**
	 * item name
	 */
	private	String itemName;
	/**
	 * item type
	 */
	private String itemType;
	/**
	 * which ability type to affect
	 */
	private String affectedType;
	/**
	 * the value to affect
	 */
	private int affectedValue;
	/**
	 * image to show for the item
	 */
	private String itemImage;
	/**
	 * icon to show in the map
	 */
	private String itemIcon;
	/**
	 * list all items by item ID in the file
	 */
//--------------------------------------------------Methods--------------------------------------------------	
	/**
	 * default construct
	 */
	public ItemModel(){
		
	}
	
	/**
	 * custom construct
	 * create a new item
	 * @param itemID
	 * @param itemName
	 * @param itemType
	 * @param affectedType
	 * @param affectedValue
	 * @param itemImage
	 * @param itemIcon
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	public ItemModel(String itemName, String itemType, String affectedType, int affectedValue) throws NumberFormatException, UnsupportedEncodingException, FileNotFoundException{
		this.itemID = Integer.parseInt(this.lastItemID())+1+"";
		this.itemName = itemName;
		this.itemType = itemType;
		this.affectedType = affectedType;
		this.affectedValue = affectedValue;
		Random random = new Random();
		String randomImageIcon;
//		randomImageIcon = (random.nextInt(10)+1)+"";
		randomImageIcon = (random.nextInt(5)+1)+"";
		this.itemImage = itemType+randomImageIcon+".jpeg";
		this.itemIcon = itemType+randomImageIcon+".jpeg";
	}
	
	/**
	 * used in controller to call the viewer
	 */
	public void setItemView(){
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * edit an existed item
	 * @param itemID
	 * @param affectedType
	 * @param affectedValue
	 */
	public void editItem(String itemID, String affectedType,int affectedValue){
		this.setItemID(itemID);
		this.setAffectedType(affectedType);
		this.setAffectedValue(affectedValue);
	}
	
	/**
	 * @return the last key of the map in the json file
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public String lastItemID() throws UnsupportedEncodingException, FileNotFoundException{
		ArrayList<String> allKeysInMap = new ArrayList<String>();
		Map<String, ItemModel> itemsByMap = this.listAllItems();
		Iterator<Entry<String, ItemModel>> it = itemsByMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ItemModel> entry = (Map.Entry<String, ItemModel>)it.next();
			allKeysInMap.add(entry.getKey());
		}
		String lastItemID = allKeysInMap.get(allKeysInMap.size()-1);
		return lastItemID;
	}
	
	/**
	 * save the item into the item file
	 * @param itemModel
	 * @throws IOException 
	 */
	public void saveItem(ItemModel itemModel) throws IOException{
		Map<String, ItemModel> itemsByMap = this.listAllItems();
		itemsByMap.put(itemModel.getItemID(), itemModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("files\\items.json");
		fw.write(gson.toJson(itemsByMap));
		fw.close();
	}
	
	/**
	 * list all the items from the item file
	 * @return a map
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, ItemModel> listAllItems() throws UnsupportedEncodingException, FileNotFoundException{
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		InputStreamReader isreader = new InputStreamReader(new FileInputStream("files\\items.json"), "UTF-8");
		Map<String, ItemModel> itemsByMap = gson.fromJson(isreader, new TypeToken<Map<String, ItemModel>>(){}.getType());
		return itemsByMap;
	}
	
	/**
	 * load the specific item by itemID
	 * @return
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public ItemModel loadItem(String itemID) throws UnsupportedEncodingException, FileNotFoundException{
		Map<String, ItemModel> itemsByMap = this.listAllItems();
		ItemModel itemModel = itemsByMap.get(itemID);
		return itemModel;	
	}
	
	/**
	 * delete the item from the item file
	 * @param itemID
	 * @throws IOException 
	 */
	public void deleteItem(String itemID) throws IOException{
		Map<String, ItemModel> itemsByMap = this.listAllItems();
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		itemsByMap.remove(itemID);
		FileWriter fw = new FileWriter("files\\items.json");
		fw.write(gson.toJson(itemsByMap));
		fw.close();
	}
//--------------------------------------------------Getters/Setters--------------------------------------------------	
	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	public String getAffectedType() {
		return affectedType;
	}

	public void setAffectedType(String affectedType) {
		this.affectedType = affectedType;
	}

	public int getAffectedValue() {
		return affectedValue;
	}

	public void setAffectedValue(int affectedValue) {
		this.affectedValue = affectedValue;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(String itemIcon) {
		this.itemIcon = itemIcon;
	}
	
	@Override
	public String toString(){
		return this.itemName;
	}

}
