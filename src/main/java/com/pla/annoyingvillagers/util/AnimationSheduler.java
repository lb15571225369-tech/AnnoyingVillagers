package com.pla.annoyingvillagers.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnimationSheduler {
    private final Mob mob;
    private final CompoundTag data;

    private static final Map<IdleAnimation, List<String>> idleMessages = Map.of(
            IdleAnimation.LAY, Arrays.asList(
                    "Just laying down a bit, nothing to worry about.",
                    "Oof... I need to lay flat for a sec.",
                    "You ever just flop? Yeah.",
                    "Don't step on me, I'm laying here!",
                    "I'm not dead, just horizontal.",
                    "Gravity wins again.",
                    "This is my combat strategy: play dead.",
                    "Let me lay in peace!",
                    "No thoughts. Just laying.",
                    "I'm a pancake now.",
                    "The floor is oddly comfortable today.",
                    "Ugh, Mondays...",
                    "Back problems? Nah. Just vibes.",
                    "Flat and fabulous.",
                    "Laying low... literally.",
                    "Give me 5 minutes. Or 50.",
                    "I fell. On purpose.",
                    "This is fine.",
                    "Laying is productive, okay?",
                    "You wish you were this chill."
            ),
            IdleAnimation.SLEEP, Arrays.asList(
                    "Zzz... Wait what?",
                    "I was not sleeping, I was... recharging.",
                    "I dream of better loot.",
                    "Don't wake me unless it's urgent.",
                    "I take naps seriously.",
                    "This is strategic slumber.",
                    "Goodnight, cruel world.",
                    "I'm in stealth mode (asleep).",
                    "Being awake is overrated.",
                    "Wake me up when it's diamond o'clock.",
                    "You ever nap in full armor? No? Weak.",
                    "If I snore, it's intentional.",
                    "Yes, I sleep with one eye open.",
                    "I'm testing the bed physics.",
                    "Rest is resistance.",
                    "I'm on an AFK break.",
                    "I fought, I survived, I nap.",
                    "Don't judge me, I'm healing.",
                    "I look peaceful, don't I?",
                    "Sleeping beauty? That's me."
            ),
            IdleAnimation.SIT, Arrays.asList(
                    "Just sitting... contemplating my existence.",
                    "Taking a break from saving the world.",
                    "Is it weird to sit here? Nah.",
                    "Sit down and appreciate nature with me.",
                    "I claim this patch of grass.",
                    "My legs demanded this.",
                    "You stand, I sit. Balance.",
                    "Big brain thoughts happen in this pose.",
                    "This seat is taken. By me.",
                    "I'm not lazy, I'm energy efficient.",
                    "Sometimes you gotta sit to reflect.",
                    "Sitting builds character.",
                    "I'm too cool to stand up right now.",
                    "This is my vibe zone.",
                    "I'm one with the terrain.",
                    "Low center of gravity, maximum chill.",
                    "Sitting: 10/10 would recommend.",
                    "I squat with intent.",
                    "Oh I just sit here, don't kill me!",
                    "If you can't beat them, sit near them."
            ),
            IdleAnimation.FUN_SIT, Arrays.asList(
                    "Check out this fun pose!",
                    "I sit like no one's watching.",
                    "I'm not normal, I'm fun-sitting.",
                    "Vibing intensifies.",
                    "Bet you can't sit this fabulously.",
                    "Fun mode: activated.",
                    "Stylish and seated.",
                    "I'm not just sitting. I'm entertaining.",
                    "This pose is patented.",
                    "Join me, let's sit funny together!",
                    "Why sit boring when you can sit awesome?",
                    "Strike a pose: chairless edition.",
                    "This is my emote of power.",
                    "Look at me! I'm fabulous.",
                    "Do I look cool yet?",
                    "This is my signature move.",
                    "Chaotic neutral sitting.",
                    "The flowers envy my posture.",
                    "This is what peak performance looks like.",
                    "If sitting was an art, I'm Picasso."
            ),
            IdleAnimation.SLIGHT, Arrays.asList(
                    "Just a subtle motion...",
                    "That was barely an emote, but I meant it.",
                    "Did you see that? Blink and you miss it.",
                    "Minimal effort, maximum expression.",
                    "I call that a slight vibe.",
                    "Nothing major. Just flexin'.",
                    "Slight movements, strong emotions.",
                    "I twitched with style.",
                    "Little gestures matter too.",
                    "The art of the subtle flex.",
                    "Keeping it lowkey.",
                    "Just making sure you're paying attention.",
                    "Did I move? Maybe.",
                    "Smooth like butter.",
                    "Calm, but expressive.",
                    "That's my signal for 'hi'.",
                    "Small movement, big energy.",
                    "Blink and you'll miss this drip.",
                    "I emote in Morse code.",
                    "One frame of pure charisma."
            ),
            IdleAnimation.PUSH_UP, Arrays.asList(
                    "One! Two! Ugh, who needs cardio?",
                    "I do push-ups to impress the mobs.",
                    "Getting blocky gains.",
                    "Training to punch harder.",
                    "Strength comes from suffering.",
                    "No gym? No problem.",
                    "Push-up speedrun!",
                    "Working on my Minecraft muscles.",
                    "Bet you can't beat 20 reps!",
                    "This is my flex routine.",
                    "Push-ups make me immune to arrows.",
                    "Core strength? Check.",
                    "Mobility training in progress.",
                    "Fitness is survival.",
                    "Strength. Endurance. Blocks.",
                    "Sweat mode: enabled.",
                    "Even Steve does push-ups.",
                    "Just flexin' on creepers.",
                    "Training arc begins now.",
                    "Do I look stronger yet?"
            )
    );

    public AnimationSheduler(Mob mob) {
        this.mob = mob;
        this.data = mob.getPersistentData();
    }

    private void resetItem() {
        if (data.contains("av_idle_animate_backup_main_hand")) {
            CompoundTag fullTag = null;
            try {
                fullTag = TagParser.parseTag(data.getString("av_idle_animate_backup_main_hand"));
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
            data.remove("av_idle_animate_backup_main_hand");
            if (data.contains("av_idle_action")) {
                data.remove("av_idle_action");
            }
            if (data.contains("idle_message_broadcasted")) {
                data.remove("idle_message_broadcasted");
            }
        }
    }

    public void run(IdleAnimation idleAnimation, boolean checkOnly, boolean reTry) {
        if (!(mob.level instanceof ServerLevel serverLevel)) return;

        if (mob.getTarget() != null) {
            resetItem();
            return;
        }

        mob.getNavigation().stop();
        mob.setDeltaMovement(0, 0, 0);
        mob.setYRot(mob.getYHeadRot());
        mob.setYBodyRot(mob.getYHeadRot());
        mob.setYHeadRot(mob.getYRot());

        if (!checkOnly) {
            if (!mob.getMainHandItem().isEmpty()) {
                mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }
            String command = switch (idleAnimation) {
                case PUSH_UP -> "indestructible @s play \"annoyingvillagers:biped/idle/push_up\" 0 1";
                case LAY -> "indestructible @s play \"annoyingvillagers:biped/idle/lay\" 0 1";
                case SLEEP -> "indestructible @s play \"annoyingvillagers:biped/other/death_idle\" 0 1";
                case SIT -> "indestructible @s play \"annoyingvillagers:biped/idle/sit\" 0 1";
                case FUN_SIT -> "indestructible @s play \"annoyingvillagers:biped/other/funny_idle\" 0 1";
                case SLIGHT -> "indestructible @s play \"annoyingvillagers:biped/idle/slight\" 0 1";
            };

            if (!mob.level.isClientSide() && mob.getServer() != null) {
                try {
                    mob.getServer().getCommands().getDispatcher().execute(
                            command,
                            mob.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                    );
                } catch (CommandSyntaxException e) {
                    
                }
            }

            if (reTry && !data.contains("idle_message_broadcasted")) {
                // reTry is false meaning temp call so no message
                String message = "<" + mob.getDisplayName().getString() + "> " + idleMessages
                        .getOrDefault(idleAnimation, List.of("..."))
                        .get(new Random().nextInt(idleMessages.get(idleAnimation).size()));
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                        Component.literal(message),
                        false
                );
                data.putBoolean("idle_message_broadcasted", true);
            }

        }
        if (reTry) {
            TaskScheduler.schedule(() -> {
                new AnimationSheduler(mob).run(idleAnimation, true, true);
            }, 20);
        }
    }
}