package net.mcreator.forlorncurios.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.ChatFormatting;

import net.mcreator.forlorncurios.backdoors.Owneruuid;

import java.util.UUID;
import java.util.List;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;

public class OwnerBadgeItem extends Item implements ICurioItem {
	private static final String TEAM_NAME = "modOwner";

	public OwnerBadgeItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
		return true;
	}

	@Override
	public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan, ItemStack stack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.literal("§bThe Cobalt Sigil"));
		list.add(Component.literal("§7A compact token of authority."));
		list.add(Component.literal("§7Bearer is beyond reproach, founder of this realm."));
		list.add(Component.literal(""));
		list.add(Component.literal("§9Passive Protection:"));
		list.add(Component.literal("§7 - Prevents XP loss on death"));
		list.add(Component.literal("§7 - Grants a powerful boost to Luck"));
	}

	// Attribute modifiers for equipped owner
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		if (slotContext.entity() instanceof Player player && player instanceof ServerPlayer serverPlayer) {
			if (Owneruuid.isOwner(serverPlayer)) {
				return ImmutableMultimap.of(Attributes.LUCK, new AttributeModifier(uuid, "cobalt_sigil_luck", 200.0, AttributeModifier.Operation.ADDITION));
			}
		}
		return ImmutableMultimap.of();
	}

	// Curios tick: runs every tick while equipped
	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		if (!(slotContext.entity() instanceof Player player))
			return;
		if (!(player.level() instanceof ServerLevel serverLevel))
			return;
		if (!(player instanceof ServerPlayer serverPlayer))
			return;
		// Only apply for your UUID
		if (!Owneruuid.isOwner(serverPlayer))
			return;
		// === Scoreboard team setup ===
		Scoreboard scoreboard = serverLevel.getScoreboard();
		PlayerTeam team = scoreboard.getPlayerTeam(TEAM_NAME);
		if (team == null) {
			team = scoreboard.addPlayerTeam(TEAM_NAME);
			team.setDisplayName(Component.literal("Mod Owner"));
			team.setColor(ChatFormatting.BLUE);
			team.setSeeFriendlyInvisibles(true);
			team.setAllowFriendlyFire(true);
		}
		PlayerTeam current = scoreboard.getPlayersTeam(player.getScoreboardName());
		if (current != team) {
			scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
		}
		// === Glowing effect ===
		player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, true, false));
		// === Particles (client-side only) ===
		if (player.level().isClientSide) {
			player.level().addParticle(ParticleTypes.ENCHANT, player.getX(), player.getY() + 1.0, player.getZ(), 0, 0.05, 0);
		}
	}

	// Prevents owner from removing/losing the sigil
	@Override
	public boolean canEquipFromUse(SlotContext ctx, ItemStack stack) {
		return Owneruuid.isOwner((ServerPlayer) ctx.entity());
	}

	@Override
	public boolean canUnequip(SlotContext ctx, ItemStack stack) {
		return false; // locked once equipped
	}
}
