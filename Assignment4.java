import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Assignment4 {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(args[0]));
		Scanner scanner2=new Scanner(new File(args[1]));
		Scanner scanner3=new Scanner(new File(args[2]));
		ArrayList<String[]> neighBours=new ArrayList<String[]>();
		Double switchTime=Double.parseDouble(args[3]);
		ArrayList<Vertex> vertex=new ArrayList<Vertex>();
		//HashMap<String, String> edgeToo=new HashMap<String,String>();
		while(scanner.hasNextLine()) {
			 String line = scanner.nextLine();
			 String[] ary = line.split(":");
			 Vertex v1=new Vertex(ary[0], false, 0,0);
			
			 vertex.add(v1);
			 String[] ary1=ary[1].split(",");
			 int size=ary1.length;
			 String et=ary1[size-1];
			 String ary2[]=et.split(">");
			 ary1[size-1]=ary2[0];
			 //System.out.println(ary2[0]+"---"+ary2[1]);
			 for(int i=0;i<size;i++) {
				 if(ary1[i].compareTo(ary2[1])==0) {
					 Edge e1=new Edge(ary[0], ary1[i], true, false, 0.0);
					 v1.edges.add(e1);
				 }
				 else {
					 Edge e1=new Edge(ary[0], ary1[i], false, false, 0.0);
					 //System.out.println("alo");
					 v1.edges.add(e1);
				 }
			 }
			 neighBours.add(ary1);
			}
		while(scanner2.hasNextLine()) {
			String line=scanner2.nextLine();
			String[] ary=line.split(" ");
			String[] ary1=ary[0].split("-");
			for(Vertex et:vertex) {
				for(Edge vEdges:et.edges) {
					if(vEdges.source.compareTo(ary1[0])==0 &&vEdges.destination.compareTo(ary1[1])==0) {
						Double d1=Double.parseDouble(ary[1]);
						vEdges.dist=d1;
					}
					else if(vEdges.source.compareTo(ary1[1])==0 &&vEdges.destination.compareTo(ary1[0])==0) {
						Double d1=Double.parseDouble(ary[1]);
						vEdges.dist=d1;
					}
					else {
						continue;
					}
				}
			}
		}
		while(scanner3.hasNextLine()) {
			String line=scanner3.nextLine();
			String[] ary=line.split(" ");
			if(ary[0].equals("MAINTAIN")) {
				Maintain(vertex, ary[1],0);
				System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
				System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+"\""+" has been executed successfully!");
				//System.out.println("1");
			}
			else if(ary[0].equals("SERVICE")) {
				Maintain(vertex, ary[1],1);
				System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
				System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("BREAK")) {
				//System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
				String[] ary1=ary[1].split(">");
				
				BreakOrNot(vertex, ary1[0], ary1[1], 0);
				System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
				System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("REPAIR")) {
				String[] ary1=ary[1].split(">");
				BreakOrNot(vertex, ary1[0], ary1[1], 1);
				System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
				System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("ADD")) {
					Vertex v1=new Vertex(ary[1], false, 0,0);
					vertex.add(v1);
					//markedList.add(0);
					System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
					System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("LINK")) {
				String[] ary1=ary[1].split(":");
				String addEdge=ary1[0];
				String[] neifhbours=ary1[1].split(",");
				int size=neifhbours.length;
				String[] arr=neifhbours[size-1].split(">");
				String et=arr[1];
				neifhbours[size-1]=arr[0];
				NewEdge(vertex, addEdge, neifhbours, et);
				System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
				System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("ROUTE")) {
				System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]+" "+ary[2]);
						for(Vertex vs:vertex) {
							vs.marked=0;
							for(Edge es:vs.edges) {
								es.marked=0;
								es.source=vs.name;
								es.velocity=0.0;
							}
						}
						ArrayList<Edge> edgeTo=new ArrayList<Edge>();
						ArrayList<String> Path=new ArrayList<String>();
						Double velo=Double.parseDouble(ary[2]);
						String[] startEnd=ary[1].split(">");
						Dijkstra2(vertex, startEnd[0], switchTime, velo, edgeTo, 0.0);
						int havePath=0;
						Double time=FindPath(edgeTo, startEnd[0],startEnd[1], Path,havePath);
						//Dijkstra(vertex, "0","5", edgeTo,0,0.0);	
						for(String et:Path) {
							if(et.equals(startEnd[0])) {
								havePath++;
							}
							else if(et.equals(startEnd[1])) {
								havePath++;
								
							}
						}
						
						int switcCount=0;
						if(havePath==2) {
							switcCount=Switch(vertex, Path);
							System.out.println("	Time (in min): "+time);
							System.out.println("	Total # of switch changes: "+switcCount);
							System.out.print("	Route from "+startEnd[0]+" to "+startEnd[1]+": ");
							
							for(int i=Path.size()-1;i>=0;i--) {
								
								 for(Vertex vs:vertex) {
									 if(vs.name.equals(Path.get(i))) {
										 vs.crossTime++;
									 }
								 }
								System.out.print(Path.get(i)+" ");
								
							
							}
							System.out.println();
							System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+" "+ary[2]+"\""+" has been executed successfully!");
							
							
							//System.out.print("Switch="+switcCount);
							//System.out.print(" Time="+time);
						}
						else if(havePath!=2) {
							System.out.println("	No root from to "+startEnd[0]+" to "+startEnd[1]+" found currently!");
							System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+" "+ary[2]+"\""+" has been executed successfully!");
						}
						//System.out.println("aa");
						
						//System.out.println();
						
						/*for(Vertex v:vertex) {
						//System.out.print(v.name+"->");
						for(Edge e:v.edges) {
							System.out.print(e.source+"-->"+e.destination+"-"+e.isActive+",");
						}
						//System.out.println();
					}*/
						for(Edge ese:edgeTo) {
							//System.out.println(ese.source+"-"+ese.destination+"="+ese.dist+"-->"+ese.velocity);
						}
			}
			else if(ary[0].equals("LISTROUTESFROM")) {
				System.out.println("COMMAND IN PROCESS >> "+ary[0]+" "+ary[1]);
				System.out.print("	Routes from "+ary[1]+": ");
				ArrayList<String> RoutesFrom=new ArrayList<String>();
				for(Vertex vt:vertex) {
					if(vt.name.equals(ary[1])) {
						for(Edge es:vt.edges) {
							RoutesFrom.add(es.destination);
							//System.out.print(es.destination+" ");
						}
					}
				}
				Collections.sort(RoutesFrom);
				for(String s:RoutesFrom) {
					System.out.print(s+" ");
				}
				System.out.println();
				System.out.println("	"+"Command "+"\""+ary[0]+" "+ary[1]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("LISTMAINTAINS")) {
				System.out.println("COMMAND IN PROCESS >> "+ary[0]);
				System.out.print("	Intersections under maintenance:");
				ArrayList<String> Maintain=new ArrayList<String>();
				for(Vertex v1:vertex) {
					if(v1.underMaintanance==true) {
						Maintain.add(v1.name);
						//System.out.print(" "+v1.name);
					}
				}
				Collections.sort(Maintain);
				for(String s:Maintain) {
					System.out.print(" "+s);
				}
				System.out.println();
				System.out.println("	"+"Command "+"\""+ary[0]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("LISTACTIVERAILS")) {
				System.out.println("COMMAND IN PROCESS >> "+ary[0]);
					System.out.print("	Active Rails:");
					ArrayList<String> active=new ArrayList<String>();
					for(Vertex vs:vertex) {
						active.add(vs.name);
						
					}
					Collections.sort(active);
					for(String ss:active) {
					for(Vertex v1:vertex) {
						if(v1.name.equals(ss)) {
						for(Edge e1:v1.edges) {
							if(e1.isActive==true && e1.isBroken==false) {
								System.out.print(" "+e1.source+">"+e1.destination);
							}
						}
						}
					}
					}
					System.out.println();
					System.out.println("	"+"Command "+"\""+ary[0]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("LISTBROKENRAILS")) {
				System.out.println("COMMAND IN PROCESS >> "+ary[0]);
					System.out.print("	Broken rails:");
					for(Vertex v1:vertex) {
						for(Edge e1:v1.edges) {
							if(e1.isBroken==true) {
								System.out.print(" "+e1.source+">"+e1.destination);
							}
						}
					}
					System.out.println();
					System.out.println("	"+"Command "+"\""+ary[0]+"\""+" has been executed successfully!");
				}
			else if(ary[0].equals("LISTCROSSTIMES")){
				System.out.println("COMMAND IN PROCESS >> "+ary[0]);
				System.out.print("	# of cross times: ");
				ArrayList<String> verName=new ArrayList<String>();
				for(Vertex v:vertex) {
					verName.add(v.name);
					//System.out.print(v.name+":"+v.crossTime+" ");
				}
				Collections.sort(verName);
				for(String n:verName) {
					for(Vertex v:vertex) {
						if(v.name.equals(n)) {
							System.out.print(v.name+":"+v.crossTime+" ");
						}
					}
				}
				System.out.println();
				System.out.println("	"+"Command "+"\""+ary[0]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("TOTALNUMBEROFJUNCTIONS")){
				System.out.println("COMMAND IN PROCESS >> "+ary[0]);
				int junctions=0;
				for(Vertex v:vertex) {
					junctions++;
				}
				System.out.println("	Total # of junctions: "+junctions);
				System.out.println("	"+"Command "+"\""+ary[0]+"\""+" has been executed successfully!");
			}
			else if(ary[0].equals("TOTALNUMBEROFRAILS")) {
				System.out.println("COMMAND IN PROCESS >> "+ary[0]);
				int num=0;
				System.out.print("	Total # of rails: ");
				for(Vertex v1:vertex) {
					for(Edge e1:v1.edges) {
					
							num++;
						
					}
				}
				System.out.print(num);
				System.out.println();
				System.out.println("	"+"Command "+"\""+ary[0]+"\""+" has been executed successfully!");
				
			}
			else {
				System.out.println("COMMAND IN PROCESS >> "+ary[0]);
				System.out.println("	Unrecognized command "+ary[0]+"!");
			}
		}
		
		/*for(Vertex v:vertex) {
			System.out.print(v.name+"->");
			for(Edge e:v.edges) {
				System.out.print(e.destination+"-"+e.isActive+",");
			}
			System.out.println();
		}*/
		 

	}
	public static void Maintain(ArrayList<Vertex> v,String inter,int number) {
		
		for(Vertex ve:v) {
			if(inter.equals(ve.name)) {
				if(number==0) {
				ve.underMaintanance=true;
				}
				else if(number==1) {
					ve.underMaintanance=false;
				}
			}
			else {
				continue;
			}
		}
	}
	public static int Switch(ArrayList<Vertex> v,ArrayList<String> Path) {
		int size=Path.size();
		int switchCount=0;
		for(int i=size-1;i>0;i--) {
			for(Vertex vs:v) {
				if(vs.name.equals(Path.get(i))) {
					int control=0;
					for(Edge et:vs.edges) {
						if(et.source.equals(Path.get(i))&&et.destination.equals(Path.get(i-1))&&et.isActive==true) {
							//System.out.println("alo");
							control=1;
							break;
						}
					}
					if(control==0) {
						for(Edge es:vs.edges) {
						if(es.isActive==true) {
							es.isActive=false;
						}
						else if(es.destination.equals(Path.get(i-1)) && es.isActive==false) {
							switchCount++;
							es.isActive=true;
						}
					}
					}
					
				}
			}
		}
		return switchCount;
	}
	public static void BreakOrNot(ArrayList<Vertex> v,String i1,String i2,int number) {
		for(Vertex ve:v) {
			if(i1.equals(ve.name)) {
				for(Edge ed:ve.edges) {
					if(i2.equals(ed.destination)) {
						if(number==0) {
							ed.isBroken=true;
						}
						else if(number==1) {
							ed.isBroken=false;
						}
					}
				}
			}
		}
	}
	public static void Dijkstra2(ArrayList<Vertex> v,String ver,Double switchTime,Double velocity,ArrayList<Edge> EdgeTo,Double lengthh) {
		
		for(Vertex et:v) {
			if(et.name.equals(ver)&& et.underMaintanance==false) {
				
				if(EdgeTo.isEmpty()) {
					for(Edge ed:et.edges) {
						//Edge np=new Edge(ed.source, ed.destination, ed.isActive, ed.isBroken, ed.dist);
						Edge np=ed;
						findTime(np, switchTime, velocity);
						if(np.isBroken==false) {
							for(Vertex veee:v) {
								if(np.destination.equals(veee.name)&& veee.underMaintanance==false) {
									et.marked=1;
									EdgeTo.add(np);
								}
							}
							
						}
						else if(np.isBroken==true) {
							//System.out.println("sa");
						}
						
					}
					Edge min=findMin(EdgeTo, switchTime, velocity,v);
					Double length=min.velocity;
					//System.out.println(min.destination);
					Dijkstra2(v, min.destination, switchTime, velocity, EdgeTo,length);
					return;
				}
				else {
					for(Edge ed:et.edges) {
						//Edge np=new Edge(ed.source, ed.destination, ed.isActive, ed.isBroken, ed.dist);
						Edge np=ed;
						if(np.isBroken==false) {
							et.marked=1;
							int control=0;
							
							findTime(np, switchTime, velocity);
							for(Edge ee:EdgeTo) {
								if((!np.source.equals(ee.source)) && np.destination.equals(ee.destination)) {
									if(lengthh+ed.velocity<ee.velocity) {
										ee.source=np.source;
										ee.velocity=np.velocity+lengthh;
										control=1;
										
									}
									else {
										control=1;
									}
								}
								else if(np.source.equals(ee.source) && np.destination.equals(ee.destination)) {
									control=1;
								}
								else if(ee.source.equals(np.destination) && ee.destination.equals(np.source)) {
									control=1;
								}
								
								
							}
							if(control==0) {
								//System.out.println("alo");
								for(Vertex vv:v) {
									if(vv.name.equals(np.destination)&&vv.underMaintanance==false) {
										EdgeTo.add(np);
										for(Edge es:EdgeTo) {
											if(es.equals(np)) {
												es.velocity=es.velocity+lengthh;
											}
										}
										break;
									}
								}
								//ed.velocity=ed.velocity+lengthh;
								//np.velocity=np.velocity+lengthh;
								
							}
							
							
						}
						else {
							continue;
						}
					
					}
					for(Edge sss:EdgeTo){
						//System.out.println("*********"+sss.source+"-<"+sss.destination+"-*-*-"+sss.velocity);
					}
					//System.out.println("aaa");
					Edge min=findMin(EdgeTo, switchTime, velocity,v);
					Double length=min.velocity;
					//System.out.println("*"+min.destination);
					Dijkstra2(v, min.destination, switchTime, velocity, EdgeTo,length);
					return;
				}
			}
			
		}
		
	}
	public static void findTime(Edge es,Double switcch,Double velocit) {
		Double time=0.0;
		if(es.isActive==true) {
			time=(es.dist*60.0)/velocit;
			es.velocity=time;
		}
		else if(es.isActive==false) {
			time=((es.dist*60.0)/velocit)+switcch;
			es.velocity=time;
		}
		
	}
	public static Edge findMin(ArrayList<Edge> es,Double switcch,Double velocity,ArrayList<Vertex> v) {
		Edge min=new Edge(null, null, true, true, 9999999999999999999999999999.0);
		int size=es.size();
		for(int i=0;i<size;i++) {
			if(es.get(i).marked==0) {
				min=es.get(i);
				break;
			}
		}
		
		//int size=es.size();
		for(int i=0;i<size;i++) {
			if(es.get(i).marked==0) {
			if(es.get(i).velocity<min.velocity) {
				for(Vertex kar:v) {
					if(kar.name.equals(es.get(i).destination)&& kar.marked==0 && kar.underMaintanance!=true) {
						min=es.get(i);
					}
				}
				
			}
			}
		}
		//System.out.println("min="+min.destination);
		for(Edge ee:es) {
			if(min.equals(ee)) {
				ee.marked=1;
			}
		}
		//System.out.println("*"+min.destination+"*");
		return min;
		
	}

	public static void NewEdge(ArrayList<Vertex> v,String addEdge,String[] neighBour,String activeEdge){
		for(Vertex ve:v) {
			if(addEdge.equals(ve.name)) {
				int nSize=neighBour.length;
				for(int i=0;i<nSize;i++) {
					String[] arr=neighBour[i].split("-");
					for(Vertex vs:v) {
						if(vs.name.equals(arr[0])) {
							Edge e1=new Edge(arr[0], ve.name, false, false, Double.parseDouble(arr[1]));
							vs.edges.add(e1);
							break;
						}
					}
					if(activeEdge.equals(arr[0])) {
						Edge e1=new Edge(ve.name, arr[0], true, false, Double.parseDouble(arr[1]));
						ve.edges.add(e1);
					}
					else {
						Edge e1=new Edge(ve.name, arr[0], false, false, Double.parseDouble(arr[1]));
						ve.edges.add(e1);
					}
				}
			}
		}
	}
	public static Double FindPath(ArrayList<Edge> edgeTo,String start,String end,ArrayList<String> Path,int control) {
		int size=edgeTo.size();
		int findingTime=0;
		Double time=0.0;
		for(int i=0;i<size;) {
			if(start.equals(end)) {
				Path.add(end);
				control=1;
				break;
			}
		else if(end.equals(edgeTo.get(i).destination)) {
				if(findingTime==0) {
					time=edgeTo.get(i).velocity;
					findingTime++;
				}
				Path.add(end);
				end=edgeTo.get(i).source;
				i=0;
			}
		else {
			i++;
		}
		}
		return time;
	}

}
