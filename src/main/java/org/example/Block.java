package org.example;

import java.util.List;

public class Block {

    private final int blockId;
    private byte[] bytes;
    private final Integer blockSize;

    private Boolean allocated = false;

    public Block(Integer id, Integer blockSize) {
        this.blockId = id;
        this.blockSize = blockSize;
        bytes = new byte[Math.toIntExact(blockSize)];
    }

    public void writeBytes(List<Byte> data) {
        for (int i = 0; i <data.size(); i++) {
            bytes[i] = data.get(i);
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    public Boolean getAllocated() {
        return allocated;
    }

    public void setAllocated(Boolean allocated) {
        this.allocated = allocated;
    }

    public int getBlockId() {
        return blockId;
    }
}
