
typedef struct combination_t {
	int n, t, j, x;
	int *c;
} combination_t;

// Create a new combination iterator
combination_t *new_combination(int n, int k);

// Call this to see if more items can be retrieved
int has_next(combination_t *self);

// Retrieve the items
// buffer: must be allocated with room for k ints (see above)
void next(combination_t *self, int *buffer);