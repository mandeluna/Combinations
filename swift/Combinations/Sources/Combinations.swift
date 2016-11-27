class Combinations : Sequence {

	let n: Int
	let k: Int

	public init(count: Int, choices: Int) {
		n = count
		k = choices
	}

	func makeIterator() -> CombinationIterator {
		return CombinationIterator(n, k)
	}
}