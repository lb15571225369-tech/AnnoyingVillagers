package com.pla.annoyingvillagers.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class AVCollider {
    public static final Collider EXECUTE = new MultiOBBCollider(3, 0.4D, 0.4D, 1.5D, 0.0D, 0.0D, -0.5D);
    public static final Collider EXECUTE_SECOND = new MultiOBBCollider(2, 0.8D, 0.8D, 5.0D, 0.0D, 0.0D, -0.66D);
    public static final Collider GREATSWORD = new MultiOBBCollider(3, 0.2D, 0.8D, 1.0D, 0.0D, 0.0D, -1.2D);
    public static final Collider GREATSWORD_DOUBLESWING = new MultiOBBCollider(3, 0.6D, 0.8D, 1.0D, -0.4D, 0.0D, -1.2D);
    public static final Collider GREATSWORD_DUAL = new OBBCollider(2.4000000953674316D, 0.699999988079071D, 2.0999999046325684D, 0.0D, 1.0D, -0.75D);
    public static final Collider AIRSLAM = new OBBCollider(1.2000000476837158D, 0.800000011920929D, 1.2000000476837158D, 0.0D, 0.800000011920929D, -1.600000023841858D);
    public static final Collider SHOULDER_BUMP = new OBBCollider(0.8D, 0.8D, 0.8D, 0.0D, 1.2D, -1.2D);
}
