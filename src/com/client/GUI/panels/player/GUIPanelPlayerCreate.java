package com.client.GUI.panels.player;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

import com.client.Main;
import com.client.GUI.collision.GUICollisionObject;
import com.client.GUI.collision.GUICollisionObjectBox;
import com.client.GUI.panels.GUIButton;
import com.client.GUI.panels.GUIPanelMovable;
import com.client.GUI.panels.ToolTip;
import com.client.Graphics.GraphicsAbstract;
import com.game.characters.GameCharacter.Gender;
import com.game.characters.GameCharacterTypes;
import com.game.characters.Player;
import com.server.command.PlayerDataCommand;
import com.utilities.Function;
import com.utilities.UtilityArray;
import com.utilities.UtilityMath;

/**
 * Encapsulates the player creation screen.
 * @author Ian
 */
public final class GUIPanelPlayerCreate extends GUIPanelMovable {
	/** Instance of the panel.  Using singleton pattern. */
	private final static GUIPanelPlayerCreate instance = new GUIPanelPlayerCreate();

	//All of these are class-level scope because setting the relative position method requires them.
	/** The gender icons. */
	private GenderIcon[] gender_icons;
	/** The button that increments the class type. */
	private ButtonArrow button_class_type_up;
	/** The button that decrements the class type. */
	private ButtonArrow button_class_type_down;
	/** The button that creates the player type. */
	private GUIButton button_create;

	/** The button that increments a previous character. */
	private ButtonArrow button_preexisting_up;
	/** The button that decrements a previous character. */
	private ButtonArrow button_preexisting_down;
	/** The button that selects a previous character. */
	private GUIButton button_preexisting_select;

	/** The selected type of character. */
	private GameCharacterTypes create_type;
	/** The selected preexisting character. */
	private Player selected_preexisting_character;
	/** The preexisting characters one may choose from, expressed as an array. */
	private Player[] preexisting_characters = null;
	
	/** Some offsets for quick adjustment. */
	private final static int create_rel_x = 80, select_rel_x = -80;
	
	/** The text of the new player's name. */
	private final StringBuilder working_name_text = new StringBuilder();
	
	/** Tells the user why they can't make a new character. */
	private final ToolTip tooltip_error = ToolTip.makeToolTip();

