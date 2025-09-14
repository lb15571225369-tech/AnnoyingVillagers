package com.pla.annoyingvillagers.procedures;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.util.DelayedTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Herobrine3OnTickProcedure {

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "execute as @s at @s anchored eyes run setblock ^ ^ ^ minecraft:air",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "execute as @s at @s anchored eyes run setblock ^ ^ ^1 minecraft:air",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            if (!entity.level().isClientSide() && entity.getServer() != null) {
                try {
                    entity.getServer().getCommands().getDispatcher().execute(
                            "execute as @s at @s anchored eyes run setblock ^ ^-1 ^ minecraft:air",
                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                } catch (CommandSyntaxException e) {
                }
            }

            Vec3 vec3 = new Vec3(d0, d1, d2);
            List<Entity> list = (List)levelaccessor.getEntitiesOfClass(Entity.class, (new AABB(vec3, vec3)).inflate(2.0D), (entity1) -> {
                return true;
            }).stream().sorted(Comparator.comparingDouble((entity1) -> {
                return entity1.distanceToSqr(vec3);
            })).collect(Collectors.toList());
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
                Entity entity1 = (Entity)iterator.next();
                LivingEntity livingentity;

                if (entity instanceof Mob) {
                    Mob mob = (Mob)entity;

                    livingentity = mob.getTarget();
                } else {
                    livingentity = null;
                }

                if (entity1 == livingentity) {
                    if (Math.random() <= 0.25D) {
                        if (!entity.level().isClientSide() && entity.getServer() != null) {
                            try {
                                entity.getServer().getCommands().getDispatcher().execute(
                                        "execute as @s at @s anchored eyes run setblock ^ ^-1 ^2 annoyingvillagers:obsidian",
                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                            } catch (CommandSyntaxException e) {
                            }
                        }
                        new DelayedTask(1) {
                            public void run() {
                                
                                Entity entity2 = entity;

                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:obsidian",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }
                            }
                        };
                    }

                    if (Math.random() <= 0.06D) {
                        
                        new DelayedTask(1) {
                            public void run() {
                                Entity entity2 = entity;

                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "execute as @s at @s anchored eyes run setblock ^ ^-1 ^1 annoyingvillagers:obsidian",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {
                                    }
                                }
                                new DelayedTask(1) {
                                    public void run() {
                                        Entity entity3 = entity;

                                        if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                            try {
                                                entity3.getServer().getCommands().getDispatcher().execute(
                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^1 annoyingvillagers:obsidian",
                                                        entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                            } catch (CommandSyntaxException e) {
                                            }
                                        }

                                        
                                        new DelayedTask(1) {
                                            public void run() {
                                                Entity entity4 = entity;

                                                if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                    try {
                                                        entity4.getServer().getCommands().getDispatcher().execute(
                                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^2 annoyingvillagers:obsidian",
                                                                entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                    }
                                                }

                                                
                                                new DelayedTask(1) {
                                                    public void run() {
                                                        Entity entity5 = entity;

                                                        if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                            try {
                                                                entity5.getServer().getCommands().getDispatcher().execute(
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^3 annoyingvillagers:obsidian",
                                                                        entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                            } catch (CommandSyntaxException e) {
                                                            }
                                                        }

                                                        
                                                        new DelayedTask(1) {
                                                            public void run() {
                                                                Entity entity6 = entity;

                                                                if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                    try {
                                                                        entity6.getServer().getCommands().getDispatcher().execute(
                                                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^4 annoyingvillagers:obsidian",
                                                                                entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                    } catch (CommandSyntaxException e) {
                                                                    }
                                                                }

                                                                
                                                                new DelayedTask(1) {
                                                                    public void run() {
                                                                        Entity entity7 = entity;

                                                                        if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                            try {
                                                                                entity7.getServer().getCommands().getDispatcher().execute(
                                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:obsidian",
                                                                                        entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                            } catch (CommandSyntaxException e) {
                                                                            }
                                                                        }
                                                                        new DelayedTask(1) {
                                                                            public void run() {
                                                                                Entity entity8 = entity;

                                                                                if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                    try {
                                                                                        entity8.getServer().getCommands().getDispatcher().execute(
                                                                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:obsidian",
                                                                                                entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                    } catch (CommandSyntaxException e) {
                                                                                    }
                                                                                }

                                                                                
                                                                            }
                                                                        };
                                                                    }
                                                                };
                                                            }
                                                        };
                                                    }
                                                };
                                            }
                                        };
                                    }
                                };
                            }
                        };
                        new DelayedTask(20) {
                            public void run() {
                                if (Math.random() <= 0.5D) {
                                    
                                    Entity entity2 = entity;

                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        try {
                                            entity2.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^ ^-1 annoyingvillagers:obsidian",
                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }

                                    entity2 = entity;
                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        try {
                                            entity2.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^-1 ^-1 annoyingvillagers:obsidian",
                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }

                                    entity2 = entity;
                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        try {
                                            entity2.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^-1 annoyingvillagers:obsidian",
                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }

                                    entity2 = entity;
                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        try {
                                            entity2.getServer().getCommands().getDispatcher().execute(
                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^ annoyingvillagers:obsidian",
                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {
                                        }
                                    }
                                    new DelayedTask(1) {
                                        public void run() {
                                            Entity entity3 = entity;

                                            if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                try {
                                                    entity3.getServer().getCommands().getDispatcher().execute(
                                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^1 annoyingvillagers:obsidian",
                                                            entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                } catch (CommandSyntaxException e) {
                                                }
                                            }

                                            
                                            new DelayedTask(1) {
                                                public void run() {
                                                    Entity entity4 = entity;

                                                    if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                        try {
                                                            entity4.getServer().getCommands().getDispatcher().execute(
                                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^2 annoyingvillagers:obsidian",
                                                                    entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                        } catch (CommandSyntaxException e) {
                                                        }
                                                    }

                                                    
                                                    new DelayedTask(1) {
                                                        public void run() {
                                                            Entity entity5 = entity;

                                                            if (!entity5.level().isClientSide() && entity5.getServer() != null) {
                                                                try {
                                                                    entity5.getServer().getCommands().getDispatcher().execute(
                                                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:obsidian",
                                                                            entity5.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                } catch (CommandSyntaxException e) {
                                                                }
                                                            }

                                                            
                                                            new DelayedTask(1) {
                                                                public void run() {
                                                                    Entity entity6 = entity;

                                                                    if (!entity6.level().isClientSide() && entity6.getServer() != null) {
                                                                        try {
                                                                            entity6.getServer().getCommands().getDispatcher().execute(
                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:obsidian",
                                                                                    entity6.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                        } catch (CommandSyntaxException e) {
                                                                        }
                                                                    }

                                                                    
                                                                    new DelayedTask(1) {
                                                                        public void run() {
                                                                            Entity entity7 = entity;

                                                                            if (!entity7.level().isClientSide() && entity7.getServer() != null) {
                                                                                try {
                                                                                    entity7.getServer().getCommands().getDispatcher().execute(
                                                                                            "execute as @s at @s anchored eyes run setblock ^ ^1 ^3 annoyingvillagers:obsidian",
                                                                                            entity7.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                } catch (CommandSyntaxException e) {
                                                                                }
                                                                            }

                                                                            
                                                                            new DelayedTask(1) {
                                                                                public void run() {
                                                                                    Entity entity8 = entity;

                                                                                    if (!entity8.level().isClientSide() && entity8.getServer() != null) {
                                                                                        try {
                                                                                            entity8.getServer().getCommands().getDispatcher().execute(
                                                                                                    "execute as @s at @s anchored eyes run setblock ^ ^1 ^4 annoyingvillagers:obsidian",
                                                                                                    entity8.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                                                        } catch (
                                                                                                CommandSyntaxException e) {
                                                                                        }
                                                                                    }

                                                                                    
                                                                                }
                                                                            };
                                                                        }
                                                                    };
                                                                }
                                                            };
                                                        }
                                                    };
                                                }
                                            };
                                        }
                                    };
                                    if (Math.random() <= 0.5D) {
                                        
                                        new DelayedTask(1) {
                                            public void run() {
                                                Entity entity3 = entity;

                                                if (!entity3.level().isClientSide() && entity3.getServer() != null) {
                                                    try {
                                                        entity3.getServer().getCommands().getDispatcher().execute(
                                                                "execute as @s at @s anchored eyes run setblock ^ ^ ^5 annoyingvillagers:obsidian",
                                                                entity3.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                    } catch (CommandSyntaxException e) {
                                                    }
                                                }

                                                
                                                new DelayedTask(1) {
                                                    public void run() {
                                                        Entity entity4 = entity;

                                                        if (!entity4.level().isClientSide() && entity4.getServer() != null) {
                                                            try {
                                                                entity4.getServer().getCommands().getDispatcher().execute(
                                                                        "execute as @s at @s anchored eyes run setblock ^ ^ ^6 annoyingvillagers:obsidian",
                                                                        entity4.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                                            } catch (CommandSyntaxException e) {
                                                            }
                                                        }
                                                    }
                                                };
                                            }
                                        };
                                    }
                                }
                            }
                        };
                    }
                }
            }

        }
    }
}
