//
// Created by Aspire on 10/12/2019.
//

#include <iostream>
#include <omp.h>
#include <random>

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

void qucikParrel(int* arr,int size){
    int threads = 4;
    int n = size/threads;
    int   parts[threads-1];
    int   split[threads];
    int idx = 0;
    for(int i=0;i<size-1;i=i+n){
        if(i+n >size-1){
            split[idx] = size-1;
        }
        else {
            split[idx] = i+n;
        }
        ++idx;
        }

    parts[idx] = partSort(arr, 0,split[1]);
    for(int i = 1;i<threads;i=i+2){
        parts[idx] = partSort(arr, split[i-1]+1,split[i]);
    }
    for(int i = 0;i< size;i++){
        std::cout<<arr[i]<<",";
    }
    std::cout<<std::endl;
    quicksort(arr,0,parts[0]-1);
    quicksort(arr,parts[0]+1,split[1]);
    quicksort(arr,split[1]+1,parts[1]-1);
    quicksort(arr,parts[1]+1,split[2]);
//    for(int i=1;i<threads;i++){
//       // quicksort(arr,split[i-1],parts[i]-1);
//        quicksort(arr,parts[i]+1,split[i]);
//    }

}


int main(){
    int size = 10;
    int arr[10] ;//= {970,360,350,172,138,399,924,563,687,525};
    std::random_device rd;
    std::mt19937 mt(rd());
    std::uniform_real_distribution<double> dist(0,1000);
    for(int i = 0;i< size;i++){
        arr[i] = floor(dist(mt));
    }
    for(int i = 0;i< size;i++){
        std::cout<<arr[i]<<",";
    }
    std::cout<<std::endl;
    qucikParrel(arr,size);
    //partSort(arr,0,size-1);
    for(int i = 0;i< size;i++){
        std::cout<<arr[i]<<",";
    }
}