package com.openrsc.server.plugins.npcs.khazard;

import com.openrsc.server.constants.ItemId;
import com.openrsc.server.constants.NpcId;
import com.openrsc.server.event.rsc.GameStateEvent;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.listeners.action.TalkToNpcListener;
import com.openrsc.server.plugins.listeners.executive.TalkToNpcExecutiveListener;

import static com.openrsc.server.plugins.Functions.*;

public class Docky implements TalkToNpcExecutiveListener, TalkToNpcListener {

	@Override
	public GameStateEvent onTalkToNpc(Player p, Npc n) {
		return new GameStateEvent(p.getWorld(), p, 0, getClass().getSimpleName() + " " + getClass().getEnclosingMethod().getName()) {
			public void init() {
				addState(0, () -> {
					if (n.getID() == NpcId.DOCKY.id()) {
						playerTalk(p, n, "hello there");
						npcTalk(p, n, "ah hoy there, wanting",
							"to travel on the beatiful",
							"lady valentine are we");
						int menu = showMenu(p, n, "not really, just looking around", "where are you travelling to");
						if (menu == 0) {
							npcTalk(p, n, "o.k land lover");
						} else if (menu == 1) {
							npcTalk(p, n, "we sail direct to Birmhaven port",
								"it really is a speedy crossing",
								"so would you like to come",
								"it cost's 30 gold coin's");
							int travel = showMenu(p, n, false, //do not send over
								"no thankyou", "ok");
							if (travel == 0) {
								playerTalk(p, n, "no thankyou");
							} else if (travel == 1) {
								playerTalk(p, n, "Ok");
								if (hasItem(p, ItemId.COINS.id(), 30)) {
									message(p, 1900, "You pay 30 gold");
									removeItem(p, ItemId.COINS.id(), 30);
									message(p, 3000, "You board the ship");
									p.teleport(467, 647);
									sleep(2000);
									p.message("The ship arrives at Port Birmhaven");
								} else {
									playerTalk(p, n, "Oh dear I don't seem to have enough money");
								}
							}
						}
					}

					return null;
				});
			}
		};
	}

	@Override
	public boolean blockTalkToNpc(Player p, Npc n) {
		return n.getID() == NpcId.DOCKY.id();
	}
}
