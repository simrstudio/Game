package com.openrsc.server.plugins.npcs.catherby;

import com.openrsc.server.constants.ItemId;
import com.openrsc.server.constants.NpcId;
import com.openrsc.server.constants.Skills;
import com.openrsc.server.model.Shop;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.model.world.World;
import com.openrsc.server.net.rsc.ActionSender;
import com.openrsc.server.plugins.ShopInterface;
import com.openrsc.server.plugins.listeners.action.TalkToNpcListener;
import com.openrsc.server.plugins.listeners.executive.TalkToNpcExecutiveListener;

import java.util.ArrayList;
import java.util.List;
import static com.openrsc.server.plugins.Functions.*;


public class HicktonArcheryShop implements ShopInterface,
	TalkToNpcListener, TalkToNpcExecutiveListener {

	private final Shop shop = new Shop(false, 10000, 100, 80, 1,
		new Item(ItemId.CROSSBOW_BOLTS.id(), 200), new Item(ItemId.BRONZE_ARROWS.id(), 200), new Item(ItemId.IRON_ARROWS.id(), 200),
		new Item(ItemId.STEEL_ARROWS.id(), 0), new Item(ItemId.MITHRIL_ARROWS.id(), 0), new Item(ItemId.ADAMANTITE_ARROWS.id(), 0),
		new Item(ItemId.RUNE_ARROWS.id(), 0), new Item(ItemId.BRONZE_ARROW_HEADS.id(), 200), new Item(ItemId.IRON_ARROW_HEADS.id(), 180),
		new Item(ItemId.STEEL_ARROW_HEADS.id(), 160), new Item(ItemId.MITHRIL_ARROW_HEADS.id(), 140),
		new Item(ItemId.ADAMANTITE_ARROW_HEADS.id(), 120), new Item(ItemId.RUNE_ARROW_HEADS.id(), 100), new Item(ItemId.SHORTBOW.id(), 4),
		new Item(ItemId.LONGBOW.id(), 2), new Item(ItemId.CROSSBOW.id(), 2), new Item(ItemId.OAK_SHORTBOW.id(), 4),
		new Item(ItemId.OAK_LONGBOW.id(), 4));

	@Override
	public boolean blockTalkToNpc(final Player p, final Npc n) {
		return n.getID() == NpcId.HICKTON.id();
	}

	@Override
	public Shop[] getShops(World world) {
		return new Shop[]{shop};
	}

	@Override
	public boolean isMembers() {
		return true;
	}

	@Override
	public void onTalkToNpc(final Player p, final Npc n) {
		npcTalk(p, n, "Welcome to Hickton's Archery Store",
			"Do you want to see my wares?");
		List<String> choices = new ArrayList<>();
		choices.add("Yes please");
		choices.add("No, I prefer to bash things close up");
		if (p.getWorld().getServer().getConfig().WANT_CUSTOM_QUESTS
		&& getMaxLevel(p, Skills.FLETCHING) >= 99)
			choices.add("Fletching Skillcape");

		final int option = showMenu(p, n, false, //do not send over
			choices.toArray(new String[0]));
		if (option == 0) {
			playerTalk(p, n, "Yes Please");
			p.setAccessingShop(shop);
			ActionSender.showShop(p, shop);
		} else if (option == 1) {
			playerTalk(p, n, "No, I prefer to bash things close up");
		} else if (option == 2) {
			if (getMaxLevel(p, Skills.FLETCHING) >= 99) {
				npcTalk(p, n, "I see you've carved your way to the top",
					"i can offer you cape",
					"made for those who excel in fletching",
					"the cost is 99,000 coins");
				int choice2 = showMenu(p, n, true, "I'll buy one", "Not at the moment");
				if (choice2 == 0) {
					if (p.getInventory().countId(ItemId.COINS.id()) >= 99000) {
						if (p.getInventory().remove(ItemId.COINS.id(), 99000) > -1) {
							addItem(p, ItemId.FLETCHING_CAPE.id(), 1);
							npcTalk(p, n, "while wearing this cape",
								"fletching arrows, bolts and darts",
								"may give you extras");
						}
					} else {
						npcTalk(p, n, "come back with the money anytime");
					}
				}
			}
		}
	}

}
