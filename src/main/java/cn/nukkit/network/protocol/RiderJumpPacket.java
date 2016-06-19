package cn.nukkit.network.protocol;

public class RiderJumpPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.RIDER_JUMP_PACKET;

    @Override
    public void decode() {

    }

    @Override
    public void encode() {

    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
