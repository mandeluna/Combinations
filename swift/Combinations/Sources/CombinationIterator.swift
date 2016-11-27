class CombinationIterator : IteratorProtocol {

    var n, t, j : Int
    var x = 0
    var c : [Int]

    public init(_ count: Int, _ choices: Int) {
    	n = count
    	t = choices
    	c = [Int]()

    	// T1. Initialize
    	for i in 0...t - 1 {
    		c.append(i)
    	}
    	c.append(n)
    	c.append(0)
    	j = t
    }

    private func visit() -> [Int] {
    	var result = [Int]()
    	for i in 0...t - 1 {
    		result.append(c[i])
    	}
    	return result
    }

    private func findj() {
    	while (true) {
    		c[j - 2] = j - 2
    		x = c[j - 1] + 1
    		if x != c[j] { return }
    		j = j + 1
    	}
    }

    public func next() -> [Int]? {
    	guard j <= t
    		else { return nil }

    	let result = visit()
    	if j > 0 {
    		x = j
    		// T4. Increase c[j]
    		c[j - 1] = x
    		j = j - 1
    	}
    	// T3. Easy case?
    	else if c[0] + 1 < c[1] {
    		c[0] = c[0] + 1
    	}
    	else {
    		j = 2
    		findj()
    		// T5. Terminate the algorithm if j > t
    		if j <= t {
    			// T6. Increase c[j]
    			c[j - 1] = x
    			j = j - 1
    		}
    	}
    	return result
    }
}
