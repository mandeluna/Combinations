var combs = Combinations(count:6, choices:3)
for result in combs {
	print(result)
}

var now = millis()
var count = 0

for result in Combinations(count:100, choices:5) {
	count = count + 1
}
var delta = millis() - now
print("Took \(delta) ms to find \(count) results")