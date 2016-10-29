package cn.nukkit.level.sound;

import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.LevelEventPacket;

/**
 * Created by CreeperFace on 29. 10. 2016.
 */
public class ExpPickupSound extends GenericSound {

    public ExpPickupSound(Vector3 pos) {
        this(pos, 0);
    }

    public ExpPickupSound(Vector3 pos, float pitch) {
        super(pos, LevelEventPacket.EVENT_SOUND_EXP_PICKUP, pitch);
    }
}
