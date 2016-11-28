import Glibc

public let millis: () -> Int = {
	var time: timespec = timespec(tv_sec: 0, tv_nsec: 0)
	var error = Glibc.clock_gettime(CLOCK_REALTIME, &time)
	if error < 0 {
		var code = Int8(errno)
		print("clock_gettime() failed \(perror(&code))")
	}
	return time.tv_sec * 1000 + time.tv_nsec / 1000000
}

public func millisecondsToRun(_ closure: () -> Void) -> Int {
	let now = millis()
	closure()
	return millis() - now
}