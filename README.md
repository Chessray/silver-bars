[![CircleCI](https://circleci.com/gh/Chessray/silver-bars.svg?style=svg)](https://circleci.com/gh/Chessray/silver-bars)
# silver-bars
Solution to an interview exercise called Silver Bars Marketplace.

# Development notes:
- The project requires Java 8.
- Build the code by running `./gradlew build` from the root directory.
- Some tests are using the [AssertJ Assertions Generator](http://joel-costigliola.github.io/assertj/assertj-assertions-generator.html). You will need to run the generator at least once before being able to run the tests in your IDE. You can do this either by running the explicit `./gradlew generateAssertJ` or the regular build as described above.
- You will need to install the Lombok [plugin](https://projectlombok.org/setup/overview) and enable Annotation Processing for your respective IDE.