	/** Sets up the gender selection. */
	private void setupCreateGender() {
		final int NUMOF_GENDERS = 3; //The number of genders that exist.
		gender_icons = new GenderIcon[NUMOF_GENDERS];
		gender_icons[0] = new GenderIcon(this,Gender.  MALE,    "icon_male",1.00f);
		gender_icons[1] = new GenderIcon(this,Gender.FEMALE,  "icon_female",1.00f);
		gender_icons[2] = new GenderIcon(this,Gender.    IT,"icon_trollbot",0.54f);

		GUICollisionObject collision_objects_gender[] = new GUICollisionObject[NUMOF_GENDERS];
		for (int i=0;i<NUMOF_GENDERS;++i) {
			final int j = i;
			collision_objects_gender[j] = new GUICollisionObjectBox(gender_icons[j],54,54);
			collision_objects_gender[j].setFunctionClick(
					new Function() { @Override public void execute(Object... arguments) {
						for (int k=0;k<NUMOF_GENDERS;++k) gender_icons[k].setSelected(false);
						gender_icons[j].setSelected(true);
					} }
			);
			gender_icons[j].addCollisionObject(collision_objects_gender[j]);
		}
		collision_objects_gender[NUMOF_GENDERS-1].executeFunctionClick();

		children.put(    "male",gender_icons[0]);
		children.put(  "female",gender_icons[1]);
		children.put("trollbot",gender_icons[2]);
	}
	/** Sets up the type selection for a new character. */
	private void setupCreateType() {
		button_class_type_up   = new ButtonArrow(this,ButtonArrow.ARROW_TYPE.  UP);
		button_class_type_down = new ButtonArrow(this,ButtonArrow.ARROW_TYPE.DOWN);

		GUICollisionObject collision_objects_buttons[] = new GUICollisionObjectBox[2];
		collision_objects_buttons[0] = new GUICollisionObjectBox(button_class_type_up,32,32);
		collision_objects_buttons[0].setFunctionClick(
				new Function() { @Override public void execute(Object... arguments) {
					GameCharacterTypes[] types = GameCharacterTypes.values();
					create_type = types[UtilityMath.mod( UtilityArray.indexOf(types,create_type)+1, types.length )];
				} }
		);
		button_class_type_up.addCollisionObject(collision_objects_buttons[0]);

		collision_objects_buttons[1] = new GUICollisionObjectBox(button_class_type_down,32,32);
		collision_objects_buttons[1].setFunctionClick(
				new Function() { @Override public void execute(Object... arguments) {
					GameCharacterTypes[] types = GameCharacterTypes.values();
					create_type = types[UtilityMath.mod( UtilityArray.indexOf(types,create_type)-1, types.length )];
				} }
		);
		button_class_type_down.addCollisionObject(collision_objects_buttons[1]);

		children.put(  "btn_class_up",  button_class_type_up);
		children.put("btn_class_down",button_class_type_down);
	}
	/** Sets up the creation button. */
	private void setupCreate() {
		button_create = new GUIButton(this,"CREATE!");
		button_create.setFunctionPressed(new Function() { @Override public void execute(Object... arguments) {
			//Must be able to check the new character for validity.
			//System.out.println("preexisting_characters: "+preexisting_characters);
			if (preexisting_characters==null) {
				tooltip_error.setText("No characters could be compared against.");
				tooltip_error.show();
				return;
			}
			
			String requested_name = working_name_text.toString();
			if (requested_name.length()==0) {
				tooltip_error.setText("Cannot have an empty name!");
				tooltip_error.show();
				return;
			}
			for (Player p : preexisting_characters) { //Cannot have same name as already existing character.
				if (requested_name.equals(p.name)) {
					tooltip_error.setText("Cannot have the same name as an existing character!");
					tooltip_error.show();
					return;
				}
			}
			
			Gender requested_gender = null;
			for (int i=0;i<gender_icons.length;++i) {
				if (gender_icons[i].getSelected()) { requested_gender = gender_icons[i].gender; break; }
			}
			
			Player player = create_type.instantiate(requested_name,requested_gender);
			Main.player_name = player.name;
			//"Zartoth of the Woodland Realm";
// try {
// player.getInventory().add(new ShortSword());
// player.getInventory().add(new PlateMail());
// } catch (IllegalInventoryException e) {}
// player.getInventory().makeDeposit(1204538);
			player.setMap(Main.getCurrentMap().getName());
			
			System.out.println("###############################Creating a player "+player);

			Main.sendCommand(new PlayerDataCommand(Main.userprofile.getUserName(),player));

			closePanel();
		} });

		children.put("btn_create",button_create);
	}
	/** Sets up the selection for a preexisting character. */
	private void setupSelectPreexisting() {
		button_preexisting_up   = new ButtonArrow(this,ButtonArrow.ARROW_TYPE.  UP);
		button_preexisting_down = new ButtonArrow(this,ButtonArrow.ARROW_TYPE.DOWN);

		GUICollisionObject collision_objects_buttons[] = new GUICollisionObjectBox[2];
		collision_objects_buttons[0] = new GUICollisionObjectBox(button_preexisting_up,32,32);
		collision_objects_buttons[0].setFunctionClick(
				new Function() { @Override public void execute(Object... arguments) {
					if (preexisting_characters!=null&&preexisting_characters.length>0) {
						selected_preexisting_character = preexisting_characters[UtilityMath.mod(
								UtilityArray.indexOf(preexisting_characters,selected_preexisting_character)+1,
								preexisting_characters.length
						)];
					}
				} }
		);
		button_preexisting_up.addCollisionObject(collision_objects_buttons[0]);

		collision_objects_buttons[1] = new GUICollisionObjectBox(button_preexisting_down,32,32);
		collision_objects_buttons[1].setFunctionClick(
				new Function() { @Override public void execute(Object... arguments) {
					if (preexisting_characters!=null&&preexisting_characters.length>0) {
						selected_preexisting_character = preexisting_characters[UtilityMath.mod(
								UtilityArray.indexOf(preexisting_characters,selected_preexisting_character)-1,
								preexisting_characters.length
						)];
					}
				} }
		);
		button_preexisting_down.addCollisionObject(collision_objects_buttons[1]);

		children.put(  "btn_preexisting_up",  button_preexisting_up);
		children.put("btn_preexisting_down",button_preexisting_down);
	}
	/** Sets up the button that selects a previous character. */
	private final void setupSelect() {
		button_preexisting_select = new GUIButton(this,"SELECT!");
		button_preexisting_select.setFunctionPressed(new Function() { @Override public void execute(Object... arguments) {
			Player player = selected_preexisting_character;
			//"Zartoth of the Woodland Realm";
			player.setMap(Main.getCurrentMap().getName());
			Main.player_name = player.name;
			
			System.out.println("###############################Selecting a player "+player);

			Main.sendCommand(new PlayerDataCommand(Main.userprofile.getUserName(),player));
			Main.player_name = player.name;
			closePanel();
		} });

		children.put("btn_select",button_preexisting_select);
	}

