import java.util.Arrays;


public class FastCollinearPoints {

	private LineSegment[] segments = new LineSegment[0];
	private Point[][] pointSegments = new Point[0][0];

	public FastCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Null item provided!");
		for (int i = 0; i < points.length; i++) {
			 if (points[i] == null) throw new IllegalArgumentException("null element");
		}
		checkDuplicates(points);
		 Arrays.sort(points);
		 Point[] pointsCopy = Arrays.copyOf(points, points.length);
		 int count;
		 for (int i = 0; i < points.length; i++) {
			 	count = 2;
		    	Arrays.sort(pointsCopy);
		    	Arrays.sort(pointsCopy, points[i].slopeOrder());
		    	for (int j = 1; j < points.length; j++) {
	    			double slopeA = points[i].slopeTo(pointsCopy[j-1]);
	    			double slopeB = points[i].slopeTo(pointsCopy[j]);
	    			
	    			if (slopeA == slopeB && j < points.length-1) count++;
	    			else if (slopeA == slopeB && j == points.length-1 && count == 3) {
	    				count++;
	    				check(pointsCopy[j], points[i]);
	    			}
	    			else if (slopeA != slopeB && count >= 4 && count > j-1) {
	    				check(pointsCopy[j-1], points[i]);
	    				count = 2;
	    			}
	    			else if (slopeA != slopeB && count >= 4) {
	    				check(pointsCopy[j-1], points[i]);
	    				count = 2;
	    			}
	    			else count = 2;
	    		}
	    	}
}
	
	private void check(Point a, Point b) {
		int check = 0;
		if (pointSegments.length < 1) {
			pointSegments = new Point[1][2];
			pointSegments[0][0] = a;
			pointSegments[0][1] = b;
			addSegment(a, b);
		} else {
			for (int i = 0; i < pointSegments.length; i++) {
				if (a.slopeTo(pointSegments[i][0]) == pointSegments[i][0].slopeTo(b) && b.slopeTo(pointSegments[i][0]) == pointSegments[i][1].slopeTo(pointSegments[i][0]) ) check++;
				if (a == pointSegments[i][0] || a == pointSegments[i][1] && a.slopeTo(b) == pointSegments[i][0].slopeTo(pointSegments[i][1])) check++;
				if (b == pointSegments[i][0] || b == pointSegments[i][1] && a.slopeTo(b) == pointSegments[i][0].slopeTo(pointSegments[i][1])) check++;

			}
			if (check == 0) {
				Point[][] pointTemp = new Point[pointSegments.length+1][2];
				pointTemp[0][0] = a;
				pointTemp[0][1] = b;

				for (int j = 0; j < pointSegments.length; j++) {
					pointTemp[1+j][0] = pointSegments[j][0];
					pointTemp[1+j][1] = pointSegments[j][1];
				}

				pointSegments = pointTemp;
				addSegment(a, b);
			}
			
		}
	}
	
	private void addSegment(Point a, Point b) {

		 LineSegment[] temp = new LineSegment[segments.length+1];
		 temp[0] = new LineSegment(a, b);
		 if (temp.length > 1) {
			 for (int i = 0; i < segments.length; i++) {
				 temp[1+i] = segments[i];
			 }
		 }
		 segments = temp;
	}
	
	private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate points");
                }
            }
        }
    }
	
	public int numberOfSegments()  { // the number of line segments
		 return segments.length;
	 }
	 
	 public LineSegment[] segments() {  // the line segments
		 return segments;
	 }
	 
}
