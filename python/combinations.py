#
# combinations.py - generate lexicographic combinations of symbols
#
import time

#
# Algorithm Algorithm 7.2.1.3T TAOCP by Donald Knuth: Lexicographic Combinations
# This algorithm is like Algorithm L, but faster. It assumes, for convenience, t < n
#
# Return a list of digits from 1 to n taken t at a time, in lexicographic order
#
def combine(n, t):
  # T1. Initialize
  c = [i for i in range(t)]
  c.append(n)   # c[t] = n
  c.append(0)   # c[t+1] = 0
  j = t
  x = 0

  def findj():
    nonlocal x, j, c
    while (True):
      c[j-2] = j - 2
      x = c[j-1] + 1
      if x != c[j]:
        return j
      j = j + 1

  while j <= t:
    # T2. Visit. At this point j is the smallest index such that c[j+1] > j
    # Visit the combination ct ... c2 c1
    result = [c[i] for i in range(t)]

    if j > 0:
      x = j
      # T4. Increase c[j]
      c[j-1] = x
      j = j - 1
    # T3. Easy case?
    elif (c[0] + 1) < c[1]:
      c[0] = c[0] + 1
    else:
      j = 2
      j = findj()
      # T5. Terminate the algorithm if j > t
      if j <= t:
        c[j-1] = x
        j = j - 1
    yield result

for combination in combine(6, 3):
  print(combination)

millis = int(round(time.time() * 1000))
count = 0
for comb in combine(100, 5):
  count = count +1
now = int(round(time.time() * 1000))
print("Took %3.3f seconds to generate %d results" % ((now - millis) / 1000.0, count))