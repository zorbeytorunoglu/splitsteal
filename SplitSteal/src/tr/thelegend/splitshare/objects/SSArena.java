package tr.thelegend.splitshare.objects;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SSArena {
	
	private String name=null;
	private Location pos1=null;
	private Location pos2=null;
	private Location resultSpot1=null;
	private Location resultSpot2=null;
	private Location spawn=null;
	private boolean isOccupied=false;
	private int amount=0;
	private Player firstPlayer=null;
	private Player secondPlayer=null;
	private ArrayList<Location> chooseButtons=new ArrayList<Location>();
	private String firstPlayerChoice=null;
	private String secondPlayerChoice=null;
	
	public SSArena(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getPos1() {
		return pos1;
	}

	public void setPos1(Location pos1) {
		this.pos1 = pos1;
	}

	public Location getPos2() {
		return pos2;
	}

	public void setPos2(Location pos2) {
		this.pos2 = pos2;
	}

	public Location getResultSpot1() {
		return resultSpot1;
	}

	public void setResultSpot1(Location resultSpot1) {
		this.resultSpot1 = resultSpot1;
	}

	public Location getResultSpot2() {
		return resultSpot2;
	}

	public void setResultSpot2(Location resultSpot2) {
		this.resultSpot2 = resultSpot2;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(Player secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public ArrayList<Location> getChooseButtons() {
		return chooseButtons;
	}

	public void setChooseButtons(ArrayList<Location> chooseButtons) {
		this.chooseButtons = chooseButtons;
	}

	public String getFirstPlayerChoice() {
		return firstPlayerChoice;
	}

	public void setFirstPlayerChoice(String firstPlayerChoice) {
		this.firstPlayerChoice = firstPlayerChoice;
	}

	public String getSecondPlayerChoice() {
		return secondPlayerChoice;
	}

	public void setSecondPlayerChoice(String secondPlayerChoice) {
		this.secondPlayerChoice = secondPlayerChoice;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
}
