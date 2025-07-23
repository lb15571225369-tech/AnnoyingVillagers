package com.pla.annoyingvillagers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BurnItemScheduler {
    private final Mob mob;
    private final CompoundTag data;
    public BurnItemScheduler(Mob mob) {
        this.mob = mob;
        this.data = mob.getPersistentData();
    }

    private static final List<String> burnMessages = Arrays.asList(
            "I burned a %s",
            "I destroyed a %s",
            "Trash item removed, %s",
            "No room for this garbage, %s",
            "Away with this worthless %s",
            "This %s? Pathetic.",
            "I have no use for this %s",
            "Begone, filthy %s",
            "What even is this %s?",
            "%s? Into the flames you go.",
            "I spit on this %s",
            "Disgusting %s eliminated",
            "Only fools carry %s",
            "Carrying this %s would insult me",
            "Just setting fire to a %s, nothing special",
            "Another useless %s gone",
            "Why would anyone keep a %s?",
            "%s… trash belongs in the fire",
            "I don't hoard junk like this %s",
            "Hah, this %s is beneath me",
            "My inventory deserves better than a %s",
            "This %s offends me.",
            "Did someone seriously drop a %s?",
            "Get that %s out of my sight.",
            "This %s belongs in a dumpster fire.",
            "I'd rather hold a rotten potato than this %s",
            "The flames deserve the %s more than I do.",
            "I wouldn't even trade a stick for this %s",
            "Burning a %s improves the world",
            "Even zombies wouldn't use this %s",
            "Let fire consume the pathetic %s",
            "My hand feels dirty from touching this %s",
            "Purging the %s like it never existed",
            "This %s? Yeah, no.",
            "Useless %s detected and removed",
            "Say goodbye to your precious %s",
            "Who dropped this excuse of a %s?",
            "I refuse to carry this %s any longer.",
            "Only peasants use a %s",
            "Cursed %s gone.",
            "Trash fire activated, %s",
            "Firing up the pit for a %s",
            "Look what I found… a disgusting %s",
            "I clean the world of %s like this",
            "Into the fire, you go, %s",
            "Burning this %s makes me stronger",
            "This %s is a disgrace to all gear",
            "Flames, take this %s from my sight",
            "An offering to the lava gods, %s",
            "You call this a %s? Please.",
            "Throwing away this %s like it's nothing",
            "A toddler's toy? No, it's just a %s",
            "This %s was a mistake to create",
            "Forged in mediocrity, %s",
            "This %s makes me lose brain cells",
            "The %s has been sentenced to fire"
    );


    private String getRandomBurnMessage(String itemName) {
        String template = burnMessages.get(new Random().nextInt(burnMessages.size()));
        return String.format(template, itemName);
    }

    private void resetItem() {
        if (data.contains("av_idle_burn_backup_main_hand")) {
            CompoundTag fullTag = null;
            try {
                fullTag = TagParser.parseTag(data.getString("av_idle_burn_backup_main_hand"));
            } catch (CommandSyntaxException e) {
                
            }
            String id = fullTag.getString("id");
            String nbtPart = fullTag.contains("tag") ? fullTag.getCompound("tag").toString() : "";
            String cmd = "item replace entity @s weapon.mainhand with " + id;
            if (!nbtPart.isEmpty()) {
                cmd += nbtPart;
            }
            try {
                mob.getServer().getCommands().getDispatcher().execute(
                        cmd,
                        mob.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                );
            } catch (CommandSyntaxException e) {
                
            }
            data.remove("av_idle_burn_backup_main_hand");
        }
    }

    public void run() {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        if (mob.getTarget() != null) {
            resetItem();
            return;
        }

        List<ItemEntity> items = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(10));
        ItemEntity targetItem = items.stream()
                .filter(item -> item.isAlive() && !item.getItem().isEmpty())
                .findFirst()
                .orElse(null);

        if (targetItem != null) {
            if (mob.getMainHandItem().getItem() != Items.FLINT_AND_STEEL) {
                mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FLINT_AND_STEEL));
            }

            if (mob.getNavigation().createPath(targetItem, 0) == null) {
                resetItem();
                return;
            }

            mob.getNavigation().moveTo(targetItem, 1.0);

            if (mob.getTarget() != null) {
                resetItem();
                return;
            }

            if (mob.distanceTo(targetItem) < 1.5) {
                mob.getLookControl().setLookAt(
                        targetItem.getX(),
                        targetItem.getY() + targetItem.getBbHeight() / 2.0,
                        targetItem.getZ(),
                        30.0F,
                        30.0F
                );
                mob.swing(InteractionHand.MAIN_HAND);
                targetItem.kill();

                serverLevel.sendParticles(ParticleTypes.FLAME,
                        targetItem.getX(), targetItem.getY(), targetItem.getZ(),
                        8, 0.2, 0.2, 0.2, 0.01);

                mob.level().playSound(null, mob.blockPosition(), SoundEvents.FLINTANDSTEEL_USE,
                        SoundSource.HOSTILE, 1.0f, 1.0f);

                String rawName = targetItem.getItem().getDisplayName().getString();
                if (rawName.startsWith("[") && rawName.endsWith("]")) {
                    rawName = rawName.substring(1, rawName.length() - 1).toLowerCase();;
                }
                if (!(ForgeRegistries.ENTITY_TYPES.getKey(mob.getType()).toString().equals("minecraft:zombie") || ForgeRegistries.ENTITY_TYPES.getKey(mob.getType()).toString().equals("minecraft:skeleton"))
                        && new Random().nextFloat() < 0.05f) {
                    String message = "<" + mob.getDisplayName().getString() + "> " +
                            getRandomBurnMessage(rawName);
                    serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                            Component.literal(message),
                            false
                    );
                }
            }
            TaskScheduler.schedule(() -> {
                new BurnItemScheduler(mob).run();
            }, 20);
        } else {
            resetItem();
        }
    }
}