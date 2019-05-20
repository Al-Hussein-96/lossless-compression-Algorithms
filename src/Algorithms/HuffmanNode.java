/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

/**
 *
 * @author nEW u
 */
public class HuffmanNode {

    private byte byteValue;
    private long frequency;
    private HuffmanNode right_Child, left_Child;
    private String code;
    private boolean ok;

    HuffmanNode() {
    }

    public HuffmanNode(byte byteValue, long frequency) {
        this.byteValue = byteValue;
        this.frequency = frequency;
        this.right_Child = this.left_Child = null;

    }

    public HuffmanNode createNode(HuffmanNode left, HuffmanNode right) {
        this.frequency = left.getFrequency() + right.getFrequency();

        this.byteValue = '-';
        this.left_Child = left;
        this.right_Child = right;
        this.ok = false;
        return this;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public HuffmanNode getrChild() {
        return right_Child;
    }

    public void setrChild(HuffmanNode right_Child) {
        this.right_Child = right_Child;
    }

    public HuffmanNode getlChild() {
        return left_Child;
    }

    public void setlChild(HuffmanNode left_Child) {
        this.left_Child = left_Child;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    
    

}
