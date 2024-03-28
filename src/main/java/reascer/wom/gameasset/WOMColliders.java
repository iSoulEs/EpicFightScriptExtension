package reascer.wom.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class WOMColliders {
    public static final Collider STAFF = (Collider)new MultiOBBCollider(4, 0.5D, 0.5D, 1.9D, 0.0D, 0.0D, 0.0D);

    public static final Collider STAFF_EXTENTION = (Collider)new MultiOBBCollider(4, 0.5D, 0.5D, 2.5D, 0.0D, 0.0D, 0.0D);

    public static final Collider STAFF_CHARYBDIS = (Collider)new MultiOBBCollider(4, 0.6D, 0.6D, 2.3D, 0.0D, 0.0D, 0.0D);

    public static final Collider GREATSWORD = (Collider)new MultiOBBCollider(3, 0.2D, 0.8D, 1.0D, 0.0D, 0.0D, -1.2D);

    public static final Collider AGONY = (Collider)new MultiOBBCollider(5, 0.45D, 0.45D, 1.4D, 0.0D, 0.0D, -1.0D);

    public static final Collider AGONY_AIRSLASH = (Collider)new MultiOBBCollider(4, 0.55D, 0.55D, 1.6D, 0.0D, 0.0D, -0.8D);

    public static final Collider AGONY_PLUNGE = (Collider)new OBBCollider(5.0D, 2.0D, 5.0D, 0.0D, 0.0D, 0.0D);

    public static final Collider PLUNDER_PERDITION = (Collider)new OBBCollider(8.0D, 4.0D, 8.0D, 0.0D, 0.0D, 0.0D);

    public static final Collider RUINE = (Collider)new MultiOBBCollider(5, 0.4D, 0.4D, 1.45D, 0.0D, 0.0D, -1.05D);

    public static final Collider RUINE_COMET = (Collider)new MultiOBBCollider(4, 0.6D, 0.6D, 1.35D, 0.0D, 0.0D, -0.95D);

    public static final Collider SHOULDER_BUMP = (Collider)new OBBCollider(0.8D, 0.8D, 1.6D, 0.0D, 1.2D, -0.6D);

    public static final Collider BULL_CHARGE = (Collider)new OBBCollider(1.5D, 1.5D, 1.5D, 0.0D, 1.5D, 0.0D);

    public static final Collider TORMENT = (Collider)new MultiOBBCollider(3, 0.4000000059604645D, 0.6000000238418579D, 1.399999976158142D, 0.0D, -0.20000000298023224D, -0.6000000238418579D);

    public static final Collider TORMENT_AIRSLAM = (Collider)new OBBCollider(1.399999976158142D, 0.800000011920929D, 1.399999976158142D, 0.0D, 0.800000011920929D, -1.600000023841858D);

    public static final Collider TORMENT_BERSERK_AIRSLAM = (Collider)new OBBCollider(2.0999999046325684D, 2.0999999046325684D, 2.0999999046325684D, 0.0D, 2.0999999046325684D, -2.0999999046325684D);

    public static final Collider TORMENT_BERSERK_DASHSLAM = (Collider)new OBBCollider(1.7999999523162842D, 1.600000023841858D, 1.7999999523162842D, 0.0D, 1.600000023841858D, -1.7999999523162842D);

    public static final Collider KATANA_SHEATHED_AUTO = (Collider)new OBBCollider(2.0D, 1.0D, 2.0D, 0.0D, 1.0D, -1.0D);

    public static final Collider KATANA_SHEATHED_DASH = (Collider)new OBBCollider(1.600000023841858D, 0.699999988079071D, 2.5D, 0.0D, 1.0D, -0.5D);

    public static final Collider KATANA = (Collider)new MultiOBBCollider(5, 0.2D, 0.3D, 1.0D, 0.0D, 0.0D, -1.0D);

    public static final Collider FATAL_DRAW = (Collider)new OBBCollider(1.75D, 0.7D, 1.35D, 0.0D, 1.0D, -1.0D);

    public static final Collider FATAL_DRAW_DASH = (Collider)new OBBCollider(1.0D, 1.0D, 2.75D, 0.0D, 1.2D, -0.2D);

    public static final Collider PUNCH = (Collider)new MultiOBBCollider(4, 0.4D, 0.6D, 0.4D, 0.0D, 0.0D, 0.0D);

    public static final Collider ENDER_BLASTER_CROSS = (Collider)new MultiOBBCollider(4, 0.4D, 0.4D, 0.4D, 0.0D, 0.0D, 0.6D);

    public static final Collider KICK = (Collider)new MultiOBBCollider(4, 0.4D, 0.4D, 0.4D, 0.0D, 0.6D, 0.0D);

    public static final Collider KICK_HUGE = (Collider)new MultiOBBCollider(4, 0.8D, 0.8D, 0.8D, 0.0D, 0.9D, 0.0D);

    public static final Collider ENDER_DASH = (Collider)new OBBCollider(1.5D, 1.5D, 1.5D, 0.0D, 1.0D, -1.0D);

    public static final Collider ENDER_TISHNAW = (Collider)new MultiOBBCollider(4, 1.5D, 1.5D, 1.5D, 0.0D, 1.0D, 0.0D);

    public static final Collider KNEE = (Collider)new MultiOBBCollider(4, 0.8D, 0.8D, 0.8D, 0.0D, 0.6D, 0.0D);

    public static final Collider ENDER_LASER = (Collider)new MultiOBBCollider(4, 0.3D, 20.0D, 0.3D, 0.0D, -20.2D, 0.0D);

    public static final Collider ENDER_SHOOT = (Collider)new MultiOBBCollider(4, 0.3D, 3.0D, 0.3D, 0.0D, -3.1D, 0.0D);

    public static final Collider ENDER_BULLET_DASH = (Collider)new MultiOBBCollider(4, 1.2D, 5.0D, 1.2D, 0.0D, -5.1D, 0.0D);

    public static final Collider ENDER_BULLET_BACKANDFORTH = (Collider)new MultiOBBCollider(4, 2.5D, 10.0D, 2.5D, 0.0D, 1.0D, 0.0D);

    public static final Collider ENDER_BULLET_ALl_DIRECTION = (Collider)new MultiOBBCollider(4, 2.5D, 10.0D, 10.0D, 0.0D, 1.0D, 0.0D);

    public static final Collider ENDER_BULLET_WIDE = (Collider)new OBBCollider(7.5D, 1.5D, 5.0D, 0.0D, 0.75D, -5.0D);

    public static final Collider ENDER_PISTOLERO = (Collider)new OBBCollider(2.5D, 2.0D, 2.5D, 0.0D, -1.5D, 0.0D);

    public static final Collider ANTITHEUS = (Collider)new MultiOBBCollider(3, 0.4000000059604645D, 1.2000000476837158D, 1.0D, 0.0D, -0.20000000298023224D, -1.7000000476837158D);

    public static final Collider ANTITHEUS_AGRESSION = (Collider)new MultiOBBCollider(3, 1.2000000476837158D, 1.600000023841858D, 1.399999976158142D, 0.0D, -0.6000000238418579D, -2.0D);

    public static final Collider ANTITHEUS_AGRESSION_REAP = (Collider)new OBBCollider(1.2000000476837158D, 1.600000023841858D, 1.600000023841858D, 0.0D, 0.800000011920929D, -3.4000000953674316D);

    public static final Collider ANTITHEUS_GUILLOTINE = (Collider)new OBBCollider(3.5D, 1.5D, 3.5D, 0.0D, 0.0D, 0.0D);

    public static final Collider ANTITHEUS_ASCENDED_PUNCHES = (Collider)new OBBCollider(1.0D, 1.0D, 1.7999999523162842D, 0.0D, 1.0D, -1.149999976158142D);

    public static final Collider ANTITHEUS_ASCENDED_BLINK = (Collider)new OBBCollider(3.5D, 1.5D, 3.5D, 0.0D, 0.75D, 0.0D);

    public static final Collider ANTITHEUS_ASCENDED_DEATHFALL = (Collider)new OBBCollider(5.0D, 3.0D, 5.0D, 0.0D, 0.0D, 0.0D);

    public static final Collider ANTITHEUS_SHOOT = (Collider)new MultiOBBCollider(4, 0.5D, 1.5D, 0.5D, 0.0D, -1.5D, 0.0D);

    public static final Collider HERSCHER = (Collider)new MultiOBBCollider(5, 0.2D, 0.3D, 0.8D, 0.0D, 0.0D, -0.8D);

    public static final Collider HERSCHER_THRUST = (Collider)new OBBCollider(0.6000000238418579D, 0.6000000238418579D, 1.2999999523162842D, 0.0D, 0.800000011920929D, -1.100000023841858D);

    public static final Collider HERSCHER_CHARGE_1 = (Collider)new MultiOBBCollider(6, 0.2D, 0.4D, 1.2D, 0.0D, 0.0D, -1.2D);

    public static final Collider HERSCHER_CHARGE_2 = (Collider)new MultiOBBCollider(8, 0.2D, 0.6D, 1.6D, 0.0D, 0.0D, -1.6D);

    public static final Collider HERSCHER_CHARGE_3 = (Collider)new MultiOBBCollider(10, 0.2D, 0.8D, 2.0D, 0.0D, 0.0D, -2.0D);

    public static final Collider GESETZ = (Collider)new MultiOBBCollider(5, 0.4D, 0.6D, 1.0D, 0.0D, 0.0D, 0.4D);

    public static final Collider GESETZ_KRUMMEN = (Collider)new MultiOBBCollider(5, 0.4D, 0.6D, 1.6D, 0.0D, 0.0D, 0.0D);

    public static final Collider GESETZ_INSET_LARGE = (Collider)new MultiOBBCollider(5, 0.9D, 0.6D, 1.0D, 0.3D, 0.0D, 0.4D);

    public static final Collider MOONLESS = (Collider)new MultiOBBCollider(4, 0.2D, 1.5D, 0.4D, 0.0D, 1.0D, 0.2D);

    public static final Collider MOONLESS_BYPASS = (Collider)new MultiOBBCollider(4, 0.2D, 1.8D, 0.7D, 0.0D, 1.0D, 0.3D);

    public static final Collider LUNAR_ECHO = (Collider)new OBBCollider(6.0D, 6.0D, 6.0D, 0.0D, 0.0D, -6.0D);
}

