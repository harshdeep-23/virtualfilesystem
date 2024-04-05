package org.example;

public class Inode {

    private String fileName;

    private Integer fileSizeBytes;

    private Integer startingBlockId;

    private Integer endingBlockId;

    public Inode(String fileName, Integer fileSizeBytes, Integer startingBlockId, Integer endingBlockId) {
        this.fileName = fileName;
        this.fileSizeBytes = fileSizeBytes;
        this.startingBlockId = startingBlockId;
        this.endingBlockId = endingBlockId;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getFileSizeBytes() {
        return fileSizeBytes;
    }

    public Integer getStartingBlockId() {
        return startingBlockId;
    }

    public Integer getEndingBlockId() {
        return endingBlockId;
    }

    public void setStartingBlockId(Integer startingBlockId) {
        this.startingBlockId = startingBlockId;
    }

    public void setEndingBlockId(Integer endingBlockId) {
        this.endingBlockId = endingBlockId;
    }
}
