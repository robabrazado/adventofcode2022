These are my solutions for the [Advent of Code](https://adventofcode.com/) 2022. I'm using Java as my language of choice and Eclipse as my IDE. This is my second year participating in Advent of Code.

Shoutout to the fun folks at the [Indie Game Reading Club](https://www.indiegamereadingclub.com/) alongside whom I'm doing the Advent of Code this year! Thanks for the support and encouragement!

---

Each day's solution works independently from other days, though all days use the InputGetter class, which basically just reads the puzzle input from a local file and stuffs it into a String array of lines. Each day's work is in its own package, and the entry point for each day's solution will be the DayXXCli class, where XX is the left-zero-padded number of the day (e.g. "Day01Cli").

Days 1, 2, and 3 kind of got off to a ramshackle start. They're effectively just everything stuck into a "main" function, and the error handling is particularly gnarly. Don't judge. For one-off stuff like this, I use runtime exceptions as "asserts" so that in cases where my preconditions aren't met, I'll error out instead of returning bad data. I made the decision that for day 4 and beyond, I'll shoot for more strictly object-oriented design and maybe cleaner error handling. I considered rewriting days 1, 2, and 3 in light of this decision, but I figure for purposes of the challenge, once stars are earned I just close the door on that particular day.

Update: Day 16 is a disaster structurally, and part 2 especially is *not* a good (read: fast) solution. I rewrote part 1 like five or six times, then couldn't adapt my solution to part 2, so basically started from scratch, but it eventually finished and got an answer, so I'm putting it behind me. The whole debacle set me back like four days.

Lastly, I'll just say that I'm not participating in Advent of Code competitively. I like coding to solve problems, but I'm not going to do it fast, and I'm *definitely* not looking to lose any sleep over it. Happy trails!
