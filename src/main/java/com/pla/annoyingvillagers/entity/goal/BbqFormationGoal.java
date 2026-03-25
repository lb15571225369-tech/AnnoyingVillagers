//package com.pla.annoyingvillagers.entity.goal;
//
//import com.pla.annoyingvillagers.entity.BbqEntity;
//import com.pla.annoyingvillagers.entity.BlueDemonEntity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.phys.Vec3;
//
//import java.util.EnumSet;
//
//public class BbqFormationGoal extends Goal {
//    private final BbqEntity bbq;
//    private final double speed;
//    private final double sideDistance;
//    private final double backDistance;
//    private int swapCooldown = 50;
//
//    public BbqFormationGoal(BbqEntity bbq, double speed, double sideDistance, double backDistance) {
//        this.bbq = bbq;
//        this.speed = speed;
//        this.sideDistance = sideDistance;
//        this.backDistance = backDistance;
//        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
//    }
//
//    @Override
//    public boolean canUse() {
//        BlueDemonEntity leader = this.bbq.getLeader();
//        return leader != null && leader.isAlive();
//    }
//
//    @Override
//    public boolean canContinueToUse() {
//        return this.canUse();
//    }
//
//    @Override
//    public void tick() {
//        BlueDemonEntity leader = this.bbq.getLeader();
//        if (leader == null) {
//            return;
//        }
//
//        LivingEntity target = leader.getTarget();
//        Vec3 desiredPos;
//
//        if (target != null && target.isAlive()) {
//            Vec3 forward = target.position().subtract(leader.position());
//            forward = new Vec3(forward.x, 0.0D, forward.z);
//
//            if (forward.lengthSqr() < 1.0E-4D) {
//                forward = new Vec3(leader.getLookAngle().x, 0.0D, leader.getLookAngle().z);
//            }
//
//            forward = forward.normalize();
//
//            Vec3 side = new Vec3(-forward.z, 0.0D, forward.x).scale(this.sideDistance * this.bbq.getFormationSide());
//            Vec3 back = forward.scale(-this.backDistance);
//            desiredPos = leader.position().add(side).add(back);
//
//            if (this.swapCooldown > 0) {
//                this.swapCooldown--;
//            } else {
//                this.bbq.swapFormationSide();
//                this.swapCooldown = 40 + this.bbq.getRandom().nextInt(30);
//            }
//
//            this.bbq.getLookControl().setLookAt(target, 30.0F, 30.0F);
//        } else {
//            Vec3 forward = new Vec3(leader.getLookAngle().x, 0.0D, leader.getLookAngle().z).normalize();
//            Vec3 side = new Vec3(-forward.z, 0.0D, forward.x).scale(1.5D * this.bbq.getFormationSide());
//            desiredPos = leader.position().add(side).add(forward.scale(-1.0D));
//        }
//
//        this.bbq.getNavigation().moveTo(desiredPos.x, desiredPos.y, desiredPos.z, this.speed);
//    }
//}