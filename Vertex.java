import java.util.ArrayList;

public class Vertex {
	public  String name;
	public boolean underMaintanance;
	public int totalPasses;
	public int marked;
	public int crossTime;
	public ArrayList<Edge> edges=new ArrayList<Edge>();
	public Vertex(String word, boolean mainT,int Passes,int mark) {
		this.name=word;
		this.underMaintanance=mainT;
		this.totalPasses=Passes;
		this.marked=mark;
		this.crossTime=0;
	}
}
