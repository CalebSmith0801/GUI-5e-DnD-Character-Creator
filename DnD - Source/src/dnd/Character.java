/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.util.Pair;

/**
 *
 * @author calebs
 */
public class Character {
    private String raceName;
    private String subraceName;
    private String className;
    private String alignment;
    private String hitDice;
    private String size;
    private String spellCastingAbility;
    private ArrayList<Pair<String, String>> traits;
    private ArrayList<String> armorProficiencies;
    private ArrayList<String> weaponProficiencies;
    private ArrayList<String> toolProficiencies;
    private ArrayList<String> skills;
    private ArrayList<String> languages;
    private ArrayList<String> saves;
    private ArrayList<String> spells;
    private int strScore, strMod, strSave, strRaceBonus;
    private int dexScore, dexMod, dexSave, dexRaceBonus;
    private int conScore, conMod, conSave, conRaceBonus;
    private int intScore, intMod, intSave, intRaceBonus;
    private int wisScore, wisMod, wisSave, wisRaceBonus;
    private int charScore, charMod, charSave, charRaceBonus;
    private int age;
    private int speed;
    private int proficiencyBonus;
    private int darkvision;

    
    public Character(){
        raceName="";
        subraceName="";
        className="";
        alignment="";
        hitDice="";
        size="";
        spellCastingAbility = "";
        traits = new ArrayList<>();
        armorProficiencies = new ArrayList<>();
        weaponProficiencies = new ArrayList<>();
        toolProficiencies = new ArrayList<>();
        skills = new ArrayList<>();
        languages = new ArrayList<>();
        saves = new ArrayList<>();
        spells = new ArrayList<>();
        strScore = 0; strMod = 0; strSave = 0; strRaceBonus = 0;
        dexScore = 0; dexMod = 0; dexSave = 0; dexRaceBonus = 0;
        conScore = 0; conMod = 0; conSave = 0; conRaceBonus = 0;
        intScore = 0; intMod = 0; intSave = 0; intRaceBonus = 0;
        wisScore = 0; wisMod = 0; wisSave = 0; wisRaceBonus = 0;
        charScore = 0; charMod = 0; charSave = 0; charRaceBonus = 0;
        age = 0;
        speed = 0;
        proficiencyBonus = 0;
        darkvision = 0;
    }
    
    public Character(Character r){
        this.raceName = r.raceName;
        this.subraceName = r.subraceName;
        this.className = r.className;
        this.alignment = r.alignment;
        this.hitDice = r.hitDice;
        this.size = r.size;
        this.spellCastingAbility = r.spellCastingAbility;
        this.traits = new ArrayList<>(r.traits);
        this.armorProficiencies = new ArrayList<>(r.armorProficiencies);
        this.weaponProficiencies = new ArrayList<>(r.weaponProficiencies);
        this.toolProficiencies = new ArrayList<>(r.toolProficiencies);
        this.skills = new ArrayList<>(r.skills);
        this.languages = new ArrayList<>(r.languages);
        this.saves = new ArrayList<>(r.saves);
        this.spells = new ArrayList<>(r.spells);
        this.strScore = r.strMod; this.strMod = r.strMod; this.strSave = r.strSave; this.strRaceBonus = r.strRaceBonus;
        this.dexScore = r.dexMod; this.dexMod = r.dexMod; this.dexSave = r.dexSave; this.dexRaceBonus = r.dexRaceBonus;
        this.conScore = r.conMod; this.conMod = r.conMod; this.conSave = r.conSave; this.conRaceBonus = r.conRaceBonus;
        this.intScore = r.intMod; this.intMod = r.intMod; this.intSave = r.intSave; this.intRaceBonus = r.intRaceBonus;
        this.wisScore = r.wisMod; this.wisMod = r.wisMod; this.wisSave = r.wisSave; this.wisRaceBonus = r.wisRaceBonus;
        this.charScore = r.charMod; this.charMod = r.charMod; this.charSave = r.charSave; this.charRaceBonus = r.charRaceBonus;
        this.age = r.age;
        this.speed = r.speed;
        this.proficiencyBonus = r.proficiencyBonus;
        this.darkvision = r.darkvision;
    }
    
