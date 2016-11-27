import XCTest
@testable import Combinations

class CombinationsTests: XCTestCase {
    func testExample() {
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        XCTAssertEqual(Combinations().text, "Hello, World!")
    }


    static var allTests : [(String, (CombinationsTests) -> () throws -> Void)] {
        return [
            ("testExample", testExample),
        ]
    }
}
