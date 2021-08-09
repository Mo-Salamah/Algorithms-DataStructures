/**
 * Percolation. Given a composite systems comprised of randomly distributed
 * insulating and metallic materials: what fraction of the materials need to be
 * metallic so that the composite system is an electrical conductor? Given a
 * porous landscape with water on the surface (or oil below), under what
 * conditions will the water be able to drain through to the bottom (or the oil
 * to gush through to the surface)? Scientists have defined an abstract process
 * known as percolation to model such situations.
 *
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    //Instance Variables here (unless otherwise needed, make variables private)

    //ufArray is an object of the WeightedQuickUnionUF class
    //which implements methods that efficiently solve dynamic connectivity
    //functions like union() and find()
    private WeightedQuickUnionUF ufArray;
    private boolean[] open;
    //number of sites that have been opened; used for calculating stats
    private int openSites = 0;
    //for grid of size n-by-n, gridWidth is n; used for getIndex() method
    private int gridWidth;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Value not appropriate");
        }
        //Union Find object set to size: n^2 + 2
        //n^2 is to represent the grid and the additional 2 elements
        //are to represent the imaginary 'topSite' and 'bottomSite'
        //index 0 is 'topSite' index n^2 + 1 is 'bottomSite'
        ufArray = new WeightedQuickUnionUF(n * n + 2);

        //initially all sites are blocked
        open = new boolean[n * n + 2];

        gridWidth = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > gridWidth || col <= 0 || col > gridWidth) {
            throw new IllegalArgumentException("Value not appropriate");
        }
        if (isOpen(row, col) == false) {
            openSites += 1;
            int siteIndex = getIndex(row, col);
            //check if the to-be-opened site is at the top row
            if (1 <= siteIndex && siteIndex <= gridWidth) {
                //if so, call union(siteIndex, topSite)
                ufArray.union(siteIndex, 0);
            }
            //check if the to-be-opened site is at the bottom row
            else if ((gridWidth * gridWidth - gridWidth) < siteIndex && siteIndex <= (gridWidth
                    * gridWidth)) {
                ufArray.union(siteIndex, (gridWidth * gridWidth + 1));
            }

            //for each open neighbor, connect it with to-be-opened site; call union(siteIndex, neighborIndex)
            int[] neighbors = getNeighbors(row, col);
            for (int i = 0; i < neighbors.length; i++) {
                //only connect with open neighbors
                if (open[neighbors[i]]) {
                    ufArray.union(siteIndex, neighbors[i]);
                }
            }
            open[siteIndex] = true;
        }


    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > gridWidth || col <= 0 || col > gridWidth) {
            throw new IllegalArgumentException("Value not appropriate");
        }
        int siteIndex = getIndex(row, col);
        return open[siteIndex];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > gridWidth || col <= 0 || col > gridWidth) {
            throw new IllegalArgumentException("Value not appropriate");
        }
        int leaderOfTopSite = ufArray.find(0);
        int leaderOfSite = ufArray.find(getIndex(row, col));
        return leaderOfTopSite == leaderOfSite;

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        //return true if topSite and bottomSite are connected
        //ufArray.connected(0, (gridWidth * gridWidth + 1));
        return ufArray.find(0) == ufArray.find(gridWidth * gridWidth + 1);
    }


    private int getIndex(int row, int col) {
        //helper method. Takes row and col values (for a site)
        //and returns the corresponding array index
        return row * gridWidth + (col - gridWidth);
    }

    //implementation can be improved, but good for now
    private int[] getNeighbors(int row, int col) {
        //helper method. Takes row and col values (for a site)
        //and returns an array with indices to the site's neighbors
        int siteIndex = getIndex(row, col);
        int neighborsNum = 0;
        int[] temp = new int[4];

        //left neighbor, if exists
        if (((siteIndex - 1) % gridWidth) != 0) {
            temp[0] = siteIndex - 1;
        }

        //right neighbor, if exists
        if (((siteIndex + 1) % gridWidth) != 1) {
            temp[1] = siteIndex + 1;
        }

        //down neighbor, if exists
        if ((siteIndex - gridWidth) > 0) {
            temp[2] = siteIndex - gridWidth;
        }

        //up neighbor, if exists
        if ((siteIndex + gridWidth) <= (gridWidth * gridWidth)) {
            temp[3] = siteIndex + gridWidth;
        }

        //count actual number of neighbors
        for (int i = 0; i < 4; i++) {
            //neighbor will never have index = 0
            if (temp[i] != 0) neighborsNum += 1;
        }

        int[] neighbors = new int[neighborsNum];
        int neighborsArrIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (temp[i] != 0) {
                neighbors[neighborsArrIndex] = temp[i];
                neighborsArrIndex += 1;
            }
        }
        return neighbors;
    }


    //test client (optional)
    public static void main(String[] args) {
        Percolation perTest = new Percolation(3);
        perTest.open(2, 3);
        System.out.println(perTest.percolates());
        perTest.open(1, 3);
        System.out.println(perTest.percolates());
        perTest.open(3, 2);
        System.out.println(perTest.percolates());
        perTest.open(2, 1);
        System.out.println(perTest.percolates());
        perTest.open(1, 2);
        System.out.println(perTest.percolates());
        perTest.open(2, 2);
        System.out
                .println("number of open sites test, should be : 6" + perTest.numberOfOpenSites());
        System.out.println(perTest.percolates());
        perTest.open(2, 2);
        System.out.println(perTest.percolates());
        System.out
                .println("number of open sites test, should be : 6" + perTest.numberOfOpenSites());

        //System.out.println(perTest.percolates());
        //perTest.open(2, 2);
        //System.out.println("Should be true: " + perTest.isOpen(1, 1));
        //System.out.println("Should be true: " + perTest.isFull(1, 1));
        //System.out.println("Should be false: " + perTest.isFull(2, 2));
        //System.out.println("Should be false: " + perTest.percolates());
        //System.out.println("Should be true: " + perTest.isFull(2, 2));
        //perTest.open(1, 2);
        //System.out.println("Should be true: " + perTest.percolates());
    }
}














