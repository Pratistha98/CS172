//@author Ben King



/*Graph File Format:
 * First Line: # of vertices
 * Second Line: U [undirected] || D [directed]
 * 3rd Line ->: Edges
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Graph {
	
	
	public static void main(String[] args) throws FileNotFoundException {
		/*
		//graph gen method
		Graph graph = new Graph(500, false);
		
		Random r = new Random();
		
		for(int i = 0; i < 500; i++){
			for(int j = 0; j < 500; j++){
				if(r.nextInt(100) % 100 == 0){
					System.out.println(i + " " +  j);
				}
			}
		}*/
		
		
		
		
		Graph g1 = readGraph("Graph1.txt");
		Graph g2 = readGraph("Graph2.txt");
		Graph g3 = readGraph("Graph3.txt");
		Graph g4 = readGraph("Graph4.txt");
		Graph g5 = readGraph("Graph5.txt");
		Graph g6 = readGraph("Graph6.txt");

		

		g1.show();
		
		System.out.println("Graph1poop:");
		g1.show();
		Vertex[] verts = g1.unweighted(1);
		//for(int i = 0; i < verts.length; i++){
		//	System.out.println(i + ": " + verts[i].dist + " " + verts[i].path + " " + verts[i].known);
		//}
		System.out.println();
		g1.printPath(verts);
		
		Vertex[] verts2 = g2.unweighted(1);
		System.out.println();
		System.out.println("Graph2:");
		g2.printPath(verts2);
		System.out.println();

		
		
		Vertex[] verts3 = g3.unweighted(1);
		System.out.println();
		System.out.println("Graph3:");
		g3.printPath(verts3);
		System.out.println();

		Vertex[] verts4 = g4.unweighted(1);
		System.out.println();
		System.out.println("Graph4:");
		g4.printPath(verts4);
		System.out.println();
		
		Vertex[] verts5 = g5.unweighted(5);
		System.out.println();
		System.out.println("Graph5:");
		g5.printPath(verts5);
		System.out.println();
		
		Vertex[] verts6 = g6.unweighted(1);
		System.out.println();
		System.out.println("Graph6:");
		g6.printPath(verts6);
		System.out.println();
		
	}
	
	public void printPath(Vertex [] verts){
		for(int i = 0; i < verts.length; i++){
			Vertex v = verts[i];
			System.out.print(i);
			if(v.dist == 0){
				System.out.println();
				continue;
			}
			while(v.dist != 0 && v.path != -1) {
				System.out.print(" > " + v.path);
				v = verts[v.path];
			}
			System.out.println();
		}
	}
	
	
	public static Graph readGraph(String fileName) throws FileNotFoundException{
		Scanner read = new Scanner(new File(fileName));
		int verts = Integer.parseInt(read.nextLine());
		boolean directed = (read.nextLine().equals("U")) ? false : true;
		Graph g = new Graph(verts, directed);
		while(read.hasNextInt()){
			g.insert(new Edge(read.nextInt(), read.nextInt()));
		}
		return g;
	}
	
	
	private int vertexCount, edgeCount;
	boolean directed; // false for undirected graphs, true for directed
	private boolean adj[][];

	public Graph(int numVertices, boolean isDirected) { 
		directed = isDirected;
		adj = new boolean[numVertices][numVertices];
		for(boolean[] i : adj)
			for(boolean j : i)
				j = false;
		vertexCount = numVertices;
	}

	public boolean isDirected() { 
		return directed;
	}

	public int vertices() {
		return vertexCount;// return the number of vertices }
	}

	public int edges() {
		return edgeCount;// return number of edges }
	}

	public void insert(Edge e) {
		adj[e.v][e.w] = true;
		if(!directed)
			adj[e.w][e.v] = true;// your code here }
	}

	public void delete(Edge e) {
		adj[e.v][e.w] = false;
		if(!directed)
			adj[e.w][e.v] = false;// your code here }
	}

	public boolean connected(int node1, int node2) {
		return adj[node2][node1];// are they connected? }
	}

	public AdjList getAdjList(int vertex) { // your code here }
		return new AdjArray(vertex);
	}

	public void show () {
		for (int s = 0; s < vertices(); s++) {
			System.out.print(s + ": ");
			AdjList A = getAdjList(s);
			for (int t = A.begin(); !A.end(); t = A.next()) // use of iterator
				System.out.print(t + " " );//+ adj[s][t] + " " );
			System.out.println();
		}
	}
	
	
	



	public Vertex[] unweighted (int start)
	{
		
		Vertex[] verts = new Vertex[vertexCount];
		for(int i = 0; i < vertexCount; i++){
			verts[i] = new Vertex();
		}
		verts[start].dist = 0;
				
		for (int currdist = 0; currdist < vertexCount; currdist++)
		{
			for( int i = 0; i < verts.length; i++){
				Vertex v = verts[i];
				if (!v.known && v.dist == currdist)
				{
					v.known = true;
					
					AdjList A = getAdjList(i);
					for( int t = A.begin(); !A.end(); t = A.next() ){
						if(t == -1)
							break;
						Vertex w = verts[t];
						
						if(w.dist == Integer.MAX_VALUE)//doesnt need > < b/c unweighted
						{
							w.dist = currdist + 1;
							w.path = i;
						}
					}
				}
			}
		}
		return verts;
		
	}
	
	private class Vertex {// implements Comparable<Vertex>{
		public int dist;
		public boolean known;
		public int path;
		public Vertex(){
			dist = Integer.MAX_VALUE;
			known = false;
			path = -1;//-1 means self
		}
		/*@Override
		public int compareTo(Vertex v) {
			if(v.dist == dist)
				return 0;
			else if(dist > v.dist)
				return 1;
			else return -1;
		}*/
	}

	
	
	
	
	
	
	
	



	private class AdjArray implements AdjList {
		private int v; // what vertex we are interested in
		private int i; // so we can keep track of where we are
		public AdjArray(int v) {
			// write the code for the constructors
			// save the value of the vertex passed in
			// (that will be where the iterator starts)
			// start the i counter at negative one
			i = -1; this.v = v;
		}
		public int next() { // perhaps the trickiest method
			// use a for loop to advance the value of i
			// for (++i; i < vertices(); i++)
			// and search the appropriate row return the index
			// of the next true value found
			// if (connected(v,i) == true) return i;
			// if the loop completes without finding anything return -1
			for(++i; i < vertices(); i++){
				if(connected(v,i))
					return i;
			}
			return -1;
		}
		public int begin() {
			// reset i back to negative one
			// return the value of a call to next
			i = -1;
			return next();
		}
		public boolean end() {
			// if i is less than the number of vertices return false
			return i > vertices();
		}
	}
}