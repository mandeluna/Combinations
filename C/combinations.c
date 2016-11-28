#include "combinations.h"
#include <stdlib.h>

#ifndef COMBINATIONS_H
#define COMBINATIONS_H

int has_next(combination_t *self) {
	return self->j <= self->t;
}

void visit(combination_t *self, int *buffer) {
	for (int i=0; i < self->t; i++) {
		buffer[i] = self->c[i];
	}
}

void findj(combination_t *self) {
	int *c = self->c;
	while (1) {
		int j = self->j;
		c[j-2] = j - 2;
		self->x = c[j-1] + 1;
		if (self->x != c[j]) {
			return;
		}
		self->j++;
	}
}

void next(combination_t *self, int *buffer) {
	visit(self, buffer);

	int j = self->j;
	int *c = self->c;

	if (j > 0) {
		self->x = j;
		// T4. Increase c[j]
		c[j-1] = self->x;
		self->j--;
	}
	// T3. Easy case?
	else if (c[0] + 1 < c[1]) {
		c[0]++;
	}
	else {
		self->j = 2;
		findj(self);
		// T5. Terminate the algorithm if j > t
		if (self->j <= self->t) {
			// T6. Increase c[j]
			c[self->j-1] = self->x;
			self->j--;
		}
	}
}

combination_t *new_combination(int n, int k) {
	combination_t *self = malloc(sizeof(combination_t));
	self->n = n;
	self->t = k;
	int *c = malloc(sizeof(int) * (n + 2));
	for (int i=0; i < k; i++) {
		c[i] = i;
	}
	c[k] = n;
	c[k+1] = 0;
	self->c = c;
	self->j = k;
	return self;
}

#endif