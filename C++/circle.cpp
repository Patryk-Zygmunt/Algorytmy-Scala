//
// Created by Aspire on 10/11/2019.
//

#include <random>
#include <iostream>
#include <omp.h>

#define RADIUS 4800
#define SHOOTS 1000000000

using namespace std;

bool  isInCircle(int r,int x, int y){
    return (r*r) >= ((x*x) + (y*y));
}

double circle(){
    int r = RADIUS, a = r*2;
    std::random_device rd;
    std::mt19937 mt(rd());
    double inCircle = 0;
    std::uniform_real_distribution<double> dist(0,r);
    int y=0, x=0;
    for (long i=0; i<SHOOTS; ++i){
        y = dist(mt);
        x = dist(mt);
        if(isInCircle(r,x,y))
            ++inCircle;
    }
    double pi = 4.0 * (inCircle / SHOOTS);
    cout<<pi<<"\n";;
}


double circleParallel(){
    int r = RADIUS, a = r*2;
    random_device rad;
    std::vector<std::default_random_engine> generators;
    for (int i = 0, N = omp_get_max_threads(); i < N; ++i) {
        generators.emplace_back(default_random_engine(rad()));
    }

    double inCircle = 0;
    int y=0, x=0;
#pragma omp parallel for reduction(+:inCircle)
    for (long i=0; i<SHOOTS; ++i){
        default_random_engine& engine = generators[omp_get_thread_num()];
        uniform_int_distribution<int> uniform_dist(0, r);
       int x =  uniform_dist(engine);
       int y =  uniform_dist(engine);
        if(isInCircle(r,x,y))
            ++inCircle;
    }
    double pi = 4.0 * (inCircle / SHOOTS);
    cout<<pi<<"\n";
}

int main(){
    double start;
    double end;
    start = omp_get_wtime();
    circleParallel();
    end = omp_get_wtime();
    cout<<(end - start)<<"\n";
    start = omp_get_wtime();
    circle();
    end = omp_get_wtime();
    cout<<(end - start)<<"\n";

    return 0;
}