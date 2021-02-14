A simulation of http://brooker.co.za/blog/2012/11/13/increment.html

This is just a rough simulation, not a serious one(for example using JMH).

## Result
non_volatile time: 21ms, value 150000000

volatile time: 3012ms, value 71904714

atomic time: 2935ms, value 150000000

non_volatile_non_contention time: 10ms, value 150000000

volatile_non_contention time: 9ms, value 150000000

atomic_non_contention time: 800ms, value 150000000 

## Conclusion
When there is no contention, the volatile version is as fast as non_volatile version; otherwise when highly contention, volatile version is almost same as atomic version.
Even when no contention, atomic is slower.