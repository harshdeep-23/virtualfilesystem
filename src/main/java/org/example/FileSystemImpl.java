package org.example;

import java.util.*;

public class FileSystemImpl implements FileSystem {

    private final BlockDevice blockDevice;

    private final Map<String, Inode> inodes;

    private final Set<Inode> openInodes;

    public FileSystemImpl(Integer numberOfBlocks, Integer blockSize) {
        this.blockDevice = new BlockDevice(numberOfBlocks, blockSize);
        this.inodes = new HashMap<>();
        this.openInodes = new HashSet<>();
    }


    @Override
    public VFile fopen(String fileName) {
        Inode inode = inodes.get(fileName);
        if (openInodes.contains(inode)) {
            // prevent same file from opening again (if file being opened for locking)
            throw new RuntimeException("File already open by other process");
        }
        openInodes.add(inode);
        return new VFile(fileName);
    }

    @Override
    public List<Byte> fread(VFile vFile) {
        Inode inode = inodes.get(vFile.getName());
        if (inode == null) {
            throw new RuntimeException("File not found");
        } else {
            byte[] bytes = blockDevice.readBlocks(inode.getStartingBlockId(), inode.getEndingBlockId());
            List<Byte> data = new ArrayList<>();
            for (int i = 0; i < bytes.length; i++) {
                data.add(bytes[i]);
            }
            return data;
        }
    }

    @Override
    public void fwrite(VFile vFile, List<Byte> data) {
        String fileName = vFile.getName();
        int blockSize = blockDevice.getBlockSize();
        Integer blocksRequired = calculateNumberOfBlocksRequired(data);
        List<Block> blocks = blockDevice.allocateBlocks(blocksRequired);
        int blockIdx = 0;
        int dataStartI = 0;
        for (int i = 0; i < data.size(); i += blockSize) {
            int dataEndI = Math.min(i + blockSize, data.size());
            List<Byte> dataChunk = data.subList(dataStartI, dataEndI);
            blocks.get(blockIdx++).writeBytes(dataChunk);
            dataStartI = dataEndI;
        }
        int startingBlockId = blocks.get(0).getBlockId();
        int endingBlockId = blocks.get(blocks.size()-1).getBlockId();
        Inode inode = new Inode(fileName, data.size(), startingBlockId, endingBlockId);
        inodes.put(fileName, inode);
    }

    @Override
    public void fclose(VFile vFile) {
        Inode inode = inodes.get(vFile.getName());
        openInodes.remove(inode);
    }

    private Integer calculateNumberOfBlocksRequired(List<Byte> bytes) {
        Integer blockSize = blockDevice.getBlockSize();
        return (int) Math.ceil((double) bytes.size() / blockSize);
    }
}