	/** Constructor. */
	private GUIPanelPlayerCreate() {
		super(null);

		setupCreateGender();
		setupCreateType();
		setupCreate();

		setupSelectPreexisting();
		setupSelect();

		create_type = GameCharacterTypes.FIGHTER;

		setRelPositions();
	}
	/** Returns an instance of the message panel.  Using singleton pattern. */
	public final static GUIPanelPlayerCreate getInstance() {
		return instance;
	}

	/** 
	 * {@inheritDoc}
	 * 
	 * Good for reinitializing certain things.
	 */
	@Override public final void componentResized(ComponentEvent ce) {
		setRelPositions();
	}
	/**
	 * {@inheritDoc}
	 * 
	 * Adds characters to the creating player's name.
	 */
	@Override public boolean processKey(KeyEvent ke) {
		synchronized(working_name_text) {
			int key_code = ke.getExtendedKeyCode();
			if (key_code==KeyEvent.VK_BACK_SPACE||key_code==KeyEvent.VK_DELETE) {
				if (working_name_text.length()==0) return true;
				working_name_text.deleteCharAt(working_name_text.length()-1);
			} else {
				char c = ke.getKeyChar();
				if (c!=KeyEvent.CHAR_UNDEFINED) {
					working_name_text.append(c);
				}
			}
		}
		return true;
	}
	
	/** Sets the relative position of the creation screen. */
	private final void setRelPositions() {
		int cx = GraphicsAbstract.getWidth()/2;
		int cy = GraphicsAbstract.getHeight()/2;

		this.setPositionRel(cx,cy-87);

		gender_icons[0].setPositionRel(create_rel_x+0,  0);
		gender_icons[1].setPositionRel(create_rel_x+0, 60);
		gender_icons[2].setPositionRel(create_rel_x+0,120);

		button_class_type_up  .setPositionRel(create_rel_x+95,142-30+20);
		button_class_type_down.setPositionRel(create_rel_x+95,   +30+20);
		button_create.setPositionRel(create_rel_x+80,10);

		button_preexisting_up.setPositionRel(select_rel_x+-80,142-30+20);
		button_preexisting_down.setPositionRel(select_rel_x+-80,   +30+20);
		button_preexisting_select.setPositionRel(select_rel_x+-95,10);
	}

	/** {@inheritDoc} */
	@Override public final void draw() {
		Point pos = getPositionAbs();

		GraphicsAbstract.setColor(new Color(64,64,64,225));

		int border = 20;

		//int w = 400;
		int h = 174+50;
		//int x = pos.x-200;
		int y = GraphicsAbstract.getHeight()-pos.y-h+20;

		//GraphicsAbstract.drawRectFill(x-border,y-border,w+2*border,h+2*border);
		GraphicsAbstract.drawRectFill(0,y-border,GraphicsAbstract.getWidth(),h+2*border);

		create_type.drawRepresentation(create_rel_x+pos.x+85,GraphicsAbstract.getHeight()-pos.y-115+25);
		GraphicsAbstract.setFont(GraphicsAbstract.font12); GraphicsAbstract.setColor(Color.WHITE);
		GraphicsAbstract.drawText("New Character Name: \""+working_name_text.toString()+"\"",create_rel_x+pos.x,GraphicsAbstract.getHeight()-pos.y-190);
		tooltip_error.draw(pos.x+141,GraphicsAbstract.getHeight()-pos.y-10);
		
		if (preexisting_characters==null&&Main.userprofile!=null) {
			Collection<Player> players = Main.userprofile.getPlayers();
			System.out.println("##############################PLAYERS: "+players);

			preexisting_characters = new Player[players.size()];
			int i = 0;
			for (Player p : Main.userprofile.getPlayers()) {
				preexisting_characters[i++] = p;
			}
			
			if (players.size()!=0) selected_preexisting_character = preexisting_characters[0];
		}
		if (selected_preexisting_character!=null) {
			GraphicsAbstract.drawCharacterAbsolute(
					selected_preexisting_character,
					select_rel_x+pos.x-66,
					GraphicsAbstract.getHeight()-pos.y-115+37
			);
			GraphicsAbstract.setFont(GraphicsAbstract.font12); GraphicsAbstract.setColor(Color.WHITE);
			GraphicsAbstract.drawText(
					"Existing Character Name: \""+selected_preexisting_character.name+"\"",
					select_rel_x+pos.x-180,GraphicsAbstract.getHeight()-pos.y+20
			);
		}
	}
}