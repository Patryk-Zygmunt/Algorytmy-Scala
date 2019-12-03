
#include <iostream>
#include <climits>
#include <queue>
#include <fstream>

using namespace std;

#define size 7

void fill(bool* tab, bool v) {
    for (int i = 0; i < size; i++)
        tab[i] = v;
}


bool bfs(int rGraph[size][size], int s, int t, int* parent)
{
    bool visited[size];
    fill(visited, false);
    queue <int> q;
    q.push(s);
    visited[s] = true;
    parent[s] = -1;
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
    return visited[t];
}


int fordFulkerson(int graph[size][size], int s, int t)
{
    int i, j;
    int rGraph[size][size];
    for (i = 0; i < size; i++)
        for (j = 0; j < size; j++)
            rGraph[i][j] = graph[i][j];
    int parent[size];
    int max_flow = 0;

    while (bfs(rGraph, s, t, parent))
    {
        int flow = INT_MAX;
        for (j = t; j != s; j = parent[j])
        {
            i = parent[j];
            flow = min(flow, rGraph[i][j]);
        }

        for (j = t; j != s; j = parent[j])
        {
            i = parent[j];
            rGraph[i][j] -= flow;
            rGraph[j][i] += flow;
        }
        max_flow += flow;
    }
    return max_flow;
}



int main34(){

    ifstream fp;
    fp.open("C:\\Users\\Aspire\\IdeaProjects\\Algorytmy-Scala\\C++\\graph");
    if (!fp) {
        cout << "Error, file couldn't be opened" << endl;
        return 1;
    }
    int graph[size][size] ;
    for (int row = 0; row < size; row++) {
        for (int column = 0; column < size; column++) {
            fp >> graph[row][column];
        }
    }
fp.close();

   cout<<fordFulkerson(graph,2,4);
    return 0;

}
