package org.example;

import java.util.ArrayList;
import java.util.List;

public class BlockDevice {

    private final List<Block> blocks;

    private final Integer blockSize;

    private Integer filledBlockIdx = 0;

    public BlockDevice(Integer numberOfBlocks, Integer blockSize) {
        this.blockSize = blockSize;
        blocks = new ArrayList<>();
        for (int i = 0; i < numberOfBlocks; i++) {
            blocks.add(new Block(i, blockSize));
        }
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    // TODO: handle numberofBlocks not available to allocated
    public List<Block> allocateBlocks(Integer numberOfBlocks) {
        List<Block> allocatedBlocks = new ArrayList<>();
        for (int i = filledBlockIdx; i < numberOfBlocks; i++) {
            blocks.get(i).setAllocated(true);
            allocatedBlocks.add(blocks.get(i));
        }
        return allocatedBlocks;
    }

    public byte[] readBlocks(Integer startingBlockId, Integer endingBlockId) {
        int size = (endingBlockId - startingBlockId + 1) * blockSize;
        byte[] data = new byte[size];
        int di = 0;
        for (int i = startingBlockId; i <= endingBlockId; i++) {
            byte[] blockBytes = blocks.get(i).getBytes();
            for (int j = 0; j < size; j++) {
                data[di++] = blockBytes[j];
            }
        }
        return data;
    }
}
