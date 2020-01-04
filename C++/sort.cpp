//
// Created by Aspire on 10/12/2019.
//

#include <iostream>
#include <omp.h>
#include <random>
#include <queue>
#include <cstring>


void swap(int* arr, int i, int j){
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
}

int  partSort(int* arr, int l, int h){
    int pivot = arr[h];
    int i = l;
    for (int j = l; j < h; j++) {
        if (arr[j] <= pivot) {
            swap(arr,i,j);
            ++i;
        }
    }
    swap(arr,i,h);
    return i;
}

void quicksort(int* arr, int l, int h){
        if(l<h) { ;

           int i = partSort(arr,l,h);
            quicksort(arr, l, i - 1);
            quicksort(arr, i + 1, h);
        }
}


void quickPart(int* arr, int size){
    const int threads = 4;
    int n = size/threads;
    int   parts[threads+1];
    std::queue <int> q;
    q.push(0);
    q.push(size-1);
    int idx = 0;
    parts[threads] = 0;
    parts[threads-1] = size-1;

    while (idx < threads-1){
        int s = q.front();
        q.pop();
        int e =  q.front();
        q.pop();

        parts[idx] = partSort(arr,s,e);
        if((idx %4 == 0 && parts[idx]!=s) || parts[idx]==e) {
            q.push(s);
            q.push(parts[idx]-1);
            q.push(parts[idx]-1);
            q.push(e);
        }
        else {
            q.push(parts[idx]+1);
            q.push(e);
            q.push(s);
            q.push(parts[idx]+1);

        }
       idx++;
    }

    quicksort(parts,0,threads);
//    for(int i = 0;i< threads+1;i++){
//        std::cout<<parts[i]<<",";
//    }
//    std::cout<<std::endl;
   for(int i=0;i<threads;i++){
       if(i%2==0)
           quicksort(arr,parts[i],parts[i+1]-1);
       else
       quicksort(arr,parts[i]+1,parts[i+1]);
    }

}


void quickPartParrel(int* arr, int size){
    const int threads = 4;
    int n = size/threads;
    int   parts[threads+1];
    std::queue <int> q;
    q.push(0);
    q.push(size-1);
    int idx = 0;
    parts[threads] = 0;
    parts[threads-1] = size-1;

    while (idx < threads-1){
        int s = q.front();
        q.pop();
        int e =  q.front();
        q.pop();

        parts[idx] = partSort(arr,s,e);
        if((idx %4 == 0 && parts[idx]!=s) || parts[idx]==e) {
            q.push(s);
            q.push(parts[idx]-1);
            q.push(parts[idx]-1);
            q.push(e);
        }
        else {
            q.push(parts[idx]+1);
            q.push(e);
            q.push(s);
            q.push(parts[idx]+1);

        }
        idx++;
    }

    quicksort(parts,0,threads);
//    for(int i = 0;i< threads+1;i++){
//        std::cout<<parts[i]<<",";
//    }
//    std::cout<<std::endl;
#pragma omp parallel
    {
        int i = omp_get_thread_num();
        if(i%2==0)
            quicksort(arr,parts[i],parts[i+1]-1);
        else
            quicksort(arr,parts[i]+1,parts[i+1]);
    }

}




int maineeeeee(){
    int size = 1000000 ;
    double start,end;
    int* arr = new int[size] ;//= {970,360,350,172,138,399,924,563,687,525};
    int* arr1 = new int[size] ;//= {970,360,350,172,138,399,924,563,687,525};
    int* arr2 = new int[size] ;//= {970,360,350,172,138,399,924,563,687,525};
    std::random_device rd;
    std::mt19937 mt(rd());
    std::uniform_real_distribution<double> dist(0,1000);
    for(int i = 0;i< size;i++){
        arr[i] = floor(dist(mt));
        arr1[i] = arr[i];
        arr2[i] = arr[i];
    }
    for(int i = 0;i< size;i++){
        std::cout<<arr[i]<<",";
    }
    std::cout<<std::endl;
    start = omp_get_wtime();
    quickPart(arr, size);
    end = omp_get_wtime();
    std::cout<<(end - start)<<"\n";
    start = omp_get_wtime();
    quicksort(arr1,0, size-1);
    end = omp_get_wtime();
    std::cout<<(end - start)<<"\n";
    start = omp_get_wtime();
    quickPartParrel(arr2, size);
    end = omp_get_wtime();
    std::cout<<(end - start)<<"\n";
    for(int i = 0;i< size;i++){
     //   std::cout<<arr2[i]<<",";
    }
}