    //*******GETTERS***********//
    public String getraceName(){
        return raceName;
    }
    
    public String getsubraceName(){
        return subraceName;
    }
    
    public String getclassName(){
        return className;
    }
    
    public String getAlignment(){
        return alignment;
    }
    
    public String getHitDice(){
        return hitDice;
    }
    
    public String getSize(){
        return size;
    }
    
    public String getSpellCastingAbility(){
        return spellCastingAbility;
    }
    
    public int getAge(){
        return age;
    }
    
    public int getSpeed(){
        return speed;
    }
    
    public int getProficiencyBonus(){
        return proficiencyBonus;
    }
    
    public int getDarkVision(){
        return darkvision;
    }
    
    
    //******get ability scores******//
    public int getStrScore(){
        return strScore;
    }
    
    public int getDexScore(){
        return dexScore;
    }
    
    public int getConScore(){
        return conScore;
    }
    
    public int getIntScore(){
        return intScore;
    }
    
    public int getWisScore(){
        return wisScore;
    }
    
    public int getCharScore(){
        return charScore;
    }
    
    
    //**********get ability modifiers*********//
    public int getStrMod(){
        return strMod;
    }
    
    public int getDexMod(){
        return dexMod;
    }
    
    public int getConMod(){
        return conMod;
    }
    
    public int getIntMod(){
        return intMod;
    }
    
    public int getWisMod(){
        return wisMod;
    }
    
    public int getCharMod(){
        return charMod;
    }
    
    
    //**********get ability saves**********//
    public int getStrSave(){
        return strSave;
    }
    
    public int getDexSave(){
        return dexSave;
    }
    
    public int getConSave(){
        return conSave;
    }
    
    public int getIntSave(){
        return intSave;
    }
    
    public int getWisSave(){
        return wisSave;
    }
    
    public int getCharSave(){
        return charSave;
    }
    
    
    //*************get ability race bonuses***************//
    public int getStrRaceBonus(){
        return strRaceBonus;
    }
    
    public int getDexRaceBonus(){
        return dexRaceBonus;
    }
    
    public int getConRaceBonus(){
        return conRaceBonus;
    }
    
    public int getIntRaceBonus(){
        return intRaceBonus;
    }
    
    public int getWisRaceBonus(){
        return wisRaceBonus;
    }
    
    public int getCharRaceBonus(){
        return charRaceBonus;
    }
    
    
    
    //**********SETTERS***************//
    public void setraceName(String r){
        raceName = r;
    }
        
    public void setsubraceName(String r){
        subraceName = r;
    }
    
    public void setclassName(String r){
        className = r;
    }
    
    public void setAlignment(String a){
        alignment = a;
    }
    
    public void setHitDice(String h){
        hitDice = h;
    }
    
    public void setSize(String s){
        size = s;
    }
    
    public void setSpellCastingAbility(String s){
        spellCastingAbility = s;
    }
    
    public void setAge(int a){
        age = a;
    }
    
    public void setSpeed(int s){
        speed = s;
    }
    
    public void setProficiencyBonus(int p){
        proficiencyBonus = p;
    }
    
    public void setDarkVision(int d){
        darkvision = d;
    }

    
    
    //*********set ability scores*********//
    public void setStrScore(int s){
        strScore = s;
    }
    
    public void setDexScore(int d){
        dexScore = d;
    }
    
    public void setConScore(int c){
        conScore = c;
    }
    
    public void setIntScore(int i){
        intScore = i;
    }
    
    public void setWisScore(int w){
        wisScore = w;
    }
    
    public void setCharScore(int c){
        charScore = c;
    }
    
    
    //***********set ability saves********//
    public void setStrSave(int s){
        strSave = s;
    }
    
    public void setDexSave(int d){
        dexSave = d;
    }
    
    public void setConSave(int c){
        conSave = c;
    }
    
    public void setIntSave(int i){
        intSave = i;
    }
    
    public void setWisSave(int w){
        wisSave = w;
    }
    
    public void setCharSave(int c){
        charSave = c;
    }
    
    
    //*************set ability race bonuses***********//
    public void setStrRaceBonus(int s){
        strRaceBonus = s;
    }
    
    public void setDexRaceBonus(int d){
        dexRaceBonus = d;
    }
    
