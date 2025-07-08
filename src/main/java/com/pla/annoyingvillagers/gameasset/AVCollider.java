package com.pla.annoyingvillagers.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

public class AVCollider {

    public static final Collider SLASH = new MultiOBBCollider(3, 1.5D, 1.0D, 1.5D, 0.0D, 1.5D, -1.0D);
    public static final Collider AXE_SLAM = new MultiOBBCollider(2, 0.7D, 0.7D, 0.7D, 0.0D, 1.0D, -1.0D);
    public static final Collider SLAM = new MultiOBBCollider(6, 2.0D, 1.5D, 2.0D, 0.0D, 1.0D, -1.0D);
    public static final Collider EXECUTE = new MultiOBBCollider(3, 0.4D, 0.4D, 1.5D, 0.0D, 0.0D, -0.5D);
    public static final Collider EXECUTE_SECOND = new MultiOBBCollider(2, 0.8D, 0.8D, 5.0D, 0.0D, 0.0D, -0.66D);
}
