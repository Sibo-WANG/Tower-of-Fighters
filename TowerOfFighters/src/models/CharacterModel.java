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
import java.util.Observable;
import java.util.Map.Entry;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CharacterModel extends Observable{
//--------------------------------------------------Attributes--------------------------------------------------	
	/**
	 * unique character ID
	 */
	private String characterID;
	/**
	 * character name
	 */
	private String characterName;
	/**
	 * character type
	 */
	private String characterType;
	/**
	 * image to show for the character
	 */
	private String characterImage;
	/**
	 * image to show in the map
	 */
	private String characterIcon;
	/**
	 * character's level
	 */
	private int level;
	/**
	 * character's Strength
	 */
	private int strength;
	/**
	 * character's Dexterity
	 */
	private int dexterity;
	/**
	 * character's Constitution
	 */
	private int constitution;
	/**
	 * character's Intelligence
	 */
	private int intelligence;
	/**
	 * character's Wisdom
	 */
	private int wisdom;
	/**
	 * character's Charisma
	 */
	private int charisma;
	/**
	 * character's strength modifier
	 */
	private int strengthModifier;
	/**
	 * character's dexterity modifier
	 */
	private int dexterityModifier;
	/**
	 * character's constitution modifier
	 */
	private int constitutionModifier;
	/**
	 * character's intelligence modifier
	 */
	private int intelligenceModifier;
	/**
	 * character's wisdom modifier;
	 */
	private int wisdomModifier;
	/**
	 * character's charisma modifier
	 */
	private int charismaModifier;
	/**
	 * character's hit points
	 */
	private int hitPoints;
	/**
	 * character's armor class
	 */
	private int armorClass;
	/**
	 * character's attack bonus
	 */
	private int attackBonus;
	/**
	 * character's damage bonus
	 */
	private int damageBonus;
	/**
	 * character's multiple attacks
	 */
	private int multipleAttacks;
	/**
	 * character's item slots
	 */
	private ArrayList<ItemModel> itemSlots;
	/**
	 * character's backpack
	 */
	private ArrayList<ItemModel> backpack;
	
//--------------------------------------------------Methods--------------------------------------------------	
	/**
	 * default construct
	 */
	public CharacterModel(){
		
	}
	
	/**
	 * custom construct
	 * @param characterName
	 * @param characterType
	 * @param characterImage
	 * @param characterIcon
	 * @param level
	 * @param backpackItems
	 * generate other attributes of the character
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	public CharacterModel(String characterName, String characterType, int level, ArrayList<ItemModel> backpackItems) throws NumberFormatException, UnsupportedEncodingException, FileNotFoundException{
		this.itemSlots = null;
		this.backpack = backpackItems;
		this.characterID = Integer.parseInt(this.lastCharacterID())+1+"";
		this.characterName = characterName;
		this.characterType = characterType;
		this.level = level;
		Random random = new Random();
		String randomImageIcon;
		randomImageIcon = (random.nextInt(5)+1)+"";
		this.characterImage = characterType+randomImageIcon+".jpeg";
		this.characterIcon = characterType+randomImageIcon+".jpeg";
		this.strength = this.generateCharacterAttributes();
		this.dexterity = this.generateCharacterAttributes();
		this.constitution = this.generateCharacterAttributes();
		this.intelligence = this.generateCharacterAttributes();
		this.wisdom = this.generateCharacterAttributes();
		this.charisma = this.generateCharacterAttributes();
		this.strengthModifier = this.calculateModifierByModifierTable(strength);
		this.dexterityModifier = this.calculateModifierByModifierTable(dexterity);
		this.constitutionModifier = this.calculateModifierByModifierTable(constitution);
		this.intelligenceModifier = this.calculateModifierByModifierTable(intelligence);
		this.wisdomModifier = this.calculateModifierByModifierTable(wisdom);
		this.charismaModifier = this.calculateModifierByModifierTable(charisma);
		this.hitPoints = (random.nextInt(10)+1+this.constitution)*this.level;
		this.armorClass = this.dexterityModifier+10;
		this.attackBonus = this.level+this.strengthModifier+this.dexterityModifier;
		this.damageBonus = this.strengthModifier;
		if (this.attackBonus < 6) {
			this.multipleAttacks = 0;
		}
		if (this.attackBonus >= 6 && this.attackBonus < 11) {
			this.multipleAttacks = 1;
		}
		if (this.attackBonus >= 11 && this.attackBonus <16) {
			this.multipleAttacks = 2;
		}
		if (this.attackBonus >=16 && this.attackBonus <21) {
			this.multipleAttacks = 3;
		}
		if (this.attackBonus >=21 && this.attackBonus <26) {
			this.multipleAttacks = 4;
		}
		if (this.attackBonus >=26) {
			this.multipleAttacks = 5;
		}
	}
	
	/**
	 * equip an item will add the item to the character item slot
	 * @param itemToEquip
	 * @return add the item to the corresbonding slot of the character
	 */
	public CharacterModel equipItem(ItemModel itemToEquip){
		this.itemSlots.add(itemToEquip);
		this.backpack.remove(itemToEquip);
		switch (itemToEquip.getAffectedType()) {
		case "Strength":
			this.strength += itemToEquip.getAffectedValue();
			this.strengthModifier = this.calculateModifierByModifierTable(strength);
			this.attackBonus = this.level+this.strengthModifier+this.dexterityModifier;
			if (this.attackBonus < 6) {
				this.multipleAttacks = 0;
			}
			if (this.attackBonus >= 6 && this.attackBonus < 11) {
				this.multipleAttacks = 1;
			}
			if (this.attackBonus >= 11 && this.attackBonus <16) {
				this.multipleAttacks = 2;
			}
			if (this.attackBonus >=16 && this.attackBonus <21) {
				this.multipleAttacks = 3;
			}
			if (this.attackBonus >=21 && this.attackBonus <26) {
				this.multipleAttacks = 4;
			}
			if (this.attackBonus >=26) {
				this.multipleAttacks = 5;
			}
			break;
		case "Dexterity":
			this.dexterity += itemToEquip.getAffectedValue();
			this.dexterityModifier = this.calculateModifierByModifierTable(dexterity);
			this.attackBonus = this.level+this.strengthModifier+this.dexterityModifier;
			if (this.attackBonus < 6) {
				this.multipleAttacks = 0;
			}
			if (this.attackBonus >= 6 && this.attackBonus < 11) {
				this.multipleAttacks = 1;
			}
			if (this.attackBonus >= 11 && this.attackBonus <16) {
				this.multipleAttacks = 2;
			}
			if (this.attackBonus >=16 && this.attackBonus <21) {
				this.multipleAttacks = 3;
			}
			if (this.attackBonus >=21 && this.attackBonus <26) {
				this.multipleAttacks = 4;
			}
			if (this.attackBonus >=26) {
				this.multipleAttacks = 5;
			}
			break;
		case "Constitution":
			this.constitution += itemToEquip.getAffectedValue();
			this.constitutionModifier = this.calculateModifierByModifierTable(constitution);
			this.hitPoints = ((this.hitPoints/this.level)+this.constitution)*this.level;
			break;
		case "Intelligence":
			this.intelligence += itemToEquip.getAffectedValue();
			this.intelligenceModifier = this.calculateModifierByModifierTable(intelligence);
			break;
		case "Wisdom":
			this.wisdom += itemToEquip.getAffectedValue();
			this.wisdomModifier = this.calculateModifierByModifierTable(wisdom);
			break;
		case "Charisma":
			this.charisma += itemToEquip.getAffectedValue();
			this.charismaModifier = this.calculateModifierByModifierTable(charisma);
			break;
		case "Armor_class":
			this.armorClass += itemToEquip.getAffectedValue();
			break;
		case "Attack_bonus":
			this.attackBonus += itemToEquip.getAffectedValue();
			if (this.attackBonus < 6) {
				this.multipleAttacks = 0;
			}
			if (this.attackBonus >= 6 && this.attackBonus < 11) {
				this.multipleAttacks = 1;
			}
			if (this.attackBonus >= 11 && this.attackBonus <16) {
				this.multipleAttacks = 2;
			}
			if (this.attackBonus >=16 && this.attackBonus <21) {
				this.multipleAttacks = 3;
			}
			if (this.attackBonus >=21 && this.attackBonus <26) {
				this.multipleAttacks = 4;
			}
			if (this.attackBonus >=26) {
				this.multipleAttacks = 5;
			}
			break;
		case "Damage_bonus":
			this.damageBonus += itemToEquip.getAffectedValue();
			break;
		}
		return this;
	}
	
	/**
	 * drop the item from the character's backpack
	 * @param itemToDrop
	 * @return drop the item from the character's backpack
	 */
	public CharacterModel dropItemFromBackpack(ItemModel itemToDrop){
		this.backpack.remove(itemToDrop);
		return this;
	}
	
	/**
	 * unequip an item will remove the item from the character item slot
	 * @param itemToUnequip
	 * @return unequip the item, add the item to the character's backpack
	 */
	public CharacterModel unequipItem(ItemModel itemToUnequip){
		this.itemSlots.remove(itemToUnequip);
		this.backpack.add(itemToUnequip);
		switch (itemToUnequip.getAffectedType()) {
		case "Strength":
			this.strength -= itemToUnequip.getAffectedValue();
			this.strengthModifier = this.calculateModifierByModifierTable(strength);
			this.attackBonus = this.level+this.strengthModifier+this.dexterityModifier;
			if (this.attackBonus < 6) {
				this.multipleAttacks = 0;
			}
			if (this.attackBonus >= 6 && this.attackBonus < 11) {
				this.multipleAttacks = 1;
			}
			if (this.attackBonus >= 11 && this.attackBonus <16) {
				this.multipleAttacks = 2;
			}
			if (this.attackBonus >=16 && this.attackBonus <21) {
				this.multipleAttacks = 3;
			}
			if (this.attackBonus >=21 && this.attackBonus <26) {
				this.multipleAttacks = 4;
			}
			if (this.attackBonus >=26) {
				this.multipleAttacks = 5;
			}
			break;
		case "Dexterity":
			this.dexterity -= itemToUnequip.getAffectedValue();
			this.dexterityModifier = this.calculateModifierByModifierTable(dexterity);
			this.attackBonus = this.level+this.strengthModifier+this.dexterityModifier;
			if (this.attackBonus < 6) {
				this.multipleAttacks = 0;
			}
			if (this.attackBonus >= 6 && this.attackBonus < 11) {
				this.multipleAttacks = 1;
			}
			if (this.attackBonus >= 11 && this.attackBonus <16) {
				this.multipleAttacks = 2;
			}
			if (this.attackBonus >=16 && this.attackBonus <21) {
				this.multipleAttacks = 3;
			}
			if (this.attackBonus >=21 && this.attackBonus <26) {
				this.multipleAttacks = 4;
			}
			if (this.attackBonus >=26) {
				this.multipleAttacks = 5;
			}
			break;
		case "Constitution":
			this.constitution -= itemToUnequip.getAffectedValue();
			this.constitutionModifier = this.calculateModifierByModifierTable(constitution);
			this.hitPoints = ((this.hitPoints/this.level)+this.constitution)*this.level;
			break;
		case "Intelligence":
			this.intelligence -= itemToUnequip.getAffectedValue();
			this.intelligenceModifier = this.calculateModifierByModifierTable(intelligence);
			break;
		case "Wisdom":
			this.wisdom -= itemToUnequip.getAffectedValue();
			this.wisdomModifier = this.calculateModifierByModifierTable(wisdom);
			break;
		case "Charisma":
			this.charisma -= itemToUnequip.getAffectedValue();
			this.charismaModifier = this.calculateModifierByModifierTable(charisma);
			break;
		case "Armor_class":
			this.armorClass -= itemToUnequip.getAffectedValue();
			break;
		case "Attack_bonus":
			this.attackBonus -= itemToUnequip.getAffectedValue();
			if (this.attackBonus < 6) {
				this.multipleAttacks = 0;
			}
			if (this.attackBonus >= 6 && this.attackBonus < 11) {
				this.multipleAttacks = 1;
			}
			if (this.attackBonus >= 11 && this.attackBonus <16) {
				this.multipleAttacks = 2;
			}
			if (this.attackBonus >=16 && this.attackBonus <21) {
				this.multipleAttacks = 3;
			}
			if (this.attackBonus >=21 && this.attackBonus <26) {
				this.multipleAttacks = 4;
			}
			if (this.attackBonus >=26) {
				this.multipleAttacks = 5;
			}
			break;
		case "Damage_bonus":
			this.damageBonus -= itemToUnequip.getAffectedValue();
			break;
		}
		return this;
	}
	
	/**
	 * used in controller to call the viewer
	 */
	public void setCharacterView(){
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * generate randomly using 4d6
	 * @return int value
	 */
	public int generateCharacterAttributes(){
		Random random = new Random();
		int randomCollection[] = new int[4];
		for (int i = 0; i < randomCollection.length; i++) {
			randomCollection[i] = random.nextInt(6)+1;
		}
		return randomCollection[0]+randomCollection[1]+randomCollection[2]+randomCollection[3];
	}
	
	/**
	 * calculate ability modifier by modifier table
	 * @param basicAttribute
	 * @return ability modifier value
	 */
	public int calculateModifierByModifierTable(int basicAttribute){
		int modifier = 0;
		switch (basicAttribute) {
		case 1:
			modifier = -5;
			break;
		case 2:
			modifier = -4;
			break;
		case 3:
			modifier = -4;
			break;
		case 4:
			modifier = -3;
			break;
		case 5:
			modifier = -3;
			break;
		case 6:
			modifier = -2;
			break;
		case 7:
			modifier = -2;
			break;
		case 8:
			modifier = -1;
			break;
		case 9:
			modifier = -1;
			break;
		case 10:
			modifier = 0;
			break;
		case 11:
			modifier = 0;
			break;
		case 12:
			modifier = 1;
			break;	
		case 13:
			modifier = 1;
			break;
		case 14:
			modifier = 2;
			break;
		case 15:
			modifier = 2;
			break;
		case 16:
			modifier = 3;
			break;
		case 17:
			modifier = 3;
			break;
		case 18:
			modifier = 4;
			break;
		case 19:
			modifier = 4;
			break;
		case 20:
			modifier = 5;
			break;
		case 21:
			modifier = 5;
			break;
		case 22:
			modifier = 6;
			break;
		case 23:
			modifier = 6;
			break;
		case 24:
			modifier = 7;
			break;
		case 25:
			modifier = 7;
			break;
		case 26:
			modifier = 8;
			break;
		case 27:
			modifier = 8;
			break;
		case 28:
			modifier = 9;
			break;
		case 29:
			modifier = 9;
			break;
		case 30:
			modifier = 10;
			break;
		case 31:
			modifier = 10;
			break;
		case 32:
			modifier = 11;
			break;
		case 33:
			modifier = 11;
			break;
		case 34:
			modifier = 12;
			break;
		case 35:
			modifier = 12;
			break;
		case 36:
			modifier = 13;
			break;
		case 37:
			modifier = 13;
			break;
		case 38:
			modifier = 14;
			break;
		case 39:
			modifier = 14;
			break;
		case 40:
			modifier = 15;
			break;
		case 41:
			modifier = 15;
			break;
		case 42:
			modifier = 16;
			break;
		case 43:
			modifier = 16;
			break;
		case 44:
			modifier = 17;
			break;
		case 45:
			modifier = 17;
			break;
		}
		return modifier;
	}
	
	/**
	 * @return the last key of the map in the json file
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public String lastCharacterID() throws UnsupportedEncodingException, FileNotFoundException{
		ArrayList<String> allKeysInMap = new ArrayList<String>();
		Map<String, CharacterModel> charactersByMap = this.listAllCharacters();
		Iterator<Entry<String, CharacterModel>> it = charactersByMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, CharacterModel> entry = (Map.Entry<String, CharacterModel>)it.next();
			allKeysInMap.add(entry.getKey());
		}
		String lastCharacterID = allKeysInMap.get(allKeysInMap.size()-1);
		return lastCharacterID;
	}
	
	/**
	 * list all the characters from the character file
	 * @return a map
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public Map<String, CharacterModel> listAllCharacters() throws UnsupportedEncodingException, FileNotFoundException{
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		InputStreamReader isreader = new InputStreamReader(new FileInputStream("files\\characters.json"), "UTF-8");
		Map<String, CharacterModel> charactersByMap = gson.fromJson(isreader, new TypeToken<Map<String, CharacterModel>>(){}.getType());
		return charactersByMap;
	}
	
	/**
	 * save the character into the character file
	 * @param characterModel
	 * @throws IOException 
	 */
	public void saveCharacter(CharacterModel characterModel) throws IOException{
		Map<String, CharacterModel> charactersByMap = this.listAllCharacters();
		charactersByMap.put(characterModel.getCharacterID(), characterModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("files\\characters.json");
		fw.write(gson.toJson(charactersByMap));
		fw.close();
	}
	
	/**
	 * load the specific character by characterID
	 * @param characterID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public CharacterModel loadCharacter(String characterID) throws UnsupportedEncodingException, FileNotFoundException{
		Map<String, CharacterModel> charactersByMap = this.listAllCharacters();
		CharacterModel characterModel = charactersByMap.get(characterID);
		return characterModel;
	}
	
//--------------------------------------------------Getters/Setters--------------------------------------------------	
	public String getCharacterID() {
		return characterID;
	}
	public void setCharacterID(String characterID) {
		this.characterID = characterID;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getCharacterType() {
		return characterType;
	}
	public void setCharacterType(String characterType) {
		this.characterType = characterType;
	}
	public String getCharacterImage() {
		return characterImage;
	}
	public void setCharacterImage(String characterImage) {
		this.characterImage = characterImage;
	}
	public String getCharacterIcon() {
		return characterIcon;
	}
	public void setCharacterIcon(String characterIcon) {
		this.characterIcon = characterIcon;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getDexterity() {
		return dexterity;
	}
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
	public int getConstitution() {
		return constitution;
	}
	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}
	public int getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getWisdom() {
		return wisdom;
	}
	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}
	public int getCharisma() {
		return charisma;
	}
	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}
	public int getStrengthModifier() {
		return strengthModifier;
	}
	public void setStrengthModifier(int strengthModifier) {
		this.strengthModifier = strengthModifier;
	}
	public int getDexterityModifier() {
		return dexterityModifier;
	}
	public void setDexterityModifier(int dexterityModifier) {
		this.dexterityModifier = dexterityModifier;
	}
	public int getConstitutionModifier() {
		return constitutionModifier;
	}
	public void setConstitutionModifier(int constitutionModifier) {
		this.constitutionModifier = constitutionModifier;
	}
	public int getIntelligenceModifier() {
		return intelligenceModifier;
	}
	public void setIntelligenceModifier(int intelligenceModifier) {
		this.intelligenceModifier = intelligenceModifier;
	}
	public int getWisdomModifier() {
		return wisdomModifier;
	}
	public void setWisdomModifier(int wisdomModifier) {
		this.wisdomModifier = wisdomModifier;
	}
	public int getCharismaModifier() {
		return charismaModifier;
	}
	public void setCharismaModifier(int charismaModifier) {
		this.charismaModifier = charismaModifier;
	}
	public int getHitPoints() {
		return hitPoints;
	}
	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	public int getArmorClass() {
		return armorClass;
	}
	public void setArmorClass(int armorClass) {
		this.armorClass = armorClass;
	}
	public int getAttackBonus() {
		return attackBonus;
	}
	public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}
	public int getDamageBonus() {
		return damageBonus;
	}
	public void setDamageBonus(int damageBonus) {
		this.damageBonus = damageBonus;
	}
	public int getMultipleAttacks() {
		return multipleAttacks;
	}
	public void setMultipleAttacks(int multipleAttacks) {
		this.multipleAttacks = multipleAttacks;
	}
	public ArrayList<ItemModel> getItemSlots() {
		return itemSlots;
	}
	public void setItemSlots(ArrayList<ItemModel> itemSlots) {
		this.itemSlots = itemSlots;
	}
	public ArrayList<ItemModel> getBackpack() {
		return backpack;
	}
	public void setBackpack(ArrayList<ItemModel> backpack) {
		this.backpack = backpack;
	}
	@Override
	public String toString(){
		return "lvl"+this.level+" "+this.characterName;
	}
}
