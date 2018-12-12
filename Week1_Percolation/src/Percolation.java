import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/************************************************************************
 * Percolation
 * Name: Matt DeSaix
 * 
 * Description: Simulates a percolation problem.  
 * Creates a closed grid and randomly opens sites.
 * Stops opening sites when a phase transition has occurred, which is defined as 
 * any combination of open cells connecting the bottom to the top row.
 * Note: col,row is specified as 1->n (per assignment guidelines), however the array is 0->(n-1)
 * This makes for some confusing code in places and I could probably do better...
 */

public class Percolation {

	private int numberOfOpenSites = 0;  // The number of sites that are open
	private boolean[][] isOpen;     // Boolean array of whether a site is open
	private boolean[][] isFull;		// Boolean array of whether a site is full
	private final WeightedQuickUnionUF uf; // used to call union() and connected()
	private final int N; // one side of the array
	
	public Percolation(int n) {
		if (n <= 0) throw new IllegalArgumentException("n out of bounds");
		N = n;
		uf = new WeightedQuickUnionUF(n*n+2);
		for (int i = 0; i < n; i++) // loops through all of the top row of the array and 
			uf.union(i, (n*n)); // connects to top dummy variable. q|p == n*n
		for (int i = 0; i < n; i++) // loops through all of the bottom row of the array and connects to
			uf.union((n*n - n + i), (n*n+1)); // bottom dummy variable.  q|p == n*n+1
		isOpen = new boolean[n][n];
		isFull = new boolean[n][n];
	} // end of Percolation()
	
	private int xyTo1D(int row, int col) { //converts 2d array to 1d for .union() and .connected90
		int x;
		x = ((row-1) * N) + (col-1);
		return x;  // 0 <= x < N
	}
	
	private boolean outOfBounds(int row, int col) {
		return (row <= 0 || row > N || col <= 0 || col > N);
	}
	/* open()
	 * This method changes the value of the isOpen array to 'true'
	 * Then, it checks to see which of the four possible adjacent cells are valid
	 * i.e. if the opened cell is at isOpen[0][0], then only [1][0] and [0][1] would be valid
	 * and [-1][0] and [0][-1] would not be valid.
	 * If an adjacent cell is valid, then the next 'if' checks to see if it is opened
	 * 	if it is open, then the cells are 'connected' via .union() from WeightedQuickUnionUF class
	 */
	
	public void open(int row, int col) {
		if (row <= 0 || row > N) throw new IllegalArgumentException("row index out of bounds");
		if (col <= 0 || col > N) throw new IllegalArgumentException("col index out of bounds");
		int p;
		if (!isOpen[row-1][col-1]) {
			isOpen[row-1][col-1] = true; // row/col are 1:n, thus need to have 1 subtracted for array
			numberOfOpenSites++;
		}
		p = xyTo1D(row, col); // p is array appropriate
		
		
		if (!outOfBounds(row, col-1) && isOpen(row, col-1)) uf.union(p, xyTo1D(row, col-1));
		if (!outOfBounds(row-1, col) && isOpen(row-1, col)) uf.union(p,  xyTo1D(row-1, col));
		if (!outOfBounds(row, col+1) && isOpen(row, col+1)) uf.union(p, xyTo1D(row, col+1));
		if (!outOfBounds(row+1, col) && isOpen(row+1, col)) uf.union(p, xyTo1D(row+1, col));
		
		if (uf.connected((N*N), p)) {
			checkCells(row, col);
		}


	} // end of open()
	
	private void checkCells(int row, int col) {
		isFull[row-1][col-1] = true;
		if (!outOfBounds(row, col-1) && isOpen(row, col-1) && !isFull(row, col-1)) checkCells(row, col-1);
		if (!outOfBounds(row-1, col) && isOpen(row-1, col) && !isFull(row-1, col)) checkCells(row-1, col);
		if (!outOfBounds(row, col+1) && isOpen(row, col+1) && !isFull(row, col+1)) checkCells(row, col+1);
		if (!outOfBounds(row+1, col) && isOpen(row+1, col) && !isFull(row+1, col)) checkCells(row+1, col);
		
	}
	
	public boolean isOpen(int row, int col) {
		if (row <= 0 || row > N) throw new IllegalArgumentException("row index out of bounds");
		if (col <= 0 || col > N) throw new IllegalArgumentException("col index out of bounds");
		return isOpen[row-1][col-1]; // returns array values (note difference between row&col indices)
	} // end of isOpen()
	
	public boolean isFull(int row, int col) {
		if (row <= 0 || row > N) throw new IllegalArgumentException("row index out of bounds");
		if (col <= 0 || col > N) throw new IllegalArgumentException("col index out of bounds");
		return isFull[row-1][col-1]; // returns array values (note difference b/n row&col indices
	} // end of isFull()
	
	public int numberOfOpenSites() {
		return numberOfOpenSites;
	} // end of numberOfOpenSites()
	
	
	public boolean percolates() {
		if (N > 1) return uf.connected((N*N),(N*N+1));
		else return (isOpen(1,1));
	} //end of percolates()

} // end of class
