
public class Edge {
	public String source;
	public String destination;
	public boolean isActive;
	public boolean isBroken;
	public Double dist;
	public int marked;
	public Double velocity;
	public Edge(String w1,String w2,boolean active,boolean broken,Double dist) {
		this.source=w1;
		this.destination=w2;
		this.isActive=active;
		this.isBroken=broken;
		this.dist=dist;
		this.marked=0;
		this.velocity=0.0;
	}
}
