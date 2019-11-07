#include <omp.h>
#include <iostream>

using namespace std {
	double fun(double x) {
		return 1 / (1 + x * x);

	}

	double d() {
		double a = 0, b = 1;
		double dx = 0.0000000000000000000000001, w = 0, m;
		m = (b - a) / dx;
		for (long i = 0; i < m; i++)
			w += fun(a + i * dx) * dx;

		return w * 4;

	}


	int main() {
		double a = 0, b = 1;
		double dx = 0.00000000000001, m;
		m = (b - a) / dx;
		int tab[4] = { 0,0,0,0 };
		#pragma omp parallel{
		double w = 0;
		int n = omp_get_num_threads();
		int k = omp_get_thread_num();

		for (long i = k; i < m; i + n)
			w += fun(a + i * dx) * dx;

		tab[k] = w;
		}

		// dzieli na ilość podproblemoów i sumuje
		#pragma omp parrel for reduction(+:sum)
		for (long i = 0; i < m; i++)
			sum += fun(a + i * dx) * dx;
		
		// wyliczyć pi z kwadratu i koła na podstawie ilości trafien w koło funkcja losowa z omp
		/// trzyw ersje liczenia całki 
		// przygotować jakiegoś quicksora na zajeciahc zrownologlić



		cout << tab;
		return 0;

	}

}
