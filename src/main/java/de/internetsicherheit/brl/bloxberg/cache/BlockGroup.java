package de.internetsicherheit.brl.bloxberg.cache;

import java.math.BigInteger;

public class BlockGroup{

    // Startingblock of group
    private BigInteger start;

    // sum of all transactions of blocks within this group
    private int sum;
    private int range;

    /**
     * a group of blocks
     * @param start startingblock of the group
     * @param sum sum of all transactions of blocks withing this group
     * @param range count of blocks within the group
     */
    public BlockGroup(BigInteger start, int sum, int range){
        this.range = range;
        this.start = start;
        this.sum = sum;
    }
    public void addToSum(int addition) {
        sum = sum + addition;
    }
    public BigInteger getStart() {
        return start;
    }
    public int getSum() {
        return sum;
    }
    public int getRange() { return  range; }
}