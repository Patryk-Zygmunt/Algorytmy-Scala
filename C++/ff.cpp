
#include <iostream>
// Part of Cosmos by  OpenGenus Foundation //
#include <climits>
#include <cstring>
#include <queue>
#include <fstream>

using namespace std;
#define size 7

void fill(bool *tab, bool v) {
for (int i = 0; i < size; i++)
tab[i] = v;
}

/* Returns true if there is a path from source 's' to sink 't' in
 * residual graph. Also fills parent[] to store the path */
bool bfs(int rGraph[size][size], int s, int t, int* parent)
{
    // Create a visited array and mark all vertices as not visited
    bool visited[size];
    fill(visited ,false);
    queue <int> q;
    q.push(s);
    visited[s] = true;
    parent[s] = -1;
    // Standard BFS Loop
    while (!q.empty())
    {
        int u = q.front();
        q.pop();
        for (int v = 0; v < size; v++)
            if (!visited[v] && rGraph[u][v] > 0)
            {
                q.push(v);
                parent[v] = u;
                visited[v] = true;
            }
    }
    // If we reached sink in BFS starting from source, then return
    // true, else false
    return visited[t];
}
// Returns tne maximum flow from s to t in the given graph
int fordFulkerson(int graph[size][size], int s, int t)
{
    int i, j;
    // Create a residual graph and fill the residual graph with
    // given capacities in the original graph as residual capacities
    // in residual graph
    int rGraph[size][size]; // Residual graph where rGraph[i][j] indicates
    // residual capacity of edge from i to j (if there
    // is an edge. If rGraph[i][j] is 0, then there is not)
    for (i = 0; i < size; i++)
        for (j = 0; j < size; j++)
            rGraph[i][j] = graph[i][j];
    int parent[size];  // This array is filled by BFS and to store path
    int max_flow = 0;  // There is no flow initially
    // Augment the flow while tere is path from source to sink
    while (bfs(rGraph, s, t, parent))
    {
        // Find minimum residual capacity of the edges along the
        // path filled by BFS. Or we can say find the maximum flow
        // through the path found.
        int flow = INT_MAX;
        for (j = t; j != s; j = parent[j])
        {
            i = parent[j];
            flow = min(flow, rGraph[i][j]);
        }
        // update residual capacities of the edges and reverse edges
        // along the path
        for (j = t; j != s; j = parent[j])
        {
            i = parent[j];
            rGraph[i][j] -= flow;
            rGraph[j][i] += flow;
        }
        // Add path flow to overall flow
        max_flow += flow;
    }
    // Return the overall flow
    return max_flow;
}




int main(){
   int graph[size][size] ;

    ifstream fp;
    // open the file stream
    fp.open("main.cpp");
    if (!fp) {
        cout << "Error, file couldn't be opened" << endl;
        return 1;
    }
    for (int row = 0; row < size; row++) {
        for (int column = 0; column < size; column++) {
            fp >> graph[row][column];
        }
    }
fp.close();
   cout<<fordFulkerson(graph,2,4);
    return 0;

}
