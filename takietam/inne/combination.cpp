#include <iostream>
#include <algorithm>

using namespace std;

void comb(int x[], int size) {
	sort(x, x + size);
		do {
			for(size_t i = 0; i < size; ++i) {
				cout << x[i] << (i+1!=size?" ":"\n");
			}
		} while(next_permutation(x, x + size));
}

int main(void) {
	cout << "Enter a size of array >> ";
	size_t size;
	cin >> size;
	int x[size];
	cout << "Enter a array >> ";
	for(size_t i = 0; i < size; i++) {
		cin >> x[i];
	}
	for(size_t i = 0; i <= size; i++) {
		comb(x, i);
	}
}
