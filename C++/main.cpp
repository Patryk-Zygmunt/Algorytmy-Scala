#include <iostream>

#include<cstdio>
#include<vector>
#include <climits>

using namespace std;


void fill(int *tab, int v, int size) {
    for (int i = 0; i < size; i++)
        tab[i] = v;
}


int main3() {
    const int INF = INT_MAX;
    const int size = 6;
    int graph[size][size] = {
            {0, 5,  0,  0, 0, 0},
            {0, 0,  0,  3, 9, 0},
            {3, -4, 0,  0, 0, 0},
            {0, 0,  0,  0, 3, 2},
            {0, 0,  -1, 0, 0, -5},
            {9, 0,  8,  0, 0, 0}
    };
    int cost[size];
    int parent[size];

    fill(cost, INF, size);
    fill(parent, -1, size);
    cost[0] = 0;
    for (int n = 1; n <= size; n++) {
        for (int i = 0; i < size; i++) {
            for (int j = 0;
                 j < size; j++) // sprawdzamy, czy istnieje krawędź, ale dla której nie zachodzi Obserwacja 1.
            {
                if (graph[i][j] != 0 && cost[i] != INF && cost[i] + graph[i][j] <
                                                          cost[j]) //jeżeli koszt dotarcia do poprzedniego wierzchołka (+7) jest mniejszy niż koszt dostanie się do aktualnego wierzchołka
                {
                    cost[j] = cost[i] + graph[i][j];
                    parent[j] = i;

                    if (n ==
                        size) // jeżeli i dojdzie do n i wejdzie do tej pętli znaczy, że odkryliśmy cykl o ujemnej wadze
                    {
                        cout << "Cykl ujemny";
                        return 0;
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


    return 0;
}