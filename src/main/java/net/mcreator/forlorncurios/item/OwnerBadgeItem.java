package net.mcreator.forlorncurios.item;

import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

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
        list.add(Component.literal("§7 - Grants an overwhelming boost to Luck (Owner only)"));
        list.add(Component.literal("§cNon-owners suffer a heavy curse"));
        list.add(Component.literal(""));
        list.add(Component.literal("§7Owner: §b" + Owneruuid.getOwnerName()));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            if (Owneruuid.isOwner(serverPlayer)) {
                return ImmutableMultimap.of(
                    Attributes.LUCK,
                    new AttributeModifier(uuid, "cobalt_sigil_luck", 20.0, AttributeModifier.Operation.ADDITION)
                );
            }
        }
        return ImmutableMultimap.of();
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) return;
        if (!(player.level() instanceof ServerLevel serverLevel)) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        boolean isOwner = Owneruuid.isOwner(serverPlayer);

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
        if (isOwner && current != team) {
            scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
        }

        if (isOwner) {
            // Positive effects for owner
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 40, 4, true, false));
        } else {
            // Heavy debuffs for non-owner
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 2, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, 2, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40, 2, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 40, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 40, 4, true, false));

            if (!player.getPersistentData().getBoolean("OwnerBadgeWarned")) {
                player.displayClientMessage(Component.literal("§cYou are not the owner — this item curses you!"), true);
                player.getPersistentData().putBoolean("OwnerBadgeWarned", true);
            }

            // Show owner name on tick
            if (player.level().getGameTime() % 100 == 0) { // every 5 seconds
                player.displayClientMessage(Component.literal("§cOwner of this badge: §b" + Owneruuid.getOwnerName()), true);
            }
        }

        if (player.level().isClientSide) {
            player.level().addParticle(ParticleTypes.ENCHANT,
                player.getX(), player.getY() + 1.0, player.getZ(),
                0, 0.05, 0);
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext ctx, ItemStack stack) {
        return true; // Anyone can equip
    }

    @Override
    public boolean canUnequip(SlotContext ctx, ItemStack stack) {
        if (ctx.entity() instanceof ServerPlayer sp && !Owneruuid.isOwner(sp)) {
            return false; // Non-owner cannot unequip
        }
        return true; // Owner can unequip
    }

    public boolean canDrop(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer sp && !Owneruuid.isOwner(sp)) {
            return false; // Non-owner cannot drop
        }
        return true;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
