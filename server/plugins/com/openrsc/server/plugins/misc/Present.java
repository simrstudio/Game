package com.openrsc.server.plugins.misc;

import com.openrsc.server.constants.IronmanMode;
import com.openrsc.server.constants.ItemId;
import com.openrsc.server.content.DropTable;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.model.entity.update.ChatMessage;
import com.openrsc.server.plugins.Functions;
import com.openrsc.server.plugins.listeners.action.InvActionListener;
import com.openrsc.server.plugins.listeners.action.InvUseOnPlayerListener;
import com.openrsc.server.plugins.listeners.executive.InvActionExecutiveListener;
import com.openrsc.server.plugins.listeners.executive.InvUseOnPlayerExecutiveListener;
import com.openrsc.server.util.rsc.DataConversions;

import static com.openrsc.server.plugins.Functions.showBubble;

public class Present implements InvUseOnPlayerListener, InvUseOnPlayerExecutiveListener, InvActionListener, InvActionExecutiveListener {

	private static DropTable presentDrops;

	static {
		presentDrops = new DropTable();
		DropTable holidayTable = new DropTable();
		DropTable junkTable = new DropTable();
		DropTable bronzeTable = new DropTable();
		DropTable ironTable = new DropTable();
		DropTable steelTable = new DropTable();
		DropTable blackTable = new DropTable();
		DropTable mithTable = new DropTable();
		DropTable addyTable = new DropTable();
		DropTable runeTable = new DropTable();
		DropTable ultraRareTable = new DropTable();

		/**
		 * Holiday Items Table
		 * Authentic holiday items are meant to be more common
		 */
		holidayTable.addItemDrop(ItemId.CHRISTMAS_CRACKER.id(), 1, 64);
		holidayTable.addItemDrop(ItemId.SANTAS_HAT.id(), 1, 64);
		holidayTable.addItemDrop(ItemId.CHRISTMAS_CAPE.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.SANTAS_HAT_BEARD.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.CHRISTMAS_APRON.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.SANTAS_GLOVES.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.SANTAS_MITTENS.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.SANTAS_SUIT_TOP.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.SANTAS_SUIT_BOTTOM.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.GREEN_SANTAS_HAT.id(), 1, 9);
		holidayTable.addItemDrop(ItemId.RUDOLPHS_ANTLERS.id(), 1, 8);
		holidayTable.addItemDrop(ItemId.GLASS_MILK.id(), 1, 12);
		holidayTable.addItemDrop(ItemId.CANE_COOKIE.id(), 1, 12);
		holidayTable.addItemDrop(ItemId.STAR_COOKIE.id(), 1, 12);
		holidayTable.addItemDrop(ItemId.TREE_COOKIE.id(), 1, 12);

		/**
		 * Junk Items
		 */
		junkTable.addItemDrop(ItemId.COAL.id(), 1, 128);
		junkTable.addItemDrop(ItemId.CHOCOLATE_BAR.id(), 1, 16);
		junkTable.addItemDrop(ItemId.BEER.id(), 1, 16);
		junkTable.addItemDrop(ItemId.TINDERBOX.id(), 1, 16);
		junkTable.addItemDrop(ItemId.MILK.id(), 1, 16);
		junkTable.addItemDrop(ItemId.CHOCOLATY_MILK.id(), 1, 16);
		junkTable.addItemDrop(ItemId.LOGS.id(), 1, 16);
		junkTable.addItemDrop(ItemId.CAKE.id(), 1, 16);
		junkTable.addItemDrop(ItemId.CAKE_TIN.id(), 1, 16);

		/**
		 * Bronze Items
		 */
		bronzeTable.addItemDrop(ItemId.BRONZE_ARROWS.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_PICKAXE.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_AXE.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_LONG_SWORD.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_PLATE_MAIL_BODY.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_PLATE_MAIL_LEGS.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_PLATE_MAIL_TOP.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_PLATED_SKIRT.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_2_HANDED_SWORD.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_CHAIN_MAIL_BODY.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_SPEAR.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_THROWING_DART.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_THROWING_KNIFE.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_KITE_SHIELD.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_SQUARE_SHIELD.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_BATTLE_AXE.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_DAGGER.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_SCIMITAR.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_SHORT_SWORD.id(), 1, 1);
		bronzeTable.addItemDrop(ItemId.BRONZE_MACE.id(), 1, 1);

		/**
		 * Iron Items
		 */
		ironTable.addItemDrop(ItemId.IRON_ARROWS.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_PICKAXE.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_AXE.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_LONG_SWORD.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_PLATE_MAIL_BODY.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_PLATE_MAIL_LEGS.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_PLATE_MAIL_TOP.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_PLATED_SKIRT.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_2_HANDED_SWORD.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_CHAIN_MAIL_BODY.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_SPEAR.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_THROWING_DART.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_THROWING_KNIFE.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_KITE_SHIELD.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_SQUARE_SHIELD.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_BATTLE_AXE.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_DAGGER.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_SCIMITAR.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_SHORT_SWORD.id(), 1, 1);
		ironTable.addItemDrop(ItemId.IRON_MACE.id(), 1, 1);

		/**
		 * Steel Items
		 */
		steelTable.addItemDrop(ItemId.STEEL_ARROWS.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_PICKAXE.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_AXE.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_LONG_SWORD.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_PLATE_MAIL_BODY.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_PLATE_MAIL_LEGS.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_PLATE_MAIL_TOP.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_PLATED_SKIRT.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_2_HANDED_SWORD.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_CHAIN_MAIL_BODY.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_SPEAR.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_THROWING_DART.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_THROWING_KNIFE.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_KITE_SHIELD.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_SQUARE_SHIELD.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_BATTLE_AXE.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_DAGGER.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_SCIMITAR.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_SHORT_SWORD.id(), 1, 1);
		steelTable.addItemDrop(ItemId.STEEL_MACE.id(), 1, 1);

		/**
		 * Black Items
		 */
		blackTable.addItemDrop(ItemId.BLACK_AXE.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_LONG_SWORD.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_PLATE_MAIL_BODY.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_PLATE_MAIL_LEGS.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_PLATE_MAIL_TOP.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_PLATED_SKIRT.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_2_HANDED_SWORD.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_CHAIN_MAIL_BODY.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_THROWING_KNIFE.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_KITE_SHIELD.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_SQUARE_SHIELD.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_BATTLE_AXE.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_DAGGER.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_SCIMITAR.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_SHORT_SWORD.id(), 1, 1);
		blackTable.addItemDrop(ItemId.BLACK_MACE.id(), 1, 1);

		/**
		 * Mith Items
		 */
		mithTable.addItemDrop(ItemId.MITHRIL_ARROWS.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_PICKAXE.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_AXE.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_LONG_SWORD.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_PLATE_MAIL_BODY.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_PLATE_MAIL_LEGS.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_PLATE_MAIL_TOP.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_PLATED_SKIRT.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_2_HANDED_SWORD.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_CHAIN_MAIL_BODY.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_SPEAR.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_THROWING_DART.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_THROWING_KNIFE.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_KITE_SHIELD.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_SQUARE_SHIELD.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_BATTLE_AXE.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_DAGGER.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_SCIMITAR.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_SHORT_SWORD.id(), 1, 1);
		mithTable.addItemDrop(ItemId.MITHRIL_MACE.id(), 1, 1);

		/**
		 * Addy Items
		 */
		addyTable.addItemDrop(ItemId.ADAMANTITE_ARROWS.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_PICKAXE.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_AXE.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_LONG_SWORD.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_PLATE_MAIL_BODY.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_PLATE_MAIL_LEGS.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_PLATE_MAIL_TOP.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_PLATED_SKIRT.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_2_HANDED_SWORD.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_CHAIN_MAIL_BODY.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_SPEAR.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_THROWING_DART.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_THROWING_KNIFE.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_KITE_SHIELD.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_SQUARE_SHIELD.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_BATTLE_AXE.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_DAGGER.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_SCIMITAR.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_SHORT_SWORD.id(), 1, 1);
		addyTable.addItemDrop(ItemId.ADAMANTITE_MACE.id(), 1, 1);

		/**
		 * Rune Items
		 */
		runeTable.addItemDrop(ItemId.RUNE_ARROWS.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_PICKAXE.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_AXE.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_LONG_SWORD.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_PLATE_MAIL_BODY.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_PLATE_MAIL_LEGS.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_PLATE_MAIL_TOP.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_SKIRT.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_2_HANDED_SWORD.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_CHAIN_MAIL_BODY.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_SPEAR.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_THROWING_DART.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_THROWING_KNIFE.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_KITE_SHIELD.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_SQUARE_SHIELD.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_BATTLE_AXE.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_DAGGER.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_SCIMITAR.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_SHORT_SWORD.id(), 1, 1);
		runeTable.addItemDrop(ItemId.RUNE_MACE.id(), 1, 1);

		/**
		 * Ultra Rare Items
		 */
		ultraRareTable.addItemDrop(ItemId.LOOP_KEY_HALF.id(), 1, 38);
		ultraRareTable.addItemDrop(ItemId.TOOTH_KEY_HALF.id(), 1, 36);
		ultraRareTable.addItemDrop(ItemId.DRAGONSTONE.id(), 1, 19);
		ultraRareTable.addItemDrop(ItemId.DRAGONSTONE.id(), 2, 3);
		ultraRareTable.addItemDrop(ItemId.DRAGON_SWORD.id(), 1, 14);
		ultraRareTable.addItemDrop(ItemId.DRAGON_AXE.id(), 1, 10);
		ultraRareTable.addItemDrop(ItemId.DRAGON_MEDIUM_HELMET.id(), 1, 2);
		ultraRareTable.addItemDrop(ItemId.RIGHT_HALF_DRAGON_SQUARE_SHIELD.id(), 1, 4);
		ultraRareTable.addItemDrop(ItemId.LEFT_HALF_DRAGON_SQUARE_SHIELD.id(), 1, 2);

		/**
		 * Bring all the tables together
		 */
		presentDrops.addTableDrop(holidayTable, 128);
		presentDrops.addTableDrop(junkTable, 48);
		presentDrops.addTableDrop(bronzeTable, 14);
		presentDrops.addTableDrop(ironTable, 13);
		presentDrops.addTableDrop(steelTable, 12);
		presentDrops.addTableDrop(blackTable, 12);
		presentDrops.addTableDrop(mithTable, 11);
		presentDrops.addTableDrop(addyTable, 10);
		presentDrops.addTableDrop(runeTable, 6);
		presentDrops.addTableDrop(ultraRareTable, 2);
	}

