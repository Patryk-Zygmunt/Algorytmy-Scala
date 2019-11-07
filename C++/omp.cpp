#include <omp.h>
#include <iostream>

//i do 0 1 / (1 + x^2)
double fun(double x) {
	return 1 / (1 + x * x);

}

double d() {
	double a = 0, b = 1;
	double dx = 0.0000000000000000000000001,w=0,m;
	m = (b - a) / dx;
	for (long i = 0; i < m; i++)
		w += fun(a + i * dx) * dx;

	return w*4;

}


int main() {
	double a = 0, b = 1;
	double dx = 0.00000000000001, w = 0, m;
	m = (b - a) / dx;
	int tab[4] = { 0,0,0,0 };
#pragma omp parallel{
	int n, k;
	n = omp_get_num_threads();
	k = omp_get_thread_num();

	for (long i = k; i < m; i+n)
		w += fun(a + i * dx) * dx;

	}
	std::cout << tab;
	return 0;

}

