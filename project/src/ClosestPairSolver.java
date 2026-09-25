import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {
    public long comparisons = 0;
    public int maxRecursionDepth =0;

    public double closestPair(Point[] points){
        if(points == null || points.length < 2){
            throw new IllegalArgumentException("points must have at least 2 points");
        }
        comparisons=0;
        maxRecursionDepth=0;

        Point[] px = Arrays.copyOf(points,points.length);
        Arrays.sort(px,Comparator.comparingDouble(p -> p.x));

        Point[] py = Arrays.copyOf(points,points.length);
        Arrays.sort(py,Comparator.comparingDouble(p -> p.y));

        return closetRecursive(px,py, 0,points.length-1,1);
    }

    private double closetRecursive(Point[] px, Point[] py, int left, int right, int depth){
        if(depth>maxRecursionDepth){maxRecursionDepth=depth;}
        if(right-left<=3){
            return bruteForce(px,left,right);
        }
        int  mid =left + (right-left)/2;
        Point midPoint = px[mid];

        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        Point[] pyl = new Point[leftSize];
        Point[] pyr = new Point[rightSize];
        int l = 0;
        int r = 0;

        for (Point p:py) {
            if ((p.x<midPoint.x||(p.x==midPoint.x&&l<leftSize))&&l<leftSize) {
                pyl[l++] = p;
            } else if (r < rightSize) {
                pyr[r++] = p;
            }
        }

        double distLeft = closetRecursive(px,pyl,left,mid,depth+1);
        double distRight = closetRecursive(px,pyr,mid+1,right,depth+1);

        double d = Math.min(distLeft,distRight);

        Point[] strip = new Point[right-left+1];
        int stripSize = 0;
        for (Point p:py) {
            comparisons++;
            if (Math.abs(p.x-midPoint.x)<d) {
                strip[stripSize++]=p;
            }
        }
        return Math.min(d,stripClosets(strip,stripSize,d));
    }

    private double stripClosets(Point[] strip, int size,double d){
        double minDistance = d;
        for(int i=0;i<size;i++){
            for(int j=i+1; j<size && (strip[j].y - strip[i].y) < minDistance;j++){
                comparisons++;
                double distance = strip[i].distanceTo(strip[j]);
                if(distance < minDistance){minDistance=distance;}
            }
        }
        return minDistance;
    }


    public double bruteForce(Point[] points, int left, int right) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = left; i < right; i++) {
            for (int j = i+1; j <=right; j++) {
                comparisons++;
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }
}