#include <omp.h>
#include <iostream>

using namespace std;

//i do 0 1 / (1 + x^2)
double fun(double x) {
	return (1 / (1 + x * x)) * 4 ;

}

double d() {
	double a = 0, b = 1;
	double dx = 0.0001,w=0,m;
	m = (b - a) / dx;
	for (long i = 0; i < m; i=i+1)
		w += fun(a + i * dx) * dx;

	return w;

}


int main67() {
 //  cout<< d();
	double a = 0, b = 1;
	double dx = 0.000001;
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


    double sum = 0;
#pragma omp parallel for reduction(+:sum)
    for (long i = 0; i < m; i++)
        sum += fun(a + i * dx) * dx;

    cout<<sum;



	return 0;

}

