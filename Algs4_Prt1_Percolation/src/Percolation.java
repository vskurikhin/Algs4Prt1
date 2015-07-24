/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Victor Skurikhin
 */


public class Percolation {
    // private variables:
    private boolean[] aGrid;
    private int iGridSize;
    private WeightedQuickUnionUF cUF;
    private WeightedQuickUnionUF cUFull;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException("input N "
                                              + N + " is not positive.");
        }
        iGridSize = N;
        // create grid:
        aGrid = new boolean[N * N];
        for (int i = 0; i < N * N; ++i) {
            aGrid[i] = false;
        }
        // we have n² fields
        cUF = new WeightedQuickUnionUF(N*N + 3);
        cUFull = new WeightedQuickUnionUF(N*N + 1);
    }

    // validate that idx is a valid input
    private void validateInput(int idx) {
        if (idx < 1 || idx > iGridSize) {
            throw new IndexOutOfBoundsException("input index " + idx
                                                + " is not between 1 and "
                                                + iGridSize);
        }
    }

    // validate that idx is a valid index
    private int validateIndex(int idx) {
        if (idx < 0 || idx >= iGridSize) {
            throw new IndexOutOfBoundsException("array index " + idx
                                                + " is not between 0 and "
                                                + iGridSize);
        }
        if (0 == idx)               { return -1; }
        if (idx == (iGridSize - 1)) { return -2; }
        return 0;
    }

    private int calcIdxaGrid(int row, int col) {
        return row*iGridSize + col;
    }

    private void union(int idx00aGrid, int idx01aGrid) {
        if (aGrid[idx00aGrid]) {
            cUF.union(idx00aGrid + 1, idx01aGrid + 1);
            cUFull.union(idx00aGrid + 1, idx01aGrid + 1);
        }
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validateInput(i);
        validateInput(j);
        int idxI = i - 1;
        int idxJ = j - 1;
        int idxaGrid = calcIdxaGrid(idxI, idxJ);
        aGrid[idxaGrid] = true;

        if (0 == idxI) {
            // connect with source
            cUF.union(0, idxaGrid + 1);
            cUFull.union(0, idxaGrid + 1);
        }

        if (idxI == (iGridSize - 1)) {
            cUF.union(idxaGrid + 1, iGridSize*iGridSize + 2);
        }

        if (1 == iGridSize) { return; }
        
        // look at row
        int valIdxI = validateIndex(idxI);
        if (-1 != valIdxI) {
            // connect with upsite if neccessary
            union(calcIdxaGrid(idxI - 1, idxJ), idxaGrid);
        }

        if (-2 != valIdxI) {
            // connect with downsite if neccessary
            union(calcIdxaGrid(idxI + 1, idxJ), idxaGrid);
        }

        // look at col
        int valIdxJ = validateIndex(idxJ);
        if (-2 != valIdxJ) {
            // right
            union(calcIdxaGrid(idxI, idxJ + 1), idxaGrid);
        }

        if (-1 != valIdxJ) {
            // left
            union(calcIdxaGrid(idxI, idxJ - 1), idxaGrid);
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateInput(i);
        validateInput(j);
        return aGrid[calcIdxaGrid(i - 1 , j - 1)];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateInput(i);
        validateInput(j);
        return cUFull.connected(0, calcIdxaGrid(i - 1, j - 1) + 1);
    }
    
    // does the system percolate?
    public boolean percolates() {
        return cUF.connected(0, iGridSize*iGridSize + 2);
    }
    /*
    public void dumpVars() {
        StdOut.println("Yes");
        for (int idx00Col = 0; idx00Col < iGridSize; ++idx00Col) {
            for (int idx01Row = 0; idx01Row < iGridSize; ++idx01Row) {
                StdOut.printf("aGrid[%d, %d] = %b\n", 
                              idx00Col, idx01Row,
                              aGrid[calcIdxaGrid(idx00Col, idx01Row)]);
            }
        }
        cUF.printparent();
    }
    */
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
// EOF
