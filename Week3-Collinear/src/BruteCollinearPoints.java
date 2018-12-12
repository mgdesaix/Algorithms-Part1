import java.util.Arrays;

public class BruteCollinearPoints {
	
	private int numberOfSegments = 0;
	private LineSegment[] segments = new LineSegment[0];
	
	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Null item provided!");
		for (int i = 0; i < points.length; i++) {
			 if (points[i] == null) throw new IllegalArgumentException("null element");
		}
		checkDuplicates(points);
		for (int i = 0; i < points.length; i++) {
			for (int j = i+1; j < points.length; j++) {
				for (int k = j+1; k < points.length; k++) {
					for (int m = k+1; m < points.length; m++) {
						if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])) 
						{
							Point[] subarray = new Point[] {points[i], points[j], points[k], points[m]};
							Arrays.sort(subarray);
							LineSegment[] temp = new LineSegment[segments.length+1];
							temp[0] = new LineSegment(subarray[0], subarray[3]);
							if (temp.length > 1) {
								for (int n = 0; n < segments.length; n++) {
									temp[1+n] = segments[n];
								}
							}
							segments = temp;

							numberOfSegments++;
						}
					}
				}
			}
			
		}
		
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

	public int numberOfSegments() {
		return numberOfSegments;
	}
	
	public LineSegment[] segments() {
		return segments;
	}
	

} // end of class
