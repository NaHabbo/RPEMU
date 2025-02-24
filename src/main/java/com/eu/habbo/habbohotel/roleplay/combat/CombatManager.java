package com.eu.habbo.habbohotel.roleplay.combat;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.outgoing.rooms.users.WhisperMessage;

public class CombatManager {

    public static void attack(Habbo attacker, Habbo target) {
        if (attacker.getHabboStats().getEnergy() <= 0) {
            attacker.getClient().sendResponse(new WhisperMessage(attacker, "You are too exhausted to attack!"));
            return;
        }

        if (!target.isAttackable()) {
            attacker.getClient().sendResponse(new WhisperMessage(attacker, "You can't attack this player right now!"));
            return;
        }

        int damage = calculateDamage(attacker);
        target.getHabboStats().decreaseHealth(damage);

        if (target.getHabboStats().getHealth() <= 0) {
            target.die();
        }

        attacker.getRoomUnit().shout("swings at " + target.getHabboInfo().getUsername() + ", dealing " + damage + " damage.");
        attacker.applyCombatCooldown();
    }

    private static int calculateDamage(Habbo attacker) {
        if (attacker.hasWeaponEquipped()) {
            return attacker.getHabboStats().getWeapon().getRandomDamage();
        } else {
            return (int) (Math.random() * (5 - 2 + 1) + 2); // Fist damage: 2-5
        }
    }
}