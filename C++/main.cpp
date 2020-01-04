#include <iostream>

#include<cstdio>
#include<vector>
#include <climits>
using namespace std;

#define size 6
#define edges 11
//#define INF INT_MAX;




void fill(int tab[size], int v) {
    for (int i = 0; i < size; i++)
        tab[i] = v;
}

void bl(int graph[size][size]){
    int cost[size];
    int parent[size];

    fill(cost, INT_MAX) ;
    fill(parent, -1);
    cost[0] = 0;
    for (int n = 1; n <= size; n++) {
        for (int i = 0; i < size; i++) {
            for (int j = 0;
                 j < size; j++)
            {
                if (graph[i][j] != 0 && cost[i] != INT_MAX && cost[i] + graph[i][j] <
                                                          cost[j])
                {
                    cost[j] = cost[i] + graph[i][j];
                    parent[j] = i;

                    if (n ==size)
                    {
                        cout << "Cykl ujemny";
                        return ;
                    }
                }
            }
        }
    }

    for (int i : cost)
        cout << i << ", ";

    cout << "\n";

    for (int i : parent)
        cout << i << ", ";

}

void blflist(int    graph[size][size]){
    int cost[size];
    int parent[size];

    fill(cost, INT_MAX) ;
    fill(parent, -1);
    cost[0] = 0;
    int lst[edges][3];
    int idx= 0;
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (graph[i][j] != 0) {
                lst[idx][0] = i;
                lst[idx][1] = j;
                lst[idx][2] = graph[i][j];
                ++idx;
            }
        }
    }
        for (int i = 0; i <= size; i++)
        for (int n = 1; n <= size; n++) {
            for (int e = 0; e < edges; e++) {
                int from = lst[e][0];
                int to = lst[e][1];
                int costE = lst[e][2];
                if ( cost[from] != INT_MAX && cost[from] + costE < cost[to])
                {
                    cost[to] = cost[from] + costE;
                    parent[to] = from;

                    if (i ==size)
                    {
                        cout << "Cykl ujemny";
                        return ;
                    }
                }
        }
        }
    for (int i : cost)
        cout << i << ", ";


    cout << "\n";

    for (int i : parent)
        cout << i << ", ";

    cout << "\n";
    }


int muuuuain() {

    int graph[size][size] = {
            {0, 5,  0,  0, 0, 0},
            {0, 0,  0,  3, 9, 0},
            {3, -4, 0,  0, 0, 0},
            {0, 0,  0,  0, 3, 2},
            {0, 0,  -1, 0, 0, -5},
            {9, 0,  8,  0, 0, 0}
    };

blflist(graph);
bl(graph);
    return 0;
}