    public void setConRaceBonus(int c){
        conRaceBonus = c;
    }
    
    public void setIntRaceBonus(int i){
        intRaceBonus = i;
    }
    
    public void setWisRaceBonus(int w){
        wisRaceBonus = w;
    }
    
    public void setCharRaceBonus(int c){
        charRaceBonus = c;
    }
    
    //ArrayList Functions
    public void addTrait(String trait, String descrip){
        traits.add(new Pair(trait, descrip));
    }
    
    public void addArmorProficiency(String... p){
        armorProficiencies.addAll(Arrays.asList(p));
    }
    
    public void addWeaponProficiency(String... p){
        weaponProficiencies.addAll(Arrays.asList(p));
    }
    
    public void addToolProficiency(String... p){
        toolProficiencies.addAll(Arrays.asList(p));
    }
    
    public void addSkill(String... p){
        skills.addAll(Arrays.asList(p));
    }
    
    public void addLanguage(String... l){
        languages.addAll(Arrays.asList(l));
    }
    
    public void addSaves(String... s){
        saves.addAll(Arrays.asList(s));
    }
    
    public void addSpell(String... spell){
        spells.addAll(Arrays.asList(spell));
    }
    
    
    public void RemoveTrait(String s){
        for (int i = 0; i < traits.size(); i++){
            if (traits.get(i).getKey().equals(s)){
                traits.remove(i);
                break;
            }
        }
    }
    
    public void RemoveSpell(String... s){
        spells.removeAll(Arrays.asList(s));
    }
    
    public void RemoveLastSpell(){
        spells.remove(spells.size()-1);
    }
    
    public void RemoveAllTraits(){
        traits.clear();
    }
    
    public void RemoveAllSpells(){
        spells.clear();
    }

    public void RemoveLastArmorProficiency(){
        armorProficiencies.remove(armorProficiencies.size()-1);
    }
    
    public void RemoveLastWeaponProficiency(){
        weaponProficiencies.remove(weaponProficiencies.size()-1);
    }
    
    public void RemoveLastToolProficiency(){
        toolProficiencies.remove(toolProficiencies.size()-1);
    }
    
    public void RemoveLastSkill(){
        skills.remove(skills.size()-1);
    }
    
    //Remove specific proficiency given its name
    public void RemoveArmorProficiency(String... s){
        armorProficiencies.removeAll(Arrays.asList(s));
    }
    
    public void RemoveWeaponProficiency(String... s){
        weaponProficiencies.removeAll(Arrays.asList(s));
    }
    
    public void RemoveToolProficiency(String... s){
        toolProficiencies.removeAll(Arrays.asList(s));
    }
    
    public void RemoveSkill(String... s){
        skills.removeAll(Arrays.asList(s));
    }
    
    public void RemoveAllArmorProficiencies(){
        armorProficiencies.clear();
    }
    
    public void RemoveAllWeaponProficiencies(){
        weaponProficiencies.clear();
    }
    
    public void RemoveAllToolProficiencies(){
        toolProficiencies.clear();
    }
    
    public void RemoveAllSkills(){
        skills.clear();
    }
    
    public void RemoveLanuguage(String s){
        int index = languages.indexOf(s);
        languages.remove(index);
    }
    
    public void RemoveLastLanguage(){
        languages.remove(languages.size()-1);
    }
    
    public void RemoveAllLanguages(){
        languages.clear();
    }
    
    public void RemoveSave(String s){
        int index = saves.indexOf(s);
        saves.remove(index);
    }
    
    public void RemoveAllSaves(){
        saves.clear();
    }
    
    public int getTraitsSize(){
        return traits.size();
    }
    
    public int getArmorProficienciesSize(){
        return armorProficiencies.size();
    }
    
    public int getWeaponProficienciesSize(){
        return weaponProficiencies.size();
    }
    
    public int getToolProficienciesSize(){
        return toolProficiencies.size();
    }
    
    public int getSkillsSize(){
        return skills.size();
    }
    
    public int getLanguagesSize(){
        return languages.size();
    }
    
    public Pair getTrait(int index){
        return traits.get(index);
    }
    
