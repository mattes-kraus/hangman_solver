# hangman_solver
## Idea
Everybody knows the game [hangman](https://de.wikipedia.org/wiki/Galgenm%C3%A4nnchen) (=Galgenmänchen). Given the length of a word the user is thinking of, this solver proposes the best letter-guess.

## Run it on windows
- clone the repository
- eventually you have to create a folder bin on the same level as your cloned repo is
- in the cmd type: `javac -d ./bin ./hangman_solver/*.java`
- then type: `java -cp bin hangman_solver.MainClass`

## Manual
- type in how many letters the word has, e.g. if there are 5 blanks you can type whatever 5 letters come into your mind
- wait a second, then the engine will propose a letter. you click on each field matching the letter, then press commit
- do this until the word is found