	@Override
	public void onInvUseOnPlayer(Player player, Player otherPlayer, Item item) {
		if (item.getID() == ItemId.PRESENT.id()) {
			if (otherPlayer.isIronMan(IronmanMode.Ironman.id()) || otherPlayer.isIronMan(IronmanMode.Ultimate.id())
				|| otherPlayer.isIronMan(IronmanMode.Hardcore.id()) || otherPlayer.isIronMan(IronmanMode.Transfer.id())) {
				player.message(otherPlayer.getUsername() + " is an Iron Man. " + (otherPlayer.isMale() ? "He" : "She") + " stands alone.");
				return;
			}

			if(!player.getWorld().getServer().getConfig().CAN_USE_CRACKER_ON_SELF && !player.isAdmin() && player.getCurrentIP().equalsIgnoreCase(otherPlayer.getCurrentIP())) {
				player.message(otherPlayer.getUsername() + " does not want your present...");
				return;
			}

			player.setBusy(true);
			//otherPlayer.setBusy(true);
			player.face(otherPlayer);
			otherPlayer.face(player);

			showBubble(player, item);
			player.message("You give a present to " + otherPlayer.getUsername());
			otherPlayer.message(player.getUsername() + " handed you a present...");
			Functions.sleep(player.getWorld().getServer().getConfig().GAME_TICK);
			otherPlayer.message("You unwrap the present and reach your hand inside...");
			Functions.sleep(player.getWorld().getServer().getConfig().GAME_TICK);

			Item prize = presentDrops.rollItem(false, otherPlayer);
			String prizeName = prize.getDef(player.getWorld()).getName().toLowerCase();

			player.message(otherPlayer.getUsername() + " got a " + prizeName + " from your present!");
			otherPlayer.message("You take out a " + prizeName + ".");
			Functions.sleep(player.getWorld().getServer().getConfig().GAME_TICK);

			String playerDialogue;

			if(prize.getID() == ItemId.COAL.id()) {
				switch(DataConversions.random(0, 8)) {
					default:
					case 0:
						playerDialogue = "No presents for you!";
						break;
					case 1:
						playerDialogue = "Naughty boys and girls get coal for christmas";
						break;
					case 2:
						playerDialogue = "Oh, behave!";
						break;
					case 3:
						playerDialogue = "I can get you off the Naughty List, for a price...";
						break;
					case 4:
						playerDialogue = "Darn! Almost had it.";
						break;
					case 5:
						playerDialogue = "a glitch for a grinch, a pile of coal for you!";
						break;
					case 6:
						playerDialogue = "For not believing in Christmas you get a lump of coal";
						break;
					case 7:
						playerDialogue = "for behavior so cold, you are getting a lump of coal";
						break;
					case 8:
						playerDialogue = "I know what you did last summer";
						break;
				}
			} else {
				playerDialogue = "Happy holidays";
			}

			player.getUpdateFlags().setChatMessage(new ChatMessage(player, playerDialogue, null));

			otherPlayer.getInventory().add(prize);
			player.getInventory().remove(item);

			player.setBusy(false);
			//otherPlayer.setBusy(false);
		}
	}

	@Override
	public void onInvAction(Item item, Player player, String command) {
		player.message("It would be selfish to keep this for myself");
		player.message("I should give it to someone else");
	}

	@Override
	public boolean blockInvUseOnPlayer(Player player, Player otherPlayer, Item item) {
		return item.getID() == ItemId.PRESENT.id();
	}

	@Override
	public boolean blockInvAction(Item item, Player player, String command) {
		return item.getID() == ItemId.PRESENT.id();
	}
}
