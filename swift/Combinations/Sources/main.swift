var combs = Combinations(count:6, choices:3)
for result in combs {
	print(result)
}

var count = 0
var delta = millisecondsToRun({
	for result in Combinations(count:100, choices:5) {
		count = count + 1
	}
	})
print("Took \(delta) ms to find \(count) results")