    public String getTraitKey(int index){
        return traits.get(index).getKey();
    }
    
    public String getTraitValue(int index){
        return traits.get(index).getValue();
    }
    
    public String getArmorProficiency(int index){
        return armorProficiencies.get(index);
    }
    
    public String getWeaponProficiency(int index){
        return weaponProficiencies.get(index);
    }
    
    public String getToolProficiency(int index){
        return toolProficiencies.get(index);
    }
    
    public String getSkills(int index){
        return skills.get(index);
    }
    
    public ArrayList getAllLanguages(){
        return languages;
    }
    
    public ArrayList getAllSkills(){
        return skills;
    }
    
    public String getLanguage(int index){
        return languages.get(index);
    }
    
    public String getLastSpell(){
        return spells.get(spells.size()-1);
    }
    
    public boolean hasTrait(String trait){
        for (int i = 0; i < traits.size(); i++){
            if (traits.get(i).getKey().equals(trait))
                return true;
        }
        return false;
    }
    
    public boolean hasProficiency(String proficiency){
        return (armorProficiencies.contains(proficiency) || weaponProficiencies.contains(proficiency) || 
                toolProficiencies.contains(proficiency) || skills.contains(proficiency));
    }
    
    public boolean hasSpell(String spell){
        return spells.contains(spell);
    }
   
    
    
    //Other Class Functions
    public void setModifiers(){ //Function from DnD rulebook on how to create modifiers
        strMod = (int) Math.floor((strScore - 10) / 2.0);
        dexMod = (int) Math.floor((dexScore - 10) / 2.0);
        conMod = (int) Math.floor((conScore - 10) / 2.0);
        intMod = (int) Math.floor((intScore - 10) / 2.0);
        wisMod = (int) Math.floor((wisScore - 10) / 2.0);
        charMod = (int) Math.floor((charScore - 10) / 2.0);
    }
    
    public void RemoveAllRaceBonuses(){
        strRaceBonus = 0;
        dexRaceBonus = 0;
        conRaceBonus = 0;
        intRaceBonus = 0;
        wisRaceBonus = 0;
        charRaceBonus = 0;
    }
    
    //for testing purposes
    public void printCharacter(){
        System.out.println("Race: " + raceName + " Subrace: " + subraceName + " Class: " + className);
        System.out.println("HitDice: " + hitDice);
        System.out.println("Str:   Score: " + strScore + " Mod: " + strMod + " Save: " + strSave + " Bonus: " + strRaceBonus);
        System.out.println("Dex:   Score: " + dexScore + " Mod: " + dexMod + " Save: " + dexSave + " Bonus: " + dexRaceBonus);
        System.out.println("Con:   Score: " + conScore + " Mod: " + conMod + " Save: " + conSave + " Bonus: " + conRaceBonus);
        System.out.println("Int:   Score: " + intScore + " Mod: " + intMod + " Save: " + intSave + " Bonus: " + intRaceBonus);
        System.out.println("Wis:   Score: " + wisScore + " Mod: " + wisMod + " Save: " + wisSave + " Bonus: " + wisRaceBonus);
        System.out.println("Char:   Score: " + charScore + " Mod: " + charMod + " Save: " + charSave + " Bonus: " + charRaceBonus);
        System.out.println("Size: " + size + " Speed: " + speed + " Darkvision: " + darkvision);
		System.out.println("Alignment: " + alignment + " Age: " + age + " Proficiency Bonus: " + proficiencyBonus);
		System.out.println("Spellcasting Ability: " + spellCastingAbility);
        System.out.println("Languages: " + Arrays.toString(languages.toArray()));
        System.out.println("Armor Proficiencies: " + Arrays.toString(armorProficiencies.toArray()));
        System.out.println("Weapon Proficiencies: " + Arrays.toString(weaponProficiencies.toArray()));
        System.out.println("Tool Proficiencies: " + Arrays.toString(toolProficiencies.toArray()));
        System.out.println("Skills: " + Arrays.toString(skills.toArray()));
        System.out.println("Saves: " + Arrays.toString(saves.toArray()));
        System.out.println("Spells: " + Arrays.toString(spells.toArray()));
        System.out.println("Traits: " + Arrays.toString(traits.toArray()));
    }
}
