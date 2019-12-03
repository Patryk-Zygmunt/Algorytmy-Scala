#include <omp.h>
#include <iostream>
#define  DXX 0.00000001

using namespace std;

//i do 0 1 / (1 + x^2)
double fun(double x) {
	return (1 / (1 + x * x)) * 4 ;

}

double d() {
	double a = 0, b = 1;
	double dx = DXX,w=0,m;
	m = (b - a) / dx;
	for (long i = 0; i < m; i=i+1)
		w += fun(a + i * dx) * dx;

	return w;

}


int main222() {
    double start;
    double end;
    start = omp_get_wtime();
    double ddd = d();
    end = omp_get_wtime();
    cout<<(end - start)<<"\n";

  cout<< ddd<<endl;
    start = omp_get_wtime();

    double a = 0, b = 1;
	double dx = DXX;
	long  m = (b - a) / dx;
	double tab[4] = { 0,0,0,0 };
#pragma omp parallel
	{
	int n, k;
	double w = 0;
	n = omp_get_num_threads();
	k = omp_get_thread_num();

	for (long i = k; i < m; i=i+n)
		w += fun(a + i * dx) * dx;

	tab[k] = w;
	};
    double w = 0;
    for (double x : tab)
        w+=x;
    cout << w;


    cout << "\n";
    end = omp_get_wtime();
    cout<<(end - start)<<"\n";
    start = omp_get_wtime();

    double sum = 0;
#pragma omp parallel for reduction(+:sum)
    for (long i = 0; i < m; i++)
        sum += fun(a + i * dx) * dx;

    cout<<sum<<endl;
    end = omp_get_wtime();
    cout<<(end - start)<<"\n";


	return 0;

}

