package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Objects;
import java.util.UUID;

public class EnderGlaiveSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("f79be742-fddd-454d-bd28-4d030613b284");

    public EnderGlaiveSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 40, 3));
            skillContainer.getExecutor().playAnimationSynchronized(AnimsAgony.AGONY_AUTO_1, 0.0F);

            Player player = skillContainer.getExecutor().getOriginal();
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() instanceof EnderGlaiveItem) {
                new DelayedTask(6) {
                    @Override
                    public void run() {
                        Vec3 tipPos = EnderGlaiveItem.getJointWithTranslation(
                                player,
                                new Vec3f(0.0F, 0.0F, 0.0F),
                                Armatures.BIPED.get().toolR,
                                4.3F,
                                2.3F
                        );
                        if (tipPos != null) {
                            BlockPos mutePos = BlockPos.containing(tipPos);
                            AnnoyingVillagers.PACKET_HANDLER.send(
                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                                    new ClientboundMuteExplosionAtPos(mutePos, 4)
                            );
                            player.level().explode(player, tipPos.x, tipPos.y, tipPos.z,
                                    2.0F, true, Level.ExplosionInteraction.TNT);
                            Vec3 glaivePos = EnderGlaiveItem.getJointWithTranslation(player, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                            Vec3 explosionPos = EnderGlaiveItem.getJointWithTranslation(player, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                            AnnoyingVillagers.PACKET_HANDLER.send(
                                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),
                                    new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                            );
                            if (explosionPos != null) {
                                player.level().playSound((Player) null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_shot"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            }
                        }
                    }
                };
            }
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        Player player = container.getExecutor().getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (container.getStack() == 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof EnderGlaiveItem && !itemStack.getTag().getBoolean("PlaySound")) {
            container.getExecutor().playSound(AnnoyingVillagersModSounds.SECOND_FORM_RELEASE.get(), 0.0F, 0.0F);
            itemStack.getTag().putBoolean("PlaySound", true);
        } else if (container.getStack() < 1 && itemStack.getTag() != null &&
                itemStack.getItem() instanceof EnderGlaiveItem && itemStack.getTag().getBoolean("PlaySound")) {
            itemStack.getTag().remove("PlaySound");
        }
    }
}
