#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "combinations.h"

#define BUFSIZE 256

char outputString[BUFSIZE];

void arrayToString(char *buffer, int *array, int k) {
	buffer[0] = '[';
	int index = 1;
	char tmp[BUFSIZE];
	tmp[0] = '\0';
	for (int i = 0; i < k; i++) {
		sprintf(tmp, "%d", array[i]);
		sprintf(&buffer[index], "%s", tmp);
		index += strlen(tmp);
		if (i < k - 1) {
			// use post-decrement to overwrite NUL character
			buffer[index++] = ',';
			buffer[index++] = ' ';
		}
	}
	buffer[index++] = ']';
	buffer[index++] = '\0';
}

int millis() {
	struct timespec time;
	clock_gettime(CLOCK_REALTIME, &time);
	return time.tv_sec * 1000 + time.tv_nsec / 1000000;
}

void time_combinations(int n, int k) {
	combination_t *comb = new_combination(n, k);
	long now = millis();
	int count = 0;
	int *result = malloc(sizeof(int) * k);
	while (has_next(comb)) {
		next(comb, result);
		count++;
	}
	free(result);
	printf("Took %ld ms to find %d results\n", millis() - now, count);
}

int main(int argc, char **argv) {
	if (argc != 3) {
		printf("Usage: combinations <n> <k>\n");
		return 0;
	}
	long n = strtol(argv[1], NULL, 0);
	long k = strtol(argv[2], NULL, 0);

	int *result = malloc(sizeof(int) * k);
	if (result < 0) {
		fprintf(stderr, "Unable to allocate %lu bytes\n", sizeof(int) * k);
		return -1;
	}

	combination_t *comb = new_combination(n, k);
	while (has_next(comb)) {
		next(comb, result);
		arrayToString(outputString, result, k);
		printf("%s\n", outputString);
	}
	free(result);

	time_combinations(100, 5);
}
